package br.com.south.batch.sales.job.initializer;

import br.com.south.batch.sales.config.BatchProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static br.com.south.batch.sales.job.constants.SalesReportConstants.*;
import static br.com.south.batch.sales.utils.message.MessageCodeEnum.*;
import static br.com.south.batch.sales.utils.message.MessageUtils.getMessage;


@Component
@ConditionalOnProperty(name = "sales.batch.salesReport.enabled", havingValue = "true")
public class SalesReportFromFileJobInitializer implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesReportFromFileJobInitializer.class);


    private final JobLauncher launcher;
    private final BatchProperties batchProperties;
    private final Job salesReportJob;

    public SalesReportFromFileJobInitializer(
            JobLauncher launcher,
            BatchProperties batchProperties,
            @Qualifier(JOB_BUILDER_JOB_NAME) Job salesReportJob) {
        this.launcher = launcher;
        this.batchProperties = batchProperties;
        this.salesReportJob = salesReportJob;
    }

    private Path getCompletePath(String path)
    {
        return Paths.get(System.getProperty(USER_HOME)+ path);
    }

    @Async
    @Override
    public void run(ApplicationArguments arg0) {
        final BatchProperties.PathStream input = batchProperties.getSalesReport().getInput();


        final Path inputPath = getCompletePath(input.getPath());
        LOGGER.info(getMessage(FILE_WATCHER, inputPath.normalize().toString()));

        try {
            Files.createDirectories(inputPath);
            scanAlreadyExistentFiles(inputPath);


            final WatchService watchService = FileSystems.getDefault().newWatchService();
            inputPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    LOGGER.info(getMessage(FILE_CREATION_DETECTED, event.context()));

                    startJob(inputPath.resolve((Path) event.context()).toAbsolutePath());
                    key.reset();
                }
            }
        } catch (IOException e) {
            LOGGER.error(getMessage(ERROR_IO_EXCEPTION, e.getMessage()));
        } catch (JobParametersInvalidException e) {
            LOGGER.error(getMessage(ERROR_JOB_PARAMETERS_INVALID_EXCEPTION, e.getMessage()));
        } catch (JobExecutionAlreadyRunningException e) {
            LOGGER.error(getMessage(ERROR_JOB_EXECUTION_RUNNING_EXCEPTION, e.getMessage()));
        } catch (JobRestartException e) {
            LOGGER.error(getMessage(ERROR_JOB_RESTART_EXCEPTION, e.getMessage()));
        } catch (JobInstanceAlreadyCompleteException e) {
            LOGGER.error(getMessage(ERROR_JOB_ALREADY_COMPLETE_EXCEPTION, e.getMessage()));
        } catch (InterruptedException e) {
            LOGGER.error(getMessage(ERROR_INTERRUPTED_EXCEPTION, e.getMessage()));
            Thread.currentThread().interrupt();
        }
    }

    private void scanAlreadyExistentFiles(Path inputPath) throws IOException, JobParametersInvalidException,
            JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(inputPath)) {
            for (Path path : directoryStream) {
                LOGGER.info(getMessage(FILE_EXISTENT_DETECTED, path.getFileName()));
                startJob(path.toAbsolutePath());
            }
        }
    }

    private void startJob(final Path path)
            throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException {
        final BatchProperties.PathStream output = batchProperties.getSalesReport().getOutput();
        launcher.run(
                salesReportJob,
                new JobParametersBuilder()
                        .addString(START_DATE, LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                        .addString(FILENAME, path.getFileName().normalize().toString())
                        .addString(FILE_INPUT_ABSOLUTE_PATH, path.normalize().toString())
                        .addString(FILE_OUTPUT_ABSOLUTE_PATH, getCompletePath(output.getPath()).normalize().toString())
                        .toJobParameters());
    }
}

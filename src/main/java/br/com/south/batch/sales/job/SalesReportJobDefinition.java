package br.com.south.batch.sales.job;


import br.com.south.batch.sales.config.BatchProperties;
import br.com.south.batch.sales.job.processor.SalesReportFileProcessor;
import br.com.south.batch.sales.job.reader.SalesReportFileItemReader;
import br.com.south.batch.sales.job.writer.SalesReportFileWriter;
import br.com.south.batch.sales.model.dto.SalesReportResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static br.com.south.batch.sales.job.constants.SalesReportConstants.*;
import static br.com.south.batch.sales.utils.message.MessageCodeEnum.*;
import static br.com.south.batch.sales.utils.message.MessageUtils.getMessage;


@Component
public class SalesReportJobDefinition extends JobExecutionListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesReportJobDefinition.class);

    private final JobBuilderFactory jobBuilderFactory;
    private final BatchProperties batchProperties;
    private final StepBuilderFactory stepBuilderFactory;
    private final SalesReportFileItemReader reader;
    private final SalesReportFileProcessor processor;
    private final SalesReportFileWriter writer;

    public SalesReportJobDefinition(JobBuilderFactory jobBuilderFactory,
                                                BatchProperties batchProperties,
                                                StepBuilderFactory stepBuilderFactory,
                                    SalesReportFileItemReader reader,
                                    SalesReportFileProcessor processor,
                                    SalesReportFileWriter writer) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.batchProperties = batchProperties;
        this.stepBuilderFactory = stepBuilderFactory;
        this.reader = reader;
        this.processor = processor;
        this.writer = writer;
    }

    @Bean(name = "salesReportJob")
    public Job loanPaymentCompensation() {
        final BatchProperties.BatchJobConfig salesReport = this.batchProperties.getSalesReport();

        final Step main =
                stepBuilderFactory
                        .get(JOB_BUILDER_STEP_MAIN_NAME)
                        .<File, SalesReportResultDTO>chunk(salesReport.getChunkSize().intValue())
                        .reader(reader)
                        .processor(processor)
                        .writer(writer)
                        .faultTolerant().retryLimit(salesReport.getRetryLimit().intValue()).retry(Exception.class)
                        .build();

        return jobBuilderFactory
                .get(JOB_BUILDER_FACTORY_NAME)
                .incrementer(new RunIdIncrementer())
                .listener(this)
                .start(main).on(BatchStatus.COMPLETED.name()).end()
                .from(main).on(BatchStatus.FAILED.name()).fail().end()
                .build();
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        LOGGER.info(getMessage(JOB_EXECUTION_START, jobExecution.getJobInstance().getJobName(), jobExecution.getJobParameters()));
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        final String filename = jobExecution.getJobParameters().getString(FILENAME);
        final String filePath = jobExecution.getJobParameters().getString(FILE_INPUT_ABSOLUTE_PATH);
        final Path inputFile = Paths.get(filePath);
        final String outputPath = jobExecution.getJobParameters().getString(FILE_OUTPUT_ABSOLUTE_PATH);
        final BatchProperties.BatchJobStreamConfig salesReport = this.batchProperties.getSalesReport();
        final String newFileName = filename.replace(salesReport.getFileExtension(), salesReport.getProcessedExtension());

        try {
            Files.createDirectories(Paths.get(outputPath));
            Files.move(inputFile, Paths.get(outputPath, newFileName));
        } catch (IOException e) {
            LOGGER.error(getMessage(ERROR_IO_EXCEPTION));
        }

        LOGGER.info(getMessage(JOB_EXECUTION_FINISH, jobExecution.getJobInstance().getJobName(), jobExecution.getStatus()));
    }
}

package br.com.south.batch.sales.job.writer;

import br.com.south.batch.sales.config.BatchProperties;
import br.com.south.batch.sales.exceptions.SalesReportException;
import br.com.south.batch.sales.model.dto.SalesReportResultDTO;
import br.com.south.batch.sales.service.SalesReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static br.com.south.batch.sales.job.constants.SalesReportConstants.FILENAME;
import static br.com.south.batch.sales.job.constants.SalesReportConstants.FILE_OUTPUT_ABSOLUTE_PATH;
import static br.com.south.batch.sales.utils.message.MessageCodeEnum.ERROR_SALES_REPORT;
import static br.com.south.batch.sales.utils.message.MessageCodeEnum.INFO_SALES_REPORT_RESULT_CREATED;
import static br.com.south.batch.sales.utils.message.MessageUtils.getMessage;

@Component
@StepScope
public class SalesReportFileWriter implements ItemWriter<SalesReportResultDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesReportFileWriter.class);
    private final SalesReportService service;
    private final BatchProperties batchProperties;

    @Value("#{jobParameters}")
    private Map<String, JobParameter> jobParameters;

    public SalesReportFileWriter(SalesReportService service, BatchProperties batchProperties) {
        this.service = service;
        this.batchProperties = batchProperties;
    }


    @Override
    public void write(List<? extends SalesReportResultDTO> list) throws SalesReportException {

        for (SalesReportResultDTO salesReportResult : list) {
            try {

                final BatchProperties.BatchJobStreamConfig salesReport = this.batchProperties.getSalesReport();
                final String filename = (String) jobParameters.get(FILENAME).getValue();
                final String outputPath = (String) jobParameters.get(FILE_OUTPUT_ABSOLUTE_PATH).getValue();
                Files.createDirectories(Paths.get(outputPath));

                final String resultFileName = filename.replace(salesReport.getFileExtension(), salesReport.getResultExtension());
                final Path fileOutputPath = Paths.get(outputPath, resultFileName);

                new ObjectMapper().writeValue(new File(fileOutputPath.normalize().toString()), salesReportResult);

                LOGGER.info(getMessage(INFO_SALES_REPORT_RESULT_CREATED, filename, fileOutputPath.normalize().toString()));
            } catch (Exception error) {
                throw new SalesReportException(getMessage(ERROR_SALES_REPORT, error), error);
            }

        }

    }
}

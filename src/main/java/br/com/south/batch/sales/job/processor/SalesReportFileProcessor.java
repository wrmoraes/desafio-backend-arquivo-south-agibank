package br.com.south.batch.sales.job.processor;

import br.com.south.batch.sales.exceptions.SalesReportException;
import br.com.south.batch.sales.model.dto.SalesReportInputDTO;
import br.com.south.batch.sales.model.dto.SalesReportResultDTO;
import br.com.south.batch.sales.service.SalesReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.io.File;

import static br.com.south.batch.sales.utils.file.FileUtil.fileToBase64String;
import static br.com.south.batch.sales.utils.message.MessageCodeEnum.ERROR_SALES_REPORT;
import static br.com.south.batch.sales.utils.message.MessageUtils.getMessage;

@Component
public class SalesReportFileProcessor implements ItemProcessor<File, SalesReportResultDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesReportFileProcessor.class);
    private final SalesReportService service;

    public SalesReportFileProcessor(SalesReportService service) {
        this.service = service;
    }

    @Override
    public SalesReportResultDTO process(File file) throws SalesReportException {
        try {
            SalesReportInputDTO inputObject = service.convertBase64ToSalesReportDTO(fileToBase64String(file));
            SalesReportResultDTO resultObject = service.processSalesReport(inputObject);

            return resultObject;
        } catch (Exception e) {
            throw new SalesReportException(getMessage(ERROR_SALES_REPORT, e.getMessage(), file), e);
        }
    }
}

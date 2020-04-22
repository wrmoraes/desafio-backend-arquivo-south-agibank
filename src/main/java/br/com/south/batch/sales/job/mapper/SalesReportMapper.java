package br.com.south.batch.sales.job.mapper;
import br.com.south.batch.sales.converter.PositionalFileMapper;
import br.com.south.batch.sales.exceptions.SalesReportException;
import br.com.south.batch.sales.model.dto.SalesReportInputDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static br.com.south.batch.sales.utils.message.MessageCodeEnum.*;
import static br.com.south.batch.sales.utils.message.MessageUtils.getMessage;

public class SalesReportMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(SalesReportMapper.class);

    private SalesReportMapper() {
        throw new IllegalArgumentException(getMessage(ERROR_UTILITY_CLASS));
    }

    public static SalesReportInputDTO decodeFromBase64(final String base64) throws SalesReportException {
        try {
            final SalesReportInputDTO deserializer = PositionalFileMapper.deserializer(base64, SalesReportInputDTO.class);
            LOGGER.info(getMessage(INFO_SALES_REPORT_DECODED, deserializer));
            return deserializer;
        } catch (Exception e) {
            throw new SalesReportException(getMessage(ERROR_SALES_REPORT_DECODE, e.getMessage()), e);
        }
    }
}

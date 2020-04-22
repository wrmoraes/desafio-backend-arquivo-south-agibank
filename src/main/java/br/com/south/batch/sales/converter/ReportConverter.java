package br.com.south.batch.sales.converter;

import br.com.south.batch.sales.converter.reports.SalesConverter;

import static br.com.south.batch.sales.utils.message.MessageCodeEnum.ERROR_UTILITY_CLASS;
import static br.com.south.batch.sales.utils.message.MessageUtils.getMessage;

public class ReportConverter {
    private ReportConverter() {
        throw new IllegalArgumentException(getMessage(ERROR_UTILITY_CLASS));
    }

    public static SalesConverter getSalesReportConverter(){
        return new SalesConverter();
    }


}

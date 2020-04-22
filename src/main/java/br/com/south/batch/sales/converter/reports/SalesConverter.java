package br.com.south.batch.sales.converter.reports;

import br.com.south.batch.sales.exceptions.SalesReportException;
import br.com.south.batch.sales.job.mapper.SalesReportMapper;
import br.com.south.batch.sales.model.dto.SalesReportInputDTO;

public class SalesConverter{
    public SalesReportInputDTO convertToSalesReport(final String base64) throws SalesReportException{
        return SalesReportMapper.decodeFromBase64(base64);
    }
}

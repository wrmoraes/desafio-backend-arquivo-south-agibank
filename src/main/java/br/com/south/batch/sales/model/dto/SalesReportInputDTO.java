package br.com.south.batch.sales.model.dto;

import br.com.south.batch.sales.annotation.PositionalLineIdentifier;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class SalesReportInputDTO implements Serializable {

    @PositionalLineIdentifier(linesStartingWithChar = "001", delimiter = "ç")
    private List<SalesReportInputSalesmanDTO> sellers;
    @PositionalLineIdentifier(linesStartingWithChar = "002", delimiter = "ç")
    private List<SalesReportInputCustomerDTO> customers;
    @PositionalLineIdentifier(linesStartingWithChar = "003", delimiter = "ç")
    private List<SalesReportInputSaleDTO> sales;

}

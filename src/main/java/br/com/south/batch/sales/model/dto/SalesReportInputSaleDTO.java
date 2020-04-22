package br.com.south.batch.sales.model.dto;

import br.com.south.batch.sales.annotation.PositionalField;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import static br.com.south.batch.sales.annotation.PositionalField.PositionalFileFormat.*;

@Getter
@Setter
@ToString
public class SalesReportInputSaleDTO implements Serializable {
    @PositionalField(position = 1, type = FIXED, description = "Identificação do Registro Venda: 003", fixedValue = "003")
    private String registerType;
    @PositionalField(position = 2, description = "Id da Venda", type = NUMBER)
    private BigDecimal id;
    @PositionalField(position = 3, description = "Itens da venda", type = LIST, listStart = "[", listEnd =  "]", listDelimiter = ",", listItemDelimiter = "-")
    private List<SalesReportInputSaleItemDTO> items;
    @PositionalField(position = 4, description = "Vendedor")
    private String seller;

    @Getter(lazy = true) private final double totalSale = calculateTotalSale();

    private double calculateTotalSale() {
        return this.getItems()
                .stream()
                .mapToDouble(item-> item.getTotalItem())
                .sum();
    }
}


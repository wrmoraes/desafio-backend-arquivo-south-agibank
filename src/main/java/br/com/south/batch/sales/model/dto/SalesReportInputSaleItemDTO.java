package br.com.south.batch.sales.model.dto;

import br.com.south.batch.sales.annotation.PositionalField;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

import static br.com.south.batch.sales.annotation.PositionalField.PositionalFileFormat.NUMBER;

@Getter
@Setter
@ToString
public class SalesReportInputSaleItemDTO implements Serializable {
    @PositionalField(position = 1, description = "Id do item vendido")
    private String id;
    @PositionalField(position = 2, description = "Quantidade de Itens Vendidos", type = NUMBER)
    private BigDecimal quantity;
    @PositionalField(position = 3, description = "Preço do item", type = NUMBER)
    private BigDecimal price;
    @Getter(lazy = true) private final double totalItem = calculateTotalItem();

    private double calculateTotalItem() {
        return this.quantity.multiply(this.price).doubleValue();
    }
}

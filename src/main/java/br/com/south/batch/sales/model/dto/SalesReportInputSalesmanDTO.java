package br.com.south.batch.sales.model.dto;

import br.com.south.batch.sales.annotation.PositionalField;
import br.com.south.batch.sales.model.enumeration.SalesmanNameEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

import static br.com.south.batch.sales.annotation.PositionalField.PositionalFileFormat.FIXED;
import static br.com.south.batch.sales.annotation.PositionalField.PositionalFileFormat.NUMBER;

@Getter
@Setter
@ToString
public class SalesReportInputSalesmanDTO implements Serializable {
    @PositionalField(position = 1, type = FIXED, description = "Identificação do Registro Vendedor: 001", fixedValue = "001")
    private String registerType;
    @PositionalField(position = 2, description = "CPF do Vendedor")
    private String cpf;
    @PositionalField(position = 3, description = "Nome do Vendedor")
    private String name;
    @PositionalField(position = 4, description = "Salário do Vendedor", type = NUMBER)
    private BigDecimal salary;
}


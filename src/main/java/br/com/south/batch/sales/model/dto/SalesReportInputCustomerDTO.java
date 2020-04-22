package br.com.south.batch.sales.model.dto;

import br.com.south.batch.sales.annotation.PositionalField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

import static br.com.south.batch.sales.annotation.PositionalField.PositionalFileFormat.FIXED;

@Getter
@Setter
@ToString
public class SalesReportInputCustomerDTO implements Serializable {
    @PositionalField(position = 1, type = FIXED, description = "Identificação do Registro Cliente: 002", fixedValue = "002")
    private String registerType;
    @PositionalField(position = 2, description = "CNPJ do cliente")
    private String cnpj;
    @PositionalField(position = 3, description = "Nome do cliente")
    private String name;
    @PositionalField(position = 4, description = "Área de negócio do cliente")
    private String businessArea;

}


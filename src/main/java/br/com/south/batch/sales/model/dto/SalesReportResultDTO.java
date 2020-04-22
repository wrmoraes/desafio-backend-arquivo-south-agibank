package br.com.south.batch.sales.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
public class SalesReportResultDTO implements Serializable {
    @JsonProperty("quantidadeClientes")
    private BigDecimal customersNum;
    @JsonProperty("quantidadeVendedores")
    private BigDecimal sellersNum;
    @JsonProperty("piorVendedor")
    private String worstSeller;
    @JsonProperty("vendaMaisCara")
    private BigDecimal mostExpensiveSale;
}

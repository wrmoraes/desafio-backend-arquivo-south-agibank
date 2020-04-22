package br.com.south.batch.sales.model.enumeration;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SalesmanNameEnum {
    PEDRO,
    PAULO,
    @JsonEnumDefaultValue UNKNOWN;
}

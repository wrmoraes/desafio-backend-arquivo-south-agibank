package br.com.south.batch.sales.model.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LineTypeEnum {
    SALESMAN("001"),
    CUSTOMER("002"),
    SALE("003");

    private final String code;

    @Override
    public String toString() {
        return code ;
    }
}

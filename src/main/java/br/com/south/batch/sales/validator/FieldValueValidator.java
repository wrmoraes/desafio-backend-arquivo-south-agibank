package br.com.south.batch.sales.validator;

import br.com.south.batch.sales.annotation.PositionalField;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Field;

import static br.com.south.batch.sales.annotation.PositionalField.PositionalFileFormat.FIXED;
import static br.com.south.batch.sales.utils.message.MessageCodeEnum.ERROR_FIELD_FIXED_INVALID;
import static br.com.south.batch.sales.utils.message.MessageCodeEnum.ERROR_FIELD_FIXED_REQUIRED;
import static br.com.south.batch.sales.utils.message.MessageUtils.getMessage;


public class FieldValueValidator {
    private FieldValueValidator() {
    }

    public static void validate(Field field) {
        PositionalField annotation = field.getAnnotation(PositionalField.class);

        if (FIXED.equals(annotation.type())){
            Assert.notNull(
                annotation.fixedValue(),
                getMessage(ERROR_FIELD_FIXED_REQUIRED, field.getName(), field.getDeclaringClass()));
            }

        if (!StringUtils.isBlank(annotation.fixedValue())) {
            Assert.isTrue(
                annotation.type().equals(FIXED),
                getMessage(ERROR_FIELD_FIXED_INVALID, annotation.type(), field.getDeclaringClass(), field.getName(), FIXED.name())
            );
        }
    }
}

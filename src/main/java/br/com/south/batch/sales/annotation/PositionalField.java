package br.com.south.batch.sales.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static br.com.south.batch.sales.annotation.PositionalField.PositionalFileFormat.TEXT;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface PositionalField {
    int position();

    String description();

    String fixedValue() default "";

    String listDelimiter() default "";

    String listItemDelimiter() default "";

    String listStart() default "";

    String listEnd() default "";

    PositionalField.PositionalFileFormat type() default TEXT;

    enum PositionalFileFormat {
        TEXT,
        NUMBER,
        FIXED,
        LIST;
    }
}

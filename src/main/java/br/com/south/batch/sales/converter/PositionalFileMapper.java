package br.com.south.batch.sales.converter;

import br.com.south.batch.sales.exceptions.PositionalDeserializerException;

import java.io.File;

import static br.com.south.batch.sales.utils.message.MessageCodeEnum.ERROR_UTILITY_CLASS;
import static br.com.south.batch.sales.utils.message.MessageUtils.getMessage;


public class PositionalFileMapper {
    private PositionalFileMapper() {
        throw new IllegalArgumentException(getMessage(ERROR_UTILITY_CLASS));
    }

    public static <T> T deserializer(File file, Class<T> clazz) throws PositionalDeserializerException {
        return PositionalFileDeserializer.apply(file, clazz);
    }

    public static <T> T deserializer(final String base64, Class<T> clazz) throws PositionalDeserializerException {
        return PositionalFileDeserializer.apply(base64, clazz);
    }

}

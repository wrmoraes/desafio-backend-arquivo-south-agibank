package br.com.south.batch.sales.utils.file;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import java.io.File;
import java.io.IOException;

import static br.com.south.batch.sales.utils.message.MessageCodeEnum.*;
import static br.com.south.batch.sales.utils.message.MessageUtils.getMessage;

public class FileHelper {

    private FileHelper() {
        throw new IllegalArgumentException(getMessage(ERROR_UTILITY_CLASS));
    }

    public static File createTmpTextFileFromBase64(final String base64)
        throws IOException, IllegalAccessException {

        try {

            if (StringUtils.isBlank(base64)) {
                throw new IllegalAccessException(getMessage(ERROR_PARAMETER_BASE64_CANNOT_BE_NULL));
            }

            if (!Base64.isBase64(base64)) {
                throw new IllegalAccessException(getMessage(ERROR_PARAMETER_BASE64_IS_INVALID));
            }

            // write in disk.
            File file = File.createTempFile("/tmp", ".txt");
            byte[] bytes = Base64.decodeBase64(base64);


            FileUtils.writeByteArrayToFile(file, bytes);
            return file;
        } catch (IOException e) {
            throw new IOException(e.getCause());
        }
    }

}

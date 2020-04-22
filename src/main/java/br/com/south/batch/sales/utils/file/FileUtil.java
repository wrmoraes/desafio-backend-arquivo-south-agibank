package br.com.south.batch.sales.utils.file;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    private static final String DAT_FILE_EXT = ".dat";

    private FileUtil() {
    }

    public static String fileToBase64String(final File file) throws IOException {
        return Base64Utils.encodeToString(FileUtils.readFileToByteArray(file));
    }

    public static boolean isDatFile(String fileName) {
        return !StringUtils.isBlank(fileName) && fileName.endsWith(DAT_FILE_EXT);
    }
}

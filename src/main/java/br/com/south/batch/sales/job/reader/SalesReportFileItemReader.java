package br.com.south.batch.sales.job.reader;

import br.com.south.batch.sales.utils.file.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static br.com.south.batch.sales.utils.message.MessageCodeEnum.SALES_REPORT_READER;
import static br.com.south.batch.sales.utils.message.MessageUtils.getMessage;

@Component
@StepScope
public class SalesReportFileItemReader implements ItemReader<File> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesReportFileItemReader.class);

    private List<File> files;
    private int nextIndex;

    public SalesReportFileItemReader(@Value("#{jobParameters[fileInputAbsolutePath]}") String fileAbsolutePath) {
        this.files = FileUtil.isDatFile(fileAbsolutePath) ?
                Collections.singletonList(new File(fileAbsolutePath)) :
                Collections.emptyList();

        this.nextIndex = 0;
    }

    @Override
    public File read() {
        File file = null;

        if (this.nextIndex < this.files.size()) {
            file = this.files.get(this.nextIndex);
            this.nextIndex++;

            LOGGER.info(getMessage(SALES_REPORT_READER, nextIndex, files.size(), file.getName()));
        }

        return file;
    }
}

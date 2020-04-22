package br.com.south.batch.sales.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "sales.batch")
@Getter
@Setter
public class BatchProperties {
    private BatchJobStreamConfig salesReport = new BatchJobStreamConfig();

    @Getter
    @Setter
    public static class BatchJobStreamConfig extends BatchJobConfig {
        private PathStream input;
        private PathStream output;
        private String fileExtension;
        private String processedExtension;
        private String resultExtension;
    }

    @Getter
    @Setter
    public static class PathStream {
        private String path;
    }
    @Getter
    @Setter
    public static class BatchJobConfig {
        private Boolean enabled;
        private String cron;
        private Long chunkSize;
        private Long retryLimit;
    }

}

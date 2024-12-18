package com.tdtu.logistics_files_storage_service.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "aws.s3")
public class S3ConfigProperties {

    private String region;

    private String accessKey;

    private String secretKey;

    private String bucketName;

}

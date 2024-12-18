package com.tdtu.logistics_files_storage_service.configuration;

import com.tdtu.logistics_files_storage_service.configuration.properties.S3ConfigProperties;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class S3Config {

    S3ConfigProperties s3ConfigProperties;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider
                        .create(AwsBasicCredentials.create(
                                s3ConfigProperties.getAccessKey(),
                                s3ConfigProperties.getSecretKey())
                        ))
                .region(Region.of(s3ConfigProperties.getRegion()))
                .build();
    }
}

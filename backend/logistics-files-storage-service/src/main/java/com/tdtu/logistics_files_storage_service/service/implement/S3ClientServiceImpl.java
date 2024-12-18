package com.tdtu.logistics_files_storage_service.service.implement;

import com.tdtu.logistics_files_storage_service.configuration.properties.S3ConfigProperties;
import com.tdtu.logistics_files_storage_service.dto.response.FileRecordResponse;
import com.tdtu.logistics_files_storage_service.entity.FileRecord;
import com.tdtu.logistics_files_storage_service.exception.AppException;
import com.tdtu.logistics_files_storage_service.exception.ErrorCode;
import com.tdtu.logistics_files_storage_service.repository.FileRecordRepository;
import com.tdtu.logistics_files_storage_service.service.FileRecordService;
import com.tdtu.logistics_files_storage_service.service.S3ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ClientServiceImpl implements S3ClientService {

    private final S3Client s3Client;

    private final S3ConfigProperties s3ConfigProperties;

    private final FileRecordService fileRecordService;

    private final FileRecordRepository fileRecordRepository;

    @Override
    public ResponseInputStream<GetObjectResponse> getFile(String filePath) {

        try {
            return s3Client.getObject(GetObjectRequest.builder()
                    .bucket(s3ConfigProperties.getBucketName())
                    .key(filePath)
                    .build());

        } catch (Exception e) {
            log.error("Download Error for file {} in bucket {}: {}",
                    filePath, s3ConfigProperties.getBucketName(), e.getMessage(), e);

            throw new AppException(ErrorCode.S3_DOWNLOAD_FAILED);
        }
    }

    @Override
    public FileRecordResponse uploadFile(String filePath, File file, ObjectCannedACL cannedAccessControlList) {

        log.info("Uploading file {} to bucket {}", filePath, s3ConfigProperties.getBucketName());

        try {
            PutObjectResponse response = s3Client.putObject(PutObjectRequest.builder()
                    .bucket(s3ConfigProperties.getBucketName())
                    .key(filePath)
                    .acl(cannedAccessControlList)
                    .build(), RequestBody.fromFile(file));

            log.info("File uploaded successfully with eTag: {}", response.eTag());

            FileRecord fileRecord = FileRecord.builder()
                    .taskId("later")
                    //fixme: get username from JWT Token by SecurityContextHolder.class
                    .user("user")
                    .contentType("application/octet-stream")
                    .bucketName(s3ConfigProperties.getBucketName())
                    .s3Path(filePath)
                    .size(file.length())
                    .build();

            log.info("FileRecord saved to database....");
            return fileRecordService.saveFile(fileRecord);

        } catch (Exception e) {
            log.error("Upload Error for file {} in bucket {}: {}",
                    filePath, s3ConfigProperties.getBucketName(), e.getMessage(), e);
            throw new AppException(ErrorCode.S3_UPLOAD_FAILED);
        }

    }

    @Override
    public void deleteFile(String fileId) {
        log.info("Deleting file {} from bucket {}", fileId, s3ConfigProperties.getBucketName());
        try {
            FileRecord fileRecord = fileRecordRepository.findById(fileId)
                    .orElseThrow(() -> new AppException(ErrorCode.S3_FILE_NOT_FOUND));

            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(s3ConfigProperties.getBucketName())
                    .key(fileRecord.getS3Path())
                    .build());

            fileRecordRepository.delete(fileRecord);
            log.info("File {} deleted successfully", fileRecord.getS3Path());

        } catch (Exception e) {
            log.error("Delete Error for file {} in bucket {}: {}",
                    fileId, s3ConfigProperties.getBucketName(), e.getMessage(), e);

            throw new AppException(ErrorCode.S3_DELETE_FAILED);
        }
    }
}

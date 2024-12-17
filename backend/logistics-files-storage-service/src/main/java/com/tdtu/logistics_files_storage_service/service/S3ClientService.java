package com.tdtu.logistics_files_storage_service.service;

import com.tdtu.logistics_files_storage_service.dto.response.FileRecordResponse;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;

import java.io.File;

public interface S3ClientService {

    ResponseInputStream<GetObjectResponse> getFile(String filePath);

    FileRecordResponse uploadFile(String filePath, File file, ObjectCannedACL cannedAccessControlList);

    void deleteFile(String filePath);
}
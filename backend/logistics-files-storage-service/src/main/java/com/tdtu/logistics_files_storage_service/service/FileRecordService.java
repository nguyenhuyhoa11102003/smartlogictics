package com.tdtu.logistics_files_storage_service.service;

import com.tdtu.logistics_files_storage_service.dto.response.FileRecordResponse;
import com.tdtu.logistics_files_storage_service.entity.FileRecord;

import java.util.List;

public interface FileRecordService {
    FileRecordResponse saveFile(FileRecord fileRecord);

    FileRecordResponse findById(String id);

    List<FileRecordResponse> findAllByUserAndTaskId(String user, String taskId);
}

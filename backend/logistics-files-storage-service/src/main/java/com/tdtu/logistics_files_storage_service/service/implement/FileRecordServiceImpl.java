package com.tdtu.logistics_files_storage_service.service.implement;

import com.tdtu.logistics_files_storage_service.dto.response.FileRecordResponse;
import com.tdtu.logistics_files_storage_service.entity.FileRecord;
import com.tdtu.logistics_files_storage_service.exception.AppException;
import com.tdtu.logistics_files_storage_service.exception.ErrorCode;
import com.tdtu.logistics_files_storage_service.mapper.FileRecordMapper;
import com.tdtu.logistics_files_storage_service.repository.FileRecordRepository;
import com.tdtu.logistics_files_storage_service.service.FileRecordService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileRecordServiceImpl implements FileRecordService {

    private final FileRecordRepository fileRecordRepository;

    private final FileRecordMapper fileRecordMapper;

    @Override
    @Transactional
    public FileRecordResponse saveFile(FileRecord fileRecord) {
        try {
            FileRecordResponse response = fileRecordMapper.toResponse(fileRecordRepository.save(fileRecord));
            log.info("FileRecord saved: {}", response.toString());
            return response;
        } catch (Exception e) {
            log.error("Error saving file record: {}", e.getMessage());
            throw new AppException(ErrorCode.S3_UPLOAD_FAILED);
        }
    }

    @Override
    public FileRecordResponse findById(String id) {

        FileRecord fileRecord = fileRecordRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.S3_FILE_NOT_FOUND));
        log.info("Finding file record have result: {}", fileRecord.toString());

        return fileRecordMapper.toResponse(fileRecord);
    }

    @Override
    public List<FileRecordResponse> findAllByUserAndTaskId(String user, String taskId) {
        log.info("Finding all file records by user: {} and task id: {}", user, taskId);
        List<FileRecord> fileRecords = fileRecordRepository.findAllByUserAndTaskId(user, taskId);

        return fileRecords.stream()
                .map(fileRecordMapper::toResponse)
                .toList();
    }

}

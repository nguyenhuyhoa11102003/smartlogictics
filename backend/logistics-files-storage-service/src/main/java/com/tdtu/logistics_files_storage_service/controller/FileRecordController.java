package com.tdtu.logistics_files_storage_service.controller;

import com.tdtu.logistics_files_storage_service.dto.response.FileRecordResponse;
import com.tdtu.logistics_files_storage_service.service.FileRecordService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class FileRecordController {
    FileRecordService fileRecordService;

    @GetMapping("/{fileId}")
    public ApiResponse<FileRecordResponse> getFileRecordById(@PathVariable String fileId) {
        log.info("Getting file record by id: {}", fileId);

        return ApiResponse.<FileRecordResponse>builder()
                .code(HttpStatus.OK.value())
                .result(fileRecordService.findById(fileId))
                .isSuccess(true)
                .message("File record found.")
                .build();
    }

    @GetMapping("/user/{user}/task/{taskId}")
    public ApiResponse<List<FileRecordResponse>> getFileRecordByUserAndTaskId(@PathVariable String user, @PathVariable String taskId) {
        log.info("Getting file record by user: {} and task id: {}", user, taskId);

        return ApiResponse.<List<FileRecordResponse>>builder()
                .code(HttpStatus.OK.value())
                .result(fileRecordService.findAllByUserAndTaskId(user, taskId))
                .isSuccess(true)
                .message("List file record found.")
                .build();
    }
}

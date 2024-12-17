package com.tdtu.logistics_files_storage_service.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileRecordResponse {
    String id;

    // task_id is the id of the task that the file is attached to
    // Will be created when the task is created: 'later'
    String taskId;

    // account_id is the id of the account that the file is attached to
    // Get username from JWT Token by SecurityContextHolder.class
    String user;

    String bucketName;

    String filePath;

    Long size;

    LocalDateTime updatedAt;
}

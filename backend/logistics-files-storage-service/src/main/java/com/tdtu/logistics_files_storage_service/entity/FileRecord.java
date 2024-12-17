package com.tdtu.logistics_files_storage_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class FileRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    // task_id is the id of the task that the file is attached to
    // Will be created when the task is created: 'later'
    @Column(name = "task_id")
    String taskId;

    // account_id is the id of the account that the file is attached to
    // Get username from JWT Token by SecurityContextHolder.class
    @Column(name = "username")
    String user;

    @Column(name = "bucket_name", nullable = false)
    String bucketName;

    @Column(name = "s3_path", nullable = false)
    String s3Path;

    @Column(name = "e_tag", nullable = false)
    String eTag;

    @Column(name = "version_id")
    String versionId;

    @Column(name = "content_type")
    String contentType;

    @Column(name = "size")
    Long size;
}

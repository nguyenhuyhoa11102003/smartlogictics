package com.tdtu.logistics_files_storage_service.repository;

import com.tdtu.logistics_files_storage_service.entity.FileRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRecordRepository extends JpaRepository<FileRecord, String> {
    List<FileRecord> findAllByUserAndTaskId(String user, String taskId);
}

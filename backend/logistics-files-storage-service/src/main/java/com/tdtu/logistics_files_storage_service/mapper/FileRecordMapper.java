package com.tdtu.logistics_files_storage_service.mapper;

import com.tdtu.logistics_files_storage_service.dto.response.FileRecordResponse;
import com.tdtu.logistics_files_storage_service.entity.FileRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FileRecordMapper {
    @Mapping(target = "filePath", source = "s3Path")
    FileRecordResponse toResponse(FileRecord fileRecord);
}

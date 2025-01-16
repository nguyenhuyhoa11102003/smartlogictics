package com.tdtu.logistics_files_storage_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Cho phép kế thừa với các bảng con.
@Table(name = "file_records")
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseFileRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "bucket_name", nullable = false)
    String bucketName;

    @Column(name = "s3_path", nullable = false)
    String s3Path;

    @Column(name = "file_name", nullable = false)
    String fileName;

    @Column(name = "content_type")
    String contentType;

    @Column(name = "size")
    Long size;

    @Column(name = "upload_time", nullable = false)
    LocalDateTime uploadTime;
}
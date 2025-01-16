package com.tdtu.logistics_files_storage_service.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "customer_avatar")
public class CustomerAvatar extends BaseFileRecord {

    @Column(name = "customer_id", nullable = false)
    String customerId;
}
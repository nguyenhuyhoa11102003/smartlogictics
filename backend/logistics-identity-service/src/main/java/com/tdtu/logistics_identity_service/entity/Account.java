package com.tdtu.logistics_identity_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "account")
@EntityListeners(AuditingEntityListener.class)
public class Account extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    String username;

    @NotBlank
    @Column(name = "password", nullable = false)
    String password;

    @NotBlank
    @Column(name = "user_profile_id")
    String userProfileId;

    @ManyToMany
    @ToString.Exclude
    Set<Role> roles;
}
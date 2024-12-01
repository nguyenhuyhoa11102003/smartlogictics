package com.tdtu.logistics_identity_service.enumrator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum Gender {
    MALE, FEMALE;
}
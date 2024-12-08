package com.tdtu.logistics_users_service.utils;


import com.tdtu.logistics_users_service.enumrators.StaffPosition;

import java.time.Year;
import java.util.Random;

public class GenerateStaffId {

    private static final Random RANDOM = new Random();

    /**
     * Generate a unique StaffId based on department, position, and the current year.
     *
     * @param departmentCode Code of the department (e.g., "DEP001").
     * @param positionCode   Code of the position (e.g., "MAN", "SHI").
     * @return Generated StaffId (e.g., "DEP001-MAN-2024-12345").
     */

    public static String generate(String departmentCode, StaffPosition positionCode) {
        int currentYear = Year.now().getValue();
        int randomSuffix = RANDOM.nextInt(90000) + 10000; // Generate a 5-digit random number
        return String.format("%s-%s-%d-%05d", departmentCode, positionCode, currentYear, randomSuffix);
    }


}
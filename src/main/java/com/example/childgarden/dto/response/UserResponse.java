package com.example.childgarden.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;


@Data
public class UserResponse {

    private String userName;

    private Map<LocalDate,Boolean> participationSheet;

    private int totalOfSkippedDays;

    private int totalNumberOfDaysMissedDueToIllness;

    private int totalOfVisitedDays;

}

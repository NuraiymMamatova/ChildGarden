package com.example.childgarden.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class GroupResponse{

   private List<UserResponse> children;
   private Map<LocalDate, Integer> totalOfDays;
   private int totalVisitedDays;
   private String teacherName;
   private LocalDate date;

}

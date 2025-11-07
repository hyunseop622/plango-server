package com.plango.server.ai.dto;

import java.util.*;

public record AiTravelResponse(
    List<AiDay> days
){
    public record AiDay(
            List<AiCourse> courses
    ){}

    public record AiCourse(
            Integer order,
            String locationName,
            Double lat,
            Double lng,
            String note,
            String theme,
            Integer howLong
    ){}
}
package com.ptit.ktx.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RoomOverviewResponse(
    @JsonProperty("room") String code,          // FE dùng room
    String block,
    @JsonProperty("memberCount") int count      
) {}
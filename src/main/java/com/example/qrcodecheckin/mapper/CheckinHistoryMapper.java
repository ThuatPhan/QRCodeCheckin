package com.example.qrcodecheckin.mapper;

import com.example.qrcodecheckin.dto.request.CheckinRequest;
import com.example.qrcodecheckin.dto.response.CheckinHistoryResponse;
import com.example.qrcodecheckin.entity.CheckinHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CheckinHistoryMapper {
    @Mapping(source = "locationId", target = "location.id")
    CheckinHistory toCheckinHistory(CheckinRequest checkinRequest);
    CheckinHistoryResponse toResponse(CheckinHistory checkinHistory);
}

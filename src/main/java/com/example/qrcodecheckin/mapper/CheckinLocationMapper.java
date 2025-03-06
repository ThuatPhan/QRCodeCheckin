package com.example.qrcodecheckin.mapper;

import com.example.qrcodecheckin.dto.request.CheckinLocationRequest;
import com.example.qrcodecheckin.dto.response.CheckinLocationResponse;
import com.example.qrcodecheckin.entity.CheckinLocation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CheckinLocationMapper {
    CheckinLocation toCheckinLocation(CheckinLocationRequest checkinLocationRequest);
    CheckinLocationResponse toResponse(CheckinLocation checkinLocation);
    void updateCheckinLocation(@MappingTarget CheckinLocation checkinLocation, CheckinLocationRequest checkinLocationRequest);
}

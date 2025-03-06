package com.example.qrcodecheckin.mapper;

import com.example.qrcodecheckin.dto.response.CheckinQrCodeResponse;
import com.example.qrcodecheckin.entity.CheckinQrCode;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CheckinQrCodeMapper {
    CheckinQrCodeResponse toResponse(CheckinQrCode checkinQrCode);
}

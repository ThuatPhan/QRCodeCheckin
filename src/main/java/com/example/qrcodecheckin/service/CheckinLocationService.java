package com.example.qrcodecheckin.service;

import com.example.qrcodecheckin.dto.request.CheckinLocationRequest;
import com.example.qrcodecheckin.dto.response.CheckinLocationResponse;
import com.example.qrcodecheckin.dto.response.PagedResponse;
import com.example.qrcodecheckin.entity.CheckinLocation;
import com.example.qrcodecheckin.exception.AppException;
import com.example.qrcodecheckin.exception.ErrorCode;
import com.example.qrcodecheckin.mapper.CheckinLocationMapper;
import com.example.qrcodecheckin.repository.CheckinLocationRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CheckinLocationService {
    CheckinLocationMapper checkinLocationMapper;
    CheckinLocationRepository checkinLocationRepository;

    @Autowired
    public CheckinLocationService(CheckinLocationMapper checkinLocationMapper, CheckinLocationRepository checkinLocationRepository) {
        this.checkinLocationMapper = checkinLocationMapper;
        this.checkinLocationRepository = checkinLocationRepository;
    }

    @CacheEvict(value = "locations", allEntries = true)
    @CachePut(value = "locations", key = "#result.id")
    public CheckinLocationResponse createCheckinLocation(CheckinLocationRequest checkinLocationRequest) {
        CheckinLocation checkinLocation = checkinLocationMapper.toCheckinLocation(checkinLocationRequest);
        if (checkinLocationRepository.existsByNameAndLongitudeAndLatitude(checkinLocationRequest.getName(), checkinLocation.getLongitude(), checkinLocation.getLatitude())) {
            throw new AppException(ErrorCode.CHECKIN_LOCATION_EXISTED);
        }
        return checkinLocationMapper.toResponse(checkinLocationRepository.save(checkinLocation));
    }

    @Cacheable(value = "locations", key = "#id")
    public CheckinLocationResponse findCheckinLocationById(Long id) {
        return checkinLocationMapper.toResponse(checkinLocationRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CHECKIN_LOCATION_NOT_EXIST)));
    }

    @Cacheable(value = "locations", key = "'page:' + #page + ',size:' + #size")
    public PagedResponse<CheckinLocationResponse> findAllCheckinLocations(int page, int size) {
        Page<CheckinLocation> resultPage = checkinLocationRepository.findAll(PageRequest.of(page - 1, size));
        return new PagedResponse<>(
                resultPage.getTotalElements(),
                resultPage.getContent().stream().map(checkinLocationMapper::toResponse).toList()
        );
    }

    @CacheEvict(value = "locations", allEntries = true)
    @CachePut(value = "locations", key = "#id")
    public CheckinLocationResponse updateCheckinLocation(Long id, CheckinLocationRequest checkinLocationRequest) {
        CheckinLocation locationToUpdate = checkinLocationRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CHECKIN_LOCATION_NOT_EXIST));
        checkinLocationMapper.updateCheckinLocation(locationToUpdate, checkinLocationRequest);
        return checkinLocationMapper.toResponse(checkinLocationRepository.save(locationToUpdate));
    }

    @CacheEvict(value = "locations", allEntries = true)
    public void deleteCheckinLocationById(Long id) {
        if (checkinLocationRepository.existsById(id)) {
            checkinLocationRepository.deleteById(id);
            return;
        }
        throw new AppException(ErrorCode.CHECKIN_LOCATION_NOT_EXIST);
    }
}

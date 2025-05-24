package com.iztechceng.graduation_managment.systemsetting.service;

import com.iztechceng.graduation_managment.systemsetting.repository.SystemSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystemSettingService {
    private final SystemSettingsRepository systemSettingRepository;

    public boolean isGraduationRequestEnabled() {
        return systemSettingRepository.getGraduationRequestStatus();
    }

    public void toggleGraduationRequest(boolean enabled) {
        if (systemSettingRepository.existsById(1L)) {
            systemSettingRepository.findById(1L).ifPresent(systemSetting -> {
                systemSetting.setGraduationRequestEnabled(enabled);
                systemSettingRepository.save(systemSetting);
            });
        }
    }
}

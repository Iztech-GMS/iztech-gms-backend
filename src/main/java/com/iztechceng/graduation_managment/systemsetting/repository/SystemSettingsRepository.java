package com.iztechceng.graduation_managment.systemsetting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.iztechceng.graduation_managment.systemsetting.model.entity.SystemSetting;
import org.springframework.data.jpa.repository.Query;


public interface SystemSettingsRepository extends JpaRepository<SystemSetting, Long> {
    @Query("SELECT ss.graduationRequestEnabled FROM SystemSetting ss")
    boolean getGraduationRequestStatus();

}

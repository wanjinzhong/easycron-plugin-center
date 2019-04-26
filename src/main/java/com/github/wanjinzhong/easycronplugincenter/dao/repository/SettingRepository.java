package com.github.wanjinzhong.easycronplugincenter.dao.repository;
import com.github.wanjinzhong.easycronplugincenter.dao.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Integer> {
}

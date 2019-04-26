package com.github.wanjinzhong.easycronplugincenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EntityScan(basePackages = "com.github.wanjinzhong.easycronplugincenter")
@EnableJpaRepositories(basePackages = "com.github.wanjinzhong.easycronplugincenter.dao")
@EnableAsync
public class EasycronPluginCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasycronPluginCenterApplication.class, args);
    }

}

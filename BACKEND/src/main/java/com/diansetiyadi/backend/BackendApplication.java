package com.diansetiyadi.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.convert.Jsr310Converters;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EntityScan(basePackageClasses = {
        BackendApplication.class,
        Jsr310Converters.class
})
public class BackendApplication {
    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
    }
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}

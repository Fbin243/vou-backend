package com.vou.pkg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class PkgApplication {

    public static void main(String[] args) {
        SpringApplication.run(PkgApplication.class, args);
    }

}

package com.assignmnt.ice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MusicMetadataServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicMetadataServiceApplication.class, args);
    }

}

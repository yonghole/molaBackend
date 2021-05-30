package com.mola.mola.config;

import com.mola.mola.file_management_util.FileUnzipper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileManagementConfig {
    @Bean
    public FileUnzipper fileUnzipper(){
        return new FileUnzipper();
    }
}

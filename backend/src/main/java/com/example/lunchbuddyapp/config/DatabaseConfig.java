package com.example.lunchbuddyapp.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class DatabaseConfig {
    private final DataSource dataSource;

    @PostConstruct
    public void loadData() {
        ResourceDatabasePopulator populate = new ResourceDatabasePopulator();
        populate.addScript(new ClassPathResource("data.sql"));
        DatabasePopulatorUtils.execute(populate, dataSource);
    }
}

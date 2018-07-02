package org.cycop.reg.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cycop.reg.SecretRetriever;
import org.cycop.reg.dao.*;
import org.cycop.reg.dataobjects.Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@ComponentScan(basePackages="org.cycop.reg")
@EnableWebMvc
public class MvcConfiguration implements WebMvcConfigurer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        Credential dbCredential;

        SecretRetriever s = new SecretRetriever("secretsmanager.us-east-1.amazonaws.com", "us-east-1");
        String secret = s.getSecret("database");

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            dbCredential = objectMapper.readValue(secret, Credential.class);
            logger.info("Retrieved database credentials");
        } catch (IOException e) {
            logger.error("Failed to retrieve database credentials", e);
            return null;
        }

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/regtracker");
        dataSource.setUsername(dbCredential.getUsername());
        dataSource.setPassword(dbCredential.getPassword());
        return dataSource;
    }

    @Bean
    public PersonDAO getPersonDAO()  {
        return new PersonDAO(getDataSource());
    }

    @Bean
    public GradeDAO getGradeDAO()  {
        return new GradeDAO(getDataSource());
    }

    @Bean
    public RankDAO getRankDAO()  {
        return new RankDAO(getDataSource());
    }

    @Bean
    public PersonAddressDAO getAddressDAO(){
        return new PersonAddressDAO(getDataSource());
    }

    @Bean
    public AccountDAO getAccountDAO(){
        return new AccountDAO(getDataSource());
    }

    @Bean
    public RoleDAO getRoleDAO(){
        return new RoleDAO(getDataSource());
    }

    @Bean
    public UserDAO getUserDAO(){
        return new UserDAO(getDataSource());
    }
}
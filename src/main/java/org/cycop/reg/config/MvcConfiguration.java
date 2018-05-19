package org.cycop.reg.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cycop.reg.SecretRetriever;
import org.cycop.reg.dao.PersonDAO;
import org.cycop.reg.dao.PersonDAOImpl;

import org.cycop.reg.dataobjects.Credential;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@ComponentScan(basePackages="net.codejava.spring")
@EnableWebMvc
public class MvcConfiguration implements WebMvcConfigurer {

    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        Credential dbCredential;

        SecretRetriever s = new SecretRetriever("secretsmanager.us-east-1.amazonaws.com", "us-east-1");
        String secret = s.getSecret("database");

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            dbCredential = objectMapper.readValue(secret, Credential.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/regtracker");
        dataSource.setUsername(dbCredential.getUsername());
        dataSource.setPassword(dbCredential.getPassword());
        return dataSource;
    }

    @Bean
    public PersonDAO getPersonDAO() throws Exception {
        if(getDataSource() == null){
            throw new Exception("Null data source");
        }
        return new PersonDAOImpl(getDataSource());
    }

}
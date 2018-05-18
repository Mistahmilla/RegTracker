package org.cycop.reg.config;

import org.cycop.reg.SecretRetriever;
import org.cycop.reg.dao.PersonDAO;
import org.cycop.reg.dao.PersonDAOImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages="net.codejava.spring")
@EnableWebMvc
public class MvcConfiguration implements WebMvcConfigurer {

    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        SecretRetriever s = new SecretRetriever("secretsmanager.us-east-1.amazonaws.com", "us-east-1");
        String secret = s.getSecret("database");

        String user = secret.substring(secret.indexOf(":")+2, secret.indexOf(",")-1);
        String pass = secret.substring(secret.indexOf(","));
        pass = pass.substring(pass.indexOf(":")+2, pass.length()-2);
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/regtracker");
        dataSource.setUsername(user);
        dataSource.setPassword(pass);
        System.out.println(user);
        System.out.println(pass);
        return dataSource;
    }

    @Bean
    public PersonDAO getPersonDAO() {
        return new PersonDAOImpl(getDataSource());
    }

}
package org.cycop.reg.config;

import org.cycop.reg.dao.AccountDAO;
import org.cycop.reg.dao.UserDAO;
import org.cycop.reg.dataobjects.User;
import org.cycop.reg.security.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
@EnableWebSecurity
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private UserDAO userDAO;

    /**
     * Constructor disables the default security settings
     */
    public WebSecurityConfig() {
        super(true);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/register");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**").authorizeRequests().anyRequest().authenticated().and().authorizeRequests().antMatchers("/register**").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.eraseCredentials(false) // 4
                .userDetailsService(accountDAO)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public ApplicationListener<AuthenticationSuccessEvent> authenticationSuccessListener(final PasswordEncoder encoder) {

        return (AuthenticationSuccessEvent event) -> {
            final Authentication auth = event.getAuthentication();
            User u;
            Account a;

            if (auth instanceof UsernamePasswordAuthenticationToken && auth.getCredentials() != null) {

                final CharSequence clearTextPass = (CharSequence) auth.getCredentials(); // 1
                final String newPasswordHash = encoder.encode(clearTextPass); // 2

                a = accountDAO.getAccountByEmailAddress(auth.getName()).get(0);

                if (!a.getPassword().startsWith("{bcrypt}")) {
                    logger.info("Upgrading password for user {}", auth.getName());
                    u = userDAO.getUserByEmailAddress(auth.getName()).get(0);
                    u.setPassword(newPasswordHash);
                    u.setSalt(a.getPasswordSalt());
                    userDAO.updateExisting(u, u);
                }
                ((UsernamePasswordAuthenticationToken) auth).eraseCredentials(); // 3
            }

        };
    }

}

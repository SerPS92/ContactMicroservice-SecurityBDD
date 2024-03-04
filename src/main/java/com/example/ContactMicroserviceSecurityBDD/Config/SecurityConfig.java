package com.example.ContactMicroserviceSecurityBDD.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * @Bean public InMemoryUserDetailsManager userDetailsManager() throws Exception {
     * List<UserDetails> users = List.of(
     * <p>
     * User
     * .withUsername("user1")
     * .password("{noop}user1")
     * .roles("USERS")
     * .build(),
     * User
     * .withUsername("user2")
     * .password("{noop}user2")
     * .roles("OPERATOR")
     * .build(),
     * User
     * .withUsername("admin")
     * .password("{noop}admin")
     * .roles("ADMIN")
     * .build());
     * <p>
     * return new InMemoryUserDetailsManager(users);
     * }
     */


    @Bean
    public JdbcUserDetailsManager userDetailsManager() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/springsecurity?serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("select user, pwd, enabled from users where user=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select user, rol from roles where user=?");
        return jdbcUserDetailsManager;
    }

    //For encrypted passwords
    /**
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(cus -> cus.disable())
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(HttpMethod.POST, "/contacts").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/contacts/**").hasAnyRole("ADMIN", "OPERATOR")
                                .requestMatchers("/contacts").authenticated()
                                .anyRequest().permitAll())
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }


}

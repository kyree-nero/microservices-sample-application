package com.example.ratingservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean PasswordEncoder passwordEncoder() {
		return  NoOpPasswordEncoder.getInstance();
	}
	
	
    @Autowired
    public void configureGlobal1(AuthenticationManagerBuilder auth) throws Exception {
        //try in memory auth with no users to support the case that this will allow for users that are logged in to go anywhere
        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//        .authorizeRequests()
//            .regexMatchers("^/ratings\\?bookId.*$").authenticated()
//            .antMatchers(HttpMethod.POST,"/ratings").authenticated()
//            .antMatchers(HttpMethod.PATCH,"/ratings/*").hasRole("ADMIN")
//            .antMatchers(HttpMethod.DELETE,"/ratings/*").hasRole("ADMIN")
//            .antMatchers(HttpMethod.GET,"/ratings").hasRole("ADMIN")
//            .antMatchers(HttpMethod.GET,"/ratings/*").hasRole("ADMIN")
//            .antMatchers(HttpMethod.GET,"/hystrix").authenticated()
//            .anyRequest().authenticated()
        .authorizeRequests()
		.antMatchers("/ratings").permitAll()
	  .antMatchers("/ratings/*").hasAnyRole("USER", "ADMIN")
    .anyRequest().authenticated()
            .and()
         .httpBasic().and()   
        .csrf()
            .disable();
       
        
    }
}

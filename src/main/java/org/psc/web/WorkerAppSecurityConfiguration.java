package org.psc.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WorkerAppSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//@formatter:off
	    http
	    	.authorizeRequests()
	    		.antMatchers("/**").permitAll()
	    		.and()
    		.authorizeRequests()
               	.antMatchers("/h2-console/**").permitAll()
               	.and()
            .headers().frameOptions().disable()
                .and()
            .csrf().ignoringAntMatchers("/h2-console/**")
                .and()
                .cors().disable()
           	.formLogin()
               	.loginPage("/login")
               	.permitAll()
               	.and()
           	.logout()
           		.permitAll();
		//@formatter:on
	}
}

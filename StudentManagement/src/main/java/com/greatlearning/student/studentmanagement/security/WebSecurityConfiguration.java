package com.greatlearning.student.studentmanagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.greatlearning.student.studentmanagement.services.MyUserDetailsService;

@SuppressWarnings("deprecation")
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Bean
	public UserDetailsService getUserDetailsService() {
		return new MyUserDetailsService();
	}
	
	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(getUserDetailsService());
		provider.setPasswordEncoder(getBCryptPasswordEncoder());
		return provider;
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder authMgr) {
		authMgr.authenticationProvider(getAuthenticationProvider());
	}
	
	@Override
	public void configure(HttpSecurity security) throws Exception {
		security.authorizeRequests()
			.antMatchers("/","/students/list","/students/showFormForAdd","/students/save","/students/403")
			//.permitAll()
			.hasAnyAuthority("ADMIN","USER")
			.antMatchers("/students/showFormForUpdate","/students/delete").hasAuthority("ADMIN")
			.anyRequest().authenticated()
			.and()
			.formLogin().loginProcessingUrl("/login").successForwardUrl("/students/list").permitAll()
			.and()
			.logout().logoutSuccessUrl("/login").permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/students/403")
			.and()
			.cors()
			.and()
			.csrf()
			.disable();
	}

}

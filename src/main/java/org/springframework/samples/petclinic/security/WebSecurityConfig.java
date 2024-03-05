package org.springframework.samples.petclinic.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final OwnerUserDetailsService ownerUserDetailsService;

  public WebSecurityConfig(OwnerUserDetailsService ownerUserDetailsService) {
    this.ownerUserDetailsService = ownerUserDetailsService;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(ownerUserDetailsService);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/", "/home").permitAll() // Allow access to specific paths
        .anyRequest().authenticated() // Require authentication for any other request
        .and()
        .formLogin()
        .loginPage("/login") // Specify custom login page
        .permitAll() // Allow access to login page without authentication
        .and()
        .logout()
        .permitAll()
        .and()
        .addFilter(new FirstNameLastNameAuthenticationFilter(authenticationManager()));
  }
}

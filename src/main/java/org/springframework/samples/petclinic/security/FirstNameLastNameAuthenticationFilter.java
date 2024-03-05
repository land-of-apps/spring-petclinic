package org.springframework.samples.petclinic.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class FirstNameLastNameAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  public FirstNameLastNameAuthenticationFilter(AuthenticationManager authenticationManager) {
    super.setAuthenticationManager(authenticationManager);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {
    String firstName = obtainUsername(request);
    String lastName = obtainPassword(request); // Considering the last name as the password for the demo

    // Generate UsernamePasswordAuthenticationToken or your custom authentication
    // token
    // BCrypt the lastName
    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(firstName,
        lastName);

    setDetails(request, authRequest);
    return this.getAuthenticationManager().authenticate(authRequest);
  }
}

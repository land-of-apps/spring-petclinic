package org.springframework.samples.petclinic.security;

import java.util.Collections;

import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OwnerUserDetailsService implements UserDetailsService {
  private final OwnerRepository ownerRepository;

  public OwnerUserDetailsService(OwnerRepository ownerRepository) {
    this.ownerRepository = ownerRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String firstName) throws UsernameNotFoundException {
    Owner owner = ownerRepository.findByFirstName(firstName);
    if (owner == null) {
      throw new UsernameNotFoundException("Owner not found with the name: " + firstName);
    }
    String encodedLastName = new BCryptPasswordEncoder().encode(owner.getLastName());
    return new User(owner.getFirstName(), encodedLastName, Collections.emptyList());
  }
}

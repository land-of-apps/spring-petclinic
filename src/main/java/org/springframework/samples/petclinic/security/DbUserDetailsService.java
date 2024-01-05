package org.springframework.samples.petclinic.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DbUserDetailsService implements UserDetailsService {

	@Autowired
	private VetRepository vetRepository;

	@Autowired
	private OwnerRepository ownerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Person person;

		try {
			person = ownerRepository.findByLogin(username);
		}
		catch (Exception e) {
			person = vetRepository.findByLogin(username);
		}

		if (person == null)
			throw new UsernameNotFoundException("User not found");

		return new org.springframework.security.core.userdetails.User(person.getLogin(), person.getPassword(),
				getAuthorities(person));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Person user) {
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE"));
	}

}

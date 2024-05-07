package com.Odzywianie.services;

import com.Odzywianie.models.Uzytkownik;
import com.Odzywianie.repositories.UzytkownikRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final UzytkownikRepository uzytkownikRepository;

    public CustomUserDetailsService(UzytkownikRepository uzytkownikRepository) {
        this.uzytkownikRepository = uzytkownikRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Uzytkownik uzytkownik = uzytkownikRepository.findByName(username);
        if (uzytkownik == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(uzytkownik.getName(), uzytkownik.getPassword(), Collections.singleton(new SimpleGrantedAuthority(uzytkownik.getRole())));
    }
}
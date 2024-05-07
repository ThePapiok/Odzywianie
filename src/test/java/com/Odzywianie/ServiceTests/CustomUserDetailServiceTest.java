package com.Odzywianie.ServiceTests;

import com.Odzywianie.models.Uzytkownik;
import com.Odzywianie.repositories.UzytkownikRepository;
import com.Odzywianie.services.CustomUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailServiceTest {
    private CustomUserDetailsService customUserDetailsService;
    @Mock
    private UzytkownikRepository uzytkownikRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        customUserDetailsService = new CustomUserDetailsService(uzytkownikRepository);
    }


    @Test
    public void shouldTrueWhenLoadUserByUsername(){
        String username = "Test";
        String password = "Test123!";
        String role = "ADMIN";
        Uzytkownik uzytkownik = new Uzytkownik();
        uzytkownik.setName(username);
        uzytkownik.setPassword(password);
        uzytkownik.setRole(role);
        Mockito.when(uzytkownikRepository.findByName(username)).thenReturn(uzytkownik);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        Assertions.assertEquals(userDetails.getUsername(), username);
        Assertions.assertEquals(userDetails.getPassword(), password);
        Assertions.assertTrue(userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals(role)));
    }

    @Test
    public void shouldFalseWhenLoadUserByUsername(){
        String username = "Test";
        Mockito.when(uzytkownikRepository.findByName(username)).thenReturn(null);
        Assertions.assertThrows(UsernameNotFoundException.class, () ->{
            customUserDetailsService.loadUserByUsername("Test");
                });
    }
}

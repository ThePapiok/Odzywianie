package com.Odzywianie.ServiceTests;

import com.Odzywianie.enums.Gender;
import com.Odzywianie.models.Uzytkownik;
import com.Odzywianie.modelsDTO.UzytkownikDTO;
import com.Odzywianie.repositories.UzytkownikRepository;
import com.Odzywianie.services.UzytkownikService;
import com.Odzywianie.utils.UzytkownikConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UzytkownikServiceTest {
    private UzytkownikService uzytkownikService;

    @Mock
    private UzytkownikRepository uzytkownikRepository;
    @Mock
    private UzytkownikConverter uzytkownikConverter;

    @Mock
    private Authentication authentication;

    @Mock
    private PasswordEncoder passwordEncoder;


    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);
        uzytkownikService = new UzytkownikService(uzytkownikConverter, uzytkownikRepository, passwordEncoder);
    }

    @Test
    public void shouldTrueWhenGetUzytkownik()
    {
        UzytkownikDTO uzytkownikDTO = new UzytkownikDTO();
        uzytkownikDTO.setName("Test");
        Uzytkownik uzytkownik = new Uzytkownik();
        uzytkownik.setName("Test");
        Mockito.when(uzytkownikConverter.createUzytkownik(uzytkownikDTO)).thenReturn(uzytkownik);
        Assertions.assertEquals(uzytkownik, uzytkownikService.getUzytkownik(uzytkownikDTO));
    }

    @Test
    public void shouldTrueWhenGetUzytkownikByName()
    {
        Uzytkownik uzytkownik = new Uzytkownik();
        uzytkownik.setName("Test");
        Mockito.when(uzytkownikRepository.findByName("Test")).thenReturn(uzytkownik);
        Assertions.assertEquals(uzytkownik, uzytkownikService.isUzytkownik("Test"));
    }

    @Test
    public void shouldTrueWhenGetUzytkownikByAuthentication(){
        Uzytkownik uzytkownik = new Uzytkownik();
        uzytkownik.setName("Test");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Mockito.when(authentication.getName()).thenReturn("Test");
        Mockito.when(uzytkownikRepository.findByName("Test")).thenReturn(uzytkownik);
        Assertions.assertEquals(uzytkownik, uzytkownikService.getUzytkownikByAuthentication());
    }

    @Test
    public void shouldTrueWhenGetUzytkownikDTOByAuthentication(){
        Uzytkownik uzytkownik = new Uzytkownik();
        uzytkownik.setName("Test");
        UzytkownikDTO uzytkownikDTO = new UzytkownikDTO();
        uzytkownikDTO.setName("Test");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Mockito.when(authentication.getName()).thenReturn("Test");
        Mockito.when(uzytkownikRepository.findByName("Test")).thenReturn(uzytkownik);
        Mockito.when(uzytkownikConverter.createUzytkownikDTO(uzytkownik)).thenReturn(uzytkownikDTO);
        Assertions.assertEquals(uzytkownikDTO, uzytkownikService.getUzytkownikDTOByAuthentication());
    }

    @Test
    public void shouldTrueWhenUpdateUzytkownikInformations()
    {
        UzytkownikDTO uzytkownikDTO = new UzytkownikDTO();
        uzytkownikDTO.setWeight(30);
        uzytkownikDTO.setAge(20);
        uzytkownikDTO.setHeight(180);
        uzytkownikDTO.setActivity(3);
        uzytkownikDTO.setGender(Gender.WOMAN);
        Uzytkownik uzytkownik = new Uzytkownik();
        uzytkownik.setName("Test");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Mockito.when(authentication.getName()).thenReturn("Test");
        Mockito.when(uzytkownikRepository.findByName("Test")).thenReturn(uzytkownik);
        uzytkownikService.updateUzytkownikInformations(uzytkownikDTO);
        Assertions.assertEquals(uzytkownik.getAge(), uzytkownikDTO.getAge());
        Assertions.assertEquals(uzytkownik.getRole(), uzytkownikDTO.getRole());
        Assertions.assertEquals(uzytkownik.getHeight(), uzytkownikDTO.getHeight());
        Assertions.assertEquals(uzytkownik.getWeight(), uzytkownikDTO.getWeight());
        Assertions.assertEquals(uzytkownik.getActivity(), uzytkownikDTO.getActivity());
    }

    @Test
    public void shouldTrueWhenUpdatePassword(){
        UzytkownikDTO uzytkownikDTO = new UzytkownikDTO();
        uzytkownikDTO.setPassword("Test");
        Uzytkownik uzytkownik = new Uzytkownik();
        uzytkownik.setName("Test");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Mockito.when(authentication.getName()).thenReturn("Test");
        Mockito.when(uzytkownikRepository.findByName("Test")).thenReturn(uzytkownik);
        Mockito.when(passwordEncoder.encode(uzytkownikDTO.getPassword())).thenReturn("encodeTest");
        uzytkownikService.updatePassword(uzytkownikDTO);
        Assertions.assertEquals("encodeTest", uzytkownik.getPassword());
    }

    @Test
    public void shouldTrueWhenMatchesPassword(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Uzytkownik uzytkownik = new Uzytkownik();
        uzytkownik.setName("Test");
        uzytkownik.setPassword(bCryptPasswordEncoder.encode("Test"));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Mockito.when(authentication.getName()).thenReturn("Test");
        Mockito.when(uzytkownikRepository.findByName("Test")).thenReturn(uzytkownik);
        Assertions.assertTrue(uzytkownikService.matchesPassword("Test"));
    }

}

package com.Odzywianie.UtilsTests;

import com.Odzywianie.enums.Gender;
import com.Odzywianie.models.Uzytkownik;
import com.Odzywianie.modelsDTO.UzytkownikDTO;
import com.Odzywianie.utils.UzytkownikConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UzytkownikConverterTest {
    private UzytkownikConverter uzytkownikConverter;
    @Mock
    private PasswordEncoder passwordEncoder;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        uzytkownikConverter = new UzytkownikConverter(passwordEncoder);
    }

    @Test
    public void shouldTrueWhenCreateUzytkownikDTO(){
        Uzytkownik uzytkownik = new Uzytkownik();
        uzytkownik.setName("Test");
        uzytkownik.setPassword("Test123!");
        uzytkownik.setRole("ADMIN");
        uzytkownik.setGender(Gender.MAN);
        uzytkownik.setHeight(183);
        uzytkownik.setWeight(65);
        uzytkownik.setActivity(0);
        uzytkownik.setAge(22);
        UzytkownikDTO uzytkownikDTO = uzytkownikConverter.createUzytkownikDTO(uzytkownik);
        Assertions.assertEquals(uzytkownikDTO.getAge(), uzytkownik.getAge());
        Assertions.assertEquals(uzytkownikDTO.getHeight(), uzytkownik.getHeight());
        Assertions.assertEquals(uzytkownikDTO.getWeight(), uzytkownik.getWeight());
        Assertions.assertEquals(uzytkownikDTO.getRole(), uzytkownik.getRole());
        Assertions.assertEquals(uzytkownikDTO.getName(), uzytkownik.getName());
        Assertions.assertEquals(uzytkownikDTO.getPassword(), uzytkownik.getPassword());
        Assertions.assertEquals(uzytkownikDTO.getActivity(), uzytkownik.getActivity());
        Assertions.assertEquals(uzytkownikDTO.getGender(), uzytkownik.getGender());
    }

    @Test
    public void shouldTrueWhenCreateUzytkownik(){
        UzytkownikDTO uzytkownikDTO = new UzytkownikDTO();
        uzytkownikDTO.setName("Test");
        uzytkownikDTO.setPassword("Test123!");
        uzytkownikDTO.setRole("ROLE_USER");
        uzytkownikDTO.setGender(Gender.MAN);
        uzytkownikDTO.setHeight(183);
        uzytkownikDTO.setWeight(65);
        uzytkownikDTO.setActivity(0);
        uzytkownikDTO.setAge(22);
        Mockito.when(passwordEncoder.encode(uzytkownikDTO.getPassword())).thenReturn("encodeTest");
        Uzytkownik uzytkownik = uzytkownikConverter.createUzytkownik(uzytkownikDTO);
        Assertions.assertEquals(uzytkownikDTO.getAge(), uzytkownik.getAge());
        Assertions.assertEquals(uzytkownikDTO.getHeight(), uzytkownik.getHeight());
        Assertions.assertEquals(uzytkownikDTO.getWeight(), uzytkownik.getWeight());
        Assertions.assertEquals(uzytkownikDTO.getRole(), uzytkownik.getRole());
        Assertions.assertEquals(uzytkownikDTO.getName(), uzytkownik.getName());
        Assertions.assertEquals("encodeTest", uzytkownik.getPassword());
        Assertions.assertEquals(uzytkownikDTO.getActivity(), uzytkownik.getActivity());
        Assertions.assertEquals(uzytkownikDTO.getGender(), uzytkownik.getGender());
    }
}

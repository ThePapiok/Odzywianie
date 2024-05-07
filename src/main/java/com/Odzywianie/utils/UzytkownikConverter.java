package com.Odzywianie.utils;

import com.Odzywianie.models.Uzytkownik;
import com.Odzywianie.modelsDTO.UzytkownikDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UzytkownikConverter {


    private final PasswordEncoder passwordEncoder;

    public UzytkownikConverter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UzytkownikDTO createUzytkownikDTO(Uzytkownik uzytkownik)
    {
        UzytkownikDTO uzytkownikDTO = new UzytkownikDTO();
        uzytkownikDTO.setName(uzytkownik.getName());
        uzytkownikDTO.setPassword(uzytkownik.getPassword());
        uzytkownikDTO.setRole(uzytkownik.getRole());
        uzytkownikDTO.setGender(uzytkownik.getGender());
        uzytkownikDTO.setHeight(uzytkownik.getHeight());
        uzytkownikDTO.setWeight(uzytkownik.getWeight());
        uzytkownikDTO.setActivity(uzytkownik.getActivity());
        uzytkownikDTO.setAge(uzytkownik.getAge());
        return uzytkownikDTO;
    }

    public Uzytkownik createUzytkownik(UzytkownikDTO uzytkownikDTO)
    {
        Uzytkownik uzytkownik = new Uzytkownik();
        uzytkownik.setName(uzytkownikDTO.getName());
        uzytkownik.setPassword(passwordEncoder.encode(uzytkownikDTO.getPassword()));
        uzytkownik.setRole("ROLE_USER");
        uzytkownik.setWeight(uzytkownikDTO.getWeight());
        uzytkownik.setHeight(uzytkownikDTO.getHeight());
        uzytkownik.setActivity(uzytkownikDTO.getActivity());
        uzytkownik.setGender(uzytkownikDTO.getGender());
        uzytkownik.setAge(uzytkownikDTO.getAge());
        return uzytkownik;
    }

}

package com.Odzywianie.services;

import com.Odzywianie.models.Uzytkownik;
import com.Odzywianie.modelsDTO.UzytkownikDTO;
import com.Odzywianie.repositories.UzytkownikRepository;
import com.Odzywianie.utils.UzytkownikConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UzytkownikService {

    private final UzytkownikConverter uzytkownikConverter;
    private final UzytkownikRepository uzytkownikRepository;

    private final PasswordEncoder passwordEncoder;


    public UzytkownikService(UzytkownikConverter uzytkownikConverter, UzytkownikRepository uzytkownikRepository, PasswordEncoder passwordEncoder) {
        this.uzytkownikConverter = uzytkownikConverter;
        this.uzytkownikRepository = uzytkownikRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Uzytkownik getUzytkownik(UzytkownikDTO uzytkownikDTO)
    {
        return uzytkownikConverter.createUzytkownik(uzytkownikDTO);
    }

    public Uzytkownik isUzytkownik(String name)
    {
        return uzytkownikRepository.findByName(name);
    }

    public UzytkownikDTO getUzytkownikDTOByAuthentication()
    {
        return uzytkownikConverter.createUzytkownikDTO(getUzytkownikByAuthentication());
    }

    public Uzytkownik getUzytkownikByAuthentication()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return uzytkownikRepository.findByName(authentication.getName());
    }

    public void updateUzytkownikInformations(UzytkownikDTO uzytkownikDTO)
    {
        Uzytkownik uzytkownik = getUzytkownikByAuthentication();
        uzytkownik.setWeight(uzytkownikDTO.getWeight());
        uzytkownik.setHeight(uzytkownikDTO.getHeight());
        uzytkownik.setGender(uzytkownikDTO.getGender());
        uzytkownik.setActivity(uzytkownikDTO.getActivity());
        uzytkownik.setAge(uzytkownikDTO.getAge());
        uzytkownikRepository.save(uzytkownik);
    }

    public void updatePassword(UzytkownikDTO uzytkownikDTO)
    {
        Uzytkownik uzytkownik = getUzytkownikByAuthentication();
        uzytkownik.setPassword(passwordEncoder.encode(uzytkownikDTO.getPassword()));
        uzytkownikRepository.save(uzytkownik);

    }

    public boolean matchesPassword(String password)
    {
        Uzytkownik uzytkownik = getUzytkownikByAuthentication();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.matches(password, uzytkownik.getPassword());
    }

    public void deleteAccount()
    {
        Uzytkownik uzytkownik = getUzytkownikByAuthentication();
        uzytkownikRepository.delete(uzytkownik);
    }
}

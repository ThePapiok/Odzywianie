package com.Odzywianie.utils;

import com.Odzywianie.enums.Gender;
import com.Odzywianie.models.Uzytkownik;
import com.Odzywianie.repositories.UzytkownikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class DatabaseLoader implements CommandLineRunner {
    private final UzytkownikRepository uzytkownikRepository;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public DatabaseLoader(UzytkownikRepository uzytkownikRepository, PasswordEncoder passwordEncoder) {
        this.uzytkownikRepository = uzytkownikRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... strings) throws Exception {
        List<Uzytkownik> list = uzytkownikRepository.findAll();
        if(list.size()==0){
            Uzytkownik uzytkownik = new Uzytkownik();
            uzytkownik.setName("Admin123");
            uzytkownik.setPassword(passwordEncoder.encode("Admin123!"));
            uzytkownik.setRole("ROLE_ADMIN");
            uzytkownik.setWeight(65);
            uzytkownik.setHeight(183);
            uzytkownik.setActivity(1);
            uzytkownik.setGender(Gender.MAN);
            uzytkownik.setAge(22);
            uzytkownikRepository.save(uzytkownik);
        }
    }
}

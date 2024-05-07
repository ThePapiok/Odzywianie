package com.Odzywianie.RepositoryTests;

import com.Odzywianie.models.Uzytkownik;
import com.Odzywianie.repositories.UzytkownikRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UzytkownikRepositoryTest {

    @Autowired
    private UzytkownikRepository uzytkownikRepository;

    @Test
    public void findByNameNotNull(){
        Uzytkownik uzytkownik = new Uzytkownik();
        uzytkownik.setName("Test");
        uzytkownikRepository.save(uzytkownik);
        Assertions.assertEquals(uzytkownik, uzytkownikRepository.findByName("Test"));
    }

    @Test
    public void findByName(){
        Uzytkownik uzytkownik = new Uzytkownik();
        uzytkownik.setName("Test");
        uzytkownikRepository.save(uzytkownik);
        Assertions.assertNull(uzytkownikRepository.findByName("Test2"));
    }
}

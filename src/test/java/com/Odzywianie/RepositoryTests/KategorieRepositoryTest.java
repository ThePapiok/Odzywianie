package com.Odzywianie.RepositoryTests;

import com.Odzywianie.models.Kategorie;
import com.Odzywianie.repositories.KategorieRepository;
import com.Odzywianie.repositories.ZapotrzebowanieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class KategorieRepositoryTest {

    @Autowired
    private KategorieRepository kategorieRepository;

    @Test
    public void existsByNameTrue(){
        String name = "owoce";
        Kategorie kategorie = new Kategorie();
        kategorie.setName(name);
        kategorieRepository.save(kategorie);
        Assertions.assertTrue(kategorieRepository.existsByName(name));

    }

    @Test
    public void existsByNameFalse(){
        String name = "owoce";
        Kategorie kategorie = new Kategorie();
        kategorie.setName(name);
        kategorieRepository.save(kategorie);
        Assertions.assertFalse(kategorieRepository.existsByName("warzywa"));

    }

    @Test
    public void findByNameNotNull(){
        String name = "owoce";
        Kategorie kategorie = new Kategorie();
        kategorie.setName(name);
        kategorieRepository.save(kategorie);
        Assertions.assertEquals(kategorieRepository.findByName(name), kategorie);
    }

    @Test
    public void findByNameNull(){
        String name = "owoce";
        Kategorie kategorie = new Kategorie();
        kategorie.setName(name);
        kategorieRepository.save(kategorie);
        Assertions.assertNull(kategorieRepository.findByName("warzywa"));
    }
}

package com.Odzywianie.RepositoryTests;

import com.Odzywianie.models.Kategorie;
import com.Odzywianie.models.Produkty;
import com.Odzywianie.repositories.KategorieRepository;
import com.Odzywianie.repositories.ProduktyRepository;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.parameters.P;

@DataJpaTest
public class ProduktyRepositoryTest {

    @Autowired
    private ProduktyRepository produktyRepository;

    @Autowired
    private KategorieRepository kategorieRepository;

    @Test
    public void findByNameNotNull(){
        Produkty produkty = new Produkty();
        produkty.setName("Banan");
        produktyRepository.save(produkty);
        Assertions.assertEquals(produkty, produktyRepository.findByName("Banan"));
    }

    @Test
    public void findByName(){
        Produkty produkty = new Produkty();
        produkty.setName("Banan");
        produktyRepository.save(produkty);
        Assertions.assertNull(produktyRepository.findByName("Jabłko"));
    }

    @Test
    public void findAll()
    {
        Produkty produkty = new Produkty();
        produkty.setName("Banan");
        Produkty produkty1 = new Produkty();
        produkty1.setName("Jabłko");
        Produkty produkty2 = new Produkty();
        produkty2.setName("Ananas");
        produktyRepository.save(produkty);
        produktyRepository.save(produkty1);
        produktyRepository.save(produkty2);
        Page<Produkty> produkties = produktyRepository.findAll(PageRequest.of(0, 2));
        Assertions.assertEquals(2, produkties.getContent().size());
        Assertions.assertEquals(3, produkties.getTotalElements());
        Assertions.assertEquals(2, produkties.getTotalPages());
        Assertions.assertEquals(produkty, produkties.getContent().get(0));
        Assertions.assertEquals(produkty1, produkties.getContent().get(1));
    }

    @Test
    public void findAllStartsWith()
    {
        Produkty produkty = new Produkty();
        produkty.setName("Banan");
        Produkty produkty1 = new Produkty();
        produkty1.setName("Jabłko");
        Produkty produkty2 = new Produkty();
        produkty2.setName("Jabłko2");
        produktyRepository.save(produkty);
        produktyRepository.save(produkty1);
        produktyRepository.save(produkty2);
        Page<Produkty> produkties = produktyRepository.findAllByNameStartsWith(PageRequest.of(0, 3), "Jab");
        Assertions.assertEquals(2, produkties.getContent().size());
        Assertions.assertEquals(2, produkties.getTotalElements());
        Assertions.assertEquals(1, produkties.getTotalPages());
        Assertions.assertEquals(produkty1, produkties.getContent().get(0));
        Assertions.assertEquals(produkty2, produkties.getContent().get(1));
    }

    @Test
    public void findAllByKategoria()
    {
        Kategorie kategorie = new Kategorie(0l, "owoce");
        Kategorie kategorie2 = new Kategorie(0l, "warzywa");
        kategorieRepository.save(kategorie2);
        kategorieRepository.save(kategorie);
        Produkty produkty = new Produkty();
        produkty.setKategoria(kategorie);
        Produkty produkty1 = new Produkty();
        produkty1.setKategoria(kategorie2);
        Produkty produkty2 = new Produkty();
        produkty2.setKategoria(kategorie);
        produktyRepository.save(produkty);
        produktyRepository.save(produkty1);
        produktyRepository.save(produkty2);
        Page<Produkty> produkties = produktyRepository.findAllByKategoria(PageRequest.of(0, 3), kategorie);
        Assertions.assertEquals(2, produkties.getContent().size());
        Assertions.assertEquals(2, produkties.getTotalElements());
        Assertions.assertEquals(1, produkties.getTotalPages());
        Assertions.assertEquals(produkty, produkties.getContent().get(0));
        Assertions.assertEquals(produkty2, produkties.getContent().get(1));
    }

    @Test
    public void findAllByNameStartsWithAndKategoria()
    {
        Kategorie kategorie = new Kategorie(0l, "owoce");
        Kategorie kategorie2 = new Kategorie(0l, "warzywa");
        kategorieRepository.save(kategorie2);
        kategorieRepository.save(kategorie);
        Produkty produkty = new Produkty();
        produkty.setKategoria(kategorie);
        produkty.setName("Jabłko");
        Produkty produkty1 = new Produkty();
        produkty1.setKategoria(kategorie2);
        produkty1.setName("Banan");
        Produkty produkty2 = new Produkty();
        produkty2.setName("Banan2");
        produkty2.setKategoria(kategorie);
        produktyRepository.save(produkty);
        produktyRepository.save(produkty1);
        produktyRepository.save(produkty2);
        Page<Produkty> produkties = produktyRepository.findAllByNameStartsWithAndKategoria(PageRequest.of(0, 3), "Jab", kategorie);
        Assertions.assertEquals(1, produkties.getContent().size());
        Assertions.assertEquals(1, produkties.getTotalElements());
        Assertions.assertEquals(1, produkties.getTotalPages());
        Assertions.assertEquals(produkty, produkties.getContent().get(0));
    }
}

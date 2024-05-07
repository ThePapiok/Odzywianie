package com.Odzywianie.RepositoryTests;

import com.Odzywianie.enums.Gender;
import com.Odzywianie.models.Kategorie;
import com.Odzywianie.models.Produkty;
import com.Odzywianie.models.Uzytkownik;
import com.Odzywianie.models.Zapotrzebowanie;
import com.Odzywianie.repositories.ProduktyRepository;
import com.Odzywianie.repositories.UzytkownikRepository;
import com.Odzywianie.repositories.ZapotrzebowanieRepository;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
public class ZapotrzebowanieRepositoryTest {

    @Autowired
    private ZapotrzebowanieRepository zapotrzebowanieRepository;

    @Autowired
    private UzytkownikRepository uzytkownikRepository;

    @Autowired
    private ProduktyRepository produktyRepository;


    @Test
    public void findProduktyIdsByZapotrzebowanieId() {

        Produkty produkty = new Produkty();
        produkty.setName("Banan");
        produktyRepository.save(produkty);
        Zapotrzebowanie zapotrzebowanie = new Zapotrzebowanie(1L);
        zapotrzebowanie.getZapotrzebowanieProdukty().add(produkty);
        zapotrzebowanieRepository.save(zapotrzebowanie);
        Assertions.assertEquals(produktyRepository.findByName("Banan").getId(), zapotrzebowanieRepository.findProduktyIdsByZapotrzebowanieId(zapotrzebowanie.getId()));
    }

    @Test
    public void findAllByZapotrzebowanieUzytkownikOrderByTimeDesc(){
        Uzytkownik uzytkownik = new Uzytkownik();
        uzytkownikRepository.save(uzytkownik);
        Zapotrzebowanie zapotrzebowanie = new Zapotrzebowanie();
        zapotrzebowanie.setTime(LocalTime.of(15, 40));
        zapotrzebowanie.setZapotrzebowanieUzytkownik(List.of(uzytkownik));
        zapotrzebowanieRepository.save(zapotrzebowanie);
        Zapotrzebowanie zapotrzebowanie2 = new Zapotrzebowanie();
        zapotrzebowanie2.setTime(LocalTime.of(10, 40));
        zapotrzebowanie2.setZapotrzebowanieUzytkownik(List.of(uzytkownik));
        zapotrzebowanieRepository.save(zapotrzebowanie2);
        Zapotrzebowanie zapotrzebowanie3 = new Zapotrzebowanie();
        zapotrzebowanie3.setTime(LocalTime.of(20, 40));
        zapotrzebowanie3.setZapotrzebowanieUzytkownik(List.of(uzytkownik));
        zapotrzebowanieRepository.save(zapotrzebowanie3);
        List<Zapotrzebowanie> zapotrzebowanies = zapotrzebowanieRepository.findAllByZapotrzebowanieUzytkownikOrderByTimeDesc(uzytkownik);
        Assertions.assertEquals(zapotrzebowanies.get(0).getTime(), zapotrzebowanie3.getTime());
        Assertions.assertEquals(zapotrzebowanies.get(1).getTime(), zapotrzebowanie.getTime());
        Assertions.assertEquals(zapotrzebowanies.get(2).getTime(), zapotrzebowanie2.getTime());
    }

    @Test
    public void findAllByZapotrzebowanieUzytkownik(){
        Uzytkownik uzytkownik = new Uzytkownik();
        uzytkownikRepository.save(uzytkownik);
        Uzytkownik uzytkownik1 = new Uzytkownik();
        uzytkownikRepository.save(uzytkownik1);
        Zapotrzebowanie zapotrzebowanie = new Zapotrzebowanie();
        zapotrzebowanie.setZapotrzebowanieUzytkownik(List.of(uzytkownik));
        zapotrzebowanieRepository.save(zapotrzebowanie);
        Zapotrzebowanie zapotrzebowanie2 = new Zapotrzebowanie();
        zapotrzebowanie2.setZapotrzebowanieUzytkownik(List.of(uzytkownik1));
        zapotrzebowanieRepository.save(zapotrzebowanie2);
        Zapotrzebowanie zapotrzebowanie3 = new Zapotrzebowanie();
        zapotrzebowanie3.setZapotrzebowanieUzytkownik(List.of(uzytkownik));
        zapotrzebowanieRepository.save(zapotrzebowanie3);
        List<Zapotrzebowanie> zapotrzebowanies = zapotrzebowanieRepository.findAllByZapotrzebowanieUzytkownik(uzytkownik);
        Assertions.assertEquals(zapotrzebowanies.size(), 2);
        Assertions.assertEquals(zapotrzebowanies.get(0), zapotrzebowanie);
        Assertions.assertEquals(zapotrzebowanies.get(1), zapotrzebowanie3);

    }

}

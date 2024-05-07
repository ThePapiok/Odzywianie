package com.Odzywianie.repositories;


import com.Odzywianie.models.Produkty;
import com.Odzywianie.models.Uzytkownik;
import com.Odzywianie.models.Zapotrzebowanie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ZapotrzebowanieRepository extends JpaRepository<Zapotrzebowanie, Long> {
    void deleteByDateBefore(LocalDate data);
    List<Zapotrzebowanie> findAllByZapotrzebowanieUzytkownikOrderByTimeDesc(Uzytkownik uzytkownik);
    List<Zapotrzebowanie> findAllByZapotrzebowanieUzytkownik(Uzytkownik uzytkownik);


    @Query("SELECT p.id FROM Zapotrzebowanie z JOIN z.zapotrzebowanieProdukty p WHERE z.id = :zapotrzebowanieId")
    Long findProduktyIdsByZapotrzebowanieId(@Param("zapotrzebowanieId") Long zapotrzebowanieId);
}

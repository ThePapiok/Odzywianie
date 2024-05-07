package com.Odzywianie.repositories;

import com.Odzywianie.models.Uzytkownik;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UzytkownikRepository extends JpaRepository<Uzytkownik,Long> {
    Uzytkownik findByName(String name);
}

package com.Odzywianie.repositories;

import com.Odzywianie.models.Kategorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KategorieRepository extends JpaRepository<Kategorie, Long> {
    boolean existsByName(String name);

    Kategorie findByName(String name);
}

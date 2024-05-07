package com.Odzywianie.repositories;

import com.Odzywianie.models.Kategorie;
import com.Odzywianie.models.Produkty;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface ProduktyRepository extends JpaRepository<Produkty,Long> {
    Page<Produkty> findAll(Pageable pageable);
    Page<Produkty> findAllByNameStartsWith(Pageable pageable, String name);


    Page<Produkty> findAllByKategoria(Pageable pageable, Kategorie kategorie);
    Page<Produkty> findAllByNameStartsWithAndKategoria(Pageable pageable, String name, Kategorie kategorie);

    Produkty findByName(String name);

}

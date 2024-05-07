package com.Odzywianie.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Kategorie {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Lob
    private byte[] image;
    @OneToMany(fetch = FetchType.LAZY, mappedBy="kategoria")
    private List<Produkty> produkty;

    public Kategorie(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

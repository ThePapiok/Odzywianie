package com.Odzywianie.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Zapotrzebowanie {

    @Id
    @GeneratedValue
    private Long id;
    private LocalTime time;
    private float weight;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "zapotrzebowanieUzytkownik",
            joinColumns = @JoinColumn(name = "zapotrzebowanie_id"),
            inverseJoinColumns = @JoinColumn(name = "uzytkownik_id"))
    private List<Uzytkownik> zapotrzebowanieUzytkownik = new ArrayList<>();;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "zapotrzebowanieProdukty",
            joinColumns = @JoinColumn(name = "zapotrzebowanie_id"),
            inverseJoinColumns = @JoinColumn(name = "produkty_id"))
    private List<Produkty> zapotrzebowanieProdukty = new ArrayList<>();;
    private LocalDate date;

    public Zapotrzebowanie(Long id) {
        this.id = id;
    }
}

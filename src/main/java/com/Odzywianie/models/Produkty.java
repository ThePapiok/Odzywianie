package com.Odzywianie.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Produkty {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private float averageWeight;
    private int kcal;
    private float fat;
    private float saturatedFat;
    private int cholesterol;
    private int sodium;
    private int potassium;
    private float carbohydrates;
    private float fiber;
    private float sugars;
    private float protein;
    private float ascorbicAcid;
    private float iron;
    private float vitaminB6;
    private int magnesium;
    private int calcium;
    private float vitaminD;
    private float vitaminB12;
    @Lob
    private byte[] image;
    @ManyToMany(mappedBy = "zapotrzebowanieProdukty", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Zapotrzebowanie> zapotrzebowanies = new ArrayList<>();;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "typ_kategoria_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Kategorie kategoria;

    public Produkty(Long id) {
        this.id = id;
    }
}

package com.Odzywianie.models;

import com.Odzywianie.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Uzytkownik {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String password;
    private String role;
    private int weight;
    private int height;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int activity;
    private int age;
    @ManyToMany(mappedBy = "zapotrzebowanieUzytkownik", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Zapotrzebowanie> zapotrzebowanies = new ArrayList<>();;



    public Uzytkownik(String name, String password, String role, int weight, int height, Gender gender, int activity, int age) {
        this.name = name;
        this.password = password;
        this.role = role;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.activity = activity;
        this.age = age;
    }
}

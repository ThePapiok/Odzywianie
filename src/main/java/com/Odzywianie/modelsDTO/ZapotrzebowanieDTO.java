package com.Odzywianie.modelsDTO;

import com.Odzywianie.models.Produkty;
import com.Odzywianie.models.Uzytkownik;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZapotrzebowanieDTO {
    private Long id;
    private String time;
    @Min(value = 1, message = "{message.minZapotrzebowanieWeight")
    private float weight;
    private List<ProduktyDTO> zapotrzebowanieProdukty = new ArrayList<>();;
}

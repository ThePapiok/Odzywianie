package com.Odzywianie.modelsDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProduktyDTO {
    private Long id;
    @NotBlank(message = "{message.blankProduktyName}")
    @Pattern(regexp = "^[a-zA-Z ąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+$", message = "{message.patternProduktyName}")
    private String name;
    @Min(value = 0, message = "{message.minProduktyAverageWeight}")
    private float averageWeight;
    @Min(value = 0, message = "{message.minProduktyKcal}")
    private int kcal;
    @Min(value = 0, message = "{message.minProduktyFat}")
    private float fat;
    @Min(value = 0, message = "{message.minProduktySaturatedFat}")
    private float saturatedFat;
    @Min(value = 0, message = "{message.minProduktyCholesterol}")
    private int cholesterol;
    @Min(value = 0, message = "{message.minProduktySodium}")
    private int sodium;
    @Min(value = 0, message = "{message.minProduktyPotassium}")
    private int potassium;
    @Min(value = 0, message = "{message.minProduktyCarbohydrates}")
    private float carbohydrates;
    @Min(value = 0, message = "{message.minProduktyFiber}")
    private float fiber;
    @Min(value = 0, message = "{message.minProduktySugars}")
    private float sugars;
    @Min(value = 0, message = "{message.minProduktyProtein}")
    private float protein;
    @Min(value = 0, message = "{message.minProduktyAscorbicAcid}")
    private float ascorbicAcid;
    @Min(value = 0, message = "{message.minProduktyIron}")
    private float iron;
    @Min(value = 0, message = "{message.minProduktyVitaminB6}")
    private float vitaminB6;
    @Min(value = 0, message = "{message.minProduktyMagnesium}")
    private int magnesium;
    @Min(value = 0, message = "{message.minProduktyCalcium}")
    private int calcium;
    @Min(value = 0, message = "{message.minProduktyVitaminD}")
    private float vitaminD;
    @Min(value = 0, message = "{message.minProduktyVitaminB12}")
    private float vitaminB12;
    private String kategoria;
    private MultipartFile file;
    private String image;
    private String image_kategoria;

    public ProduktyDTO(Long id) {
        this.id = id;
    }
}

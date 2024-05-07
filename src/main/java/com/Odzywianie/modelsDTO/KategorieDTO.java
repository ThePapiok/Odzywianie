package com.Odzywianie.modelsDTO;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KategorieDTO {

    private Long id;
    @Size(min = 1, message = "{message.sizeKategorieName}")
    private String name;
    private MultipartFile file;
    private String image;

    public KategorieDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

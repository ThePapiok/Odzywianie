package com.Odzywianie.UtilsTests;

import com.Odzywianie.models.Kategorie;
import com.Odzywianie.modelsDTO.KategorieDTO;
import com.Odzywianie.utils.KategorieConverter;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Base64;

public class KategorieConverterTest {

    private KategorieConverter kategorieConverter;

    @BeforeEach
    public void setUp(){
        kategorieConverter = new KategorieConverter();
    }

    @Test
    public void shouldTrueWhenCreateKategorieDTO() {
        Kategorie kategorie = new Kategorie();
        kategorie.setId(0l);
        kategorie.setName("owoce");
        byte[] image = "Test".getBytes();
        kategorie.setImage(image);
        KategorieDTO expectedDTO = new KategorieDTO();
        expectedDTO.setId(0l);
        expectedDTO.setName("owoce");
        expectedDTO.setImage(Base64.getEncoder().encodeToString(image));
        KategorieDTO kategorieDTO = kategorieConverter.createKategorieDTO(kategorie);
        Assertions.assertEquals(kategorieDTO.getId(), expectedDTO.getId());
        Assertions.assertEquals(kategorieDTO.getName(), expectedDTO.getName());
        Assertions.assertArrayEquals(Base64.getDecoder().decode(expectedDTO.getImage()), Base64.getDecoder().decode(kategorieDTO.getImage()));
    }

    @Test
    public void shouldTrueWhenCreateKategorie() throws IOException {
        KategorieDTO kategorieDTO = new KategorieDTO();
        kategorieDTO.setId(0l);
        kategorieDTO.setName("owoce");
        byte[] image = "Test".getBytes();
        kategorieDTO.setFile(new MockMultipartFile("test.jpg", "test.jpg", "image/jpeg", image));
        Kategorie kategorie = kategorieConverter.createKategorie(kategorieDTO);
        Assertions.assertEquals(kategorieDTO.getId(), kategorie.getId());
        Assertions.assertEquals(kategorieDTO.getName(), kategorie.getName());
        Assertions.assertEquals(image, kategorie.getImage());
    }
}

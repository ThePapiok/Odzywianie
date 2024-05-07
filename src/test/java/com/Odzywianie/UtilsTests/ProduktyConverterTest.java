package com.Odzywianie.UtilsTests;

import com.Odzywianie.models.Kategorie;
import com.Odzywianie.models.Produkty;
import com.Odzywianie.modelsDTO.ProduktyDTO;
import com.Odzywianie.services.KategorieService;
import com.Odzywianie.utils.ProduktyConverter;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Base64;

public class ProduktyConverterTest {

    private ProduktyConverter produktyConverter;
    @Mock
    private KategorieService kategorieService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        produktyConverter = new ProduktyConverter(kategorieService);
    }

    @Test
    public void shouldTrueWhenCreateProdukty() throws IOException {
        Kategorie kategorie = new Kategorie(0l, "owoce");
        ProduktyDTO produktyDTO = new ProduktyDTO();
        produktyDTO.setName("Banan");
        produktyDTO.setAverageWeight(100);
        produktyDTO.setKcal(101);
        produktyDTO.setFat(102);
        produktyDTO.setSaturatedFat(103);
        produktyDTO.setCholesterol(104);
        produktyDTO.setSodium(105);
        produktyDTO.setPotassium(106);
        produktyDTO.setCarbohydrates(107);
        produktyDTO.setFiber(108);
        produktyDTO.setSugars(109);
        produktyDTO.setProtein(110);
        produktyDTO.setAscorbicAcid(111);
        produktyDTO.setIron(112);
        produktyDTO.setVitaminB6(113);
        produktyDTO.setMagnesium(114);
        produktyDTO.setCalcium(115);
        produktyDTO.setVitaminD(116);
        produktyDTO.setVitaminB12(117);
        byte[] image = "Test".getBytes();
        produktyDTO.setFile(new MockMultipartFile("test.jpg", "test.jpg", "image/jpeg", image));
        produktyDTO.setKategoria("owoce");
        Mockito.when(kategorieService.getKategoriaByName("owoce")).thenReturn(kategorie);
        Produkty produkty = produktyConverter.createProdukty(produktyDTO);
        Assertions.assertEquals(produkty.getName(), produktyDTO.getName());
        Assertions.assertEquals(produkty.getAverageWeight(), produktyDTO.getAverageWeight());
        Assertions.assertEquals(produkty.getKcal(), produktyDTO.getKcal());
        Assertions.assertEquals(produkty.getFat(), produktyDTO.getFat());
        Assertions.assertEquals(produkty.getSaturatedFat(), produktyDTO.getSaturatedFat());
        Assertions.assertEquals(produkty.getCholesterol(), produktyDTO.getCholesterol());
        Assertions.assertEquals(produkty.getSodium(), produktyDTO.getSodium());
        Assertions.assertEquals(produkty.getPotassium(), produktyDTO.getPotassium());
        Assertions.assertEquals(produkty.getCarbohydrates(), produktyDTO.getCarbohydrates());
        Assertions.assertEquals(produkty.getFiber(), produktyDTO.getFiber());
        Assertions.assertEquals(produkty.getSugars(), produktyDTO.getSugars());
        Assertions.assertEquals(produkty.getProtein(), produktyDTO.getProtein());
        Assertions.assertEquals(produkty.getAscorbicAcid(), produktyDTO.getAscorbicAcid());
        Assertions.assertEquals(produkty.getIron(), produktyDTO.getIron());
        Assertions.assertEquals(produkty.getVitaminB6(), produktyDTO.getVitaminB6());
        Assertions.assertEquals(produkty.getMagnesium(), produktyDTO.getMagnesium());
        Assertions.assertEquals(produkty.getCalcium(), produktyDTO.getCalcium());
        Assertions.assertEquals(produkty.getVitaminD(), produktyDTO.getVitaminD());
        Assertions.assertEquals(produkty.getVitaminB12(), produktyDTO.getVitaminB12());
        Assertions.assertEquals(produkty.getKategoria(), kategorie);
        Assertions.assertEquals(produkty.getImage(), image);
    }

    @Test
    public void shouldTrueWhenCreateProduktyDTO(){
        Kategorie kategorie = new Kategorie();
        byte[] imageKategoria = "Test2".getBytes();
        kategorie.setImage(imageKategoria);
        kategorie.setId(0l);
        kategorie.setName("owoce");
        Produkty produkty = new Produkty();
        produkty.setName("Banan");
        produkty.setId(0l);
        produkty.setAverageWeight(100);
        produkty.setKcal(101);
        produkty.setFat(102);
        produkty.setSaturatedFat(103);
        produkty.setCholesterol(104);
        produkty.setSodium(105);
        produkty.setPotassium(106);
        produkty.setCarbohydrates(107);
        produkty.setFiber(108);
        produkty.setSugars(109);
        produkty.setProtein(110);
        produkty.setAscorbicAcid(111);
        produkty.setIron(112);
        produkty.setVitaminB6(113);
        produkty.setMagnesium(114);
        produkty.setCalcium(115);
        produkty.setVitaminD(116);
        produkty.setVitaminB12(117);
        byte[] image = "Test".getBytes();
        produkty.setImage(image);
        produkty.setKategoria(kategorie);
        ProduktyDTO expectedDTO = new ProduktyDTO();
        expectedDTO.setName("Banan");
        expectedDTO.setAverageWeight(100);
        expectedDTO.setKcal(101);
        expectedDTO.setFat(102);
        expectedDTO.setId(0l);
        expectedDTO.setSaturatedFat(103);
        expectedDTO.setCholesterol(104);
        expectedDTO.setSodium(105);
        expectedDTO.setPotassium(106);
        expectedDTO.setCarbohydrates(107);
        expectedDTO.setFiber(108);
        expectedDTO.setSugars(109);
        expectedDTO.setProtein(110);
        expectedDTO.setAscorbicAcid(111);
        expectedDTO.setIron(112);
        expectedDTO.setVitaminB6(113);
        expectedDTO.setMagnesium(114);
        expectedDTO.setCalcium(115);
        expectedDTO.setVitaminD(116);
        expectedDTO.setVitaminB12(117);
        expectedDTO.setImage(Base64.getEncoder().encodeToString(image));
        expectedDTO.setImage_kategoria(Base64.getEncoder().encodeToString(imageKategoria));
        expectedDTO.setKategoria("owoce");
        ProduktyDTO produktyDTO = produktyConverter.createProduktyDTO(produkty);
        Assertions.assertEquals(expectedDTO.getName(), produktyDTO.getName());
        Assertions.assertEquals(expectedDTO.getAverageWeight(), produktyDTO.getAverageWeight());
        Assertions.assertEquals(expectedDTO.getKcal(), produktyDTO.getKcal());
        Assertions.assertEquals(expectedDTO.getFat(), produktyDTO.getFat());
        Assertions.assertEquals(expectedDTO.getSaturatedFat(), produktyDTO.getSaturatedFat());
        Assertions.assertEquals(expectedDTO.getCholesterol(), produktyDTO.getCholesterol());
        Assertions.assertEquals(expectedDTO.getSodium(), produktyDTO.getSodium());
        Assertions.assertEquals(expectedDTO.getPotassium(), produktyDTO.getPotassium());
        Assertions.assertEquals(expectedDTO.getCarbohydrates(), produktyDTO.getCarbohydrates());
        Assertions.assertEquals(expectedDTO.getFiber(), produktyDTO.getFiber());
        Assertions.assertEquals(expectedDTO.getSugars(), produktyDTO.getSugars());
        Assertions.assertEquals(expectedDTO.getProtein(), produktyDTO.getProtein());
        Assertions.assertEquals(expectedDTO.getAscorbicAcid(), produktyDTO.getAscorbicAcid());
        Assertions.assertEquals(expectedDTO.getIron(), produktyDTO.getIron());
        Assertions.assertEquals(expectedDTO.getVitaminB6(), produktyDTO.getVitaminB6());
        Assertions.assertEquals(expectedDTO.getMagnesium(), produktyDTO.getMagnesium());
        Assertions.assertEquals(expectedDTO.getCalcium(), produktyDTO.getCalcium());
        Assertions.assertEquals(expectedDTO.getVitaminD(), produktyDTO.getVitaminD());
        Assertions.assertEquals(expectedDTO.getVitaminB12(), produktyDTO.getVitaminB12());
        Assertions.assertEquals(expectedDTO.getId(), produktyDTO.getId());
        Assertions.assertArrayEquals(Base64.getDecoder().decode(expectedDTO.getImage()), Base64.getDecoder().decode(produktyDTO.getImage()));
        Assertions.assertArrayEquals(Base64.getDecoder().decode(expectedDTO.getImage_kategoria()), Base64.getDecoder().decode(produktyDTO.getImage_kategoria()));
        Assertions.assertEquals(expectedDTO.getKategoria(), produktyDTO.getKategoria());



    }
}

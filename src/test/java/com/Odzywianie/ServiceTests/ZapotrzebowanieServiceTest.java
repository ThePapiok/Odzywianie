package com.Odzywianie.ServiceTests;

import com.Odzywianie.enums.Gender;
import com.Odzywianie.models.Kategorie;
import com.Odzywianie.models.Produkty;
import com.Odzywianie.models.Uzytkownik;
import com.Odzywianie.models.Zapotrzebowanie;
import com.Odzywianie.modelsDTO.ProduktyDTO;
import com.Odzywianie.modelsDTO.UzytkownikDTO;
import com.Odzywianie.modelsDTO.ZapotrzebowanieDTO;
import com.Odzywianie.repositories.UzytkownikRepository;
import com.Odzywianie.repositories.ZapotrzebowanieRepository;
import com.Odzywianie.services.ProduktyService;
import com.Odzywianie.services.UzytkownikService;
import com.Odzywianie.services.ZapotrzebowanieService;
import com.Odzywianie.utils.UzytkownikConverter;
import com.Odzywianie.utils.ZapotrzebowanieConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class ZapotrzebowanieServiceTest {

    private ZapotrzebowanieService zapotrzebowanieService;
    @Mock
    private ProduktyService produktyService;
    @Mock
    private UzytkownikService uzytkownikService;
    @Mock
    private ZapotrzebowanieRepository zapotrzebowanieRepository;
    @Mock
    private ZapotrzebowanieConverter zapotrzebowanieConverter;

    private UzytkownikDTO uzytkownikDTO;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);
        zapotrzebowanieService = new ZapotrzebowanieService(produktyService, uzytkownikService, zapotrzebowanieRepository, zapotrzebowanieConverter);
        uzytkownikDTO = new UzytkownikDTO();
        uzytkownikDTO.setName("Test");
        uzytkownikDTO.setWeight(80);
        uzytkownikDTO.setHeight(180);
        uzytkownikDTO.setAge(21);
        uzytkownikDTO.setGender(Gender.MAN);
        uzytkownikDTO.setActivity(3);
    }


    @Test
    public void shouldTrueCalculateKcal(){
        Mockito.when(uzytkownikService.getUzytkownikDTOByAuthentication()).thenReturn(uzytkownikDTO);
        Assertions.assertEquals(zapotrzebowanieService.calculateKcal(), 3148.125f);
    }

    @Test
    public void shouldTrueCalculateFat(){
        Mockito.when(uzytkownikService.getUzytkownikDTOByAuthentication()).thenReturn(uzytkownikDTO);
        List<Float> expectedFat = List.of(629.625f/9, 1101.84375f/9);
        List<Float> fat = zapotrzebowanieService.calculateFat(3148.125f);
        Assertions.assertEquals(fat.get(0), expectedFat.get(0));
        Assertions.assertEquals(fat.get(1), expectedFat.get(1));
    }

    @Test
    public void shouldTrueCalculateSaturatedFat(){
        Mockito.when(uzytkownikService.getUzytkownikDTOByAuthentication()).thenReturn(uzytkownikDTO);
        List<Float> expectedSaturatedFat = List.of(20.9875f, 314.8125f/9);
        List<Float> saturatedFat = zapotrzebowanieService.calculateSaturatedFat(3148.125f);
        Assertions.assertEquals(saturatedFat.get(0), expectedSaturatedFat.get(0));
        Assertions.assertEquals(saturatedFat.get(1), expectedSaturatedFat.get(1));

    }

    @Test
    public void shouldTrueCalculateSodium(){
        Mockito.when(uzytkownikService.getUzytkownikDTOByAuthentication()).thenReturn(uzytkownikDTO);
        List<Float> expectedSodium = List.of(1500F, 2300F);
        List<Float> sodium = zapotrzebowanieService.calculateSodium();
        Assertions.assertEquals(sodium.get(0), expectedSodium.get(0));
        Assertions.assertEquals(sodium.get(1), expectedSodium.get(1));
    }

    @Test
    public void shouldTrueCalculatePotasium(){
        Mockito.when(uzytkownikService.getUzytkownikDTOByAuthentication()).thenReturn(uzytkownikDTO);
        List<Float> expectedPotasium = List.of(2500F, 3800F);
        List<Float> potasium = zapotrzebowanieService.calculatePotasium();
        Assertions.assertEquals(potasium.get(0), expectedPotasium.get(0));
        Assertions.assertEquals(potasium.get(1), expectedPotasium.get(1));
    }

    @Test
    public void shouldTrueCalculateCarbohydrates(){
        Mockito.when(uzytkownikService.getUzytkownikDTOByAuthentication()).thenReturn(uzytkownikDTO);
        List<Float> expectedCarbohydrates = List.of(1416.65625f/4, (3148.125f*0.65f)/4);
        List<Float> carbohydrates = zapotrzebowanieService.calculateCarbohydrates(3148.125f);
        Assertions.assertEquals(carbohydrates.get(0), expectedCarbohydrates.get(0));
        Assertions.assertEquals(carbohydrates.get(1), expectedCarbohydrates.get(1));
    }

    @Test
    public void shouldTrueCalculateFiber(){
        Mockito.when(uzytkownikService.getUzytkownikDTOByAuthentication()).thenReturn(uzytkownikDTO);
        List<Float> expectedFiber = List.of((3148.125f/1000f)*14, (3148.125f/1000f)*25);
        List<Float> fiber = zapotrzebowanieService.calculateFiber(3148.125f);
        Assertions.assertEquals(fiber.get(0), expectedFiber.get(0));
        Assertions.assertEquals(fiber.get(1), expectedFiber.get(1));
    }

    @Test
    public void shouldTrueCalculateSugars(){
        Mockito.when(uzytkownikService.getUzytkownikDTOByAuthentication()).thenReturn(uzytkownikDTO);
        List<Float> expectedProteins = List.of(80f*0.8f, 80f*2.2f);
        List<Float> proteins = zapotrzebowanieService.calculateProteins();
        Assertions.assertEquals(proteins.get(0), expectedProteins.get(0));
        Assertions.assertEquals(proteins.get(1), expectedProteins.get(1));
    }

    @Test
    public void shouldTrueCalculateAscorbicAcid(){
        Mockito.when(uzytkownikService.getUzytkownikDTOByAuthentication()).thenReturn(uzytkownikDTO);
        List<Float> expectedAscorbicAcid = List.of(90f, 2000F);
        List<Float> ascorbicAcid = zapotrzebowanieService.calculateAscorbicAcid();
        Assertions.assertEquals(ascorbicAcid.get(0), expectedAscorbicAcid.get(0));
        Assertions.assertEquals(ascorbicAcid.get(1), expectedAscorbicAcid.get(1));
    }

    @Test
    public void shouldTrueCalculateIron(){
        Mockito.when(uzytkownikService.getUzytkownikDTOByAuthentication()).thenReturn(uzytkownikDTO);
        List<Float> expectedIron = List.of(8f, 45F);
        List<Float> iron = zapotrzebowanieService.calculateIron();
        Assertions.assertEquals(iron.get(0), expectedIron.get(0));
        Assertions.assertEquals(iron.get(1), expectedIron.get(1));
    }

    @Test
    public void shouldTrueCalculateVitaminB6(){
        Mockito.when(uzytkownikService.getUzytkownikDTOByAuthentication()).thenReturn(uzytkownikDTO);
        List<Float> expectedVitaminB6 = List.of(1.3f, 100F);
        List<Float> vitaminB6 = zapotrzebowanieService.calculateVitaminB6();
        Assertions.assertEquals(vitaminB6.get(0), expectedVitaminB6.get(0));
        Assertions.assertEquals(vitaminB6.get(1), expectedVitaminB6.get(1));
    }

    @Test
    public void shouldTrueCalculateMagnesium(){
        Mockito.when(uzytkownikService.getUzytkownikDTOByAuthentication()).thenReturn(uzytkownikDTO);
        Assertions.assertEquals(zapotrzebowanieService.calculateMagnesium(), 400f);
    }

    @Test
    public void shouldTrueCalculateCalcium(){
        Mockito.when(uzytkownikService.getUzytkownikDTOByAuthentication()).thenReturn(uzytkownikDTO);
        List<Float> expectedCalcium = List.of(1000f, 2500F);
        List<Float> calcium = zapotrzebowanieService.calculateCalcium();
        Assertions.assertEquals(calcium.get(0), expectedCalcium.get(0));
        Assertions.assertEquals(calcium.get(1), expectedCalcium.get(1));
    }

    @Test
    public void shouldTrueCalculateVitaminD(){
        Mockito.when(uzytkownikService.getUzytkownikDTOByAuthentication()).thenReturn(uzytkownikDTO);
        List<Float> expectedVitaminD = List.of(600f, 4000F);
        List<Float> vitaminD = zapotrzebowanieService.calculateVitaminD();
        Assertions.assertEquals(vitaminD.get(0), expectedVitaminD.get(0));
        Assertions.assertEquals(vitaminD.get(1), expectedVitaminD.get(1));
    }

    @Test
    public void shouldTrueCalculateVitaminB12(){
        Mockito.when(uzytkownikService.getUzytkownikDTOByAuthentication()).thenReturn(uzytkownikDTO);
        Assertions.assertEquals(zapotrzebowanieService.calculateVitaminB12(), 2.4f);
    }

    @Test
    public void shouldTrueCalculateCholesterol(){
        Mockito.when(uzytkownikService.getUzytkownikDTOByAuthentication()).thenReturn(uzytkownikDTO);
        List<Float> expectedCholesterol = List.of(0f, 300F);
        List<Float> cholesterol = zapotrzebowanieService.calculateCholesterol();
        Assertions.assertEquals(cholesterol.get(0), expectedCholesterol.get(0));
        Assertions.assertEquals(cholesterol.get(1), expectedCholesterol.get(1));
    }

    @Test
    public void shouldTrueWhenGetNutreins()
    {
        Produkty produkty = new Produkty();
        produkty.setKategoria(new Kategorie(0l, "owoce"));
        produkty.setName("banan");
        produkty.setCalcium(1);
        produkty.setFat(1);
        produkty.setIron(1);
        produkty.setFiber(1);
        produkty.setAscorbicAcid(1);
        produkty.setAverageWeight(1);
        produkty.setCarbohydrates(1);
        produkty.setCholesterol(1);
        produkty.setMagnesium(1);
        produkty.setKcal(1);
        produkty.setPotassium(1);
        produkty.setProtein(1);
        produkty.setSaturatedFat(1);
        produkty.setSodium(1);
        produkty.setSugars(1);
        produkty.setVitaminB6(1);
        produkty.setVitaminB12(1);
        produkty.setVitaminD(1);
        Uzytkownik uzytkownik = new Uzytkownik();
        List<Zapotrzebowanie> zapotrzebowanies = List.of(new Zapotrzebowanie(0l, LocalTime.now(), 100, List.of(uzytkownik), List.of(produkty), LocalDate.now()));
        Mockito.when(uzytkownikService.getUzytkownikByAuthentication()).thenReturn(uzytkownik);
        Mockito.when(zapotrzebowanieRepository.findAllByZapotrzebowanieUzytkownik(uzytkownik)).thenReturn(zapotrzebowanies);
        List<Float> expectedNutreins = List.of(1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f);
        List<Float> nutreins = zapotrzebowanieService.getNutreins();
        Assertions.assertEquals(expectedNutreins.get(0), nutreins.get(0));
        Assertions.assertEquals(expectedNutreins.get(1), nutreins.get(1));
        Assertions.assertEquals(expectedNutreins.get(2), nutreins.get(2));
        Assertions.assertEquals(expectedNutreins.get(3), nutreins.get(3));
        Assertions.assertEquals(expectedNutreins.get(4), nutreins.get(4));
        Assertions.assertEquals(expectedNutreins.get(5), nutreins.get(5));
        Assertions.assertEquals(expectedNutreins.get(6), nutreins.get(6));
        Assertions.assertEquals(expectedNutreins.get(7), nutreins.get(7));
        Assertions.assertEquals(expectedNutreins.get(8), nutreins.get(8));
        Assertions.assertEquals(expectedNutreins.get(9), nutreins.get(9));
        Assertions.assertEquals(expectedNutreins.get(10), nutreins.get(10));
        Assertions.assertEquals(expectedNutreins.get(11), nutreins.get(11));
        Assertions.assertEquals(expectedNutreins.get(12), nutreins.get(12));
        Assertions.assertEquals(expectedNutreins.get(13), nutreins.get(13));
        Assertions.assertEquals(expectedNutreins.get(14), nutreins.get(14));
        Assertions.assertEquals(expectedNutreins.get(15), nutreins.get(15));
        Assertions.assertEquals(expectedNutreins.get(16), nutreins.get(16));
    }

    @Test
    public void shouldTrueWhenGetZapotrzebowanieDTO()
    {
        Uzytkownik uzytkownik = new Uzytkownik();
        Produkty produkty = new Produkty();
        produkty.setKategoria(new Kategorie(0l, "owoce"));
        produkty.setName("banan");
        produkty.setCalcium(1);
        produkty.setFat(1);
        produkty.setIron(1);
        produkty.setFiber(1);
        produkty.setAscorbicAcid(1);
        produkty.setAverageWeight(1);
        produkty.setCarbohydrates(1);
        produkty.setCholesterol(1);
        produkty.setMagnesium(1);
        produkty.setKcal(1);
        produkty.setPotassium(1);
        produkty.setProtein(1);
        produkty.setSaturatedFat(1);
        produkty.setSodium(1);
        produkty.setSugars(1);
        produkty.setVitaminB6(1);
        produkty.setVitaminB12(1);
        produkty.setVitaminD(1);
        ProduktyDTO produktyDTO = new ProduktyDTO();
        produktyDTO.setKategoria("owoce");
        produktyDTO.setName("banan");
        produktyDTO.setCalcium(1);
        produktyDTO.setFat(1);
        produktyDTO.setIron(1);
        produktyDTO.setFiber(1);
        produktyDTO.setAscorbicAcid(1);
        produktyDTO.setAverageWeight(1);
        produktyDTO.setCarbohydrates(1);
        produktyDTO.setCholesterol(1);
        produktyDTO.setMagnesium(1);
        produktyDTO.setKcal(1);
        produktyDTO.setPotassium(1);
        produktyDTO.setProtein(1);
        produktyDTO.setSaturatedFat(1);
        produktyDTO.setSodium(1);
        produktyDTO.setSugars(1);
        produktyDTO.setVitaminB6(1);
        produktyDTO.setVitaminB12(1);
        produktyDTO.setVitaminD(1);
        List<ZapotrzebowanieDTO> zapotrzebowanieDTOS = List.of(new ZapotrzebowanieDTO(0l, LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")), 100, List.of(produktyDTO)));
        List<Zapotrzebowanie> zapotrzebowanies = List.of(new Zapotrzebowanie(0l, LocalTime.now(), 100, List.of(uzytkownik), List.of(produkty), LocalDate.now()));
        Mockito.when(uzytkownikService.getUzytkownikByAuthentication()).thenReturn(uzytkownik);
        Mockito.when(zapotrzebowanieRepository.findAllByZapotrzebowanieUzytkownikOrderByTimeDesc(uzytkownik)).thenReturn(zapotrzebowanies);
        Mockito.when(zapotrzebowanieConverter.createZapotrzebowanieDTO(zapotrzebowanies)).thenReturn(zapotrzebowanieDTOS);
        List <ZapotrzebowanieDTO> zapotrzebowanies1 = zapotrzebowanieService.getZapotrzebowanieDTO();
        Assertions.assertEquals(zapotrzebowanies1.get(0).getWeight(), zapotrzebowanieDTOS.get(0).getWeight());
        Assertions.assertEquals(zapotrzebowanies1.get(0).getTime(), zapotrzebowanieDTOS.get(0).getTime());
        Assertions.assertEquals(zapotrzebowanies1.get(0).getZapotrzebowanieProdukty(), zapotrzebowanieDTOS.get(0).getZapotrzebowanieProdukty());
    }


    @Test
    public void shoudlTrueExistsZapotrzebowanieById(){
        Mockito.when(zapotrzebowanieRepository.existsById(0l)).thenReturn(true);
        Assertions.assertTrue(zapotrzebowanieService.existsZapotrzebowanieById(0l));
    }

    @Test
    public void shouldTrueWhenEditZapotrzebowanie(){
        Zapotrzebowanie zapotrzebowanie = new Zapotrzebowanie();
        Mockito.when(zapotrzebowanieRepository.findById(0l)).thenReturn(Optional.of(zapotrzebowanie));
        zapotrzebowanieService.editZapotrzebowanie(0l, 80, LocalTime.of(10, 40));
        Assertions.assertEquals(zapotrzebowanie.getWeight(), 80);
        Assertions.assertEquals(zapotrzebowanie.getTime(), LocalTime.of(10, 40));
    }

    @Test
    public void shouldTrueWhenGetIdProduktyByIdZapotrzebowanie(){
        Mockito.when(zapotrzebowanieRepository.findProduktyIdsByZapotrzebowanieId(0l)).thenReturn(1l);
        Assertions.assertEquals(1l, zapotrzebowanieService.getIdProduktByIdZapotrzebowanie(0l));
    }

}

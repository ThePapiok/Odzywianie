package com.Odzywianie.ServiceTests;

import com.Odzywianie.models.Kategorie;
import com.Odzywianie.modelsDTO.KategorieDTO;
import com.Odzywianie.repositories.KategorieRepository;
import com.Odzywianie.repositories.ProduktyRepository;
import com.Odzywianie.services.KategorieService;
import com.Odzywianie.services.ProduktyService;
import com.Odzywianie.utils.KategorieConverter;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class KategorieServiceTest {

    private KategorieService kategorieService;

    @Mock
    private KategorieRepository kategorieRepository;
    @Mock
    private KategorieConverter kategorieConverter;

    @Mock
    private ProduktyRepository produktyRepository;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);
        kategorieService = new KategorieService(kategorieConverter, kategorieRepository, produktyRepository);
    }

    @Test
    public void getKategorieDTOTrue()
    {
        List<KategorieDTO> kategorieDTOS = List.of(new KategorieDTO(1L,"owoce"), new KategorieDTO(2L, "warzywa"));
        Mockito.when(kategorieConverter.createKategorieDTO(kategorieRepository.findAll())).thenReturn(kategorieDTOS);
        List<KategorieDTO> kategorieDTOS1 =  kategorieService.getKategorieDTO();
        Assertions.assertEquals(kategorieDTOS, kategorieDTOS1);
    }

    @Test
    public void getKategorieDTOFalse()
    {
        List<KategorieDTO> kategorieDTOS = List.of(new KategorieDTO(1L,"owoce"), new KategorieDTO(2L, "warzywa"));
        Mockito.when(kategorieConverter.createKategorieDTO(kategorieRepository.findAll())).thenReturn(null);
        List<KategorieDTO> kategorieDTOS1 =  kategorieService.getKategorieDTO();
        Assertions.assertNotEquals(kategorieDTOS, kategorieDTOS1);
    }


    @Test
    public void existsKategoriaByNameTrue() {
        Mockito.when(kategorieRepository.existsByName("owoce")).thenReturn(true);
        Assertions.assertEquals(true, kategorieService.existsKategoriaByName("owoce"));
    }

    @Test
    public void existsKategoriaByNameFalse() {
        Mockito.when(kategorieRepository.existsByName("owoce")).thenReturn(false);
        Assertions.assertNotEquals(true, kategorieService.existsKategoriaByName("owoce"));
    }

    @Test
    public void existsKategoriaByIdTrue(){
        Mockito.when(kategorieRepository.existsById(1L)).thenReturn(true);
        Assertions.assertEquals(true, kategorieService.existsKategoriaById(1L));
    }

    @Test
    public void existsKategoriaByIdFalse(){
        Mockito.when(kategorieRepository.existsById(1L)).thenReturn(false);
        Assertions.assertNotEquals(true, kategorieService.existsKategoriaById(1L));
    }

    @Test
    public void getNamesOfKategorieTrue()  {
        List<String> names = List.of("owoce", "warzywa");
        List<KategorieDTO> kategorieDTOS = List.of(new KategorieDTO(1L, "owoce"), new KategorieDTO(2L, "warzywa"));
        Mockito.when(kategorieConverter.createKategorieDTO(kategorieRepository.findAll())).thenReturn(kategorieDTOS);
        Assertions.assertEquals(kategorieService.getNamesOfKategorie(), names);
    }

    @Test
    public void getNamesOfKategorieFalse()  {
        List<String> names = List.of("owoce");
        List<KategorieDTO> kategorieDTOS = List.of(new KategorieDTO(1L, "owoce"), new KategorieDTO(2L, "warzywa"));
        Mockito.when(kategorieConverter.createKategorieDTO(kategorieRepository.findAll())).thenReturn(kategorieDTOS);
        Assertions.assertNotEquals(kategorieService.getNamesOfKategorie(), names);
    }

    @Test
    public void getKategoriaByNameTrue()  {
        Kategorie kategorie = new Kategorie(1L, "owoce");
        Mockito.when(kategorieRepository.findByName("owoce")).thenReturn(kategorie);
        Assertions.assertEquals(kategorieService.getKategoriaByName("owoce"), kategorie);
    }

    @Test
    public void getKategoriaByNameFalse()  {
        Kategorie kategorie = new Kategorie(1L, "owoce");
        Mockito.when(kategorieRepository.findByName("warzywa")).thenReturn(null);
        Assertions.assertNotEquals(kategorieService.getKategoriaByName("owoce"), kategorie);
    }









}

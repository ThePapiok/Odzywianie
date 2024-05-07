package com.Odzywianie.ServiceTests;

import com.Odzywianie.models.Kategorie;
import com.Odzywianie.models.Produkty;
import com.Odzywianie.modelsDTO.ProduktyDTO;
import com.Odzywianie.repositories.ProduktyRepository;
import com.Odzywianie.services.KategorieService;
import com.Odzywianie.services.ProduktyService;
import com.Odzywianie.utils.ProduktyConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ProduktyServiceTest {
    private ProduktyService produktyService;
    @Mock
    private ProduktyConverter produktyConverter;
    @Mock
    private ProduktyRepository produktyRepository;
    @Mock
    private KategorieService kategorieService;
    private Page<Produkty> produkties;
    private List<Produkty> produktyList;
    private Pageable pageable;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);
        produktyService = new ProduktyService(produktyRepository, produktyConverter, kategorieService);
        produktyList = List.of(new Produkty(0l), new Produkty(1l));
        produkties = new Page<Produkty>() {
            @Override
            public int getTotalPages() {
                return 10;
            }

            @Override
            public long getTotalElements() {
                return 2;
            }

            @Override
            public <U> Page<U> map(Function<? super Produkty, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 5;
            }

            @Override
            public int getSize() {
                return 2;
            }

            @Override
            public int getNumberOfElements() {
                return 2;
            }

            @Override
            public List<Produkty> getContent() {
                return produktyList;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<Produkty> iterator() {
                return null;
            }
        };
        pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 0;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public Pageable withPage(int pageNumber) {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };
    }

    @Test
    public void shouldTrueWhenGetProduktyWithoutCategory()
    {
        Mockito.when(produktyRepository.findAll(pageable)).thenReturn(produkties);
        Assertions.assertEquals(produkties, produktyService.getProdukty(pageable,"-"));
    }

    @Test
    public void shouldTrueWhenGetProduktyWithCategory()
    {
        Kategorie kategorie = new Kategorie(0l, "owoce");
        Mockito.when(produktyRepository.findAllByKategoria(pageable, kategorie)).thenReturn(produkties);
        Mockito.when(kategorieService.getKategoriaByName("owoce")).thenReturn(kategorie);
        Assertions.assertEquals(produkties, produktyService.getProdukty(pageable,"owoce"));
    }

    @Test
    public void shouldTrueWhenGetProduktyStartsWithWithoutCategory()
    {
        Mockito.when(produktyRepository.findAllByNameStartsWith(pageable, "Ban")).thenReturn(produkties);
        Assertions.assertEquals(produkties, produktyService.getProduktyStartsWith(pageable,"Ban", "-"));
    }

    @Test
    public void shouldTrueWhenGetProduktyStartsWithWithCategory(){
        Kategorie kategorie = new Kategorie(0l, "owoce");
        Mockito.when(produktyRepository.findAllByNameStartsWithAndKategoria(pageable, "Ban", kategorie)).thenReturn(produkties);
        Mockito.when(kategorieService.getKategoriaByName("owoce")).thenReturn(kategorie);
        Assertions.assertEquals(produkties, produktyService.getProduktyStartsWith(pageable,"Ban","owoce"));
    }

    @Test
    public void shouldTrueWhenGetProdukty(){
        List<ProduktyDTO> produktyDTOS = List.of(new ProduktyDTO(0l), new ProduktyDTO(1l));
        Mockito.when(produktyConverter.createProduktyDTO(produktyList)).thenReturn(produktyDTOS);
        Assertions.assertEquals(produktyDTOS, produktyService.getProdukty(produktyList));
    }

    @Test
    public void shouldTrueWhenGetPagesBefore(){
        List<String> pages = List.of("...", "2", "3", "4");
        Assertions.assertEquals(pages, produktyService.getPagesBefore(produkties));

    }

    @Test
    public void shouldTrueWhenGetPagesAfter(){
        List<String> pages = List.of("6", "7", "8", "...");
        Assertions.assertEquals(pages, produktyService.getPagesAfter(produkties));

    }

    @Test
    public void shouldTrueWhenEditProdukt() throws IOException {
        Kategorie kategorie = new Kategorie(0l, "owoce");
        ProduktyDTO produktyDTO = new ProduktyDTO();
        produktyDTO.setCalcium(1);
        produktyDTO.setCholesterol(1);
        produktyDTO.setMagnesium(1);
        produktyDTO.setFat(1);
        produktyDTO.setFiber(1);
        produktyDTO.setAscorbicAcid(1);
        produktyDTO.setIron(1);
        produktyDTO.setProtein(1);
        produktyDTO.setId(0l);
        produktyDTO.setKcal(1);
        produktyDTO.setSugars(1);
        produktyDTO.setPotassium(1);
        produktyDTO.setKategoria("owoce");
        produktyDTO.setName("Banan");
        produktyDTO.setSodium(1);
        produktyDTO.setSaturatedFat(1);
        produktyDTO.setVitaminB6(1);
        produktyDTO.setVitaminB12(1);
        produktyDTO.setVitaminD(1);
        produktyDTO.setAverageWeight(1);
        produktyDTO.setCarbohydrates(1);
        produktyDTO.setFile(new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Test".getBytes()));
        Produkty produkty = new Produkty(0l);
        Mockito.when(produktyRepository.findById(0l)).thenReturn(Optional.of(produkty));
        Mockito.when(kategorieService.getKategoriaByName(produktyDTO.getKategoria())).thenReturn(kategorie);
        produktyService.editProdukt(produktyDTO, 0l);
        Assertions.assertEquals(produkty.getCalcium(), produktyDTO.getCalcium());
        Assertions.assertEquals(produkty.getCholesterol(), produktyDTO.getCholesterol());
        Assertions.assertEquals(produkty.getMagnesium(), produktyDTO.getMagnesium());
        Assertions.assertEquals(produkty.getFat(), produktyDTO.getFat());
        Assertions.assertEquals(produkty.getFiber(), produktyDTO.getFiber());
        Assertions.assertEquals(produkty.getAscorbicAcid(), produktyDTO.getAscorbicAcid());
        Assertions.assertEquals(produkty.getIron(), produktyDTO.getIron());
        Assertions.assertEquals(produkty.getProtein(), produktyDTO.getProtein());
        Assertions.assertEquals(produkty.getKcal(), produktyDTO.getKcal());
        Assertions.assertEquals(produkty.getSugars(), produktyDTO.getSugars());
        Assertions.assertEquals(produkty.getPotassium(), produktyDTO.getPotassium());
        Assertions.assertEquals(produkty.getKategoria().getName(), produktyDTO.getKategoria());
        Assertions.assertEquals(produkty.getName(), produktyDTO.getName());
        Assertions.assertEquals(produkty.getSodium(), produktyDTO.getSodium());
        Assertions.assertEquals(produkty.getSaturatedFat(), produktyDTO.getSaturatedFat());
        Assertions.assertEquals(produkty.getVitaminB6(), produktyDTO.getVitaminB6());
        Assertions.assertEquals(produkty.getVitaminB12(), produktyDTO.getVitaminB12());
        Assertions.assertEquals(produkty.getAverageWeight(), produktyDTO.getAverageWeight());
        Assertions.assertEquals(produkty.getCarbohydrates(), produktyDTO.getCarbohydrates());
    }


}

package com.Odzywianie.utils;

import com.Odzywianie.models.Produkty;
import com.Odzywianie.modelsDTO.ProduktyDTO;
import com.Odzywianie.services.KategorieService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class ProduktyConverter {

    private final KategorieService kategorieService;

    public ProduktyConverter(KategorieService kategorieService) {
        this.kategorieService = kategorieService;
    }

    public Produkty createProdukty(ProduktyDTO produktyDTO) throws IOException {
        Produkty produkty = new Produkty();
        produkty.setName(produktyDTO.getName());
        produkty.setAverageWeight(produktyDTO.getAverageWeight());
        produkty.setKcal(produktyDTO.getKcal());
        produkty.setFat(produktyDTO.getFat());
        produkty.setSaturatedFat(produktyDTO.getSaturatedFat());
        produkty.setCholesterol(produktyDTO.getCholesterol());
        produkty.setSodium(produktyDTO.getSodium());
        produkty.setPotassium(produktyDTO.getPotassium());
        produkty.setCarbohydrates(produktyDTO.getCarbohydrates());
        produkty.setFiber(produktyDTO.getFiber());
        produkty.setSugars(produktyDTO.getSugars());
        produkty.setProtein(produktyDTO.getProtein());
        produkty.setAscorbicAcid(produktyDTO.getAscorbicAcid());
        produkty.setIron(produktyDTO.getIron());
        produkty.setVitaminB6(produktyDTO.getVitaminB6());
        produkty.setMagnesium(produktyDTO.getMagnesium());
        produkty.setCalcium(produktyDTO.getCalcium());
        produkty.setVitaminD(produktyDTO.getVitaminD());
        produkty.setVitaminB12(produktyDTO.getVitaminB12());
        produkty.setImage(produktyDTO.getFile().getBytes());
        produkty.setKategoria(kategorieService.getKategoriaByName(produktyDTO.getKategoria()));
        return produkty;
    }

    public ProduktyDTO createProduktyDTO(Produkty produkty) {
        ProduktyDTO produktyDTO = new ProduktyDTO();
        produktyDTO.setId(produkty.getId());
        produktyDTO.setName(produkty.getName());
        produktyDTO.setAverageWeight(produkty.getAverageWeight());
        produktyDTO.setKcal(produkty.getKcal());
        produktyDTO.setFat(produkty.getFat());
        produktyDTO.setSaturatedFat(produkty.getSaturatedFat());
        produktyDTO.setCholesterol(produkty.getCholesterol());
        produktyDTO.setSodium(produkty.getSodium());
        produktyDTO.setPotassium(produkty.getPotassium());
        produktyDTO.setCarbohydrates(produkty.getCarbohydrates());
        produktyDTO.setFiber(produkty.getFiber());
        produktyDTO.setSugars(produkty.getSugars());
        produktyDTO.setProtein(produkty.getProtein());
        produktyDTO.setAscorbicAcid(produkty.getAscorbicAcid());
        produktyDTO.setIron(produkty.getIron());
        produktyDTO.setVitaminB6(produkty.getVitaminB6());
        produktyDTO.setMagnesium(produkty.getMagnesium());
        produktyDTO.setCalcium(produkty.getCalcium());
        produktyDTO.setVitaminD(produkty.getVitaminD());
        produktyDTO.setVitaminB12(produkty.getVitaminB12());
        produktyDTO.setImage(Base64.getEncoder().encodeToString((produkty.getImage())));
        produktyDTO.setImage_kategoria(Base64.getEncoder().encodeToString((produkty.getKategoria().getImage())));
        produktyDTO.setKategoria(produkty.getKategoria().getName());
        return produktyDTO;
    }

    public List<Produkty> createProdukty(List<ProduktyDTO> produktyDTOS) {
        List<Produkty> produkties = new ArrayList<>();
        produktyDTOS.forEach(e -> {
            try {
                produkties.add(createProdukty(e));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        return produkties;
    }

    public List<ProduktyDTO> createProduktyDTO(List<Produkty> produkties) {
        List<ProduktyDTO> produktyDTOS = new ArrayList<>();
        produkties.forEach(e -> produktyDTOS.add(createProduktyDTO(e)));
        return produktyDTOS;
    }




}

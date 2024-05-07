package com.Odzywianie.services;

import com.Odzywianie.models.Kategorie;
import com.Odzywianie.models.Produkty;
import com.Odzywianie.modelsDTO.KategorieDTO;
import com.Odzywianie.modelsDTO.ProduktyDTO;
import com.Odzywianie.repositories.ProduktyRepository;
import com.Odzywianie.utils.ProduktyConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProduktyService {

    private final ProduktyRepository produktyRepository;
    private final ProduktyConverter produktyConverter;
    private final KategorieService kategorieService;

    public ProduktyService(ProduktyRepository produktyRepository, ProduktyConverter produktyConverter, KategorieService kategorieService) {
        this.produktyRepository = produktyRepository;
        this.produktyConverter = produktyConverter;
        this.kategorieService = kategorieService;
    }

    public void addProdukt(ProduktyDTO produktyDTO) throws IOException {
        produktyRepository.save(produktyConverter.createProdukty(produktyDTO));
    }


    public Page<Produkty> getProdukty(Pageable pageable, String kategoria)
    {
        if(kategoria.equals("-")) {
            return produktyRepository.findAll(pageable);
        }
        else {
            return produktyRepository.findAllByKategoria(pageable, kategorieService.getKategoriaByName(kategoria));
        }
    }
    public Page<Produkty> getProduktyStartsWith(Pageable pageable, String name, String kategoria)
    {
        if(kategoria.equals("-")) {
            return produktyRepository.findAllByNameStartsWith(pageable, name);
        }
        else {
            return produktyRepository.findAllByNameStartsWithAndKategoria(pageable, name, kategorieService.getKategoriaByName(kategoria));
        }
    }



    public List<ProduktyDTO> getProdukty(List<Produkty> produkty)
    {
        return produktyConverter.createProduktyDTO(produkty);
    }


    public ProduktyDTO getProduktDTOById(Long id)
    {
        return produktyConverter.createProduktyDTO(getProduktById(id));
    }

    public Produkty getProduktById(Long id)
    {
        return produktyRepository.findById(id).get();
    }

    public List<String> getPagesBefore(Page<Produkty> produkties)
    {
        List<String> pagesBefore = new ArrayList<>();
        int page = produkties.getNumber();
        page-=4;
        if(page>=0)
        {
            pagesBefore.add("...");
        }
        for(int i = 1; i <= 3; i++)
        {
            page++;
            if(page<0) {
                continue;
            }
            else {
                pagesBefore.add(String.valueOf(page));
            }
        }

        return pagesBefore;
    }

    public List<String> getPagesAfter(Page<Produkty> produkties)
    {
        List<String> pagesAfter = new ArrayList<>();
        int page = produkties.getNumber();
        for(int i = 1; i <= 3; i++)
        {
            page++;
            if(page == produkties.getTotalPages()) {
                break;
            }
            else {
                pagesAfter.add(String.valueOf(page));
            }
        }
        page++;
        if(page < produkties.getTotalPages())
        {
            pagesAfter.add("...");
        }
        return pagesAfter;
    }

    public boolean existsProduktById(Long id)
    {
        return produktyRepository.existsById(id);
    }
    public void deleteProdukt(Long id)
    {
        produktyRepository.deleteById(id);
    }

    public void editProdukt(ProduktyDTO produktyDTO, Long id) throws IOException {
        Produkty produkty = getProduktById(id);
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
        produktyRepository.save(produkty);
    }




}

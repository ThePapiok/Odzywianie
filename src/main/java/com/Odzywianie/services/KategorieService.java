package com.Odzywianie.services;

import com.Odzywianie.models.Kategorie;
import com.Odzywianie.models.Produkty;
import com.Odzywianie.modelsDTO.KategorieDTO;
import com.Odzywianie.repositories.KategorieRepository;
import com.Odzywianie.repositories.ProduktyRepository;
import com.Odzywianie.utils.KategorieConverter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class KategorieService {

    private final KategorieConverter kategorieConverter;
    private final KategorieRepository kategorieRepository;
    private final ProduktyRepository produktyRepository;

    public KategorieService(KategorieConverter kategorieConverter, KategorieRepository kategorieRepository, ProduktyRepository produktyRepository) {
        this.kategorieConverter = kategorieConverter;
        this.kategorieRepository = kategorieRepository;
        this.produktyRepository = produktyRepository;
    }

    public void addKategorie(KategorieDTO kategorieDTO) throws IOException {
        kategorieRepository.save(kategorieConverter.createKategorie(kategorieDTO));
    }

    public List<KategorieDTO> getKategorieDTO()
    {
        return kategorieConverter.createKategorieDTO(kategorieRepository.findAll());
    }

    public void deleteKategorie(Long id)
    {
        String name = kategorieRepository.findById(id).get().getName();
        for(Produkty produkty: produktyRepository.findAll())
        {
            if(produkty.getKategoria().getName().equals(name))
            {
                produkty.setKategoria(kategorieRepository.findByName("Brak"));
            }
        }
        kategorieRepository.deleteById(id);
    }

    public boolean existsKategoriaByName(String name)
    {
        return kategorieRepository.existsByName(name);
    }

    public boolean existsKategoriaById(Long id)
    {
        return kategorieRepository.existsById(id);
    }

    public KategorieDTO getKategoriaDTOById(Long id)
    {
        return kategorieConverter.createKategorieDTO(getKategoriaById(id));
    }

    public Kategorie getKategoriaById(Long id)
    {
        return  kategorieRepository.findById(id).get();
    }

    public void editKategoria(KategorieDTO kategorieDTO, Long id) throws IOException {
        Kategorie kategorie = getKategoriaById(id);
        kategorie.setName(kategorieDTO.getName());
        kategorie.setImage(kategorieDTO.getFile().getBytes());
        kategorieRepository.save(kategorie);
    }

    public List<String> getNamesOfKategorie(){
        List<KategorieDTO> kategorieDTOS = kategorieConverter.createKategorieDTO(kategorieRepository.findAll());
        List<String> names = new ArrayList<>();
        kategorieDTOS.forEach(e -> names.add(e.getName()));
        return names;
    }

    public Kategorie getKategoriaByName(String name)
    {
        return kategorieRepository.findByName(name);
    }
}

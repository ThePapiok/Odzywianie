package com.Odzywianie.utils;

import com.Odzywianie.repositories.ZapotrzebowanieRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Transactional
public class DeleteOldZapotrzebowanie {

    private final ZapotrzebowanieRepository zapotrzebowanieRepository;

    public DeleteOldZapotrzebowanie(ZapotrzebowanieRepository zapotrzebowanieRepository) {
        this.zapotrzebowanieRepository = zapotrzebowanieRepository;
    }


    @Scheduled(cron = "0 0 0 * * *")
    public void delete(){
        zapotrzebowanieRepository.deleteByDateBefore(LocalDate.now());
    }
}

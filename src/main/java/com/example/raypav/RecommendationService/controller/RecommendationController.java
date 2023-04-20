package com.example.raypav.RecommendationService.controller;

import com.example.raypav.RecommendationService.error.NotFoundException;
import com.example.raypav.RecommendationService.model.Crypto;
import com.example.raypav.RecommendationService.model.CryptoData;
import com.example.raypav.RecommendationService.service.CryptoService;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    @Autowired
    private CryptoService cryptoService;

    @GetMapping
    public List<CryptoData> getAllCryptosByRange() {
        return cryptoService.getAllCryptoData();
    }

    @GetMapping("/{crypto}")
    public CryptoData getCryptoData(@PathVariable("crypto") String crypto) throws NotFoundException {
        String cryptoToUpperCase = crypto.toUpperCase();
        if (!EnumUtils.isValidEnum(Crypto.class, cryptoToUpperCase)){
            throw new NotFoundException("No data for the crypto " + crypto);
        }
        return cryptoService.getCryptoData(cryptoToUpperCase);
    }

    @GetMapping("/date/{date}")
    public CryptoData getCryptoWithHighestRangeForADay(@PathVariable("date") String date) {
        return cryptoService.getCryptoWithHighestRangeForDay(date);
    }
}

package com.example.raypav.RecommendationService.service;

import com.example.raypav.RecommendationService.model.CryptoData;
import com.example.raypav.RecommendationService.model.CryptoValue;
import com.example.raypav.RecommendationService.parser.CSVLoader;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class CryptoService {

    @Autowired
    private CSVLoader csvLoader;

    private List<CryptoData> cryptoData;

    public List<CryptoData> getAllCryptoData() {
        HashMap<String, List<CryptoValue>> loadedValues = csvLoader.loadedValues;
        populateCryptoDataFromLoadedValues(loadedValues);
        Objects.requireNonNull(cryptoData).sort(CryptoData.RangeComparator);
        return cryptoData;
    }

    public CryptoData getCryptoData(String name) {
        HashMap<String, List<CryptoValue>> loadedValues = csvLoader.loadedValues;
        populateCryptoDataFromLoadedValues(loadedValues);
        return cryptoData
                .stream()
                .filter(cryptoData1 -> cryptoData1.getCrypto().equals(name))
                .findFirst().orElse(null);
    }

    public CryptoData getCryptoWithHighestRangeForDay(String date) {
        HashMap<String, List<CryptoValue>> loadedValues = csvLoader.loadedValues;
        List<CryptoData> dataForDay = calculateDataForADay(loadedValues, LocalDate.parse(date));
        Objects.requireNonNull(dataForDay).sort(CryptoData.RangeComparator);
        return dataForDay.get(0);
    }

    private void populateCryptoDataFromLoadedValues(HashMap<String, List<CryptoValue>> loadedValues) {
        if (cryptoData.isEmpty()){
            cryptoData = calculateAllData(loadedValues);
        }
    }

    private List<CryptoData> calculateAllData(HashMap<String, List<CryptoValue>> loadedValues) {
        List<CryptoData> dataForAllCryptos = new ArrayList<>();
        for (Map.Entry<String, List<CryptoValue>> oneCryptoValues : loadedValues.entrySet()) {
            List<CryptoValue> values = oneCryptoValues.getValue();
            CryptoData data = new CryptoData();
            values.sort(CryptoValue.TimeComparator);
            data.setNewest(values.get(0).getTimestamp());
            data.setOldest(values.get(values.size() - 1).getTimestamp());
            values.sort(CryptoValue.PriceComparator);
            data.setCrypto(oneCryptoValues.getKey());
            data.setMax(values.get(0).getPrice());
            data.setMin(values.get(values.size() - 1).getPrice());
            data.setRange(calculateRange(data.getMin(), data.getMax()));
            data.setCurrency("USD");
            dataForAllCryptos.add(data);
        }
        return dataForAllCryptos;
    }

    private Double calculateRange(Double min, Double max) {
        return (max - min) / min;
    }

    private List<CryptoData> calculateDataForADay(HashMap<String, List<CryptoValue>> loadedValues, LocalDate date) {
        List<CryptoData> dataForAllCryptos = new ArrayList<>();
        for (Map.Entry<String, List<CryptoValue>> oneCryptoValues : loadedValues.entrySet()) {
            List<CryptoValue> values = new ArrayList<>(oneCryptoValues.getValue()
                    .stream()
                    .filter(cryptoValue -> cryptoValue.getTimestamp().equals(date))
                    .toList());
            CryptoData data = new CryptoData();
            values.sort(CryptoValue.TimeComparator);
            data.setNewest(values.get(0).getTimestamp());
            data.setOldest(values.get(values.size() - 1).getTimestamp());
            values.sort(CryptoValue.PriceComparator);
            data.setCrypto(oneCryptoValues.getKey());
            data.setMax(values.get(0).getPrice());
            data.setMin(values.get(values.size() - 1).getPrice());
            data.setRange(calculateRange(data.getMin(), data.getMax()));
            data.setCurrency("USD");
            dataForAllCryptos.add(data);
        }
        return dataForAllCryptos;
    }
}

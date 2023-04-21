package com.example.raypav.RecommendationService.service;

import com.example.raypav.RecommendationService.loader.CSVLoader;
import com.example.raypav.RecommendationService.model.CryptoData;
import com.example.raypav.RecommendationService.model.CryptoValue;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

/*
    CryptoService serves the information for the controller's endpoints.
     Uses the csvLoader for current existing data.
     For future improvements could use the data from a DB.
 */

@Data
@Service
public class CryptoService {

    public static final String USD = "USD";
    @Autowired
    private CSVLoader csvLoader;

    public List<CryptoData> getAllCryptoData() {
        HashMap<String, List<CryptoValue>> loadedValues = csvLoader.getLoadedValues();
        List<CryptoData> cryptoData = calculateAllData(loadedValues);
        Objects.requireNonNull(cryptoData).sort(CryptoData.RangeComparator);
        return cryptoData;
    }

    public CryptoData getCryptoDataForOneCrypto(String name) {
        HashMap<String, List<CryptoValue>> loadedValues = csvLoader.getLoadedValues();
        return populateDateForOneCrypto(name, loadedValues.get(name));
    }

    public CryptoData getCryptoWithHighestRangeForDay(String date) {
        HashMap<String, List<CryptoValue>> loadedValues = csvLoader.getLoadedValues();
        List<CryptoData> dataForDay = calculateDataForADay(loadedValues, LocalDate.parse(date));
        Objects.requireNonNull(dataForDay).sort(CryptoData.RangeComparator);
        return dataForDay.get(0);
    }

    private List<CryptoData> calculateAllData(HashMap<String, List<CryptoValue>> loadedValues) {
        List<CryptoData> dataForAllCryptos = new ArrayList<>();
        for (Map.Entry<String, List<CryptoValue>> oneCryptoValues : loadedValues.entrySet()) {
            dataForAllCryptos.add(populateDateForOneCrypto(oneCryptoValues.getKey(), oneCryptoValues.getValue()));
        }
        return dataForAllCryptos;
    }

    private CryptoData populateDateForOneCrypto(String cryptoName, List<CryptoValue> oneCryptoValues) {
        CryptoData data = new CryptoData();
        oneCryptoValues.sort(CryptoValue.TimeComparator);
        data.setNewest(oneCryptoValues.get(0).getTimestamp());
        data.setOldest(oneCryptoValues.get(oneCryptoValues.size() - 1).getTimestamp());
        oneCryptoValues.sort(CryptoValue.PriceComparator);
        data.setCrypto(cryptoName);
        data.setMax(oneCryptoValues.get(0).getPrice());
        data.setMin(oneCryptoValues.get(oneCryptoValues.size() - 1).getPrice());
        data.setRange(calculateRange(data.getMin(), data.getMax()));
        data.setCurrency(USD);
        return data;
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
            dataForAllCryptos.add(populateDateForOneCrypto(oneCryptoValues.getKey(), values));
        }
        return dataForAllCryptos;
    }
}

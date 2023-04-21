package com.example.raypav.RecommendationService.loader;

import com.example.raypav.RecommendationService.model.Crypto;
import com.example.raypav.RecommendationService.model.CryptoValue;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/*
    CSVLoader loads the data from existing files in resources/data.
    Loading happens in init() the component.
    For future improvements reading the data should be changed
    to be collected from external services and save the data in a DB.
 */
@Data
@Component
public class CSVLoader {

    private static final List<String> CRYPTO_NAME_LIST = Arrays.asList("BTC", "DOGE", "ETH", "LTC", "XRP");

    private HashMap<String, List<CryptoValue>> loadedValues;

    public CSVLoader() {
        this.loadedValues = new HashMap<>();
    }

    @PostConstruct
    public void init() throws IOException {

        List<String> files = CRYPTO_NAME_LIST;

        for (String f : files) {
            loadDataFromFile(f);
        }
    }

    public void loadDataFromFile(String f) throws IOException {

        ClassPathResource resource = new ClassPathResource("data/" + f + "_values.csv"); //TO DO check file's not empty
        InputStream inputStream = resource.getInputStream();
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        Iterable<CSVRecord> csvRecords = CSVFormat.DEFAULT.withDelimiter(',').withHeader().parse(fileReader);

        List<CryptoValue> fileValues = new ArrayList<>();
        for (CSVRecord csvRecord : csvRecords) {
            CryptoValue value = new CryptoValue(
                    convertTimestampToLocalDate(csvRecord.get("timestamp")),
                    Crypto.forValue(csvRecord.get("symbol")),
                    Double.parseDouble(csvRecord.get("price")));
            checkIfCryptoIsKnown(fileValues, value);
        }
        loadedValues.put(f, fileValues);
    }

    private static void checkIfCryptoIsKnown(List<CryptoValue> fileValues, CryptoValue value) {
        if (!value.getName().getValue().equals("UNKNOWN")) {
            fileValues.add(value);
        }
    }

    private LocalDate convertTimestampToLocalDate(String stringTimestamp) {
        return LocalDateTime
                .ofInstant(Instant.ofEpochMilli(Long.parseLong(stringTimestamp)), ZoneId.systemDefault())
                .toLocalDate();
    }
}

package com.example.raypav.RecommendationService.loader;

import com.example.raypav.RecommendationService.model.CryptoValue;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CSVLoaderTest {

    @Test
    void loadCSVFileAndReturnCorrectCryptoData() {
        // Given
        CSVLoader loader = new CSVLoader();

        // When
        try {
            loader.loadDataFromFile("XRP");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HashMap<String, List<CryptoValue>> loadedValues = loader.getLoadedValues();

        // Then
        assertNotNull(loadedValues);
        assertThat(loadedValues.containsKey("XRP"));
        assertThat(loadedValues.values().size()).isEqualTo(1);
        assertThat(loadedValues.get("XRP").size()).isEqualTo( 80);
        assertThat(loadedValues.get("XRP").get(0).getTimestamp()).isEqualTo("2022-01-01");
    }
}

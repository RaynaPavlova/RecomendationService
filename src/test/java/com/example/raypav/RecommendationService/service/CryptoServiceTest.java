package com.example.raypav.RecommendationService.service;

import com.example.raypav.RecommendationService.loader.CSVLoader;
import com.example.raypav.RecommendationService.model.Crypto;
import com.example.raypav.RecommendationService.model.CryptoData;
import com.example.raypav.RecommendationService.model.CryptoValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CryptoServiceTest {

    @Mock
    private CSVLoader csvLoader;

    @InjectMocks
    private CryptoService cryptoService = new CryptoService();

    @Test
    public void whenCallingGetAllCryptoDataReturnsRightCryptoData() throws IOException {
        // Given
        HashMap<String, List<CryptoValue>> loadedValues = populateLoadedValues();
        when(csvLoader.getLoadedValues()).thenReturn(loadedValues);

        // When
        List<CryptoData> result = cryptoService.getAllCryptoData();

        // Then
        assertResultForGetAllCrytoData(result);
    }

    private static void assertResultForGetAllCrytoData(List<CryptoData> result) {
        assertNotNull(result);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getCrypto()).isEqualTo( "BTC");
        assertThat(result.get(0).getMin()).isEqualTo( 0.298);
        assertThat(result.get(0).getMax()).isEqualTo( 0.434);
        assertThat(result.get(0).getOldest()).isEqualTo(LocalDate.parse("2023-03-31"));
        assertThat(result.get(0).getNewest()).isEqualTo(LocalDate.parse("2023-04-20"));
        assertThat(result.get(0).getRange()).isEqualTo(0.45637583892617456);
        assertThat(result.get(0).getCurrency()).isEqualTo( "USD");
    }

    private HashMap<String, List<CryptoValue>> populateLoadedValues() {
        List<CryptoValue> values = new ArrayList<>() {{
            add(new CryptoValue(LocalDate.parse("2023-04-20"), Crypto.BTC, 0.325));
            add(new CryptoValue(LocalDate.parse("2023-03-31"), Crypto.BTC, 0.434));
            add(new CryptoValue(LocalDate.parse("2023-04-06"), Crypto.BTC, 0.298));
        }};
        return new HashMap<String, List<CryptoValue>>() {{
            put("BTC", values);
        }};
    }

    @Test
    public void whenCallingGetCryptoDataForOneCryptoReturnsRightCryptoData() {

    }

    @Test
    public void getCryptoWithHighestRangeForDay() {

    }
}

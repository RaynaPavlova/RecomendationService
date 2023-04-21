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
        HashMap<String, List<CryptoValue>> loadedValues = populateLoadedValuesForOneCrypto();
        when(csvLoader.getLoadedValues()).thenReturn(loadedValues);

        // When
        List<CryptoData> result = cryptoService.getAllCryptoData();

        // Then
        assertResultForGetAllCryptoData(result);
    }

    @Test
    public void whenCallingGetCryptoDataForOneCryptoReturnsRightCryptoData() {
        // Given
        HashMap<String, List<CryptoValue>> loadedValues = populateLoadedValuesForMoreCryptos();
        when(csvLoader.getLoadedValues()).thenReturn(loadedValues);

        // When
        CryptoData result = cryptoService.getCryptoDataForOneCrypto("BTC");

        // Then
        assertNotNull(result);
        assertSingleCryptoData(result);
    }

    //TO DO finish tests
    @Test
    public void getCryptoWithHighestRangeForDay() {
//        // Given
//        HashMap<String, List<CryptoValue>> loadedValues = populateLoadedValuesForMoreCryptos();
//        when(csvLoader.getLoadedValues()).thenReturn(loadedValues);
//
//        // When
//        CryptoData result = cryptoService.getCryptoWithHighestRangeForDay("2023-04-20");
//
//        // Then
//        assertNotNull(result);
//        assertSingleCryptoDataWithHighestRangeForADay(result);

    }

    private static void assertResultForGetAllCryptoData(List<CryptoData> result) {
        assertNotNull(result);
        assertThat(result.size()).isEqualTo(1);
        CryptoData data = result.get(0);
        assertSingleCryptoData(data);
    }

    private static void assertSingleCryptoData(CryptoData data) {
        assertThat(data.getCrypto()).isEqualTo( "BTC");
        assertThat(data.getMin()).isEqualTo( 0.298);
        assertThat(data.getMax()).isEqualTo( 0.434);
        assertThat(data.getOldest()).isEqualTo(LocalDate.parse("2023-04-20"));
        assertThat(data.getNewest()).isEqualTo(LocalDate.parse("2023-04-20"));
        assertThat(data.getRange()).isEqualTo(0.45637583892617456);
        assertThat(data.getCurrency()).isEqualTo( "USD");
    }

    private static void assertSingleCryptoDataWithHighestRangeForADay(CryptoData data) {
        assertThat(data.getCrypto()).isEqualTo( "BTC");
        assertThat(data.getMin()).isEqualTo( 0.325);
        assertThat(data.getMax()).isEqualTo( 0.325);
        assertThat(data.getOldest()).isEqualTo(LocalDate.parse("2023-04-20"));
        assertThat(data.getNewest()).isEqualTo(LocalDate.parse("2023-04-20"));
        assertThat(data.getRange()).isEqualTo(0.0);
        assertThat(data.getCurrency()).isEqualTo( "USD");
    }

    private HashMap<String, List<CryptoValue>> populateLoadedValuesForOneCrypto() {
        List<CryptoValue> values = new ArrayList<>() {{
            add(new CryptoValue(LocalDate.parse("2023-04-20"), Crypto.BTC, 0.325));
            add(new CryptoValue(LocalDate.parse("2023-03-31"), Crypto.BTC, 0.434));
            add(new CryptoValue(LocalDate.parse("2023-04-06"), Crypto.BTC, 0.298));
        }};
        return new HashMap<String, List<CryptoValue>>() {{
            put("BTC", values);
        }};
    }

    private HashMap<String, List<CryptoValue>> populateLoadedValuesForMoreCryptos() {
        List<CryptoValue> valuesBTC = new ArrayList<>() {{
            add(new CryptoValue(LocalDate.parse("2023-04-20"), Crypto.BTC, 0.325));
            add(new CryptoValue(LocalDate.parse("2023-03-31"), Crypto.BTC, 0.434));
            add(new CryptoValue(LocalDate.parse("2023-04-06"), Crypto.BTC, 0.298));
        }};
        List<CryptoValue> valuesETH = new ArrayList<>() {{
            add(new CryptoValue(LocalDate.parse("2023-04-20"), Crypto.ETH, 1.012));
            add(new CryptoValue(LocalDate.parse("2023-04-01"), Crypto.ETH, 0.999));
        }};
        return new HashMap<String, List<CryptoValue>>() {{
            put("BTC", valuesBTC);
            put("ETH", valuesETH);
        }};
    }
}

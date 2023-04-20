package com.example.raypav.RecommendationService.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Comparator;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CryptoValue {

    private LocalDate timestamp;

    private Crypto name;

    //Double or BigDecimal
    private Double price;

    public static Comparator<CryptoValue> PriceComparator = new Comparator<CryptoValue>() {

        // Comparing attributes of students
        public int compare(CryptoValue v1, CryptoValue v2) {
            Double range1 = v1.getPrice();
            Double range2 = v2.getPrice();

            // Returning in ascending order
            return range2.compareTo(
                    range1);
        }
    };

    public static Comparator<CryptoValue> TimeComparator = new Comparator<CryptoValue>() {

        // Comparing attributes of students
        public int compare(CryptoValue t1, CryptoValue t2) {
            LocalDate time1 = t1.getTimestamp();
            LocalDate time2 = t2.getTimestamp();

            // Returning in ascending order
            return time2.compareTo(
                    time1);
        }
    };
}

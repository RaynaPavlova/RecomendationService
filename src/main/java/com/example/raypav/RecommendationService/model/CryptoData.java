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
public class CryptoData {

    private String crypto;

    private LocalDate oldest;

    private LocalDate newest;

    private Double min;

    private Double max;

    private Double range;

    //USD default
    private String currency;

    public static Comparator<CryptoData> RangeComparator = new Comparator<CryptoData>() {

        // Comparing attributes of students
        public int compare(CryptoData c1, CryptoData c2) {
            Double range1 = c1.getRange();
            Double range2 = c2.getRange();

            // Returning in ascending order
            return range2.compareTo(
                    range1);
        }
    };
}

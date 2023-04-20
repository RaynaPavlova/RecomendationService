package com.example.raypav.RecommendationService.model;

public enum Crypto {
    BTC("BTC"),
    DOGE("DOGE"),
    ETH("ETH"),
    LTC("LTC"),
    XRP("XRP"),
    UNKNOWN("UNKNOWN");

    private final String value;

    Crypto(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Crypto forValue(String value) {
        for (Crypto type : values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}

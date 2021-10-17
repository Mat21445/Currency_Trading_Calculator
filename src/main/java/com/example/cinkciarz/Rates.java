package com.example.cinkciarz;

public class Rates implements Comparable<Rates> {
    String currency;
    String code;
    double mid;

    public String getCurrency() {
        return currency;
    }

    public String getCode() {
        return code;
    }

    public double getMid() {
        return mid;
    }

    public Rates(String currency, String code, double mid) {
        this.currency = currency;
        this.code = code;
        this.mid = mid;
    }

    @Override
    public int compareTo(Rates o) {
        return this.getCode().compareTo(o.getCode());
    }
}

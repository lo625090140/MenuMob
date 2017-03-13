package com.demo.mob.bean;

/**
 * Created by chenjt on 2017/1/16.
 */

public class SmsItem {

    private String country;
    private String number;

    public SmsItem(String country, String number) {
        this.country = country;
        this.number = number;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

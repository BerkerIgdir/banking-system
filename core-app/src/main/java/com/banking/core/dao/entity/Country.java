package com.banking.core.dao.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "countries")
public class Country {
    @Id
    private UUID id;
    private String name;
    private String timeZone;
    private String currency;

    public Country(){}

    public Country(UUID id, String name, String timeZone, String currency) {
        this.id = id;
        this.name = name;
        this.timeZone = timeZone;
        this.currency = currency;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getCurrency() {
        return currency;
    }

}

package com.banking.core.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    private UUID id;
    private String name;
    private String surname;
    private String IBAN;
    @Column(name = "country_id")
    private UUID country;
    private BigDecimal balance;
    public Account(){}
    public UUID getUuid() {
        return id;
    }

    public void setUuid(UUID uuid) {
        this.id = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account(UUID uuid, String name, String surname, String IBAN, UUID country, BigDecimal balance) {
        this.id = uuid;
        this.name = name;
        this.surname = surname;
        this.IBAN = IBAN;
        this.country = country;
        this.balance = balance;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public void setCountry(UUID country) {
        this.country = country;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getIBAN() {
        return IBAN;
    }

    public UUID getCountry() {
        return country;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}

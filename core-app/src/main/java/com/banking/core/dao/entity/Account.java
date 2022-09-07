package com.banking.core.dao.entity;

import javax.persistence.*;
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
    @Column(name = "country_code")
    private String country;
    private BigDecimal balance;
    @Version
    private int version;
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

    public Account(UUID uuid, String name, String surname, String IBAN, String country, BigDecimal balance) {
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

    public void setCountry(String country) {
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

    public String getCountry() {
        return country;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(Account.class);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof Account a) {
            return a.id.equals(this.id);
        }
        return false;
    }
}

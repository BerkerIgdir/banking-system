package com.banking.core.dao.entity;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    private UUID id;
    @Column(name = "trans_time")
    private LocalDateTime time;
    @Column(name = "from_account")
    private UUID fromAccount;
    @Column(name = "to_account")
    private UUID toAccount;

    @Column(name = "country_code")
    private String countryId;
    private BigDecimal amount;

    public Transaction() {
    }

    public Transaction(UUID uuid, LocalDateTime time, UUID fromAccount, UUID toAccount, BigDecimal amount,String countryId) {
        this.id = uuid;
        this.time = time;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.countryId = countryId;
    }

    public void setId(UUID uuid) {
        this.id = uuid;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setFromAccount(UUID from) {
        this.fromAccount = from;
    }

    public void setToAccount(UUID to) {
        this.toAccount = to;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public UUID getFromAccount() {
        return fromAccount;
    }

    public UUID getToAccount() {
        return toAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(Transaction.class);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof Transaction t) {
            return t.getId().equals(this.id);
        }
        return false;
    }

}

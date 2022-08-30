package com.banking.core.dao.entity;

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
    private UUID uuid;
    @Column(name = "trans_time")
    private LocalDateTime time;
    @Column(name = "from_account")
    private UUID fromAccount;
    @Column(name = "to_account")
    private UUID toAccount;

    @Column(name = "country_id")
    private UUID countryId;
    private BigDecimal amount;

    public Transaction() {
    }

    public Transaction(UUID uuid, LocalDateTime time, UUID fromAccount, UUID toAccount, BigDecimal amount) {
        this.uuid = uuid;
        this.time = time;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public UUID getUuid() {
        return uuid;
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

    public UUID getCountryId() {
        return countryId;
    }

    public void setCountryId(UUID countryId) {
        this.countryId = countryId;
    }

}

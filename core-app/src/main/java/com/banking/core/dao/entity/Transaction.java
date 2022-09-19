package com.banking.core.dao.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    private UUID id;
    @Column(name = "trans_time")
    private long time;
    @Column(name = "from_account")
    private String fromAccount;
    @Column(name = "to_account")
    private String toAccount;
    private BigDecimal amount;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Version
    private int version;

    public Transaction() {
    }

    public Transaction(UUID uuid, long time, String fromAccount, String toAccount, BigDecimal amount) {
        this.id = uuid;
        this.time = time;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    public void setId(UUID uuid) {
        this.id = uuid;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setFromAccount(String from) {
        this.fromAccount = from;
    }

    public void setToAccount(String to) {
        this.toAccount = to;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public BigDecimal getAmount() {
        return amount;
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

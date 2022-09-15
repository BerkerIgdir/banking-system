package com.banking.core.dao.projection;

import java.math.BigDecimal;

public interface AccountProjection {
    String getName();
    String getSurname();
    String getIban();
    String getCurrency();
    BigDecimal getBalance();
}

package com.banking.core.business.transaction.fraud;

import com.banking.core.controller.transaction.dto.TransactionApiDto;

public interface FraudDetectionService {
    boolean isSuspicious(TransactionApiDto.TransactionRequestDto requestDto);
}

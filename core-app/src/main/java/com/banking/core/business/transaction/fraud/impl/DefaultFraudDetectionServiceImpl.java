package com.banking.core.business.transaction.fraud.impl;

import com.banking.core.business.transaction.fraud.FraudDetectionService;
import com.banking.core.controller.transaction.dto.TransactionApiDto;

public class DefaultFraudDetectionServiceImpl implements FraudDetectionService {

    @Override
    public boolean isSuspicious(TransactionApiDto.TransactionRequestDto requestDto) {
        return false;
    }

}

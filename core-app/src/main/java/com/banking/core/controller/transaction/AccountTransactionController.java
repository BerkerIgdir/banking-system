package com.banking.core.controller.transaction;

import com.banking.core.business.transaction.business.services.api.TransactionService;
import com.banking.core.controller.transaction.dto.TransactionApiDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
@Validated
@RestController
@RequestMapping(value = "/transaction")
public class AccountTransactionController {

    @Autowired
    @Qualifier(value = "default-transaction-service")
    private TransactionService transactionService;

    @PostMapping("/transfer")
    @ApiOperation(value = "Executes money transfer",
            notes = "This endpoint executes money transaction between two accounts identified by IBAN")
    ResponseEntity<Void> executeTransaction(HttpServletRequest request, @RequestBody TransactionApiDto.TransactionRequestDto requestDto) throws InterruptedException {
        transactionService.beginTransaction(requestDto.fromIban(), requestDto.toIban(), requestDto.amount());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

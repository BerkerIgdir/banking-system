package com.banking.core.controller.transaction;

import com.banking.core.business.transaction.business.facades.RetryableAccountFacade;
import com.banking.core.controller.transaction.dto.TransactionApiDto;
import com.banking.core.controller.util.ControllerUtilMethods;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@Validated
@RestController
@RequestMapping(value = "/transaction")
public class AccountTransactionController {

    @Autowired
    @Qualifier(value = "default-transaction-service")
    private RetryableAccountFacade retryableAccountFacade;

    @GetMapping
    @ApiOperation(value = "Gets a transaction",
            notes = "This fetches a specific transaction by id")
    ResponseEntity<URI> getTransactionDetail(@RequestParam("transaction-id") String transactionId) {

        var uri = ControllerUtilMethods.createUriToAnEntity(transactionId, "/transaction");
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/transfer")
    @ApiOperation(value = "Executes money transfer",
            notes = "This endpoint executes money transaction between two accounts identified by IBAN")
    ResponseEntity<URI> executeTransaction(HttpServletRequest request, @RequestBody TransactionApiDto.TransactionRequestDto requestDto) throws InterruptedException {
        var transaction = retryableAccountFacade.beginTransaction(requestDto.fromIban(), requestDto.toIban(), requestDto.amount());
        var uri = ControllerUtilMethods.createUriToAnEntity(transaction.getId().toString(), "/transaction");
        return ResponseEntity.created(uri).build();
    }
}

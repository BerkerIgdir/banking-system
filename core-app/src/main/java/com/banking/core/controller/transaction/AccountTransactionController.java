package com.banking.core.controller.transaction;

import com.banking.core.business.transaction.business.facades.RetryableAccountFacade;
import com.banking.core.controller.transaction.dto.TransactionApiDto;
import com.banking.core.controller.util.ControllerUtilMethods;
import com.banking.core.dao.entity.Transaction;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping(value = "/transaction")
public class AccountTransactionController {

    @Autowired
    private RetryableAccountFacade retryableAccountFacade;

    @GetMapping
    @ApiOperation(value = "Gets a transaction",
            notes = "This fetches a specific transaction by id")
    ResponseEntity<Transaction> getTransactionDetail(@RequestParam("transaction-id") String transactionId) {
        var transaction = retryableAccountFacade.getTransactionService().getTransaction(UUID.fromString(transactionId));
        return ResponseEntity.ok().body(transaction);
    }

    @GetMapping("/list")
    @ApiOperation(value = "Gets all transactions between two accounts",
            notes = "This fetches all transactions between two accounts")
    ResponseEntity<List<Transaction>> getTransactionDetail(@RequestParam("from-iban") String fromIban, @RequestParam("to-iban") String toIban) {
        var transactions = retryableAccountFacade.getTransactionService().getTransactions(fromIban,toIban);
        return ResponseEntity.ok().body(transactions);
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

package com.banking.core.controller.transaction;

import com.banking.core.business.transaction.TransactionService;
import com.banking.core.dao.entity.Account;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/transaction")
public class AccountTransactionController {

    @Resource(name = "default-transaction-service")
    private TransactionService transactionService;

    @GetMapping("/transfer")
    @ApiOperation(value = "Executes money transfer",
            notes = "This endpoint executes money transaction between two accounts identified by IBAN")
    ResponseEntity<List<Account>> getAllAccounts(@RequestParam("from") @NotEmpty @NotNull String fromIban,
                                                 @RequestParam("to") @NotEmpty @NotNull String toIban,
                                                 @RequestParam("amount") @NotEmpty @NotNull BigDecimal amount) throws InterruptedException {
        transactionService.beginTransaction(fromIban, toIban, amount);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

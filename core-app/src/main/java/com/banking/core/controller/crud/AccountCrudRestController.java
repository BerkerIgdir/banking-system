package com.banking.core.controller.crud;

import com.banking.core.business.crud.AccountCrudService;
import com.banking.core.business.crud.dto.AccountCrudDTO;
import com.banking.core.controller.util.ControllerUtilMethods;
import com.banking.core.dao.entity.Account;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/api/v1/account")
public class AccountCrudRestController {
    private final static String IBAN_END_POINT = "/{iban}";
    private final AccountCrudService accountCrudService;

    public AccountCrudRestController(AccountCrudService accountCrudService) {
        this.accountCrudService = accountCrudService;
    }

    @GetMapping("/country/{countryCode}")
    @ApiOperation(value = "Gets user list",
            notes = "This endpoint lists all accounts by country")
    public ResponseEntity<List<Account>> getAllAccounts(@PathVariable @NotEmpty String countryCode) {
        return ResponseEntity.ok(accountCrudService.retrieveAccountsByCountryCode(countryCode));
    }

    @GetMapping(IBAN_END_POINT)
    @ApiOperation(value = "Gets specific user",
            notes = "This method brings an unique user by iban")
    public ResponseEntity<Account> getAccountByIban(@PathVariable String iban) {
        return ResponseEntity.ok(accountCrudService.retrieveAccountByIban(iban));
    }

    @PostMapping
    @ApiOperation(value = "Create user",
            notes = "This method creates a new user")
    public ResponseEntity<URI> createNewAccount(@RequestBody @Valid AccountCrudDTO.AccountCreateRequestDto requestDto) {
        var iban = accountCrudService.create(requestDto);
        return ResponseEntity.created(ControllerUtilMethods.createUriToAnEntity(iban, IBAN_END_POINT)).build();
    }

    @PutMapping
    @ApiOperation(value = "Updates users country",
            notes = "This method updates an existing user")
    public ResponseEntity<URI> updateAccount(@RequestBody @Valid AccountCrudDTO.AccountUpdateRequestDto requestDto) {
        var iban = accountCrudService.update(requestDto);
        return ResponseEntity.created(ControllerUtilMethods.createUriToAnEntity(iban, IBAN_END_POINT)).build();
    }

    @DeleteMapping(IBAN_END_POINT)
    @ApiOperation(value = "Deletes user",
            notes = "This method deletes an existing user by iban")
    public ResponseEntity<URI> deleteAccount(@PathVariable String iban) {
        accountCrudService.delete(iban);
        return ResponseEntity.ok().build();
    }

}

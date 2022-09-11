package com.banking.core.controller.crud;

import com.banking.core.business.crud.AccountCrudService;
import com.banking.core.business.crud.dto.AccountCrudDTO;
import com.banking.core.dao.entity.Account;
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
    ResponseEntity<List<Account>> getAllAccounts(@PathVariable @NotEmpty String countryCode) {
        return ResponseEntity.ok(accountCrudService.retrieveAccountsByCountryCode(countryCode));
    }

    @GetMapping(IBAN_END_POINT)
    ResponseEntity<Account> getAccountByIban(@PathVariable() String iban) {
        return ResponseEntity.ok(accountCrudService.retrieveAccountByIban(iban));
    }


    @PostMapping
    ResponseEntity<URI> createNewAccount(@RequestBody @Valid AccountCrudDTO.AccountCrudRequestDto requestDto) {
        var iban = accountCrudService.create(requestDto);
        var uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path(IBAN_END_POINT).buildAndExpand(iban);
        return ResponseEntity.created(uri.toUri()).build();
    }

}

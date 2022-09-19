package com.banking.core.controller.transaction.aspect;

import com.banking.core.controller.transaction.dto.TransactionApiDto;
import com.banking.core.dao.repo.AccountRepository;
import org.aspectj.lang.JoinPoint;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class SuspiciousTransactionDetector {

    @Pointcut("execution(* com.banking.core.controller.transaction.AccountTransactionController.executeTransaction(..))")
    public void pointCut() {

    }

    //TO DO: Implement an aspect that will be triggered before transaction controller method to check if it is a fraud fundamentally
    @Before("pointCut() && args(request,requestDto)")
    public boolean isFraudAction(HttpServletRequest request, @RequestBody TransactionApiDto.TransactionRequestDto requestDto) {
        var fromIban = requestDto.fromIban();
        var originCountryCode = request.getLocale().getCountry();
        return fromIban.contains(originCountryCode.toUpperCase());
    }
}

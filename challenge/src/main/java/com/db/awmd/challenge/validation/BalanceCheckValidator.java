package com.db.awmd.challenge.validation;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.InsufficientBalanceException;
import com.db.awmd.challenge.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Locale;

@Component
public class BalanceCheckValidator {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private MessageSource messageSource;

    public void checkBalance(String accountId, BigDecimal balance) {
        Account account = accountsRepository.getAccount(accountId);
        if (account.getBalance().compareTo(balance) != 1) {
            throw new InsufficientBalanceException(messageSource.getMessage
                    ("Insufficient Balance" + account.getBalance() + " In This Account" + accountId + " ",
                            null, Locale.getDefault()));
        }
    }
}

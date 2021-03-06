package com.db.awmd.challenge.service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.Transaction;
import com.db.awmd.challenge.repository.AccountsRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class AccountsService {

    private final ReentrantLock lock = new ReentrantLock(true);

    @Getter
    private final AccountsRepository accountsRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    public AccountsService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    public void createAccount(Account account) {
        this.accountsRepository.createAccount(account);
    }

    public Account getAccount(String accountId) {
        return this.accountsRepository.getAccount(accountId);
    }

    @Transactional
    public void updateAccount(Transaction transaction) {
        try {
            lock.lock();
            debitBalance(transaction.getBalance(), transaction.getFromAccountId());
            creditBalance(transaction.getBalance(), transaction.getToAccountId());
        } finally {
            lock.unlock();
        }
    }
    
    private void debitBalance(BigDecimal balance, String fromAccountId) {
        Account account = getAccount(fromAccountId);
        account.getBalance().subtract(balance);
        this.accountsRepository.updateAccountBalance(account);
        notificationService.notifyAboutTransfer(account, "Debit Balance from Account");
    }
    
    private void creditBalance(BigDecimal balance, String toAccountId) {
        Account account = getAccount(toAccountId);
        account.getBalance().add(balance);
        this.accountsRepository.updateAccountBalance(account);
        notificationService.notifyAboutTransfer(account, "Credit Balance in Account");
    }
}

package com.db.awmd.challenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.Transaction;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.service.AccountsService;
import java.math.BigDecimal;

import com.db.awmd.challenge.service.NotificationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountsServiceTest {

  @Autowired
  private AccountsService accountsService;

  @Autowired
  private NotificationService notificationService;

  @Test
  public void addAccount() throws Exception {
    Account account = new Account("Id-123");
    account.setBalance(new BigDecimal(1000));
    this.accountsService.createAccount(account);

    assertThat(this.accountsService.getAccount("Id-123")).isEqualTo(account);
  }

  @Test
  public void addAccount_failsOnDuplicateId() throws Exception {
    String uniqueId = "Id-" + System.currentTimeMillis();
    Account account = new Account(uniqueId);
    this.accountsService.createAccount(account);

    try {
      this.accountsService.createAccount(account);
      fail("Should have failed when adding duplicate account");
    } catch (DuplicateAccountIdException ex) {
      assertThat(ex.getMessage()).isEqualTo("Account id " + uniqueId + " already exists!");
    }

  }
  @Test
  public void updateAccount() throws Exception {
    Transaction transaction = new Transaction("FR-Id-123", "TO-Id-123");
    Account debitAccount = new Account("FR-Id-123");
    debitAccount.setBalance(new BigDecimal(1000));
    this.accountsService.createAccount(debitAccount);

    Account creditAccount = new Account("TO-Id-123");
    creditAccount.setBalance(new BigDecimal(500));
    this.accountsService.createAccount(debitAccount);

    assertThat(this.accountsService.getAccount("TO-Id-123")).isEqualTo(debitAccount);
    assertThat(this.accountsService.getAccount("Id-123")).isEqualTo(creditAccount);
  }

}

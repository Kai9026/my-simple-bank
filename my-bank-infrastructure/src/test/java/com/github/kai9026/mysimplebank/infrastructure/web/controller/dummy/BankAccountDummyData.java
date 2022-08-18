package com.github.kai9026.mysimplebank.infrastructure.web.controller.dummy;

import com.github.kai9026.mysimplebank.application.usecase.bankaccount.model.BankAccountBaseResponse;
import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccount;
import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccountTransaction;
import com.github.kai9026.mysimplebank.domain.bankaccount.Money;
import com.github.kai9026.mysimplebank.domain.bankaccount.id.BankAccountId;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.bankaccount.model.BankAccountCreationApiRequest;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.bankaccount.model.BankAccountDepositApiRequest;
import com.github.kai9026.mysimplebank.infrastructure.web.controller.bankaccount.model.BankAccountResource;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public final class BankAccountDummyData {

  public static BankAccountBaseResponse createBankAccountBaseResponse() {
    return new BankAccountBaseResponse(UUID.randomUUID(), "number", 0.00,
        "alias", "EUR", UUID.randomUUID());
  }

  public static BankAccountResource createBankAccountResourceDummy() {
    return BankAccountResource.builder()
        .bankAccountCode(UUID.randomUUID().toString())
        .accountNumber("number")
        .accountAlias("alias")
        .accountCustomerCode(UUID.randomUUID().toString())
        .accountBalance(0.00)
        .accountCurrency("EUR")
        .build();
  }

  public static BankAccountCreationApiRequest createBankAccountCreationRequest() {
    return new BankAccountCreationApiRequest("alias", "EUR",
        UUID.fromString("04e12799-a4d1-47e3-84cc-70715130a471"));
  }

  public static BankAccountCreationApiRequest createInvalidBankAccountCreationRequest() {
    return new BankAccountCreationApiRequest(null, "EUR", UUID.randomUUID());
  }

  public static BankAccountDepositApiRequest createBankAccountDepositRequest() {
    return new BankAccountDepositApiRequest(10.00, "EUR", "Deposit");
  }

  public static BankAccountDepositApiRequest createInvalidBankAccountDepositRequest() {
    return new BankAccountDepositApiRequest(10.00, null, "Deposit");
  }

  public static BankAccount createBankAccount() {
    List<BankAccountTransaction> transactions = List.of(
        BankAccountTransaction.createTransactionWith(UUID.randomUUID(),
            BankAccountId.fromId(UUID.randomUUID()), BankAccountId.fromId(UUID.randomUUID()),
            Money.of(10.00, "EUR"), "transfer", true));
    return BankAccount.initAccountWithTransactions("alias", "number", "EUR", UUID.randomUUID(),
        UUID.randomUUID(), LocalDate.now(), 0.00, transactions);
  }
}

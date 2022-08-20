package com.github.kai9026.mysimplebank.domain.bankaccount;

import static com.github.kai9026.mysimplebank.domain.shared.RequiredFieldValidator.isEmptyField;
import static java.util.Objects.isNull;

import com.github.kai9026.mysimplebank.domain.bankaccount.id.BankAccountId;
import com.github.kai9026.mysimplebank.domain.bankaccount.id.BankAccountTransactionId;
import com.github.kai9026.mysimplebank.domain.exception.DomainValidationException;
import com.github.kai9026.mysimplebank.domain.shared.objects.Entity;
import java.time.LocalDate;
import java.util.UUID;

public class BankAccountTransaction extends Entity<BankAccountTransactionId> {

  private static final long serialVersionUID = 7580110100310853406L;
  private final BankAccountId originBankAccountId;

  private final BankAccountId targetBankAccountId;
  private final Money transactionAmount;
  private final String transactionConcept;
  private final LocalDate transactionDate;
  private final boolean newTransaction;

  private BankAccountTransaction(final BankAccountTransactionId id, final BankAccountId originAccountId,
      final BankAccountId targetAccountId, final Money amount, final String concept,
      final LocalDate transactionDate, final boolean isNewTransaction) {
    super(id);
    this.originBankAccountId = originAccountId;
    this.targetBankAccountId = targetAccountId;
    this.transactionAmount = amount;
    this.transactionConcept = concept;
    this.transactionDate = transactionDate;
    this.newTransaction = isNewTransaction;
  }

  public static BankAccountTransaction createTransactionWith(final UUID transactionId,
      final BankAccountId originAccount,final BankAccountId targetAccount,  final Money amount,
      final String concept, final LocalDate transactionDate, final boolean isNewTransaction) {
    if (isEmptyField(concept)) {
      throw new DomainValidationException("Transaction concept cannot be null");
    }
    if (isNull(amount)) {
      throw new DomainValidationException("Transaction amount cannot be null");
    }

    final var txDate = isNull(transactionDate) ? LocalDate.now(): transactionDate;
    final var bankAccountTransactionId =
        BankAccountTransactionId.fromId(transactionId);

    return new BankAccountTransaction(bankAccountTransactionId, originAccount, targetAccount, amount,
        concept, txDate, isNewTransaction);
  }

  public BankAccountId originAccountCode() {
    return this.originBankAccountId;
  }

  public BankAccountId targetAccountCode() {
    return this.targetBankAccountId;
  }

  public Money transactionAmount() {
    return this.transactionAmount;
  }

  public String concept() {
    return this.transactionConcept;
  }

  public LocalDate transactionDate() {
    return this.transactionDate;
  }

  public boolean newTransaction() {
    return this.newTransaction;
  }

}

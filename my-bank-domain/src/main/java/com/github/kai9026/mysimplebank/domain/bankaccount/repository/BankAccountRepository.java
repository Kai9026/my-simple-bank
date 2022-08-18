package com.github.kai9026.mysimplebank.domain.bankaccount.repository;

import com.github.kai9026.mysimplebank.domain.bankaccount.BankAccount;
import com.github.kai9026.mysimplebank.domain.bankaccount.id.BankAccountId;
import com.github.kai9026.mysimplebank.domain.shared.Repository;

public interface BankAccountRepository extends Repository<BankAccount, BankAccountId> {

  BankAccount update(BankAccount aggregate);
}

package com.github.kai9026.mysimplebank.infrastructure.database.repository.bankaccount;

import com.github.kai9026.mysimplebank.infrastructure.database.entity.bankaccount.BankAccountTransactionEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountTransactionJpaRepository extends
    JpaRepository<BankAccountTransactionEntity, Long> {

  List<BankAccountTransactionEntity> findByDiscriminatorAccountCode(
      final UUID discriminatorAccountCode);
}

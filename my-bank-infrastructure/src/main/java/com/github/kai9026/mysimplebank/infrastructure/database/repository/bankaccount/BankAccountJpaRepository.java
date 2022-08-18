package com.github.kai9026.mysimplebank.infrastructure.database.repository.bankaccount;

import com.github.kai9026.mysimplebank.infrastructure.database.entity.bankaccount.BankAccountEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountJpaRepository extends JpaRepository<BankAccountEntity, Long> {

  Optional<BankAccountEntity> findByAccountCode(final UUID accountCode);
}

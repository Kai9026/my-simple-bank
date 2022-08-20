package com.github.kai9026.mysimplebank.infrastructure.database.entity.bankaccount;

import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bank_accounts")
@Getter
@Setter
public class BankAccountEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private UUID accountCode;

  @Column(unique = true)
  private String accountNumber;

  @Column(nullable = false)
  private Float consolidatedBalance;

  @Column(nullable = false)
  private Float intervalBalance;

  private String alias;

  @Column(nullable = false)
  private String defaultCurrency;

  private UUID customerCode;

  @Column(nullable = false)
  private LocalDate startInterval;

  @Column(nullable = false)
  private LocalDate endInterval;

  @Version
  private Long version;

}

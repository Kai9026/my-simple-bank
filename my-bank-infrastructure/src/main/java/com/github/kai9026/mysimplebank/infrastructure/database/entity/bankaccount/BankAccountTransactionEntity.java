package com.github.kai9026.mysimplebank.infrastructure.database.entity.bankaccount;

import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class BankAccountTransactionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private UUID transactionCode;

  @Column(nullable = false)
  private UUID originAccountCode;

  @Column(nullable = false)
  private UUID targetAccountCode;

  @Column(nullable = false)
  private Float amount;

  @Column(nullable = false)
  private String concept;

  @Column(nullable = false)
  private LocalDate transactionDate;

}

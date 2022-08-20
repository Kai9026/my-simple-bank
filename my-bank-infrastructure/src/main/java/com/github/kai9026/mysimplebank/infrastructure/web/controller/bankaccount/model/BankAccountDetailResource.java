package com.github.kai9026.mysimplebank.infrastructure.web.controller.bankaccount.model;

import com.github.kai9026.mysimplebank.application.usecase.bankaccount.detail.model.TransactionDetail;
import java.util.List;

public record BankAccountDetailResource(float accountBalance, List<TransactionDetail>
    transactionsInInterval) {

}

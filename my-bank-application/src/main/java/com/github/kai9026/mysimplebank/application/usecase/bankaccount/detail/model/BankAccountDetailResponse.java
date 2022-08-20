package com.github.kai9026.mysimplebank.application.usecase.bankaccount.detail.model;

import java.util.List;

public record BankAccountDetailResponse(float accountBalance, List<TransactionDetail>
    transactions) {

}

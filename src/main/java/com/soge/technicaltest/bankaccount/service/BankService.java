package com.soge.technicaltest.bankaccount.service;

import com.soge.technicaltest.bankaccount.model.Account;
import com.soge.technicaltest.bankaccount.model.Transaction;
import com.soge.technicaltest.bankaccount.model.TransactionStatus;
import com.soge.technicaltest.bankaccount.model.TransactionType;
import com.soge.technicaltest.bankaccount.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BankService {

    private static final String DEFAULT_CURRENCY = "EUR";
    private final AccountsRepository accountsRepository;

    @Autowired
    public BankService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    public Map<String, Account> allAccounts() {
        return accountsRepository.allAccounts();
    }

    public List<Transaction> transactionHistoryOf(final String accountId, final Integer periodInMonth) {
        return accountsRepository.accountFrom(accountId).getTransactionHistory()
                .stream()
                .filter(transaction -> transaction.getDateTime().isAfter(LocalDateTime.now().minusMonths(periodInMonth)))
                .collect(Collectors.toList());
    }

    public Account deposit(final String accountId, final Double depositAmount) {
        final Account account = accountsRepository.accountFrom(accountId);
        final Double newBalance = account.getBalance() + depositAmount;
        account.setBalance(newBalance);
        final Transaction transaction = newTransactionWith(TransactionType.DEPOSIT, TransactionStatus.SUCCESS, null, depositAmount, newBalance);
        account.getTransactionHistory().add(transaction);
        return account;
    }

    public Account withdraw(final String accountId, final Double withdrawAmount) {
        final Account account = accountsRepository.accountFrom(accountId);
        if (account.getBalance()>= withdrawAmount){
            final Double newBalance = account.getBalance() - withdrawAmount;
            account.setBalance(newBalance);
            final Transaction transaction = newTransactionWith(TransactionType.WITHDRAW, TransactionStatus.SUCCESS, null, withdrawAmount, newBalance);
            account.getTransactionHistory().add(transaction);
        }
        else {
            final Transaction transaction = newTransactionWith(TransactionType.WITHDRAW, TransactionStatus.FAIL, "Balance not enough", withdrawAmount, account.getBalance());
            account.getTransactionHistory().add(transaction);
        }
        return account;
    }

    private Transaction newTransactionWith(final TransactionType transactionType,
                                           final TransactionStatus transactionStatus,
                                           final String message,
                                           final Double amount, final Double newBalance) {
        return Transaction.builder()
                        .transactionType(transactionType)
                        .transactionStatus(transactionStatus)
                        .message(message)
                        .amount(amount)
                        .balance(newBalance)
                        .currency(DEFAULT_CURRENCY)
                        .dateTime(LocalDateTime.now())
                        .build();
    }

    public Account consultAccount(final String accountId) {
        return accountsRepository.accountFrom(accountId);
    }
}

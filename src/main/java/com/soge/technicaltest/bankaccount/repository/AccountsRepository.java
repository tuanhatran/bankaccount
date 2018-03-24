package com.soge.technicaltest.bankaccount.repository;

import com.soge.technicaltest.bankaccount.model.Account;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class AccountsRepository {
    private static final String DEFAULT_CURRENCY = "EUR";
    private final Map<String, Account> accounts;

    public AccountsRepository(){
        accounts = new HashMap<>();
    }

    public Account accountFrom(final String accountId){
        final Account result;
        if (accounts.containsKey(accountId)){
            result = accounts.get(accountId);
        }
        else {
            result = Account.builder()
                    .accountId(accountId)
                    .balance(0d)
                    .currency(DEFAULT_CURRENCY)
                    .transactionHistory(new ArrayList<>())
                    .build();
            accounts.put(accountId, result);
        }
        return result;
    }

    public void addAccount(final Account account){
        accounts.put(account.getAccountId(), account);
    }

    public Map<String, Account> allAccounts() {
        return accounts;
    }
}

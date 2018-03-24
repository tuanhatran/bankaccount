package com.soge.technicaltest.bankaccount.repository;

import com.soge.technicaltest.bankaccount.model.Account;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AccountsRepositoryTest {

    private static final String DEFAULT_CURRENCY = "EUR";
    private static final String ACCOUNT_ID = "thtran";
    private AccountsRepository accountsRepository;

    @Before
    public void setUp() {
        accountsRepository = new AccountsRepository();
    }

    @Test
    public void testRetrieveAccountWhenNoAccountExist() {
        final Account expectedAccount = accountsRepository.accountFrom(ACCOUNT_ID);

        assertThat(expectedAccount.getAccountId(), is(ACCOUNT_ID));
        assertThat(expectedAccount.getBalance(), is(0d));
        assertThat(expectedAccount.getTransactionHistory(), is(Collections.EMPTY_LIST));
    }

    @Test
    public void testRetrieveAccount() {
        accountsRepository.addAccount(Account.builder()
                .accountId(ACCOUNT_ID)
                .balance(500d)
                .currency(DEFAULT_CURRENCY)
                .transactionHistory(new ArrayList<>())
                .build());

        final Account expectedAccount = accountsRepository.accountFrom(ACCOUNT_ID);

        assertThat(expectedAccount.getAccountId(), is(ACCOUNT_ID));
        assertThat(expectedAccount.getBalance(), is(500d));
        assertThat(expectedAccount.getCurrency(), is(DEFAULT_CURRENCY));
        assertThat(expectedAccount.getTransactionHistory(), is(Collections.EMPTY_LIST));
    }

    @Test
    public void testRetrieveAllAccounts() {
        final Account account1 = Account.builder()
                .accountId("AccountID_1").build();
        accountsRepository.addAccount(account1);

        final Account account2 = Account.builder()
                .accountId("AccountID_2").build();
        accountsRepository.addAccount(account2);

        final Map<String,Account> expectedAccounts = accountsRepository.allAccounts();

        assertThat(expectedAccounts.size(), is(2));
        assertThat(expectedAccounts.containsKey("AccountID_1"), is(true));
        assertThat(expectedAccounts.containsKey("AccountID_2"), is(true));
    }
}
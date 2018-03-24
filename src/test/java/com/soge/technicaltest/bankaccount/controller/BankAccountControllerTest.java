package com.soge.technicaltest.bankaccount.controller;

import com.soge.technicaltest.bankaccount.model.Account;
import com.soge.technicaltest.bankaccount.model.Transaction;
import com.soge.technicaltest.bankaccount.service.BankService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BankAccountControllerTest {

    private static final String ACCOUNT_ID = "thtran";
    private BankService bankService;
    private BankAccountController bankAccountController;

    @Before
    public void setUp() {
        bankService = Mockito.mock(BankService.class);
        bankAccountController = new BankAccountController(bankService);
    }

    @Test
    public void testConsultAllAccounts() {
        final Account mockAccount1 = Mockito.mock(Account.class);
        final Account mockAccount2 = Mockito.mock(Account.class);
        final Map<String, Account> accounts = new HashMap<>();
        accounts.put("AccountID_1", mockAccount1);
        accounts.put("AccountID_2", mockAccount2);
        Mockito.when(bankService.allAccounts()).thenReturn(accounts);

        final Map<String,Account> expectedAccounts = bankAccountController.allAccounts();

        assertThat(expectedAccounts.size(), is(2));
        assertThat(expectedAccounts.containsKey("AccountID_1"), is(true));
        assertThat(expectedAccounts.containsKey("AccountID_2"), is(true));
    }

    @Test
    public void testConsultBalance() {
        final Account mockAccount = Mockito.mock(Account.class);
        Mockito.when(bankService.consultAccount(ACCOUNT_ID)).thenReturn(mockAccount);

        final Account expectedAccount = bankAccountController.consultBalance(ACCOUNT_ID);

        assertThat(expectedAccount, is(mockAccount));
    }

    @Test
    public void testConsultTransactionHistory() {
        final Transaction transaction1 = Mockito.mock(Transaction.class);
        final Transaction transaction2 = Mockito.mock(Transaction.class);
        Mockito.when(bankService.transactionHistoryOf(ACCOUNT_ID,2)).thenReturn(Arrays.asList(transaction1, transaction2));

        final List<Transaction> expectedTransactions = bankAccountController.consultTransactionHistory(ACCOUNT_ID, 2);

        assertThat(expectedTransactions.size(), is(2));
        assertThat(expectedTransactions.containsAll(Arrays.asList(transaction1, transaction2)), is(true));

    }

    @Test
    public void testDeposit() {
        final Account mockAccount = Mockito.mock(Account.class);
        Mockito.when(bankService.deposit(ACCOUNT_ID, 500d)).thenReturn(mockAccount);

        final Account expectedAccount = bankAccountController.deposit(ACCOUNT_ID, 500d);

        assertThat(expectedAccount, is(mockAccount));
    }

    @Test
    public void testWithdraw() {
        final Account mockAccount = Mockito.mock(Account.class);
        Mockito.when(bankService.withdraw(ACCOUNT_ID, 500d)).thenReturn(mockAccount);

        final Account expectedAccount = bankAccountController.withdraw(ACCOUNT_ID, 500d);

        assertThat(expectedAccount, is(mockAccount));
    }
}
package com.soge.technicaltest.bankaccount.service;

import com.soge.technicaltest.bankaccount.model.Account;
import com.soge.technicaltest.bankaccount.model.Transaction;
import com.soge.technicaltest.bankaccount.model.TransactionStatus;
import com.soge.technicaltest.bankaccount.model.TransactionType;
import com.soge.technicaltest.bankaccount.repository.AccountsRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BankServiceTest {

    private static final String DEFAULT_CURRENCY = "EUR";
    private static final String ACCOUNT_ID = "thtran";

    private AccountsRepository accountsRepository;

    private BankService bankService;

    @Before
    public void setUp() {
        accountsRepository = Mockito.mock(AccountsRepository.class);
        bankService = new BankService(accountsRepository);
    }

    @Test
    public void testRetrieveTransactionHistory() {
        final LocalDateTime expectedTransactionTime = LocalDateTime.now().minusMonths(1);
        final Transaction expectedTransaction = Transaction.builder()
                .transactionType(TransactionType.DEPOSIT)
                .transactionStatus(TransactionStatus.SUCCESS)
                .amount(500d)
                .balance(1000d)
                .currency(DEFAULT_CURRENCY)
                .dateTime(expectedTransactionTime)
                .build();
        final Transaction transactionIn3Month = Transaction.builder()
                .dateTime(LocalDateTime.now().minusMonths(3))
                .build();
        final Transaction transactionIn3Months2weeks = Transaction.builder()
                .dateTime(LocalDateTime.now().minusMonths(3).minusWeeks(2))
                .build();
        final Account expectedAccount = basicAccountWithBalance(500d)
                .transactionHistory(Arrays.asList(expectedTransaction, transactionIn3Month, transactionIn3Months2weeks))
                .build();
        Mockito.when(accountsRepository.accountFrom(ACCOUNT_ID)).thenReturn(expectedAccount);

        final List<Transaction> expectedTransactionHistory = bankService.transactionHistoryOf(ACCOUNT_ID, 2);

        assertTransaction(expectedTransactionTime, expectedTransactionHistory);
    }

    private Account.AccountBuilder basicAccountWithBalance(final Double initialBalance) {
        return Account.builder()
                .accountId(ACCOUNT_ID)
                .balance(initialBalance)
                .currency(DEFAULT_CURRENCY);
    }

    private void assertTransaction(final LocalDateTime expectedTransactionTime, final List<Transaction> expectedTransactionHistory) {
        assertThat(expectedTransactionHistory.size(), is(1));
        assertThat(expectedTransactionHistory.get(0).getDateTime(), is(expectedTransactionTime));
        assertThat(expectedTransactionHistory.get(0).getTransactionType(), is(TransactionType.DEPOSIT));
        assertThat(expectedTransactionHistory.get(0).getTransactionStatus(), is(TransactionStatus.SUCCESS));
        assertThat(expectedTransactionHistory.get(0).getBalance(), is(1000d));
        assertThat(expectedTransactionHistory.get(0).getAmount(), is(500d));
        assertThat(expectedTransactionHistory.get(0).getCurrency(), is(DEFAULT_CURRENCY));
    }

    @Test
    public void testDeposit() {
        Mockito.when(accountsRepository.accountFrom(ACCOUNT_ID)).thenReturn(basicAccountWithBalance(700d).transactionHistory(new ArrayList<>()).build());

        final Account expectedAccount = bankService.deposit(ACCOUNT_ID, 500d);

        assertThat(expectedAccount.getAccountId(), is(ACCOUNT_ID));
        assertThat(expectedAccount.getBalance(), is(1200d));
        assertThat(expectedAccount.getCurrency(), is(DEFAULT_CURRENCY));
        assertThat(expectedAccount.getTransactionHistory().get(0).getTransactionType(), is(TransactionType.DEPOSIT));
    }

    @Test
    public void testWithdrawSuccess() {
        Mockito.when(accountsRepository.accountFrom(ACCOUNT_ID)).thenReturn(basicAccountWithBalance(500d).transactionHistory(new ArrayList<>()).build());
        
        final Account expectedAccount = bankService.withdraw(ACCOUNT_ID, 200d);

        assertThat(expectedAccount.getAccountId(), is(ACCOUNT_ID));
        assertThat(expectedAccount.getBalance(), is(300d));
        assertThat(expectedAccount.getCurrency(), is(DEFAULT_CURRENCY));
        assertThat(expectedAccount.getTransactionHistory().get(0).getTransactionType(), is(TransactionType.WITHDRAW));
    }

    @Test
    public void testWithdrawFailAsBalanceNotEnough(){
        Mockito.when(accountsRepository.accountFrom(ACCOUNT_ID)).thenReturn(basicAccountWithBalance(500d).transactionHistory(new ArrayList<>()).build());

        final Account expectedAccount = bankService.withdraw(ACCOUNT_ID, 700d);

        assertThat(expectedAccount.getAccountId(), is(ACCOUNT_ID));
        assertThat(expectedAccount.getBalance(), is(500d));
        assertThat(expectedAccount.getCurrency(), is(DEFAULT_CURRENCY));
        assertThat(expectedAccount.getTransactionHistory().get(0).getTransactionType(), is(TransactionType.WITHDRAW));
        assertThat(expectedAccount.getTransactionHistory().get(0).getTransactionStatus(), is(TransactionStatus.FAIL));
        assertThat(expectedAccount.getTransactionHistory().get(0).getMessage(), is("Balance not enough"));
    }

    @Test
    public void testConsultAccount() {
        Mockito.when(accountsRepository.accountFrom(ACCOUNT_ID)).thenReturn(basicAccountWithBalance(450d).transactionHistory(new ArrayList<>()).build());

        final Account expectedAccount = bankService.consultAccount(ACCOUNT_ID);

        assertThat(expectedAccount.getAccountId(), is(ACCOUNT_ID));
        assertThat(expectedAccount.getBalance(), is(450d));
        assertThat(expectedAccount.getCurrency(), is(DEFAULT_CURRENCY));
        assertThat(expectedAccount.getTransactionHistory().isEmpty(), is(true));
    }

    @Test
    public void testRetrieveAllAccounts() {
        final Map<String, Account> mockAccounts = Mockito.mock(HashMap.class);
        Mockito.when(accountsRepository.allAccounts()).thenReturn(mockAccounts);

        final Map<String, Account> allAccounts = bankService.allAccounts();

        assertThat(allAccounts, is(mockAccounts));
    }
}
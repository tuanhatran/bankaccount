package com.soge.technicaltest.bankaccount.controller;

import com.soge.technicaltest.bankaccount.model.Account;
import com.soge.technicaltest.bankaccount.model.Transaction;
import com.soge.technicaltest.bankaccount.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bank/account")
public class BankAccountController {

    private BankService bankService;

    @Autowired
    public BankAccountController(final BankService bankService) {
        this.bankService = bankService;
    }

    /**
     * REST API to consult all accounts
     * @return all accounts with all info about balance and history
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    final Map<String, Account> allAccounts() {
        return bankService.allAccounts();
    }

    /**
     * REST API to consult a Bank Account with all transaction history with accountId
     * @param accountId
     * @return Account which contains all info about balance and history
     */
    @RequestMapping(value = "/consult/{accountId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    final Account consultBalance(@PathVariable final String accountId) {
        return bankService.consultAccount(accountId);
    }

    /**
     * REST API to consult all Transaction History from accountId within periodInMonth
     * @param accountId
     * @param periodInMonth
     * @return All transaction histories witin periodInMonth
     */
    @RequestMapping(value = "/history/{accountId}/{periodInMonth}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    final List<Transaction> consultTransactionHistory(@PathVariable final String accountId, @PathVariable final Integer periodInMonth) {
        return bankService.transactionHistoryOf(accountId, periodInMonth);
    }

    /**
     * REST API to deposit an amount in to accountId
     * @param accountId
     * @param amount
     * @return account with all info about balance as well as transaction history. A new account with accountId will be created if not existed
     */
    @RequestMapping(value = "/deposit/{accountId}/{amount}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    final Account deposit(@PathVariable final String accountId, @PathVariable final Double amount) {
        return bankService.deposit(accountId, amount);
    }

    /**
     * REST API to withdraw an amount in to accountId
     * @param accountId
     * @param amount
     * @return account with all info about balance as well as transaction history
     *         In case balance not enough, we will have a transaction with status fail and message "Balance not enough"
     */
    @RequestMapping(value = "/withdraw/{accountId}/{amount}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    final Account withdraw(@PathVariable final String accountId, @PathVariable final Double amount) {
        return bankService.withdraw(accountId, amount);
    }
}

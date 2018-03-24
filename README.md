# Bank account kata
Think of your personal bank account experience When in doubt, go for the simplest solution

# Requirements
- Deposit and Withdrawal
- Account statement (date, amount, balance)
- Statement printing
 
# User Stories
##### US 1:
**In order to** save money  
**As a** bank client  
**I want to** make a deposit in my account  
 
##### US 2: 
**In order to** retrieve some or all of my savings  
**As a** bank client  
**I want to** make a withdrawal from my account  
 
##### US 3: 
**In order to** check my operations  
**As a** bank client  
**I want to** see the history (operation, date, amount, balance)  of my operations  


#Realisation
How to start Application : Main Spring Boot class BankAccountApplication
## Available REST API :
- Deposit to accountId an amount : http://localhost:8080/account/deposit/{accountId}/{amount} <br/>
  Example : http://localhost:8080/account/deposit/thtran/500
- Withdraw from accountId an amount : http://localhost:8080/account/withdraw/{accountId}/{amount} <br/>
  Example : http://localhost:8080/account/withdraw/thtran/200 
- Consult account : http://localhost:8080/account/consult/{accountId} <br/>
  Example : http://localhost:8080/account/consult/thtran
- Consult all account : http://localhost:8080/bank/account/all
- Consult transaction history within periodInMonth http://localhost:8080/account/history/{accountId}/{periodInMonth} <br/>
  Example : http://localhost:8080/account/history/thtran/2
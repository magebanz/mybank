# mybank

Reports SQL: see src/main/resources/static for usecase 4 & 5

I couldn't create UI due to time constrains as well work commitments, so I created MyBankResource to test all the usecases. 

Please start up the application, all the tables should be populated.

Once server is started, got to http://localhost:8080/swagger-ui.html# in your browser.

Usecase 1 : Display transactional account with balances
    Resource: /mybank/client/transactional
    
Usecase 2 : Display currency accounts with with converted Rand values
    Resource: /mybank/client/currency/accounts
    
Usecase 3 : withdraw funds from a client account
    Resource: /mybank/withdraw

Select my-bank-resource and test first 3 usecases.

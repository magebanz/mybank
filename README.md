# mybank

SQL for transactional account with highest balance is under src/main/resources/static

Please note that an assumption was made that transactional accounts have account type NOT LIKE 'LOAN'

I couldn't create UI due to time constrains as well work commitments, so I created MyBankResource to test all the usecases. Please start up the application, ensure that all the tables are populated.
Once server is started, got to http://localhost:8080/swagger-ui.html# in your browser.

Usecase 1 : Display transactional account with balances
    Resource: /mybank/client/transactional
    
Usecase 2 : Display currency accounts with with converted Rand values
    Resource: /mybank/client/currency/accounts

Select my-bank-resource and test first 3 usecases.

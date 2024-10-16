- /v1/transaction/transfer: API is used to tranfer fund from one account to another account
- Repective TransactionService, AccountService and Repository are called to execute transaction
- Record TransactionHistory as critical part of audit along with transfer.
- Lock critical code to run in thread safe
- Handle Exceptions - with proper HTTP error code

Tasks to be done

- Authentication and authorization
- Implement account validation to avoid BOLA attack
- Implement proper logging advice
- Implement JPA and Transactions to handle db transactions
- Record transactionhistory separately based on business needs.


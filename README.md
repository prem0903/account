# account
add updateBalance api

Dev Challenge (Short)
The Asset Management Digital Challenge
We are providing you with a simple REST service with some very basic functionality - to add and read an account.

It is a standard gradle project, running on Spring Boot. It uses Lombok and if you've not come across it before you'll need to configure your

IDE to use it (otherwise code will not compile).
Your task is to add functionality for a transfer of money between accounts. Transfers should be specified by providing:
accountFrom id
accountTo id
amount to transfer between accounts

The amount to transfer should always be a positive number.
It should not be possible for an account to end up with negative balance (we do not support overdrafts!)
Whenever a transfer is made, a notification should be sent to both account holders, with a message containing id of the other account and
amount transferred.
For this purpose please use the NotificationService interface
Do not use the provided (simple) implementation in your tests - it is provided for the main application to run. In your tests you should mock this service. 


I have added one method for debit and credit(updateAccount) in account controller class.
Added BalanceCheckValidator for check insufficient balance

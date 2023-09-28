[![Rate your Sample](views/Ratesample.png)][ss1][![Yes](views/Thumbup.png)][ss2][![No](views/Thumbdown.png)][ss3]

# Wallet-Java
Wallet-Java
src
├── main
│   ├── java
│   │   ├── com
│   │   │   ├── example
│   │   │   │   ├── wallet
│   │   │   │   │   ├── WalletApplication.java
│   │   │   │   │   ├── controllers
│   │   │   │   │   │   ├── UserController.java
│   │   │   │   │   │   ├── TransactionController.java
│   │   │   │   │   ├── models
│   │   │   │   │   │   ├── User.java
│   │   │   │   │   │   ├── Transaction.java
│   │   │   │   │   ├── repositories
│   │   │   │   │   │   ├── UserRepository.java
│   │   │   │   │   │   ├── TransactionRepository.java
│   │   │   │   │   ├── services
│   │   │   │   │   │   ├── UserService.java
│   │   │   │   │   │   ├── TransactionService.java
├── resources
│   ├── application.properties
## Requirements

In order to successfully run this sample app you need a few things:

1. Java 1.8
2. A [developer.intuit.com](http://developer.intuit.com) account

## First Use Instructions

1. Clone the GitHub repo to your computer
2. Import the project in Eclipse or any other IDE of your choice


Note: If you do not want to use maven, just import the project and add the jars to your project externally.

## Running the code

## Project Structure
 **Standard Java coding structure is used for the sample app**
 Here's a high-level description of the backend architecture:

Controllers:

UserController: Manages user-related API endpoints (e.g., registration, login).
TransactionController: Manages transaction-related API endpoints (e.g., transaction history, credit, debit).
Services:

UserService: Implements user-related business logic (e.g., account creation, login, etc.).
TransactionService: Implements transaction-related business logic (e.g., credit, debit, transaction history, etc.).
AccountService: Implements account-related business logic (e.g., account balance).
Data Access Layer:

MongoDB: The database used to store user account information and transaction history

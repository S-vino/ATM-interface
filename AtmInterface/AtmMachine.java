package AtmInterface;

import java.util.*;


class AccountHolder {
    private String userId;
    private String userPin;

    public AccountHolder(String userId, String userPin) {
        this.userId = userId;
        this.userPin = userPin;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPin() {
        return userPin;
    }
}

class Account {
   private String accountId;
   private double balance;
   private List<String> transactionHistory;

   public Account(String accountId, double initialBalance) {
       this.accountId = accountId;
       this.balance = initialBalance;
       this.transactionHistory = new ArrayList<>();
   }

   public String getAccountId() {
       return accountId;
   }

   public double getBalance() {
       return balance;
   }

   public void deposit(double amount) {
       balance += amount;
       transactionHistory.add("Deposited: " + amount);
   }

   public void withdraw(double amount) {
       if (balance >= amount) {
           balance -= amount;
           transactionHistory.add("Withdrew: " + amount);
       } else {
           transactionHistory.add("Failed withdrawal: " + amount);
       }
   }

   public void transfer(Account toAccount, double amount) {
       if (balance >= amount) {
           this.withdraw(amount);
           toAccount.deposit(amount);
           transactionHistory.add("Transferred: " + amount + " to " + toAccount.getAccountId());
       } else {
           transactionHistory.add("Failed transfer: " + amount + " to " + toAccount.getAccountId());
       }
   }

   public List<String> getTransactionHistory() {
       return transactionHistory;
   }
}


class Bank {
    private Map<String, AccountHolder> accountHolders;

    public Bank() {
        accountHolders = new HashMap<>();
    }

    public void addAccountHolder(AccountHolder accountHolder) {
        accountHolders.put(accountHolder.getUserId(), accountHolder);
    }

    public AccountHolder getAccountHolder(String userId) {
        return accountHolders.get(userId);
    }

    public boolean authenticate(String userId, String userPin) {
        AccountHolder accountHolder = accountHolders.get(userId);
        return accountHolder != null && accountHolder.getUserPin().equals(userPin);
    }
}   


    class BankTransaction {
        private Map<String, Account> accounts;

        public BankTransaction() {
            accounts = new HashMap<>();
        }

        public void addAccount(Account account) {
            accounts.put(account.getAccountId(), account);
        }

        public Account getAccount(String accountId) {
            return accounts.get(accountId);
        }
    }



class ATM {
    private Bank bank;
    private BankTransaction bankTransaction;

    public ATM(Bank bank, BankTransaction bankTransaction) {
        this.bank = bank;
        this.bankTransaction = bankTransaction;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter User ID:");
        String userId = scanner.nextLine();

        System.out.println("Enter User Pin:");
        String userPin = scanner.nextLine();

        if (bank.authenticate(userId, userPin)) {
            System.out.println("Login successful!");
            showMenu(scanner, userId);
        } else {
            System.out.println("Invalid credentials.");
        }

        scanner.close();
    }
  

    private void showMenu(Scanner scanner, String userId) {
        Account account = bankTransaction.getAccount(userId);

        while (true) {
            System.out.println("1. Show transaction history");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Transaction History: " + account.getTransactionHistory());
                    break;
                case 2:
                    System.out.println("Enter amount to withdraw:");
                    double withdrawAmount = scanner.nextDouble();
                    account.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.println("Enter amount to deposit:");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;
                case 4:
                    System.out.println("Enter recipient account ID:");
                    String recipientId = scanner.next();
                    Account recipientAccount = bankTransaction.getAccount(recipientId);

                    if (recipientAccount != null) {
                        System.out.println("Enter amount to transfer:");
                        double transferAmount = scanner.nextDouble();
                        account.transfer(recipientAccount, transferAmount);
                    } else {
                        System.out.println("Recipient account not found.");
                    }
                    break;
                case 5:
                    System.out.println("Quitting...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
                





public class AtmMachine {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	        Bank bank = new Bank();
	        bank.addAccountHolder(new AccountHolder("user1", "pin1"));
	        bank.addAccountHolder(new AccountHolder("user2", "pin2"));

	        BankTransaction bankTransaction = new BankTransaction();
	        bankTransaction.addAccount(new Account("user1", 1000));
	        bankTransaction.addAccount(new Account("user2", 2000));

	        ATM atm = new ATM(bank, bankTransaction);
	        atm.start();
    }
}
 
	

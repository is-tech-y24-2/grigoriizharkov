import account.AccountType;
import account.DepositConsole;
import account.DepositDTO;
import account.IAccount;
import bank.Bank;
import bank.BankConsole;
import bank.BankDTO;
import bank.CentralBank;
import client.Client;
import client.ClientConsole;
import client.ClientDTO;
import tools.BankException;

import java.util.Scanner;

public class Console {
    private CentralBank centralBank;

    public Console(CentralBank centralBank) throws BankException {
        if (centralBank == null) {
            var e = new IllegalArgumentException();
            throw new BankException("Central bank can not be null!", e);
        }

        this.centralBank = centralBank;
    }

    public void work() throws BankException {
        while (true) {
            System.out.println("Enter what you want to do: " +
                    "\n1) Add new bank " +
                    "\n2) Add new client " +
                    "\n3) Add new account " +
                    "\n4) Add money to account " +
                    "\n5) Take money from account " +
                    "\n6) Make transaction " +
                    "\n0) Exit");

            Scanner choice = new Scanner(System.in);
            String answer = choice.nextLine();

            switch (answer) {
                case "1" -> {
                    BankDTO bankData = new BankConsole().collectBankData();
                    centralBank.addBank(bankData);
                }
                case "2" -> {
                    System.out.println("Choose in what bank you want to register new client:");
                    for (Bank bank : centralBank.getBanks()) {
                        System.out.println(bank.getName() + " ");
                    }
                    System.out.println();
                    Scanner inCase2 = new Scanner(System.in);
                    int bankCase2 = inCase2.nextInt();
                    System.out.println("Now enter client data:");
                    ClientDTO clientData = new ClientConsole().collectPersonalData();
                    centralBank.getBanks().get(bankCase2).registerClient(clientData);
                }
                case "3" -> {
                    System.out.println("Choose in what bank client registered:");
                    for (Bank bank : centralBank.getBanks()) {
                        System.out.println(bank.getName() + " ");
                    }
                    System.out.println();
                    Scanner inCase3 = new Scanner(System.in);
                    int bankCase3 = inCase3.nextInt();
                    System.out.println("Choose who wants to open new account:");
                    for (Client client : centralBank.getBanks().get(bankCase3).getClients()) {
                        System.out.println(client.getId() + " ");
                    }
                    System.out.println();
                    int clientCase3 = inCase3.nextInt();
                    System.out.println("Choose what account type you want to create:" +
                            " 1 for debit, " +
                            "2 for deposit, " +
                            "3 for credit");
                    int accountCase3 = inCase3.nextInt();
                    switch (accountCase3) {
                        case 1:
                            centralBank.getBanks().get(bankCase3).registerAccount(
                                    centralBank.getBanks().get(bankCase3).getClients().get(clientCase3),
                                    AccountType.Debit, null);
                            break;
                        case 2:
                            System.out.println("Enter extra info:");
                            DepositDTO depositData = new DepositConsole().collectDepositConditions();
                            centralBank.getBanks().get(bankCase3).registerAccount(
                                    centralBank.getBanks().get(bankCase3).getClients().get(clientCase3),
                                    AccountType.Deposit, depositData);
                            break;
                        case 3:
                            centralBank.getBanks().get(bankCase3).registerAccount(
                                    centralBank.getBanks().get(bankCase3).getClients().get(clientCase3),
                                    AccountType.Credit, null);
                        default:
                            var e = new IllegalArgumentException();
                            throw new BankException("Invalid account type!", e);
                    }
                }
                case "4" -> {
                    System.out.println("Choose in what bank client registered:");
                    for (Bank bank : centralBank.getBanks()) {
                        System.out.println(bank.getName() + " ");
                    }
                    System.out.println();
                    Scanner inCase4 = new Scanner(System.in);
                    int bankCase4 = inCase4.nextInt();
                    System.out.println("Choose account's owner");
                    for (Client client : centralBank.getBanks().get(bankCase4).getClients()) {
                        System.out.println(client.getId() + " ");
                    }
                    System.out.println();
                    int clientCase4 = inCase4.nextInt();
                    System.out.println("Choose account:");
                    for (IAccount account :
                            centralBank.getBanks().get(bankCase4).getClients().get(clientCase4).getAccounts()) {
                        System.out.println(account.getId() + " ");
                    }
                    System.out.println();
                    int accountCase4 = inCase4.nextInt();
                    System.out.println("Enter amount:");
                    double amountCase4 = inCase4.nextDouble();
                    centralBank.getBanks().get(bankCase4).getClients().get(clientCase4).
                            getAccounts().get(accountCase4).addMoney(amountCase4);
                }
                case "5" -> {
                    System.out.println("Choose in what bank client registered:");
                    for (Bank bank : centralBank.getBanks()) {
                        System.out.println(bank.getName() + " ");
                    }
                    System.out.println();
                    Scanner inCase5 = new Scanner(System.in);
                    int bankCase5 = inCase5.nextInt();
                    System.out.println("Choose account's owner");
                    for (Client client : centralBank.getBanks().get(bankCase5).getClients()) {
                        System.out.println(client.getId() + " ");
                    }
                    System.out.println();
                    int clientCase5 = inCase5.nextInt();
                    System.out.println("Choose account:");
                    for (IAccount account :
                            centralBank.getBanks().get(bankCase5).getClients().get(clientCase5).getAccounts()) {
                        System.out.println(account.getId() + " ");
                    }
                    System.out.println();
                    int accountCase5 = inCase5.nextInt();
                    System.out.println("Enter amount:");
                    double amountCase5 = inCase5.nextDouble();
                    centralBank.getBanks().get(bankCase5).getClients().get(clientCase5).
                            getAccounts().get(accountCase5).takeMoney(amountCase5);
                }
                case "6" -> {
                    IAccount from;
                    IAccount to;
                    System.out.println("Choose in what bank client registered:");
                    for (Bank bank : centralBank.getBanks()) {
                        System.out.println(bank.getName() + " ");
                    }
                    System.out.println();
                    Scanner inCase6 = new Scanner(System.in);
                    int bankCase6 = inCase6.nextInt();
                    System.out.println("Choose account's owner");
                    for (Client client : centralBank.getBanks().get(bankCase6).getClients()) {
                        System.out.println(client.getId() + " ");
                    }
                    System.out.println();
                    int clientCase6 = inCase6.nextInt();
                    System.out.println("Choose account:");
                    for (IAccount account :
                            centralBank.getBanks().get(bankCase6).getClients().get(clientCase6).getAccounts()) {
                        System.out.println(account.getId() + " ");
                    }
                    System.out.println();
                    int accountCase6 = inCase6.nextInt();
                    from = centralBank.getBanks().get(bankCase6).getClients().get(clientCase6).
                            getAccounts().get(accountCase6);
                    System.out.println("Enter amount:");
                    double amountCase6 = inCase6.nextDouble();
                    centralBank.getBanks().get(bankCase6).getClients().get(clientCase6).
                            getAccounts().get(accountCase6).takeMoney(amountCase6);
                    System.out.println("Choose in what bank client registered:");
                    for (Bank bank : centralBank.getBanks()) {
                        System.out.println(bank.getName() + " ");
                    }
                    System.out.println();
                    bankCase6 = inCase6.nextInt();
                    System.out.println("Choose account's owner");
                    for (Client client : centralBank.getBanks().get(bankCase6).getClients()) {
                        System.out.println(client.getId() + " ");
                    }
                    System.out.println();
                    clientCase6 = inCase6.nextInt();
                    System.out.println("Choose account:");
                    for (IAccount account :
                            centralBank.getBanks().get(bankCase6).getClients().get(clientCase6).getAccounts()) {
                        System.out.println(account.getId() + " ");
                    }
                    System.out.println();
                    accountCase6 = inCase6.nextInt();
                    to = centralBank.getBanks().get(bankCase6).getClients().get(clientCase6).
                            getAccounts().get(accountCase6);
                    centralBank.getBanks().get(bankCase6).getClients().get(clientCase6).
                            getAccounts().get(accountCase6).addMoney(amountCase6);
                    centralBank.makeTransaction(from, amountCase6, to);
                }
                case "0" -> System.exit(0);
                default -> {
                    var e = new IllegalArgumentException();
                    throw new BankException("Invalid choice!", e);
                }
            }
        }
    }
}

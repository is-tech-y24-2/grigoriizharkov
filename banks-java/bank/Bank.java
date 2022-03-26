package bank;

import account.*;
import client.Client;
import client.ClientDTO;
import tools.BankException;
import tools.EventManager;
import tools.IEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Bank implements IEventListener {

    private CentralBank centralBank;
    private List<Client> clients;

    private String name;
    private double debitInterest;
    private double creditFee;
    private double creditLimit;
    private double unverifiedLimit;
    private double depositDefaultInterest;
    private Map<Double, Double> depositInterestConditions;

    public EventManager events;

    public Bank(CentralBank centralBank, BankDTO bankData) throws BankException {
        if (centralBank == null) {
            var e = new IllegalArgumentException();
            throw new BankException("Central bank can not be null!", e);
        }

        if (bankData == null) {
            var e = new IllegalArgumentException();
            throw new BankException("Bank data can not be null!", e);
        }

        if (bankData.getName() == null) {
            var e =  new IllegalArgumentException();
            throw new BankException("Bank data can not be null!", e);
        }
        if (bankData.getCreditFee() <= 0) throw new BankException("Credit fee can not be negative!");
        if (bankData.getCreditLimit() <= 0) throw new BankException("Credit limit can not be negative!");
        if (bankData.getDebitInterest() <= 0) throw new BankException("Debit interest can not be negative!");
        if (bankData.getDepositDefaultInterest() <= 0) throw new BankException("Deposit default interest can not be negative!");
        if (bankData.getUnverifiedLimit() <= 0) throw new BankException("Unverified limit can not be negative!");

        this.centralBank = centralBank;

        name = bankData.getName();

        debitInterest = bankData.getDebitInterest();
        creditFee = bankData.getCreditFee();
        creditLimit = bankData.getCreditLimit();
        unverifiedLimit = bankData.getUnverifiedLimit();
        depositDefaultInterest = bankData.getDepositDefaultInterest();
        depositInterestConditions = bankData.getDepositInterestConditions();

        clients = new ArrayList<>();

        events = new EventManager("unverified limit", "debit interest", "credit fee", "credit limit");
    }

    public String getName() {
        return name;
    }

    public double getDebitInterest() {
        return debitInterest;
    }

    public double getCreditFee() {
        return creditFee;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public double getUnverifiedLimit() {
        return unverifiedLimit;
    }

    public double getDepositDefaultInterest() {
        return depositDefaultInterest;
    }

    public List<Client> getClients() {
        return clients;
    }

    public Client registerClient(ClientDTO clientData) throws BankException {
        if (clientData == null) {
            var e = new IllegalArgumentException();
            throw new BankException("Client data can not be null!", e);
        }

        for (Client client: clients) {
            if (client.getId() == clientData.getId()) {
                throw new BankException("Such client already exist!");
            }
        }

        Client client = new Client(clientData, this);
        clients.add(client);

        return client;
    }

    public Client fillMissingData(Client client, ClientDTO clientData) throws BankException {
        if (client == null) {
            var e = new IllegalArgumentException();
            throw new BankException("Client can not be null!", e);
        }

        if (clientData == null) {
            var e = new IllegalArgumentException();
            throw new BankException("Client data can not be null!", e);
        }

        if (!clients.contains(client)) throw new BankException("Unknown client!");

        client.addMissingData(clientData);

        return client;
    }

    public IAccount registerAccount(Client client, AccountType accountType, DepositDTO depositData) throws BankException {
        if (client == null) {
            var e = new IllegalArgumentException();
            throw new BankException("Client can not be null!", e);
        }

        IAccount account;
        switch (accountType) {
            case Credit -> account = new Credit(creditFee, creditLimit, client.getVerified(), unverifiedLimit);
            case Debit -> account = new Debit(debitInterest, client.getVerified(), unverifiedLimit);
            case Deposit -> account = new Deposit(depositDefaultInterest, depositInterestConditions, depositData, client.getVerified(), unverifiedLimit);
            default -> {
                var e = new IllegalArgumentException();
                throw new BankException("Invalid account type!", e);
            }
        }

        client.addAccount(account);

        return account;
    }

    public void calculateDailyPayment() {
        for (Client client: clients) {
            for (IAccount account: client.getAccounts()) {
                account.calculateDailyPayment();
            }
        }
    }

    public void payReward() throws BankException {
        for (Client client: clients) {
            for (IAccount account: client.getAccounts()) {
                account.getReward();
            }
        }
    }

    public void changeUnverifiedLimit(double newLimit) throws BankException {
        if (newLimit <= 0) throw new BankException("Limit must be positive!");
        unverifiedLimit = newLimit;

        events.notify("unverified limit");
    }

    public void changeDebitInterest(double newInterest) throws BankException {
        if (newInterest <= 0) throw new BankException("Interest must be positive!");
        debitInterest = newInterest;

        events.notify("debit interest");
    }

    public void changeCreditFee(double newFee) throws BankException {
        if (newFee <= 0) throw new BankException("Fee must be positive!");
        creditFee = newFee;

        events.notify("credit fee");
    }

    public void changeCreditLimit(double newLimit) throws BankException {
        if (newLimit <= 0) throw new BankException("Limit must be positive!");
        creditFee = newLimit;

        events.notify("credit limit");
    }

    @Override
    public void update(String eventType) throws BankException {
        switch (eventType) {
            case "daily payment" -> calculateDailyPayment();
            case "monthly payment" -> payReward();
            default -> {
                var e = new IllegalArgumentException();
                throw new BankException("Invalid event!", e);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;

        Bank other = (Bank) obj;

        return other.getName().equals(this.getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

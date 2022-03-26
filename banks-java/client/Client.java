package client;

import account.IAccount;
import bank.Bank;
import tools.BankException;
import tools.IEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Client implements IEventListener {

    private final String name;
    private final String surname;

    private String address;
    private String passport;

    private final UUID id;

    private boolean verified;

    private final List<IAccount> accounts;

    private final Bank bank;

    public Client(ClientDTO clientData, Bank bank) throws BankException {

        if (clientData == null){
            var e = new IllegalArgumentException();
            throw new BankException("Client data can not be null!", e);
        }

        if (bank == null) {
            var e = new IllegalArgumentException();
            throw new BankException("Bank can not be null!", e);
        }

        this.bank = bank;

        if (clientData.getName() == null) throw new IllegalArgumentException();
        if (clientData.getSurname() == null) throw new IllegalArgumentException();

        name = clientData.getName();
        surname = clientData.getSurname();

        if (clientData.getAddress() == null) throw new IllegalArgumentException();
        if (clientData.getPassport() == null) throw new IllegalArgumentException();

        verified = !clientData.getAddress().equals("LATER") && !clientData.getPassport().equals("LATER");

        address = clientData.getAddress();
        passport = clientData.getPassport();

        id = clientData.getId();
        accounts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public String getPassport() {
        return passport;
    }

    public UUID getId() {
        return id;
    }

    public boolean getVerified() {
        return verified;
    }

    public List<IAccount> getAccounts() {
        return accounts;
    }

    public void addMissingData(ClientDTO clientData) throws BankException {
        if (clientData == null){
            var e = new IllegalArgumentException();
            throw new BankException("Client data can not be null!", e);
        }

        if (clientData.getAddress() == null) throw new IllegalArgumentException();
        if (clientData.getPassport() == null) throw new IllegalArgumentException();

        if (clientData.getAddress().equals("LATER")) throw new BankException("Address must be valid!");
        if (clientData.getPassport().equals("LATER")) throw new BankException("Passport must be valid!");

        address = clientData.getAddress();
        passport = clientData.getPassport();

        verified = true;
    }

    public void addAccount(IAccount account) throws BankException {
        if (account == null) {
            var e = new IllegalArgumentException();
            throw new BankException("Account can not be null!", e);
        }

        if (accounts.contains(account)) throw new BankException("This client already has this account!");
        accounts.add(account);
    }

    public void displayEvent(String event) {
    }

    @Override
    public void update(String eventType) throws BankException {
        displayEvent(eventType);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;

        Client other = (Client) obj;

        return other.getId() == this.getId();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}



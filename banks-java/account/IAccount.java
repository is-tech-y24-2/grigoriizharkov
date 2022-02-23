package account;

import tools.BankException;

import java.util.UUID;

public interface IAccount {

    double getBalance();
    UUID getId();

    void takeMoney(double amount) throws BankException;
    void addMoney(double amount) throws BankException;

    void calculateDailyPayment();
    void getReward() throws BankException;

}

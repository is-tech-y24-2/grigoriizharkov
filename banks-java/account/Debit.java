package account;

import tools.BankException;

import java.util.UUID;

public class Debit implements IAccount {
    private double interest;

    private double unverifiedLimit;
    private boolean verified;

    private double accumulatedAmount;
    private double balance;

    private UUID id;

    public Debit(double interest, boolean verified, double unverifiedLimit) throws BankException {
        if (interest <= 0) throw new BankException("Interest for debit account must be positive!");
        if (unverifiedLimit <= 0) throw new BankException("Limit for unverified account must be positive!");

        this.interest = interest;
        this.verified = verified;
        this.unverifiedLimit = unverifiedLimit;

        id = UUID.randomUUID();

        accumulatedAmount = 0;
        balance = 0;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    public void takeMoney(double amount) throws BankException {
        if (amount <= 0) throw new BankException("Amount must be positive!");
        if (amount > balance) throw new BankException("Amount is too big!");
        if (amount > unverifiedLimit && !verified)
            throw new BankException("Amount is bigger than limit for unverified account!");

        balance -= amount;
    }

    public void addMoney(double amount) throws BankException {
        if (amount <= 0) throw new BankException("Amount must be positive!");
        balance += amount;
    }

    @Override
    public void calculateDailyPayment() {
        accumulatedAmount += balance * interest / 365;
    }

    @Override
    public void getReward() {
        balance += accumulatedAmount;
        accumulatedAmount = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;

        Debit other = (Debit) obj;

        return other.getId() == this.getId();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

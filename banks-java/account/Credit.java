package account;

import tools.BankException;

import java.util.UUID;

public class Credit implements IAccount {
    private double fee;
    private double limit;

    private double unverifiedLimit;
    private boolean verified;

    private double accumulatedFee;
    private double balance;

    private UUID id;

    public Credit(double fee, double limit, boolean verified, double unverifiedLimit) throws BankException {
        if (fee <= 0) throw new BankException("Fee for credit account must be positive!");
        if (limit <= 0) throw new BankException("Limit for credit account must be positive!");
        if (unverifiedLimit <= 0) throw new BankException("Limit for unverified account must be positive!");

        this.fee = fee;
        this.limit = limit;
        this.unverifiedLimit = unverifiedLimit;
        this.verified = verified;

        id = UUID.randomUUID();

        accumulatedFee = 0;
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
        if (amount > balance + limit) throw new BankException("Amount is too big!");
        if (amount > unverifiedLimit && !verified)
            throw new BankException("Amount is bigger than limit for unverified account!");

        balance -= amount;
    }

    public void addMoney(double amount) throws BankException {
        if (amount <= 0) throw new BankException("Amount must be positive!");
        balance += amount;
    }

    public void calculateDailyPayment() {
        if (balance < 0) {
            accumulatedFee += fee;
        }
    }

    public void getReward() throws BankException {
        if (balance + limit < accumulatedFee) throw new BankException("Balance too low to take commission!");
        balance -= accumulatedFee;
        accumulatedFee = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;

        Credit other = (Credit) obj;

        return other.getId() == this.getId();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

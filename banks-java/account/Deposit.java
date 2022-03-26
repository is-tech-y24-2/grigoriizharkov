package account;

import tools.BankException;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public class Deposit implements IAccount {
    private double interest;

    private double unverifiedLimit;
    private boolean verified;

    private LocalDate validUntil;

    private double accumulatedAmount;
    private double balance;

    private UUID id;

    public Deposit(double interest, Map<Double, Double> interestConditions,
                   DepositDTO depositData, boolean verified, double unverifiedLimit) throws BankException {
        if (interest <= 0) throw new BankException("Interest for debit account must be positive!");
        if (unverifiedLimit <= 0) throw new BankException("Limit for unverified account must be positive!");

        for (Double percent : interestConditions.keySet()) {
            if (percent <= 0) throw new BankException("Percent can not be null!");
        }

        for (Double amount : interestConditions.values()) {
            if (amount <= 0) throw new BankException("Amount can not be null!");
        }

        if (depositData == null) {
            var e = new IllegalArgumentException();
            throw new BankException("Deposit data can not be null!", e);
        }

        balance = depositData.getBalance();
        validUntil = LocalDate.parse(depositData.getValidUntil());

        if (balance <= 0) throw new BankException("Balance must be positive!");
        if (validUntil.isBefore(LocalDate.now())) throw new BankException("Invalid date!");

        this.interest = interest;
        for (Map.Entry<Double, Double> condition : interestConditions.entrySet()) {
            if (balance <= condition.getKey()) this.interest = condition.getValue();
        }

        this.verified = verified;
        this.unverifiedLimit = unverifiedLimit;

        id = UUID.randomUUID();
        accumulatedAmount = 0;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void takeMoney(double amount) throws BankException {
        if (amount <= 0) throw new BankException("Amount must be positive!");
        if (amount > balance) throw new BankException("Amount is too big!");
        if (amount > unverifiedLimit && !verified)
            throw new BankException("Amount is bigger than limit for unverified account!");

        if (LocalDate.now().isBefore(validUntil)) throw new BankException("It is impossible to take money now!");

        balance -= amount;
    }

    @Override
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

        Deposit other = (Deposit) obj;

        return other.getId() == this.getId();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

package bank;

import java.util.Map;

public class BankDTO {

    private String name;
    private double debitInterest;
    private double creditFee;
    private double creditLimit;
    private double unverifiedLimit;
    private double depositDefaultInterest;
    private Map<Double, Double> depositInterestConditions;

    public BankDTO(String name, double debitInterest, double creditFee, double creditLimit,
                   double unverifiedLimit, double depositDefaultInterest, Map<Double, Double> depositInterestConditions) {
        this.name = name;
        this.debitInterest = debitInterest;
        this.creditFee = creditFee;
        this.creditLimit = creditLimit;
        this.unverifiedLimit = unverifiedLimit;
        this.depositDefaultInterest = depositDefaultInterest;
        this.depositInterestConditions = depositInterestConditions;
    }

    public double getCreditFee() {
        return creditFee;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public double getDebitInterest() {
        return debitInterest;
    }

    public String getName() {
        return name;
    }

    public double getDepositDefaultInterest() {
        return depositDefaultInterest;
    }

    public double getUnverifiedLimit() {
        return unverifiedLimit;
    }

    public Map<Double, Double> getDepositInterestConditions() {
        return depositInterestConditions;
    }
}

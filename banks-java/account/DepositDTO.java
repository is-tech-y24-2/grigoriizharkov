package account;

public class DepositDTO {
    private double balance;
    private String validUntil;

    public DepositDTO(double balance, String validUntil){
        this.balance = balance;
        this.validUntil = validUntil;
    }

    public double getBalance() {
        return balance;
    }

    public String getValidUntil() {
        return validUntil;
    }
}

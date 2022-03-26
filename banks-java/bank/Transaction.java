package bank;

import account.IAccount;
import tools.BankException;

public class Transaction {
    private final double amount;
    private final IAccount from;
    private final IAccount to;

    public Transaction(IAccount from, double amount, IAccount to) throws BankException {
        if (from == null) {
            var e = new IllegalArgumentException();
            throw new BankException("Account can not be null!", e);
        }

        if (to == null) {
            var e = new IllegalArgumentException();
            throw new BankException("Account can not be null!", e);
        }

        if (amount <= 0) throw new BankException("Amount can not be negative!");

        this.amount = amount;
        this.from = from;
        this.to = to;
    }

    public double getAmount() {
        return amount;
    }

    public IAccount getFrom() {
        return from;
    }

    public IAccount getTo() {
        return to;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;

        Transaction other = (Transaction) obj;

        return from.equals(other.from) && to.equals(other.to) && amount == other.amount;
    }

    @Override
    public int hashCode() {
        return from.hashCode() + to.hashCode() - (int) amount;
    }
}

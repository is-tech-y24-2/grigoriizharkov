package bank;

import account.IAccount;
import tools.BankException;
import tools.EventManager;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CentralBank {

    private final List<Bank> banks;
    private final List<Transaction> transactions;

    public EventManager events;

    public CentralBank() {
        banks = new ArrayList<>();
        transactions = new ArrayList<>();

        events = new EventManager("daily payment", "monthly payment");
    }

    public List<Bank> getBanks() {
        return banks;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Bank addBank(BankDTO bankData) throws BankException {
        if (bankData == null) {
            var e = new IllegalArgumentException();
            throw new BankException("Bank can not be null!", e);
        }

        for (Bank bank: banks) {
            if (Objects.equals(bank.getName(), bankData.getName())) {
                throw new BankException("Bank with such name already exist!");
            }
        }

        Bank bank = new Bank(this, bankData);
        banks.add(bank);

        return  bank;
    }

    public Transaction makeTransaction(IAccount from, double amount, IAccount to) throws BankException {
        if (from == null) {
            var e = new IllegalArgumentException();
            throw new BankException("Account can not be null!", e);
        }

        if (to == null) {
            var e = new IllegalArgumentException();
            throw new BankException("Account can not be null!", e);
        }

        if (amount <= 0) throw new BankException("Amount can not be negative!");

        from.takeMoney(amount);
        to.addMoney(amount);

        var transaction = new Transaction(from, amount, to);
        transactions.add(transaction);

        return transaction;
    }

    public void cancelTransaction(Transaction transaction) throws BankException {
        if (transaction == null) {
            var e = new IllegalArgumentException();
            throw new BankException("Transaction can not be null!", e);
        }

        transaction.getFrom().addMoney(transaction.getAmount());
        transaction.getTo().takeMoney(transaction.getAmount());

        transactions.remove(transaction);
    }

    public void calculateIncome(LocalDate from, LocalDate to) throws BankException {
        long daysBetween = Duration.between(from, to).toDays();
        for (long i = 0; i < daysBetween; i++) {
            events.notify("daily payment");
            if (i % 30 == 0 && i > 0) events.notify("monthly payment");
        }
    }


}

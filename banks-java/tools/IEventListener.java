package tools;

public interface IEventListener {
    void update(String eventType) throws BankException;
}

import bank.CentralBank;
import tools.BankException;

public class Main {
    public static void main(String[] args) throws BankException {
        CentralBank cb = new CentralBank();
        Console ui = new Console(cb);
        ui.work();
    }
}

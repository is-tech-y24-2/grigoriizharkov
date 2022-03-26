package account;

import java.util.Scanner;

public class DepositConsole {
    public DepositDTO collectDepositConditions() {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter amount you want to deposit:");
        double balance = in.nextDouble();

        System.out.println("Enter date you want deposit will be valid until (dd-mm-yyyy):");
        String date = in.nextLine();

        return new DepositDTO(balance, date);
    }
}

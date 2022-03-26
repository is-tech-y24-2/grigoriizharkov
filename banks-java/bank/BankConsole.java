package bank;

import java.util.HashMap;
import java.util.Scanner;

public class BankConsole {
    public BankDTO collectBankData() {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter bank name:");
        String name = in.nextLine();

        System.out.println("Enter debit interest:");
        double debitInterest = in.nextDouble();

        System.out.println("Enter credit fee:");
        double creditFee = in.nextDouble();

        System.out.println("Enter credit limit:");
        double creditLimit = in.nextDouble();

        System.out.println("Enter limit for unverified clients:");
        double unverifiedLimit = in.nextDouble();

        System.out.println("Enter deposit default interest:");
        double depositDefaultInterest = in.nextDouble();

        System.out.println("Enter how many conditions will be");
        int n = in.nextInt();

        HashMap<Double, Double> conditions = new HashMap<>(n);

        for (int i = 0; i < n; i++) {
            System.out.println("Enter amount border:");
            double amountBorder = in.nextDouble();

            System.out.println("Enter interest for this border");
            double interestBorder = in.nextDouble();

            conditions.put(amountBorder, interestBorder);
        }

        return new BankDTO(name, debitInterest, creditFee, creditLimit,
                unverifiedLimit, depositDefaultInterest, conditions);
    }
}

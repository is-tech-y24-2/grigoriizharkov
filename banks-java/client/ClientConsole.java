package client;

import tools.BankException;

import java.util.Scanner;

public class ClientConsole {
    public ClientDTO collectPersonalData() {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter your name:");
        String name = in.nextLine();

        System.out.println("Enter your surname:");
        String surname = in.nextLine();

        System.out.println("Enter your address:");
        String address = in.nextLine();

        System.out.println("Enter your passport:");
        String passport = in.nextLine();

        return new ClientDTO(name, surname, address, passport);
    }

    public ClientDTO addMissingData(ClientDTO clientData) throws BankException {
        if (clientData == null) {
            var e = new IllegalArgumentException();
            throw new BankException("Client data can not be null!", e);
        }

        String address = "LATER";
        String passport = "LATER";
        Scanner in = new Scanner(System.in);

        if (clientData.getAddress().equals("LATER")) {
            System.out.println("Enter your address^");
            address = in.nextLine();
        }

        if (clientData.getPassport().equals("LATER")) {
            System.out.println("Enter your passport");
            passport = in.nextLine();
        }

        return new ClientDTO(clientData.getName(), clientData.getSurname(), address, passport);
    }
}

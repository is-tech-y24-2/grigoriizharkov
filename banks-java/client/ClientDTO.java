package client;

import java.util.UUID;

public class ClientDTO {

    private final String name;
    private final String surname;
    private final String address;
    private final String passport;
    private final UUID id;

    public ClientDTO(String name, String surname, String address, String passport) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.passport = passport;

        id = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public String getPassport() {
        return passport;
    }

    public UUID getId() {
        return id;
    }
}

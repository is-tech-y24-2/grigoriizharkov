package controller;

import controller.tools.ControllerException;
import dao.colors.Color;
import dao.entities.Cat;
import dao.entities.Owner;
import service.Service;
import service.tools.ServiceException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class Console {
    private Service service;

    public Console(Service service) {
        this.service = service;
    }

    public void work() throws ControllerException {
        while (true) {
            System.out.println("""
                    Enter what you want to do:\s
                    1) Add new cat\s
                    2) Add new owner\s
                    3) Add cat to owner\s
                    4) Remove cat from owner\s
                    5) Make friends\s
                    6) Break friendship\s
                    7) Remove cat\s
                    8) Remove owner\s
                    0) Exit""");

            Scanner choice = new Scanner(System.in);
            String answer = choice.nextLine();

            switch (answer) {
                case "1" -> {
                    System.out.println("Enter cat's name:");
                    String name = choice.nextLine();

                    System.out.println("Enter cat's birthdate (yyyy-mm-dd):");
                    String birthdate = choice.nextLine();

                    System.out.println("Enter cat's species:");
                    String species = choice.nextLine();

                    System.out.println("Enter cat's color (Black, White, Gray or Orange):");
                    String color = choice.nextLine();

                    birthdate = birthdate.concat(" 00:00:00");

                    try {
                        service.addCat(name, Timestamp.valueOf(birthdate), species, Color.valueOf(color));
                    } catch (ServiceException e) {
                        throw new ControllerException("Problems with adding cat to service" + e.getMessage());
                    }
                }

                case "2" -> {
                    System.out.println("Enter owner's name:");
                    String name = choice.nextLine();

                    System.out.println("Enter owner's birthdate (yyyy-mm-dd):");
                    String birthdate = choice.nextLine();

                    birthdate = birthdate.concat(" 00:00:00");

                    try {
                        service.addOwner(name, Timestamp.valueOf(birthdate));
                    } catch (ServiceException e) {
                        throw new ControllerException("Problems with adding owner to service" + e.getMessage());
                    }
                }

                case "3" -> {
                    List<Cat> cats = service.getAllCats();
                    List<Owner> owners = service.getAllOwners();

                    System.out.println("Choose one cat to add to owner:");
                    for (Cat cat: cats) {
                        System.out.println(cat);
                    }
                    String catNumber = choice.nextLine();

                    if (Integer.parseInt(catNumber) >= cats.size()) {
                        throw new ArrayIndexOutOfBoundsException();
                    }

                    System.out.println("Choose one owner to add cat to:");
                    for (Owner owner: owners) {
                        System.out.println(owner);
                    }
                    String ownerNumber = choice.nextLine();

                    if (Integer.parseInt(ownerNumber) >= owners.size()) {
                        throw new ArrayIndexOutOfBoundsException();
                    }

                    try {
                        service.addCatToOwner(cats.get(Integer.parseInt(catNumber)), owners.get(Integer.parseInt(ownerNumber)));
                    } catch (ServiceException e) {
                        throw new ControllerException("Problems with adding cat to service" + e.getMessage());
                    }
                }

                case "4" -> {
                    List<Cat> cats = service.getAllCats();
                    List<Owner> owners = service.getAllOwners();

                    System.out.println("Choose one cat to be removed from owner:");
                    for (Cat cat: cats) {
                        System.out.println(cat);
                    }
                    String catNumber = choice.nextLine();

                    if (Integer.parseInt(catNumber) >= cats.size()) {
                        throw new ArrayIndexOutOfBoundsException();
                    }

                    System.out.println("Choose one owner whose cat will be removed:");
                    for (Owner owner: owners) {
                        System.out.println(owner);
                    }
                    String ownerNumber = choice.nextLine();

                    if (Integer.parseInt(ownerNumber) >= owners.size()) {
                        throw new ArrayIndexOutOfBoundsException();
                    }

                    try {
                        service.removeCatFromOwner(cats.get(Integer.parseInt(catNumber)), owners.get(Integer.parseInt(ownerNumber)));
                    } catch (ServiceException e) {
                        throw new ControllerException("Problems with removing cat from owner" + e.getMessage());
                    }
                }

                case "5" -> {
                    List<Cat> cats = service.getAllCats();

                    System.out.println("Choose first cat:");
                    for (Cat cat: cats) {
                        System.out.println(cat);
                    }
                    String firstCatNumber = choice.nextLine();

                    if (Integer.parseInt(firstCatNumber) >= cats.size()) {
                        throw new ArrayIndexOutOfBoundsException();
                    }

                    System.out.println("Choose second cat:");
                    for (Cat cat: cats) {
                        System.out.println(cat);
                    }
                    String secondCatNumber = choice.nextLine();

                    if (Integer.parseInt(secondCatNumber) >= cats.size()) {
                        throw new ArrayIndexOutOfBoundsException();
                    }

                    try {
                        service.makeFriends(cats.get(Integer.parseInt(firstCatNumber)), cats.get(Integer.parseInt(secondCatNumber)));
                    } catch (ServiceException e) {
                        throw new ControllerException("Problems with making friendship" + e.getMessage());
                    }
                }

                case "6" -> {
                    List<Cat> cats = service.getAllCats();

                    System.out.println("Choose first cat:");
                    for (Cat cat: cats) {
                        System.out.println(cat);
                    }
                    String firstCatNumber = choice.nextLine();

                    if (Integer.parseInt(firstCatNumber) >= cats.size()) {
                        throw new ArrayIndexOutOfBoundsException();
                    }

                    System.out.println("Choose second cat:");
                    for (Cat cat: cats) {
                        System.out.println(cat);
                    }
                    String secondCatNumber = choice.nextLine();

                    if (Integer.parseInt(secondCatNumber) >= cats.size()) {
                        throw new ArrayIndexOutOfBoundsException();
                    }

                    try {
                        service.breakFriendship(cats.get(Integer.parseInt(firstCatNumber)), cats.get(Integer.parseInt(secondCatNumber)));
                    } catch (ServiceException e) {
                        throw new ControllerException("Problems with breaking friendship" + e.getMessage());
                    }
                }

                case "7" -> {
                    List<Cat> cats = service.getAllCats();

                    System.out.println("Choose cat to be removed:");
                    for (Cat cat: cats) {
                        System.out.println(cat);
                    }
                    String catNumber = choice.nextLine();

                    if (Integer.parseInt(catNumber) >= cats.size()) {
                        throw new ArrayIndexOutOfBoundsException();
                    }

                    try {
                        service.removeCat(cats.get(Integer.parseInt(catNumber)));
                    } catch (ServiceException e) {
                        throw new ControllerException("Problems with removing cat from service" + e.getMessage());
                    }
                }

                case "8" -> {
                    List<Owner> owners = service.getAllOwners();

                    System.out.println("Choose owner to be removed:");
                    for (Owner owner: owners) {
                        System.out.println(owner);
                    }
                    String ownerNumber = choice.nextLine();

                    if (Integer.parseInt(ownerNumber) >= owners.size()) {
                        throw new ArrayIndexOutOfBoundsException();
                    }

                    try {
                        service.removeOwner(owners.get(Integer.parseInt(ownerNumber)));
                    } catch (ServiceException e) {
                        throw new ControllerException("Problems with removing owner from service" + e.getMessage());
                    }
                }

                case "0" -> System.exit(0);

                default -> {
                    var e = new IllegalArgumentException();
                    throw new ControllerException("Invalid option number!", e);
                }
            }
        }
    }
}

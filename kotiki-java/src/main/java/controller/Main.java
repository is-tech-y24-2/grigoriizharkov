package controller;

import controller.tools.ControllerException;
import dao.implementations.CatDAO;
import dao.implementations.CatsFriendsDAO;
import dao.implementations.OwnerDAO;
import dao.implementations.OwnersCatsDAO;
import service.Service;

public class Main {
    public static void main(String[] args) throws ControllerException {
        Service service = new Service(new CatDAO(), new OwnerDAO(), new CatsFriendsDAO(), new OwnersCatsDAO());
        Console ui = new Console(service);
        ui.work();
    }
}

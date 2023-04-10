//package driver;

import client.ClientView;
import client.ClientModel;
import client.ClientController;

public class ClientDemo {

    private ClientView view;
    private ClientModel model;
    private ClientController control;

    public static void main(String[] args) {
        new ClientController(new ClientView(), new ClientModel()).launch();

    }
}

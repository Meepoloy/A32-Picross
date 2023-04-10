package driver;

import server.ServerView;
import server.ServerModel;
import server.ServerController;

public class ServerDemo {

    private ServerView view;
    private ServerModel model;
    private ServerController control;

    public static void main(String[] args) {

        new ServerController(new ServerView(), new ServerModel()).run();
    }
}

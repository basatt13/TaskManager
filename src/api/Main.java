package api;

import controller.Manager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        KVServer kvServer = new KVServer();
        kvServer.start();

        HttpTaskManager httpTaskManager = Manager.getDefault();
        HttpTaskServer httpTaskServer = new HttpTaskServer(8080, httpTaskManager);
        httpTaskServer.start();
    }
}

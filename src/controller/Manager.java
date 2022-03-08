package controller;

import java.io.IOException;

public class Manager {

    public static TaskManager getDefault(){

        TaskManager inMemoryTasksManager = new InMemoryTasksManager();
        return  inMemoryTasksManager;
    }

}

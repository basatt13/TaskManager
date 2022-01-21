package controller;

public class Managers {

    public static TaskManager getDefault(){

        TaskManager inMemoryTasksManager = new InMemoryTasksManager();
        return  inMemoryTasksManager;
    }
}

package controller;

public class Managers {

    public static InMemoryTasksManager getDefault(){

        TaskManager inMemoryTasksManager = new InMemoryTasksManager();
        return (InMemoryTasksManager) inMemoryTasksManager;
    }
}

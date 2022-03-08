package controller;
import data.Tables;
import tasks.Epic;
import tasks.Task;
import java.io.IOException;

public interface TaskManager {

    void doneSubtask() throws IOException;

    int generateNumberTask();

    void getSubtaskByID() throws IOException;

    void updateSubtask() throws IOException;

    void createTask();

    void createEpic () ;

    void createSubtask();

    void showListTasks();

    void showListEpics();

    void showListSubtask();

    void getListSubtasksByEpicID();

    void getUpdateByID() throws IOException;

    void getAnyByID() throws IOException;

    void removeTaskByID();

    void removeAllTask();

    void updateStatusEpic();
    
    void toProgressSubtask() throws IOException;

    void updateEpic() throws IOException;

    void addEpics(Epic epic) throws IOException;

    void getEpicByID() throws IOException;

    void updateTask() throws IOException;

    void addTasks(Task o) throws IOException;

    void getTaskByID() throws IOException;




}

package controller;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.Tasks;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface TaskManager {

    int generateNumberTask();

    void createTask(Task task);

    void createEpic (Epic epic) ;

    void createSubtask(SubTask subTask, int idEpic);

    void addTasks(Task o) throws IOException;

    void addEpics(Epic epic) throws IOException;

    void addSubtask(SubTask subTask) throws IOException;

    List<Task> showListTasks(); // эдпоинт создан

    List<Epic> showListEpics();

    List<SubTask> showListSubtask();

    List<SubTask> getListSubtasksByEpicID(int numEpic);

    Task getAnyByID(Tasks task, int numOfTask);

    Task getTaskByID(int ID);

    Epic getEpicByID(int ID);

    SubTask getSubtaskByID(int numberTask);

    String removeTaskByID(int ID) throws NullPointerException;

    void removeAllTask();

    String updateTask(Task task);

    String updateEpic(Epic epic);

    void updateStatusEpic();

    void updateSubtask(SubTask subTask) throws IOException;

    void doneSubtask(int numberTask) throws IOException;

    String toProgressSubtask(int numSubtask);

    Set<Task> getPrioritizedTasks(); //эдпоинт создан




}

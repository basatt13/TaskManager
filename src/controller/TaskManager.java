package controller;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.Tasks;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface TaskManager {

    int generateNumberTask(); // тест создан

    void createTask(Task task); // тест создан

    void createEpic (Epic epic) ; // тест создан

    void createSubtask(SubTask subTask, int idEpic); // тест создан

    void addTasks(Task o) throws IOException; // тест создан

    void addEpics(Epic epic) throws IOException;

    void addSubtask(SubTask subTask) throws IOException; // тест создан

    List<Task> showListTasks(); // тест создан

    List<Epic> showListEpics(); // тест создан

    List<SubTask> showListSubtask(); // тест создан

    List<SubTask> getListSubtasksByEpicID(int numEpic); // тест создан

    Task getAnyByID(Tasks task, int numOfTask); // тест создан

    Task getTaskByID(int ID); // тест создан

    Epic getEpicByID(int ID); // тест создан

    SubTask getSubtaskByID(int numberTask); // тест создан

    String removeTaskByID(int ID) throws NullPointerException; // тест создан

    void removeAllTask(); // тест создан

    String updateTask(Task task); // тест создан

    String updateEpic(Epic epic); // тест создан

    void updateStatusEpic(); // тест создан

    void updateSubtask(SubTask subTask) throws IOException; // тест создан

    void doneSubtask(int numberTask) throws IOException; // тест создан

    String toProgressSubtask(int numSubtask); // тест создан

    Set<Task> getPrioritizedTasks();




}

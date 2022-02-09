package controller;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import java.util.HashMap;
import java.util.List;

public interface TaskManager {

    HashMap<Integer, Task> getAllTasks();

    HashMap<Integer, Epic> getAllEpics();

    HashMap<Integer, SubTask> getAllSubTusk();

    List<Task> getHistoryList();

    void doneSubtask(HashMap<Integer, SubTask> allSubtask, HashMap<Integer, Epic> allEpics);

    void getSubtaskByID(HashMap<Integer, SubTask> allSubtaask);

    void updateSubtask(HashMap<Integer, SubTask> allSubtask);

    void createTask(HashMap<Integer, Task> allTasks);

    void createEpic (HashMap<Integer, Epic> allEpics);

    void createSubtask(HashMap<Integer, SubTask> allSubTusk, HashMap<Integer, Epic> allEpics);

    void showListTasks(HashMap<Integer, Task> allTasks);

    void showListEpics(HashMap<Integer, Epic> allEpics);

    void showListSubtask(HashMap<Integer, SubTask> allSubTusk);

    void getListSubtasksByEpicID(HashMap<Integer, SubTask> allSubTusk, HashMap<Integer, Epic> allEpics);

    void getUpdateByID(HashMap<Integer, Task> allTasks, HashMap<Integer, Epic> allEpics,
                       HashMap<Integer, SubTask> allSubTusk);

    void getAnyByID(HashMap<Integer, Task> allTasks, HashMap<Integer, Epic> allEpics,
                                  HashMap<Integer, SubTask> allSubTusk, List<Task> historyList);

    void removeTaskByID();

    void removeAllTask(HashMap<Integer, Task> allTasks);

    void updateStatusEpic(HashMap<Integer, Epic> allEpics, HashMap<Integer, SubTask> allSubTusk);

    void history();

    void toProgressSubtask(HashMap<Integer, SubTask> allSubtask, HashMap<Integer, Epic> allEpics);

    void updateEpic(HashMap<Integer, Epic> allEpic);

    void addEpics(Epic epic, HashMap<Integer, Epic> allEpics);

    void getEpicByID(HashMap<Integer, Epic> allEpics);

    void updateTask(HashMap<Integer, Task> allTasks);

    void addTasks(Task o, HashMap<Integer, Task> allTasks);

    void getTaskByID(HashMap<Integer, Task> allTasks);
}

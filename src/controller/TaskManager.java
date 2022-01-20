package controller;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.HashMap;
import java.util.List;

public interface TaskManager {
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
    void removeTaskByID(HashMap<Integer, Task> allTasks);
    void removeAllTask(HashMap<Integer, Task> allTasks);
    void updateStatusEpic(HashMap<Integer, Epic> allEpics, HashMap<Integer, SubTask> allSubTusk);
    void history();
}

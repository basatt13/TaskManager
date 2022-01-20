package tasks;
import java.util.HashMap;


public interface TaskManager {
    void createTask(HashMap<Integer, Task> allTasks);
    void createEpic (HashMap<Integer, Epic> allEpics);
    void createSubtask(HashMap<Integer, SubTask> allSubTusk, HashMap<Integer, Epic> allEpics);
    void showListtasks(HashMap<Integer, Task> allTasks);
    void showListEpics(HashMap<Integer, Epic> allEpics);
    void showListSubtask(HashMap<Integer, SubTask> allSubTusk);
    void getListSubtasksByEpicID(HashMap<Integer, SubTask> allSubTusk, HashMap<Integer, Epic> allEpics);
    void getUpdateByID(HashMap<Integer, Task> allTasks, HashMap<Integer, Epic> allEpics,
                       HashMap<Integer, SubTask> allSubTusk);
    void getAnyByID(HashMap<Integer, Task> allTasks, HashMap<Integer, Epic> allEpics,
                                  HashMap<Integer, SubTask> allSubTusk);
    void removeTaskByID(HashMap<Integer, Task> allTasks);
    void removeAllTask(HashMap<Integer, Task> allTasks);
    void updateStatusEpic(HashMap<Integer, Epic> allEpics, HashMap<Integer, SubTask> allSubTusk);
    void history();
}

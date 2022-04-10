package controller;
import tasks.NODE;
import tasks.Task;
import java.util.List;

public interface HistoryManager {

    void add(Task task); // тест создан

    void removeNode(NODE node); // тест создан

    List<Task> getHistory();
}

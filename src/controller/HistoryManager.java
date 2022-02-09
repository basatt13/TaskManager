package controller;

import java.util.List;

public interface HistoryManager<Task> {

    void add(Task task);
    void remove(int id);
    List<Task> getHistory();
}

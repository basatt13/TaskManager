package test;

import controller.InMemoryHistoryManager;
import controller.InMemoryTasksManager;
import controller.Status;
import data.Tables;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

class HistoryManagerTest extends Tables{

    InMemoryHistoryManager historyManager =
            new InMemoryHistoryManager();

    InMemoryTasksManager manager = new InMemoryTasksManager();

    Task task1 = new Task(1, "name","details"
            , Status.NEW,"01.01.22/19:00",30);
    Task task2 = new Task(2, "name","details"
            , Status.NEW,"01.01.22/09:00",30);
    Epic epic1 = new Epic(3, "epic","details"
            , Status.NEW,null,0);
    Epic epic2 = new Epic(4, "epic","details"
            , Status.NEW,null,0);
    SubTask subTask5 = new SubTask(5, "name", "details"
            , Status.NEW,"01.01.22/20:00",20);
    SubTask subTask6 = new SubTask(6, "name", "details"
            , Status.NEW,"01.01.22/21:15",15);

    @BeforeEach
    public void createHistory() {
        manager.createTask(task1);
        manager.createTask( task2);
        manager.createEpic(epic1);
        manager.createEpic(epic2);
        manager.createSubtask(subTask5,3);
        manager.createSubtask(subTask6,3);
        manager.getTaskByID(1);
        manager.getTaskByID(2);
        manager.getEpicByID(3);
        manager.getEpicByID(4);
        manager.getSubtaskByID(5);
        manager.getSubtaskByID(6);
    }

    @AfterEach
    public void reset(){
        Tables.tasksHis.clear();
        Tables.deleteData.clear();
        Tables.taskHistory.clear();
    }


    // тест метода add(task);
    @Test
    void shouldAddTaskToHistoryIfHistoryIsEmpty() {
        reset();
        historyManager.add(task1);
        Assertions.assertTrue(Tables.taskHistory.contains(task1));
    }

    @Test
    void shouldAddTaskToHistoryIsTaskIsContains() {
        historyManager.add(task2);
        Assertions.assertTrue(Tables.taskHistory.get(5).equals(task2));
    }


    // тест метода removeNode(NODE node);
    @Test
    void shouldDeleteNodeFromFirst() {
        historyManager.removeNode(tasksHis.get(0));
        Assertions.assertEquals(2,Tables.tasksHis.get(0).data.getID());
        Assertions.assertEquals(3,Tables.tasksHis.get(0).next.getID());
        Assertions.assertNull(Tables.tasksHis.get(0).prev);
    }

    @Test
    void shouldDeleteNodeFromLast() {
        historyManager.removeNode(tasksHis.get(5));
        Assertions.assertEquals(5,Tables.tasksHis.get(4).data.getID());
        Assertions.assertEquals(4,Tables.tasksHis.get(4).prev.getID());
        Assertions.assertNull(Tables.tasksHis.get(4).next);
    }

    @Test
    void shouldDeleteNodeFromMiddle() {
        System.out.println(Tables.tasksHis);
        historyManager.removeNode(tasksHis.get(3));
        System.out.println(Tables.tasksHis);
        Assertions.assertEquals(5,Tables.tasksHis.get(3).data.getID());
        Assertions.assertEquals(3,Tables.tasksHis.get(3).prev.getID());
        Assertions.assertEquals(6,Tables.tasksHis.get(3).next.getID());
    }
}
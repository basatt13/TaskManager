package Test;

import controller.InMemoryTasksManager;
import controller.Status;
import data.Tables;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;

public class UpdateStatusEpicTest extends Tables{

    InMemoryTasksManager inMemoryTasksManager = new InMemoryTasksManager();

    public void createSubtaskForTest(Status status){
        int id = Tables.forGenerateID.size() + 1;
        SubTask subTask = new SubTask(id,"сабтаск " + id,"описание"
                ,status,"01.01.22/21:00",10);
        Tables.allSubTusk.put(id,subTask);
        subTask.setEpic(1);
        Tables.allEpics.get(1).getSubtasks().add(id);
        Tables.forGenerateID.add(id);
    }

    @BeforeEach
    public void createEpicForTest () {
        Epic epic = new Epic(1,"эпик","детали",Status.NEW,null,0);
        Tables.allEpics.put(1,epic);
        Tables.forGenerateID.add(1);
    }

    @Test
    void statusEpicIfListSubtaskIsEmpty() {
        inMemoryTasksManager.updateStatusEpic();
        Status status = Tables.allEpics.get(1).getStatus();
        Assertions.assertEquals(Status.NEW,status);
    }

   @Test
    void statusEpicIfAllSubtaskNew() {
        createSubtaskForTest(Status.NEW);
        createSubtaskForTest(Status.NEW);
       inMemoryTasksManager.updateStatusEpic();
       Tables.allSubTusk.get(3).toString();
       Status status = Tables.allEpics.get(1).getStatus();
       Assertions.assertEquals(Status.NEW,status);
    }

    @Test
    void statusEpicIfAllSubtaskDone() {
        createSubtaskForTest(Status.DONE);
        createSubtaskForTest(Status.DONE);
        inMemoryTasksManager.updateStatusEpic();
        Status status = Tables.allEpics.get(1).getStatus();
        Assertions.assertEquals(Status.DONE,status);
    }

    @Test
    void statusEpicIfSubTaskDoneAndNew() {
        createSubtaskForTest(Status.DONE);
        createSubtaskForTest(Status.DONE);
        inMemoryTasksManager.updateStatusEpic();
        Status status = Tables.allEpics.get(1).getStatus();
        Assertions.assertEquals(Status.DONE,status);
    }

    @Test
    void statusEpicIfSubTaskToProgress() {
        createSubtaskForTest(Status.TO_PROGRESS);
        createSubtaskForTest(Status.TO_PROGRESS);
        inMemoryTasksManager.updateStatusEpic();
        Status status = Tables.allEpics.get(1).getStatus();
        Assertions.assertEquals(Status.TO_PROGRESS,status);
    }

}

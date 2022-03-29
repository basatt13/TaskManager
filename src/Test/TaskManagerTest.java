package Test;

import controller.Status;
import controller.TaskManager;
import data.Tables;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.Tasks;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


abstract class TaskManagerTest<T extends TaskManager> extends Tables {

    TaskManager taskManager;

    public TaskManagerTest(TaskManager taskManager){
        this.taskManager = taskManager;
    }

    Task task1 = new Task(1, "name","details"
            , Status.NEW,"01.01.22/23:00",30);
    Epic epic2 = new Epic(2, "epic","details"
            , Status.NEW,"01.01.22/00:00",0);
    SubTask subTask1 = new SubTask(3, "name", "details"
            , Status.NEW,"01.01.22/20:15",20);
    SubTask subTask2 = new SubTask(4, "name", "details"
            , Status.NEW,"01.01.22/19:00",15);

    @AfterEach
    void resetAllTables() {
        Tables.allTasks.clear();
        Tables.allEpics.clear();
        Tables.allSubTusk.clear();
        Tables.forGenerateID.clear();
    }

    @BeforeEach
    void createDataForTest(){
        Tables.allTasks.put(1,task1);
        Tables.forGenerateID.add(1);
        Tables.allEpics.put(2,epic2);
        Tables.forGenerateID.add(2);
        Tables.allSubTusk.put(3,subTask1);
        Tables.forGenerateID.add(3);
        Tables.allSubTusk.put(4,subTask2);
        Tables.forGenerateID.add(4);
        Tables.allEpics.get(2).getSubtasks().add(3);
        Tables.allEpics.get(2).getSubtasks().add(4);
        Tables.allSubTusk.get(3).setEpic(2);
        Tables.allSubTusk.get(4).setEpic(2);

    }

    //тест метода doneSubtask();
    @Test
    void shouldChangeStatusEpicDone() {
        createDataForTest();
        try {
            taskManager.doneSubtask(3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        taskManager.updateStatusEpic();
        Tables.allTasks.get(1).toString();
        Assertions.assertEquals(Tables.allSubTusk.get(3).getStatus(),Status.DONE);
    }

    @Test
    void shouldWriteTextIfSubtaskIsEmpty() {
        resetAllTables();
        try {
            taskManager.doneSubtask(2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertTrue(Tables.allSubTusk.isEmpty());
    }

    @Test
    void idSubtaskIsNotContains() throws IOException {
        createDataForTest();
       taskManager.getSubtaskByID(8);
        Assertions.assertFalse(Tables.allSubTusk.containsKey(8));
    }

    // тест метода generateNumberTask();
    @Test
    void testGenerateNumberTaskIfEmpty() {
        resetAllTables();
        int ID = taskManager.generateNumberTask();
        Assertions.assertEquals(1,ID);
    }

    @Test
    void testGenerteNumberTaskIfNotEmpty() {
        Tables.forGenerateID.add(1);
        int ID = taskManager.generateNumberTask();
        Assertions.assertEquals(6,ID);
    }

    // тест метода getSubtaskByID();
    @Test
    void shouldReturnSubTaskIfIdContains() throws IOException {
        createDataForTest();
        taskManager.getSubtaskByID(3);
        Assertions.assertEquals(3,Tables.allSubTusk.get(3).getID());
    }

    @Test
    void shouldReturnSubTaskIfAllSubTaskIsEmpty() throws IOException{
        resetAllTables();
        taskManager.getSubtaskByID(9);
        Assertions.assertTrue(Tables.allSubTusk.isEmpty());
    }

    @Test
    void shouldReturnSubTaskIfIDNotContainsInAllSubTask() throws IOException{
        taskManager.getSubtaskByID(9);
        Assertions.assertFalse(Tables.allSubTusk.containsKey(9));
    }

    // тест метода updateSubtask();
    @Test
    void shouldReturnUpdatingSubTask() throws IOException {
        createDataForTest();
        taskManager.updateSubtask(new SubTask(3,"новая задача","details",Status.NEW
                ,"01.01.22/20:00",10));
        String name = Tables.allSubTusk.get(3).getName();
        Assertions.assertEquals("новая задача",name);
    }

    @Test
    void testUpdateSubtaskIfAllSubtaskIsEmpty() throws IOException {
        resetAllTables();
        taskManager.updateSubtask(subTask1);
        Assertions.assertTrue(Tables.allSubTusk.isEmpty());
    }

    @Test
    void testUpdateSubTaskIfIdNotContainsInAllSubtasks() throws IOException {
        createDataForTest();
        taskManager.updateSubtask(new SubTask(6,"Новая задача","Новое описание", Status.NEW
                ,"01.01.22/15:00", 30));
        Assertions.assertFalse(Tables.allSubTusk.containsKey(6));
    }

    //тест метода showListTasks();
    @Test
    void testShowListTasksIfTablesNotEmpty() {
        Object[] actual = taskManager.showListTasks().toArray();
        Object[] expect = Tables.allTasks.values().toArray();
        Assertions.assertArrayEquals(expect,actual);
    }

    @Test
    void testShowListTasksIfTablesIsEmpty() {
        resetAllTables();
        Assertions.assertNull(taskManager.showListTasks());
    }

    //тест метода showListEpics();
    @Test
    void testShowListEpicIfTablesNotEmpty() {
        Object[] actual = taskManager.showListEpics().toArray();
        Object[] expect = Tables.allEpics.values().toArray();
        Assertions.assertArrayEquals(expect,actual);
    }

    @Test
    void testShowListEpicIfTablesIsEmpty() {
        resetAllTables();
        Assertions.assertNull(taskManager.showListEpics());
    }

    //тест метода showListSubTask();
    @Test
    void testShowListSubTaskIfTablesNotEmpty() {
        Object[] actual = taskManager.showListSubtask().toArray();
        Object[] expect = Tables.allSubTusk.values().toArray();
        Assertions.assertArrayEquals(expect,actual);
    }

    @Test
    void testShowListSubtaskIfTablesIsEmpty() {
        resetAllTables();
        Assertions.assertNull(taskManager.showListSubtask());
    }

    // тест метода getListSubtasksByEpicID();
    @Test
    void testGetListSubtasksByEpicID() {;
        Object[] actual = taskManager.getListSubtasksByEpicID(2).toArray();
        Object[] expect = Tables.allSubTusk.values().toArray();
        Assertions.assertArrayEquals(expect,actual);
    }

    @Test
    void testGetListSubtasksByEpicIDIfTableIsEmpty() {
        resetAllTables();
        Assertions.assertNull(taskManager.getListSubtasksByEpicID(2));
    }

    @Test
    void testGetListSubtasksByEpicIDIfEpicNotInTable() {
        Assertions.assertNull(taskManager.getListSubtasksByEpicID(13));
    }

    // тест метода getTaskByID();
    @Test
    void testGetTaskByID() {
        Assertions.assertEquals(1,taskManager.getTaskByID(1).getID());
    }

    @Test
    void testGetTaskByIdIfTablesIsEmpty() {
        resetAllTables();
        Assertions.assertNull(taskManager.getTaskByID(1));
    }

    @Test
    void testGetTaskByIdIfIdNotContains() {
        Assertions.assertNull(taskManager.getTaskByID(17));
    }

    // тест метода getEpicByID();
    @Test
    void testGetEpicByIDshouldReturnOne() {
        Assertions.assertEquals(2,taskManager.getEpicByID(2).getID());
    }

    @Test
    void testGetEpicByIdshouldReturnNullIfTablesIsEmpty() {
        resetAllTables();
        Assertions.assertNull(taskManager.getEpicByID(2));
    }

    @Test
    void getEpicByIdshouldReturnNullIfIdNotContains() {
        Assertions.assertNull(taskManager
                .getEpicByID(17));
    }

    // тест метода getSubtaskByID();
    @Test
    void shouldReturnOne () {
        Assertions.assertEquals(3,taskManager.getSubtaskByID(3).getID());
    }

    @Test
    void shouldReturnNullIfTablesIsEmpty() {
        resetAllTables();
        Assertions.assertNull(taskManager.getSubtaskByID(3));
    }

    @Test
    void shouldReturnNullIfIdNotContains() {
        Assertions.assertNull(taskManager
                .getSubtaskByID(17));
    }

    // тест метода  getAnyByID();
    @Test
    void shouldReturnTrueTypeOfTask() {
        Assertions.assertEquals(Task.class,taskManager.getAnyByID(Tasks.TASK,1).getClass());
        Assertions.assertEquals(Epic.class,taskManager.getAnyByID(Tasks.EPIC,2).getClass());
        Assertions.assertEquals(SubTask.class,taskManager.getAnyByID(Tasks.SUBTASK,3).getClass());
    }

    // тест метода removeTaskByID();
    @Test
    void shouldReturnTrueIfAnyTasksDeleted() throws NullPointerException{
        taskManager.removeTaskByID(1);
        boolean check = !Tables.allTasks.containsKey(1) && !Tables.allEpics.containsKey(1)
                && !Tables.allSubTusk.containsKey(1);
        Assertions.assertTrue(check);
    }

    @Test
    void shouldReturnTextIfTableIsNotContainsId() {
        Assertions.assertEquals("Задача с указанным ID не найдена",taskManager.removeTaskByID(12));
    }

    @Test
    void shouldReturnTextIfTableIsNotContainsIdOrTableIsEmpty () {
        resetAllTables();
        Assertions.assertEquals("Задача с указанным ID не найдена",taskManager.removeTaskByID(1));
    }

    // тест  метода removeAllTask();
    @Test
    void allTaskShouldBeEmpty() {
        taskManager.removeAllTask();
        Assertions.assertTrue(Tables.allTasks.isEmpty() && Tables.allEpics.isEmpty()
                && Tables.allSubTusk.isEmpty());
    }

    //тест метода toProgressSubtask();
    @Test
    void shouldChangeStatusbySubtaskIfIdContainsInTable() {
       String actual = taskManager.toProgressSubtask(3);
        Assertions.assertEquals(Status.TO_PROGRESS, Tables.allSubTusk.get(3).getStatus());
        Assertions.assertEquals("Статус успешно изменен",actual);
    }

    @Test
    void shouldChangeTextBySubtaskIfIdContainsInTable() {
        String actual = taskManager.toProgressSubtask(18);
        Assertions.assertEquals("Подзадача с указанным ID не найдена",actual);
    }

    @Test
    void shouldChangeTextbySubtaskIsEmpty() {
        resetAllTables();
        String actual = taskManager.toProgressSubtask(3);
        Assertions.assertEquals("Список подзадач пуст",actual);
    }

    // тест метода updateEpic();
    @Test
    void shouldReturnEpicFromTableLikeAsEpicCreated() {
        taskManager.updateEpic(epic2);
        Assertions.assertEquals(Tables.allEpics.get(2),epic2);
    }

    @Test
    void shouldReturnSpecialTextIfTableIsEmpty() {
        resetAllTables();
        taskManager.updateEpic(epic2);
        Assertions.assertEquals("Список эпиков пуст",taskManager.updateEpic(epic2));
    }

    @Test
    void shouldReturnSpecialTextIfIdNotContains() {
        Epic epic = new Epic(18,"new Epic","new detail",Status.NEW,null,0);
        taskManager.updateEpic(epic);
        Assertions.assertEquals("Эпик с указанным ID не найден",taskManager.updateEpic(epic));
    }

    // тест метода updateTask();
    @Test
    void shouldReturnEpicFromTableLikeAsTaskCreated() {
        taskManager.updateTask(task1);
        Assertions.assertEquals(Tables.allTasks.get(1),task1);
    }

    @Test
    void shouldReturnSpecialTextIfTaskTableIsEmpty() {
        resetAllTables();
        Task task = new Epic(1,"new Task","new detail",Status.NEW,null,0);
        taskManager.updateTask(task);
        Assertions.assertEquals("Список задач пуст",taskManager.updateTask(task));
    }

    @Test
    void shouldReturnSpecialTextIfIdTaskNotContains() {
        Task task = new Task(18,"new Task","new detail",Status.NEW,"01.01.22/15:00", 15);
        Assertions.assertEquals("Задача с указанным ID не найдена",taskManager.updateTask(task));
    }

    //тест метода addTask();
    @Test
    void testAddTask() throws IOException {
        Task task = new Task(taskManager.generateNumberTask(), "new Task","new detail",Status.NEW
        ,"01.01.22/15:00", 15);
        taskManager.addTasks(task);
        Assertions.assertEquals(Tables.allTasks.get(taskManager.generateNumberTask()),task);
    }

    // тест метода addEpics();
    @Test
    void testAddEpics() throws IOException {
        Epic epic = new Epic(taskManager.generateNumberTask(), "new Epic","new detail",Status.NEW
        ,null,0);
        taskManager.addEpics(epic);
        Assertions.assertEquals(Tables.allEpics.get(taskManager.generateNumberTask()),epic);
    }

    // тест метода addSubtask();
    @Test
    void testAddSubTask() throws IOException {
        SubTask subTask = new SubTask(taskManager.generateNumberTask(), "new Subtask"
                ,"new detail",Status.NEW
        ,"01.01.22/15:00", 15);
        taskManager.addSubtask(subTask);
        Assertions.assertEquals(Tables.allSubTusk.get(taskManager.generateNumberTask()),subTask);
    }

    //тест метода createTask();
    @Test
    void testCreateTask() {
        Task task = new Task(taskManager.generateNumberTask(), "new Task","new detail",Status.NEW
        ,"01.01.22/15:00", 15);
        taskManager.createTask(task);
        Assertions.assertEquals(Tables.allTasks.get(5),task);
    }

    //тест метода createTask();
    @Test
    void testCreateEpic() {
        Epic epic = new Epic(taskManager.generateNumberTask(), "new Epic","new detail",Status.NEW,
                null,0);
        taskManager.createEpic(epic);
        Assertions.assertEquals(Tables.allEpics.get(5),epic);
    }

    //тест метода createSubtask();
    @Test
    void testCreateSubtask() {
        SubTask subTask = new SubTask(taskManager.generateNumberTask(), "new Task","new detail",Status.NEW,
                "01.01.22/15:00", 15);
        taskManager.createSubtask(subTask,2);
        Assertions.assertEquals(Tables.allSubTusk.get(5),subTask);
        Assertions.assertEquals(2,subTask.getEpic());
    }


    //тест метода validateTime()
    @Test
    void shouldReturnTrueIfTimeIsEmpty() {
        Task task = new Task(19,"имя", "детали",Status.NEW,"01.01.22/15:00",30);
        boolean check = task.validateTime();
        Assertions.assertTrue(check);
    }

    @Test
    void shouldReturnFalseIfTimeContains() {
        Task task = new Task(19,"имя", "детали",Status.NEW,"01.01.22/23:30",29);
        Assertions.assertFalse(task.validateTime());
    }

    //тест метода getPrioritizedTasks()
    @Test
    void testGetPrioritizedTasks(){
       List<Task> tasks = new ArrayList<>(taskManager.getPrioritizedTasks());
        Assertions.assertEquals(4,tasks.get(0).getID());
        Assertions.assertEquals(3,tasks.get(1).getID());
        Assertions.assertEquals(1,tasks.get(2).getID());
    }


}
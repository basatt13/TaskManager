package Test;

import controller.FileBackedTaskManager;
import controller.Status;
import data.Tables;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.Tasks;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static controller.FileBackedTaskManager.createFileForSave;


class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {


    public FileBackedTaskManagerTest() throws IOException {
        super(new FileBackedTaskManager("autoSave.csv"));
    }

    File file = createFileForSave();

    FileBackedTaskManager fileBackedTaskManager =
            new FileBackedTaskManager(file.toString());

    public List<String> reader() throws IOException {
        List<String> fromFileData = new ArrayList<>();
        Reader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        while (br.ready()) {
            String line = br.readLine();
            fromFileData.add(line);
        }
        br.close();
        return fromFileData;
    }

    public static Task fromString(String value) {
        String a[] = value.split(", ");
        if ((a[1]).equals(String.valueOf(Tasks.TASK))) {
            Task task = new Task(Integer.parseInt(a[0]), a[2], a[4], Status.valueOf(a[3]), a[5], Long.parseLong(a[6]));
            return task;
        } else if ((a[1]).equals(String.valueOf(Tasks.EPIC))) {
            Epic epic = new Epic(Integer.parseInt(a[0]), a[2], a[4], Status.valueOf(a[3]), a[5], Long.parseLong(a[6]));
            return epic;
        } else if ((a[1]).equals(String.valueOf(Tasks.SUBTASK))) {
            SubTask subTask = new SubTask(Integer.parseInt(a[0]), a[2], a[4], Status.valueOf(a[3])
                    , a[5], Long.parseLong(a[6]));
            return subTask;
        }
        return null;
    }

    void createHistory() {
        taskManager.getTaskByID(1);
        taskManager.getEpicByID(2);
        taskManager.getSubtaskByID(4);
    }

    @AfterEach
    void resetAllData() {
        resetAllTables();
        Tables.taskHistory.clear();
        Tables.deleteData.clear();
        Tables.tasksHis.clear();
        Tables.forGenerateID.clear();
        fileBackedTaskManager.save();
    }

    // тест метода save();

    // shouldReturnFileWithHeadAndOtherBeEmpty() возвращает длину -2 , т.к. сразу закладывается пустая строка,
    // разделяющая задачи и историю
    @Test
    void shouldReturnFileWithHeadAndOtherBeEmpty() throws IOException {
        resetAllTables();
        fileBackedTaskManager.save();
        Assertions.assertEquals(2, reader().size());
    }

    @Test
    void shouldReturnFileWith4Task() throws IOException {
        createHistory();
        Tables.allTasks.clear();
        Tables.allSubTusk.clear();
        Tables.allEpics.get(2).getSubtasks().clear();
        System.out.println(Tables.allEpics.get(2).toString());
        Tables.allEpics.get(2).toString();
        fileBackedTaskManager.save();
        Assertions.assertEquals(4, reader().size());
        Epic epic = (Epic) fromString(reader().get(1));
        Assertions.assertEquals(Tables.allEpics.get(2), epic);
    }


    // тест метода loadFromFile();
    @Test
    void loadDataIfFileisEmpty() throws IOException {
        createHistory();
        resetAllTables();
        fileBackedTaskManager.save();
        fileBackedTaskManager.loadFromFile(file);
        Assertions.assertTrue(Tables.allTasks.isEmpty() && Tables.allEpics.isEmpty()
                && Tables.allSubTusk.isEmpty());
        Assertions.assertEquals(3, Tables.taskHistory.size());
    }

    @Test
    void loadDataWhenEpicHaveNotSubtasks() throws IOException {
        createHistory();
        Tables.allTasks.clear();
        Tables.allSubTusk.clear();
        Tables.allEpics.get(2).getSubtasks().clear();
        fileBackedTaskManager.save();
        fileBackedTaskManager.loadFromFile(file);
        Epic epic = (Epic) fromString(reader().get(1));
        Assertions.assertEquals(Tables.allEpics.get(2), epic);
        Assertions.assertTrue(Tables.allSubTusk.isEmpty());
    }

    @Test
    void loadDataFromFile() throws IOException {
        createHistory();
        fileBackedTaskManager.save();
        fileBackedTaskManager.loadFromFile(file);
        Assertions.assertTrue(Tables.allTasks.size() == 1 && Tables.allEpics.size() == 1
                && Tables.allSubTusk.size() == 2 && Tables.taskHistory.size() == 3);
    }

}
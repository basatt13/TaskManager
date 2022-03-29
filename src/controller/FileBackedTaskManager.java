package controller;
import data.Tables;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.Tasks;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class FileBackedTaskManager extends InMemoryTasksManager {
    private String file;

    public FileBackedTaskManager(String file){
        super();
        this.file = file;
    }

    public static void main(String[] args) throws IOException {
        ForTest.test();
    }

    public void save() {
        try (
                Writer wr = new FileWriter(file, false)) {
            String header = "id,type,name,status,description,startTime, duration, endTime, epic\n";
            wr.write(header);
        } catch (ManagerSaveException e) {
            e.getMessage();

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i : Tables.allTasks.keySet()) {
            try (Writer wr1 = new FileWriter(file, true)) {
                wr1.write(Tables.allTasks.get(i).toString() + "\n");
            } catch (ManagerSaveException e) {
                e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int i : Tables.allEpics.keySet()) {
            try (Writer wr1 = new FileWriter(file, true)) {
                wr1.write(Tables.allEpics.get(i).toString() + "\n");
            } catch (ManagerSaveException e) {
                e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int i : Tables.allSubTusk.keySet()) {
            try (Writer wr1 = new FileWriter(file, true)) {
                wr1.write(Tables.allSubTusk.get(i).toString() + "\n");
            } catch (ManagerSaveException e) {
                e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (Writer wr1 = new FileWriter(file, true)) {
            wr1.write("\n");
        } catch (ManagerSaveException e) {
            e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Task task : Tables.taskHistory) {
            try (Writer wr2 = new FileWriter(file, true)) {
                wr2.write(task.getID() + ", ");
            } catch (ManagerSaveException e) {
                e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

// создание файла для загрузки информации
    public static File createFileForSave() throws IOException {
        if (Files.exists(Paths.get
                (".\\autoSave.csv"))) {
            System.out.println("Файл с указанным именем уже существует");
            File file = new File(".\\autoSave.csv");
            return file;
        } else {
            Files.createFile
                    (Paths.get(".\\autoSave.csv"));
            String header = "id,type,name,status,description,startTime, duration, endTime, epic\n";
            Writer wr = new FileWriter("autoSave.csv", true);
            wr.write(header);
            wr.close();
            System.out.println("Файл создан");
            File file = new File(".\\autoSave.csv");
            return file;
        }
    }


    public  FileBackedTaskManager loadFromFile(File file)
            throws IOException {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file.toString());
        List<String> fromFiledata = new ArrayList<>();
        Reader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        while (br.ready()) {
            String line = br.readLine();
            fromFiledata.add(line);
        }
        br.close();

        for (int i = 1; i < fromFiledata.size(); ++i) {
            if(!fromFiledata.get(i).isEmpty()) {
                if (fromFiledata.get(i).split(", ")[1].equals(String.valueOf(Tasks.TASK))) {
                    fileBackedTaskManager.addTasks(fromString(fromFiledata.get(i)));
                    Tables.forGenerateID.add(fromString(fromFiledata.get(i)).getID());
                } else if (fromFiledata.get(i).split(", ")[1].equals(String.valueOf(Tasks.EPIC))) {
                    fileBackedTaskManager.addEpics((Epic) fromString(fromFiledata.get(i)));
                    Tables.forGenerateID.add(fromString(fromFiledata.get(i)).getID());
                } else if (fromFiledata.get(i).split(", ")[1].equals(String.valueOf(Tasks.SUBTASK))) {
                    fileBackedTaskManager.addSubtask((SubTask) fromString(fromFiledata.get(i)));
                    Tables.forGenerateID.add(fromString(fromFiledata.get(i)).getID());
                    Tables.allEpics.get(Integer.parseInt(fromFiledata.get(i).split(", ")[8]))
                            .getSubtasks().add(fromString(fromFiledata.get(i)).getID());
                    Tables.allSubTusk.get(fromString(fromFiledata.get(i)).getID())
                            .setEpic(Integer.parseInt(fromFiledata.get(i).split(", ")[8]));
                }  else {
                    String[] b = fromFiledata.get(i).split(", ");
                    for(String j: b){
                        if(Tables.allTasks.containsKey(Integer.parseInt(j))){
                            fileBackedTaskManager.addTaskToHistory(Tables.allTasks.get(Integer.parseInt(j)));
                        } else if(Tables.allEpics.containsKey(Integer.parseInt(j))){
                            fileBackedTaskManager.addTaskToHistory(Tables.allEpics.get(Integer.parseInt(j)));
                        } else if(Tables.allSubTusk.containsKey(Integer.parseInt(j))){
                            fileBackedTaskManager.addTaskToHistory(Tables.allSubTusk.get(Integer.parseInt(j)));
                        }
                    }
                }
            }
        }
        return fileBackedTaskManager;
    }


    public static Task fromString(String value) {
        String a[] = value.split(", ");
        if ((a[1]).equals(String.valueOf(Tasks.TASK))) {
            Task task = new Task(Integer.parseInt(a[0]), a[2], a[4], Status.valueOf(a[3]), a[5],Long.parseLong(a[6]));
            return task;
        } else if ((a[1]).equals(String.valueOf(Tasks.EPIC))) {
            Epic epic = new Epic(Integer.parseInt(a[0]), a[2], a[4], Status.valueOf(a[3]),a[5],Long.parseLong(a[6]));
            return epic;
        } else if ((a[1]).equals(String.valueOf(Tasks.SUBTASK))) {
            SubTask subTask = new SubTask(Integer.parseInt(a[0]), a[2], a[4], Status.valueOf(a[3])
                    ,a[5],Long.parseLong(a[6]));
            return subTask;
        }
        return null;
    }


    @Override
    public void addSubtask(SubTask subTask)
            throws IOException {
        super.addSubtask(subTask);
        save();
    }

    @Override
    public int generateNumberTask() {
        return super.generateNumberTask();
    }

    @Override
    public void updateSubtask(SubTask subTask) throws IOException {
        super.updateSubtask(subTask);
        save();
    }


    @Override
    public void createTask(Task task)  {
        super.createTask(task);
    }


    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
    }


    @Override
    public void createSubtask(SubTask subTask, int idEpic) {
        super.createSubtask(subTask,idEpic);
    }


    @Override
    public String updateEpic(Epic epic)  {
        String text;
        if (Tables.allEpics.containsKey(epic.getID())) {
            Epic oldEpic = Tables.allEpics.get(epic.getID());
            Tables.allEpics.put(epic.getID(), epic);
            epic.setSubtasks(oldEpic.getSubtasks());
            text = "Эпик обновлен";
            save();
            return text;
        }else if(Tables.allEpics.isEmpty()){
            text = "Список эпиков пуст";
            return text;
        }else {
            text = "Эпик с указанным ID не найден";
            return text;
        }
    }


    @Override
    public void addEpics(Epic epic) throws IOException {
        super.addEpics(epic);
        save();
    }


    @Override
    public Epic getEpicByID(int ID)  {
        if (Tables.allEpics.isEmpty() || !Tables.allEpics.containsKey(ID)) {
            return null;
        } else {
            System.out.println(Tables.allEpics.get(ID).toString());
            historyManager.add(Tables.allEpics.get(ID));
            save();
            return Tables.allEpics.get(ID);
        }
    }


    @Override
    public String updateTask(Task task) {
        String text;
        if (Tables.allTasks.containsKey(task.getID())) {
            Task oldTask = Tables.allTasks.get(task.getID());
            Tables.allTasks.put(task.getID(), task);
            text = "Задача обновлена";
            save();
            return text;
        }else if(Tables.allTasks.isEmpty()){
            text = "Список задач пуст";
            return text;
        }else {
            text = "Задача с указанным ID не найдена";
            return text;
        }
    }


    @Override
    public void addTasks(Task o) throws IOException {
        super.addTasks(o);
        save();
    }


    @Override
    public Task getTaskByID(int ID) {
        if (Tables.allTasks.isEmpty() || !Tables.allTasks.containsKey(ID)) {
            return null;
        } else {
            System.out.println(Tables.allTasks.get(ID).toString());
            historyManager.add(Tables.allTasks.get(ID));
            save();
            return Tables.allTasks.get(ID);
        }
    }
}

package controller;
import data.Tables;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class FileBackedTaskManager extends InMemoryTasksManager implements TaskManager {
    private String file;

    FileBackedTaskManager(String file){
        super();
        this.file = file;
    }

    public static void main(String[] args) throws IOException {
        FileBackedTaskManager fileBackedTaskManager = loadFromFile(createFileForSave());
        fileBackedTaskManager.createTask();
        fileBackedTaskManager.createTask();
        fileBackedTaskManager.createEpic();
        fileBackedTaskManager.createEpic();
        fileBackedTaskManager.createSubtask();
        fileBackedTaskManager.createSubtask();
        fileBackedTaskManager.getTaskByID();
        fileBackedTaskManager.getTaskByID();
        fileBackedTaskManager.getTaskByID();
        fileBackedTaskManager.getEpicByID();
        fileBackedTaskManager.removeTaskByID();
    }

    void save() {
        try (
                Writer wr = new FileWriter(file, false)) {
            String header = "id,type,name,status,description,epic\n";
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


    public static File createFileForSave() throws IOException {
        if (Files.exists(Paths.get
                ("C:\\Users\\1\\OneDrive\\Рабочий стол\\Языки программирования\\копия спринт 2\\java-sprint2-hw\\src","autoSave.csv"))) {
            System.out.println("Файл с указанным именем уже существует");
            File file = new File("C:\\Users\\1\\OneDrive\\Рабочий стол\\Языки программирования\\копия спринт 2\\java-sprint2-hw\\src", "autoSave.csv");
            return file;
        } else {
            Files.createFile
                    (Paths.get("C:\\Users\\1\\OneDrive\\Рабочий стол\\Языки программирования\\копия спринт 2\\java-sprint2-hw\\src", "autoSave.csv"));
            String header = "id,type,name,status,description,epic\n";
            Writer wr = new FileWriter("autoSave.csv", true);
            wr.write(header);
            wr.close();
            System.out.println("Файл создан");
            File file = new File("C:\\Users\\1\\OneDrive\\Рабочий стол\\Языки программирования\\копия спринт 2\\java-sprint2-hw\\src", "autoSave.csv");
            return file;
        }
    }


    static FileBackedTaskManager loadFromFile(File file)
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
                if (fromFiledata.get(i).split(",")[1].equals("TASK")) {
                    fileBackedTaskManager.addTasks(fromString(fromFiledata.get(i)));
                    Tables.forGenerateID.add(fromString(fromFiledata.get(i)).getID());
                } else if (fromFiledata.get(i).split(",")[1].equals("EPIC")) {
                    fileBackedTaskManager.addEpics((Epic) fromString(fromFiledata.get(i)));
                    Tables.forGenerateID.add(fromString(fromFiledata.get(i)).getID());
                } else if (fromFiledata.get(i).split(",")[1].equals("SUBTASK")) {
                    fileBackedTaskManager.addSubtask((SubTask) fromString(fromFiledata.get(i)));
                    Tables.forGenerateID.add(fromString(fromFiledata.get(i)).getID());
                    Tables.allEpics.get(Integer.parseInt(fromFiledata.get(i).split(",")[5])).getSubtasks().add(fromString(fromFiledata.get(i)).getID());
                    Tables.allSubTusk.get(fromString(fromFiledata.get(i)).getID()).setEpic(Integer.parseInt(fromFiledata.get(i).split(",")[5]));
                }  else {
                    String[] b = fromFiledata.get(i).split(", ");
                    for(String j: b){
                        if(Tables.allTasks.containsKey(Integer.parseInt(j))){
                            fileBackedTaskManager.addTasktoHistory(Tables.allTasks.get(Integer.parseInt(j)));
                        } else if(Tables.allEpics.containsKey(Integer.parseInt(j))){
                            fileBackedTaskManager.addTasktoHistory(Tables.allEpics.get(Integer.parseInt(j)));
                        } else if(Tables.allSubTusk.containsKey(Integer.parseInt(j))){
                            fileBackedTaskManager.addTasktoHistory(Tables.allSubTusk.get(Integer.parseInt(j)));
                        }
                    }
                }
            }
        }
        return fileBackedTaskManager;
    }


    public static Task fromString(String value) {
        String a[] = value.split(",");
        if ((a[1]).equals("TASK")) {
            Task task = new Task(Integer.parseInt(a[0]), a[2], a[4], Status.valueOf(a[3]));
            return task;
        } else if ((a[1]).equals("EPIC")) {
            Epic epic = new Epic(Integer.parseInt(a[0]), a[2], a[4], Status.valueOf(a[3]));
            return epic;
        } else if ((a[1]).equals("SUBTASK")) {
            SubTask subTask = new SubTask(Integer.parseInt(a[0]), a[2], a[4], Status.valueOf(a[3]));
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
    public void getSubtaskByID()throws IOException{
        super.getSubtaskByID();
        save();
    }


    @Override
    public void updateSubtask() throws IOException {
        super.updateSubtask();
        save();
    }


    @Override
    public void createTask() throws IOException {
            super.createTask();
    }


    @Override
    public void createEpic() {
        try {
            super.createEpic();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void createSubtask() {
        try {
            super.createSubtask();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateEpic() throws IOException {
        super.updateEpic();
        save();
    }


    @Override
    public void addEpics(Epic epic) throws IOException {
        super.addEpics(epic);
        save();
    }


    @Override
    public void getEpicByID() throws IOException {
        super.getEpicByID();
        save();
    }


    @Override
    public void updateTask() throws IOException {
        super.updateTask();
        save();
    }


    @Override
    public void addTasks(Task o) throws IOException {
        super.addTasks(o);
        save();
    }


    @Override
    public void getTaskByID() throws IOException {
        super.getTaskByID();
        save();
    }
}

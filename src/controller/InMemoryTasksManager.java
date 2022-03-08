package controller;
import data.Tables;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import java.io.IOException;
import java.util.*;


public class InMemoryTasksManager extends Tables implements TaskManager {

    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    public void addSubtask(SubTask subTask) throws IOException {
        Tables.allSubTusk.put(subTask.getID(), subTask);
    }

    @Override
    public int generateNumberTask() {
        return Tables.forGenerateID.size()+1;
    }

    @Override
    public void toProgressSubtask() throws IOException {
            Scanner scanner = new Scanner(System.in);
            showListSubtask();
            System.out.println("Введите ID подзадачи, которая сейчас выполняется");
            int ID = scanner.nextInt();
            for (SubTask k : Tables.allSubTusk.values()) {
                if (k.getID() == ID) {
                    SubTask subTask = new SubTask(k.getID(), k.getName(), k.getDetails(), Status.TO_PROGRESS);
                    addSubtask(subTask);
                    subTask.setEpic(k.getEpic());
                }
            }
            updateStatusEpic();
            showListEpics();
        }


    @Override
    public void doneSubtask() throws IOException {
        Scanner scanner = new Scanner(System.in);
        showListSubtask();
        System.out.println("Введите ID задачи, выполнение которой завершилось");
        int ID = scanner.nextInt();
        for (SubTask k : Tables.allSubTusk.values()) {
            if (k.getID() == ID) {
                SubTask subTask = new SubTask(k.getID(), k.getName(), k.getDetails(), Status.DONE);
                addSubtask(subTask);
                subTask.setEpic(k.getEpic());
            }
        }
        updateStatusEpic();
        showListEpics();
    }

    @Override
    public void getSubtaskByID() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID подзадачи, которую необходимо показать");
        int ID = scanner.nextInt();
        for (SubTask k : Tables.allSubTusk.values()) {
            if (k.getID() == ID) {
                System.out.println(k.toString());
                historyManager.add(k);
            }
        }
    }

    @Override
    public void updateSubtask() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID подзадачи, которую необходимо обновить");
        int ID = scanner.nextInt();
        for (SubTask k : Tables.allSubTusk.values()) {
            if (k.getID() == ID) {
                System.out.println("Введите название подзадачи");
                Scanner scan = new Scanner(System.in);
                String name = scan.nextLine();
                System.out.println("Введите описание подзадачи ");
                String details = scan.nextLine();
                SubTask subTask = new SubTask(ID, name, details, k.getStatus());
                Tables.allSubTusk.put(subTask.getID(), subTask);
                subTask.setEpic(k.getEpic());
            }
        }
    }

    @Override
    public void createTask()  {
        System.out.println("Введите название задачи");
        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();
        System.out.println("Введите описание задачи ");
        String details = scan.nextLine();
        int numberTask = generateNumberTask();
        Status status1 = Status.NEW;
        Task task = new Task(numberTask, name, details, status1);
        try {
            addTasks(task);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Tables.forGenerateID.add(numberTask);
    }

    @Override
    public void createEpic() {
        System.out.println("Введите название эпика");
        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();
        System.out.println("Введите описание задачи ");
        String details = scan.nextLine();
        int numberEpics = generateNumberTask();
        Status status = Status.NEW;
        Epic epic = new Epic(numberEpics, name, details, status);
        try {
            addEpics(epic);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Tables.forGenerateID.add(numberEpics);
    }

    @Override
    public void createSubtask() {
        Scanner scanner = new Scanner(System.in);
        if (Tables.allEpics.isEmpty()) {
            System.out.println("Сначала создайте эпик");
        } else {
            showListEpics();
            System.out.println("Введите номер эпика для которого создается подзадача");
            int idEpic = scanner.nextInt();
            Scanner sc = new Scanner(System.in);
            System.out.println("Введите название подзадачи ");
            String name = sc.nextLine();
            System.out.println("Введите описание подзадачи ");
            String details = sc.nextLine();
            int numberSubtask = generateNumberTask();
            Status status = Status.NEW;
            SubTask subTask = new SubTask(numberSubtask, name, details, status);
            try {
                addSubtask(subTask);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Tables.allEpics.get(idEpic).getSubtasks().add(subTask.getID());
            subTask.setEpic(idEpic);
            Tables.forGenerateID.add(numberSubtask);
        }
    }

    @Override
    public void showListTasks() {
        System.out.println("Список всех задач");
        for (Task task : Tables.allTasks.values()) {
            System.out.println(task.toString());
        }
    }

    @Override
    public void showListEpics() {
        System.out.println("Список всех эпиков'");
        for (Epic epic : Tables.allEpics.values()) {
            System.out.println(epic.toString());
        }
    }

    @Override
    public void showListSubtask() {
        System.out.println("Список всех подзадач'");
        for (int epic : Tables.allSubTusk.keySet()) {
            System.out.println(Tables.allSubTusk.get(epic).toString());
        }
    }

    @Override
    public void getListSubtasksByEpicID() {
        Scanner sc = new Scanner(System.in);
        showListEpics();
        System.out.println("Введите номер эпика для которого нужно получить список всех подзадач");
        int idEpic = sc.nextInt();
        for (SubTask k : Tables.allSubTusk.values()) {
            if (k.getEpic() == idEpic) {
                System.out.println(k.toString());
            }
        }
    }

    @Override
    public void getUpdateByID()
            throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Выбирите типа задачи который необходи обновить");
        System.out.println("Если нужна задача - введите '1'");
        System.out.println("Если нужен эпик - введите '2'");
        System.out.println("Если нужна подзадача - введите '3'");
        int numType = sc.nextInt();
        if (numType == 1) {
            updateTask();
        } else if (numType == 2) {
            updateEpic();
        } else if (numType == 3) {
            updateSubtask();
        }
    }

    @Override
    public void getAnyByID() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Выбирите типа задачи который необходи показать");
        System.out.println("Если нужна задача - введите '1'");
        System.out.println("Если нужен эпик - введите '2'");
        System.out.println("Если нужна подзадача - введите '3'");
        int numType = sc.nextInt();
        if (numType == 1) {
            getTaskByID();
        } else if (numType == 2) {
            getEpicByID();
        } else if (numType == 3) {
            getSubtaskByID();
        }
    }

    @Override
    public void removeTaskByID() {
        Scanner scanner = new Scanner(System.in);
        showListTasks();
        showListEpics();
        showListSubtask();
        System.out.println("Введите ID задачи, которую необходимо удалить");
        int ID = scanner.nextInt();
        if (Tables.allTasks.containsKey(ID)) {
            Tables.allTasks.remove(ID);
        } else if (Tables.allEpics.containsKey(ID)) {
            for (Epic k : Tables.allEpics.values()) {
                if (k.getID() == ID) {
                    for (int l : k.getSubtasks()) {
                        if (Tables.deleteData.containsKey(l)) {
                            Tables.allSubTusk.remove(l);
                            historyManager.removeNode
                                    (Tables.tasksHis.get(Tables.deleteData.get(l)));
                        }
                    }
                }
            }
            Tables.allEpics.remove(ID);
        } else if (Tables.allSubTusk.containsKey(ID)) {
            for (SubTask sub : Tables.allSubTusk.values()) {
                if (sub.getID() == ID) {
                    Tables.allSubTusk.remove(ID);
                }
            }
        }
        historyManager.removeNode(Tables.tasksHis.get(Tables.deleteData.get(ID)));
    }

    @Override
    public void removeAllTask() {
        Tables.allTasks.clear();
    }

    @Override
    public void updateStatusEpic() {
        for (Epic k : Tables.allEpics.values()) {
            int countStatusNew = 0;
            int countStatusDone = 0;
            int countStatusProgress = 0;
            if (!k.getSubtasks().isEmpty()) {
                for (Integer j : k.getSubtasks()) {
                    if (Tables.allSubTusk.get(j).getStatus().equals(Status.NEW)) {
                        countStatusNew += 1;
                    } else if (Tables.allSubTusk.get(j).getStatus().equals(Status.DONE)) {
                        countStatusDone += 1;
                    } else if (Tables.allSubTusk.get(j).getStatus().equals(Status.TO_PROGRESS)) {
                        countStatusProgress += 1;
                    }
                }
            }
            System.out.println(countStatusDone + countStatusNew + countStatusProgress);
            if (countStatusNew == k.getSubtasks().size()) {
                Epic epic = new Epic(k.getID(), k.getName(), k.getDetails(), Status.NEW);
                Tables.allEpics.put(epic.getID(), epic);
                epic.setSubtasks(k.getSubtasks());
            } else if (countStatusDone == k.getSubtasks().size()) {
                Epic epic = new Epic(k.getID(), k.getName(), k.getDetails(), Status.DONE);
                Tables.allEpics.put(epic.getID(), epic);
                epic.setSubtasks(k.getSubtasks());
            } else if (countStatusProgress >= 1) {
                Epic epic = new Epic(k.getID(), k.getName(), k.getDetails(), Status.TO_PROGRESS);
                Tables.allEpics.put(epic.getID(), epic);
                epic.setSubtasks(k.getSubtasks());
            }
        }
        showListEpics();
    }

    @Override
    public void updateEpic() throws IOException {
        Scanner scanner = new Scanner(System.in);
        showListEpics();
        System.out.println("Введите ID эпика, который необходимо обновить");
        int ID = scanner.nextInt();
        for (Epic k : Tables.allEpics.values()) {
            if (k.getID() == ID) {
                System.out.println("Введите название эпика");
                Scanner scan = new Scanner(System.in);
                String name = scan.nextLine();
                System.out.println("Введите описание эпика ");
                String details = scan.nextLine();
                Epic epic = new Epic(ID, name, details, k.getStatus());
                Tables.allEpics.put(epic.getID(), epic);
                epic.setSubtasks(k.getSubtasks());
            }
        }
    }

    @Override
    public void addEpics(Epic epic) throws IOException {
        Tables.allEpics.put(epic.getID(), epic);
    }

    @Override
    public void getEpicByID() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID эпика, который необходимо показать");
        int ID = scanner.nextInt();
        for (Epic k : Tables.allEpics.values()) {
            if (k.getID() == ID) {
                System.out.println(k.toString());
                historyManager.add(k);
            }

        }
    }

    @Override
    public void updateTask() throws IOException {
        Scanner scanner = new Scanner(System.in);
        showListTasks();
        System.out.println("Введите ID задачи, которую необходимо обновить");
        int ID = scanner.nextInt();
        for (Task k : Tables.allTasks.values()) {
            if (k.getID() == ID) {
                System.out.println("Введите название задачи");
                Scanner scan = new Scanner(System.in);
                String name = scan.nextLine();
                System.out.println("Введите описание задачи ");
                String details = scan.nextLine();
                Task task = new Task(ID, name, details, k.getStatus());
                addTasks(task);
            }
        }
    }

    @Override
    public void addTasks(Task o) throws IOException {
        Tables.allTasks.put(o.getID(), o);
    }

    public void addTasktoHistory(Task task) {
        historyManager.add(task);
    }

    @Override
    public void getTaskByID() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID задачи. которую необходимо показать");
        int ID = scanner.nextInt();
        for (Task k : Tables.allTasks.values()) {
            if (k.getID() == ID) {
                System.out.println(k.toString());
                historyManager.add(k);
            }
        }
    }
}







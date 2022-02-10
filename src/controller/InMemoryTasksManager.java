package controller;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import java.util.*;


public class InMemoryTasksManager implements TaskManager {
        private HashMap<Integer, Task> allTasks = new HashMap<>();
        private HashMap<Integer, Epic> allEpics = new HashMap<>();
        private HashMap<Integer, SubTask> allSubTusk = new HashMap<>();
        private List<Integer> forGenerateID = new ArrayList<>();

    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    public void addSubtask(SubTask subTask, HashMap<Integer, SubTask> allSubTusk) {
        allSubTusk.put(subTask.getID(), subTask);
    }

    public int generateNumberTask() {
            return forGenerateID.size() + 1;
        }

    @Override
    public void toProgressSubtask(HashMap<Integer, SubTask> allSubtask, HashMap<Integer, Epic> allEpics){
        Scanner scanner = new Scanner(System.in);
        Managers.getDefault().showListSubtask(allSubtask);
        System.out.println("Введите ID подзадачи, которая сейчас выполняется");
        int ID = scanner.nextInt();
        for (SubTask k: allSubtask.values()){
            if(k.getID()==ID){
                SubTask subTask = new SubTask (k.getID(),k.getName(), k.getDetails(), Status.TO_PROGRESS);
                addSubtask(subTask,allSubtask);
                subTask.setEpic(k.getEpic());
            }
        }
        Managers.getDefault().updateStatusEpic(allEpics,allSubtask);
        Managers.getDefault().showListEpics(allEpics);
    }

    @Override
    public void doneSubtask(HashMap<Integer, SubTask> allSubtask, HashMap<Integer, Epic> allEpics){
        Scanner scanner = new Scanner(System.in);
        Managers.getDefault().showListSubtask(allSubtask);
        System.out.println("Введите ID задачи, выполнение которой завершилось");
        int ID = scanner.nextInt();
        for (SubTask k: allSubtask.values()){
            if(k.getID()==ID){
                SubTask subTask = new SubTask (k.getID(),k.getName(), k.getDetails(), Status.DONE);
                addSubtask(subTask,allSubtask);
                subTask.setEpic(k.getEpic());
            }
        }
        Managers.getDefault().updateStatusEpic(allEpics,allSubtask);
        Managers.getDefault().showListEpics(allEpics);
    }

    @Override
    public void getSubtaskByID(HashMap<Integer, SubTask> allSubtask){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID подзадачи, которую необходимо показать");
        int ID = scanner.nextInt();
        for(SubTask k: allSubtask.values()){
            if(k.getID()==ID){
                System.out.println(k.toString());
               historyManager.add(k);
            }
        }
    }

    @Override
    public void updateSubtask(HashMap<Integer, SubTask> allSubtask) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID подзадачи, которую необходимо обновить");
        int ID = scanner.nextInt();
        for (SubTask k : allSubtask.values()) {
            if (k.getID() == ID) {
                System.out.println("Введите название подзадачи");
                Scanner scan = new Scanner(System.in);
                String name = scan.nextLine();
                System.out.println("Введите описание подзадачи ");
                String details = scan.nextLine();
                SubTask subTask = new SubTask(ID, name, details, k.getStatus());
                allSubtask.put(subTask.getID(), subTask);
                subTask.setEpic(k.getEpic());
            }
        }
    }

    @Override
    public void createTask(HashMap<Integer, Task> allTasks) {
        System.out.println("Введите название задачи");
        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();
        System.out.println("Введите описание задачи ");
        String details = scan.nextLine();
        int numberTask = generateNumberTask();
        Status status = Status.NEW;
        Task task = new Task(numberTask, name, details, status);
        addTasks(task, allTasks);
        forGenerateID.add(numberTask);
    }

    @Override
    public void createEpic (HashMap<Integer, Epic> allEpics) {
        System.out.println("Введите название эпика");
        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();
        System.out.println("Введите описание задачи ");
        String details = scan.nextLine();
        int numberEpics = generateNumberTask();
        Status status = Status.NEW;
        Epic epic = new Epic(numberEpics, name, details, status);
        addEpics(epic, allEpics);
        forGenerateID.add(numberEpics);
    }

    @Override
    public void createSubtask(HashMap<Integer, SubTask> allSubTusk, HashMap<Integer, Epic> allEpics) {
        Scanner scanner = new Scanner(System.in);
        if (allEpics.isEmpty()) {
            System.out.println("Сначала создайте эпик");
        } else {
            showListEpics(allEpics);
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
            addSubtask(subTask, allSubTusk);
            allEpics.get(idEpic).getSubtasks().add(subTask.getID());
            subTask.setEpic(idEpic);
            forGenerateID.add(numberSubtask);
        }
    }

    @Override
    public void showListTasks(HashMap<Integer, Task> allTasks) {
        System.out.println("Список всех задач");
        for (Task task : allTasks.values()) {
            System.out.println(task.toString());
        }
    }

    @Override
    public void showListEpics(HashMap<Integer, Epic> allEpics) {
        System.out.println("Список всех эпиков'");
        for (Epic epic : allEpics.values()) {
            System.out.println(epic.toString());
        }
    }

    @Override
    public void showListSubtask(HashMap<Integer, SubTask> allSubTusk) {
        System.out.println("Список всех подзадач'");
        for (int epic : allSubTusk.keySet()) {
            System.out.println(allSubTusk.get(epic).toString());
        }
    }

    @Override
    public void getListSubtasksByEpicID(HashMap<Integer, SubTask> allSubTusk, HashMap<Integer, Epic> allEpics) {
        Scanner sc = new Scanner(System.in);
        showListEpics(allEpics);
        System.out.println("Введите номер эпика для которого нужно получить список всех подзадач");
        int idEpic = sc.nextInt();
        for (SubTask k : allSubTusk.values()) {
            if (k.getEpic() == idEpic) {
                System.out.println(k.toString());
            }
        }
    }

    @Override
    public void getUpdateByID(HashMap<Integer, Task> allTasks, HashMap<Integer, Epic> allEpics,
                                     HashMap<Integer, SubTask> allSubTusk) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Выбирите типа задачи который необходи обновить");
        System.out.println("Если нужна задача - введите '1'");
        System.out.println("Если нужен эпик - введите '2'");
        System.out.println("Если нужна подзадача - введите '3'");
        int numType = sc.nextInt();
        if (numType == 1) {
            updateTask(allTasks);
        } else if (numType == 2) {
            updateEpic(allEpics);
        } else if (numType == 3) {
            updateSubtask(allSubTusk);
        }
    }

    @Override
    public void getAnyByID(HashMap<Integer, Task> allTasks, HashMap<Integer, Epic> allEpics,
                                  HashMap<Integer, SubTask> allSubTusk) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Выбирите типа задачи который необходи показать");
        System.out.println("Если нужна задача - введите '1'");
        System.out.println("Если нужен эпик - введите '2'");
        System.out.println("Если нужна подзадача - введите '3'");
        int numType = sc.nextInt();
        if (numType == 1) {
            getTaskByID(allTasks);
        } else if (numType == 2) {
            getEpicByID(allEpics);
        } else if (numType == 3) {
            getSubtaskByID(allSubTusk);
        }
    }

    @Override
    public void removeTaskByID() {
        Scanner scanner = new Scanner(System.in);
        showListTasks(allTasks);
        showListEpics(allEpics);
        showListSubtask(allSubTusk);
        System.out.println("Введите ID задачи, которую необходимо удалить");
        int ID = scanner.nextInt();
        if(allTasks.containsKey(ID)){
                allTasks.remove(ID);
        }else if(allEpics.containsKey(ID)){
            for (Epic k : allEpics.values()) {
                if (k.getID() == ID) {
                    for(int l: k.getSubtasks()){
                        if(historyManager.getDeleteData().containsKey(l)){
                            allSubTusk.remove(l);
                            historyManager.removeNode
                                    (historyManager.getTasksHis().get(historyManager.getDeleteData().get(l)));
                        }
                    }
                    }
                }
                allEpics.remove(ID);
                } else if(allSubTusk.containsKey(ID)){
            for(SubTask sub: allSubTusk.values()){
                if(sub.getID() == ID){
                    allSubTusk.remove(ID);
                }
            }
        }
            historyManager.removeNode(historyManager.getTasksHis().get(historyManager.getDeleteData().get(ID)));
    }

    @Override
    public void removeAllTask(HashMap<Integer, Task> allTasks) {
        allTasks.clear();
    }

    @Override
    public void updateStatusEpic(HashMap<Integer, Epic> allEpics, HashMap<Integer, SubTask> allSubTusk) {
        for (Epic k : allEpics.values()) {
            int countStatusNew = 0;
            int countStatusDone = 0;
            int countStatusProgress = 0;
            if (!k.getSubtasks().isEmpty()) {
                for (Integer j : k.getSubtasks()) {
                    if (allSubTusk.get(j).getStatus().equals(Status.NEW)) {
                        countStatusNew += 1;
                    } else if (allSubTusk.get(j).getStatus().equals(Status.DONE)) {
                        countStatusDone += 1;
                    } else if (allSubTusk.get(j).getStatus().equals(Status.TO_PROGRESS)) {
                        countStatusProgress += 1;
                    }
                }
            }
            System.out.println(countStatusDone + countStatusNew + countStatusProgress);
            if (countStatusNew == k.getSubtasks().size()) {
                Epic epic = new Epic(k.getID(), k.getName(), k.getDetails(), Status.NEW);
                allEpics.put(epic.getID(), epic);
                epic.setSubtasks(k.getSubtasks());
            } else if (countStatusDone == k.getSubtasks().size()) {
                Epic epic = new Epic(k.getID(), k.getName(), k.getDetails(), Status.DONE);
                allEpics.put(epic.getID(), epic);
                epic.setSubtasks(k.getSubtasks());
            } else if (countStatusProgress >= 1) {
                Epic epic = new Epic(k.getID(), k.getName(), k.getDetails(), Status.TO_PROGRESS);
                allEpics.put(epic.getID(), epic);
                epic.setSubtasks(k.getSubtasks());
            }
        }
        showListEpics(allEpics);
    }

    @Override
    public void history(){
    }

    @Override
    public HashMap<Integer, Task> getAllTasks() {
        return allTasks;
    }

    @Override
    public HashMap<Integer, Epic> getAllEpics() {
        return allEpics;
    }

    @Override
    public HashMap<Integer, SubTask> getAllSubTusk() {
        return allSubTusk;
    }

    @Override
    public List<Task> getHistoryList() {
        return historyManager.getHistory();
    }

    @Override
    public void updateEpic(HashMap<Integer, Epic> allEpic) {
        Scanner scanner = new Scanner(System.in);
        Managers.getDefault().showListEpics(allEpic);
        System.out.println("Введите ID эпика, который необходимо обновить");
        int ID = scanner.nextInt();
        for (Epic k : allEpic.values()) {
            if (k.getID() == ID) {
                System.out.println("Введите название эпика");
                Scanner scan = new Scanner(System.in);
                String name = scan.nextLine();
                System.out.println("Введите описание эпика ");
                String details = scan.nextLine();
                Epic epic = new Epic(ID, name, details, k.getStatus());
                allEpic.put(epic.getID(), epic);
                epic.setSubtasks(k.getSubtasks());
            }
        }
    }

    @Override
    public void addEpics(Epic epic, HashMap<Integer, Epic> allEpics) {
        allEpics.put(epic.getID(), epic);
    }

    @Override
    public void getEpicByID(HashMap<Integer, Epic> allEpics) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID эпика, который необходимо показать");
        int ID = scanner.nextInt();
        for (Epic k : allEpics.values()) {
            if (k.getID() == ID) {
                System.out.println(k.toString());
                historyManager.add(k);
            }

        }
    }

    @Override
    public void updateTask(HashMap<Integer, Task> allTasks) {
        Scanner scanner = new Scanner(System.in);
        Managers.getDefault().showListTasks(allTasks);
        System.out.println("Введите ID задачи, которую необходимо обновить");
        int ID = scanner.nextInt();
        for (Task k : allTasks.values()) {
            if (k.getID() == ID) {
                System.out.println("Введите название задачи");
                Scanner scan = new Scanner(System.in);
                String name = scan.nextLine();
                System.out.println("Введите описание задачи ");
                String details = scan.nextLine();
                Task task = new Task(ID, name, details, k.getStatus());
                addTasks(task, allTasks);
            }
        }
    }

    @Override
    public void addTasks(Task o, HashMap<Integer, Task> allTasks) {
        allTasks.put(o.getID(), o);
    }

    @Override
    public void getTaskByID(HashMap<Integer, Task> allTasks) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID задачи. которую необходимо показать");
        int ID = scanner.nextInt();
        for (Task k : allTasks.values()) {
            if (k.getID() == ID) {
                System.out.println(k.toString());
                historyManager.add(k);
            }
        }
    }



}








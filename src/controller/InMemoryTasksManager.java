package controller;
import data.Tables;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.Tasks;

import java.io.IOException;
import java.util.*;


public class InMemoryTasksManager extends Tables implements TaskManager {

    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    @Override
    public int generateNumberTask() {
        return Tables.forGenerateID.size() + 1;
    }

    @Override
    public void createTask(Task task) {
        if(!task.validateTime()){
            System.out.println("Задача с указанным временем существует!");
            System.out.println("Выбирите другое время для выполнения задачи");
            return;
        }
        task.setID(generateNumberTask());
        try {
            addTasks(task);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Tables.forGenerateID.add(task.getID());
    }

    @Override
    public void createEpic(Epic epic) {
        if(!epic.validateTime()){
            System.out.println("Задача с указанным временем существует!");
            System.out.println("Выбирите другое время для выполнения задачи");
            return;
        }else{
            System.out.println("Время свободно");
        }
        epic.setID(generateNumberTask());
        try {
            addEpics(epic);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Tables.forGenerateID.add(epic.getID());
    }

    @Override
    public void createSubtask(SubTask subTask, int idEpic) {
        if(!subTask.validateTime()){
            System.out.println("Задача с указанным временем существует!");
            System.out.println("Выбирите другое время для выполнения задачи");
            return;
        }
        if (Tables.allEpics.isEmpty()) {
            System.out.println("Сначала создайте эпик");
        } else {
            subTask.setID(generateNumberTask());
            try {
                addSubtask(subTask);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Tables.allEpics.get(idEpic).getSubtasks().add(subTask.getID());
            subTask.setEpic(idEpic);
            Tables.forGenerateID.add(subTask.getID());
        }
    }

    @Override
    public void addTasks(Task o) throws IOException {
        Tables.allTasks.put(o.getID(), o);
    }

    @Override
    public void addEpics(Epic epic) throws IOException {
        Tables.allEpics.put(epic.getID(), epic);
    }

    @Override
    public void addSubtask(SubTask subTask) throws IOException {
        Tables.allSubTusk.put(subTask.getID(), subTask);
    }

    @Override
    public List<Task> showListTasks() {
        List<Task> tasks = new ArrayList<>();
        if (!Tables.allTasks.isEmpty()) {
            System.out.println("Список всех задач");
            for (Task task : Tables.allTasks.values()) {
                //System.out.println(task.toString());
                tasks.add(task);
            }
            return tasks;
        } else {
            System.out.println("Список задач пуст");
            return null;
        }
    }

    @Override
    public List<Epic> showListEpics() {
        List<Epic> epics = new ArrayList<>();
        if (!Tables.allEpics.isEmpty()) {
            System.out.println("Список всех задач");
            for (Epic epic : Tables.allEpics.values()) {
                System.out.println(epic.toString());
                epics.add(epic);
            }
            return epics;
        } else {
            return null;
        }
    }

    @Override
    public List<SubTask> showListSubtask() {
        List<SubTask> epics = new ArrayList<>();
        if (!Tables.allEpics.isEmpty()) {
            System.out.println("Список всех задач");
            for (SubTask subTask : Tables.allSubTusk.values()) {
                System.out.println(subTask.toString());
                epics.add(subTask);
            }
            return epics;
        } else {
            return null;
        }
    }

    @Override
    public List<SubTask> getListSubtasksByEpicID(int numEpic) {
        List<SubTask> subTaskList = new ArrayList<>();
        if (!Tables.allSubTusk.isEmpty()) {
            for (SubTask k : Tables.allSubTusk.values()) {
                if (k.getEpic() == numEpic) {
                    System.out.println(k.toString());
                    subTaskList.add(k);
                }
            }
            if (subTaskList.isEmpty()) {
                return null;
            } else {
                return subTaskList;
            }
        } else {
            return null;
        }
    }

    @Override
    public Task getAnyByID(Tasks tasks, int numOfTask) {
        if (tasks.equals(Tasks.TASK)) {
            return getTaskByID(numOfTask);
        } else if (tasks.equals(Tasks.EPIC)) {
            return   getEpicByID(numOfTask);
        } else if (tasks.equals(Tasks.SUBTASK)) {
            return getSubtaskByID(numOfTask);
        }
        return null;
    }

    @Override
    public Task getTaskByID(int ID) {
        if (Tables.allTasks.isEmpty() || !Tables.allTasks.containsKey(ID)) {
            return null;
        } else {
            System.out.println(Tables.allTasks.get(ID).toString());
            historyManager.add(Tables.allTasks.get(ID));
            return Tables.allTasks.get(ID);
        }
    }

    @Override
    public Epic getEpicByID(int ID){
        if (Tables.allEpics.isEmpty() || !Tables.allEpics.containsKey(ID)) {
            return null;
        } else {
            System.out.println(Tables.allEpics.get(ID).toString());
            historyManager.add(Tables.allEpics.get(ID));
            return Tables.allEpics.get(ID);
        }
    }

    @Override
    public SubTask getSubtaskByID(int ID) {
        if (Tables.allSubTusk.isEmpty() || !Tables.allSubTusk.containsKey(ID)) {
            return null;
        } else {
            System.out.println(Tables.allSubTusk.get(ID).toString());
            historyManager.add(Tables.allSubTusk.get(ID));
            return Tables.allSubTusk.get(ID);
        }
    }

    @Override
    public String removeTaskByID(int numTask) {
        String text;
        if (!Tables.allTasks.isEmpty() && Tables.allTasks.containsKey(numTask)) {
            Tables.allTasks.remove(numTask);
            if(Tables.deleteData.containsKey(numTask)) {
                historyManager.removeNode(Tables.tasksHis.get(Tables.deleteData.get(numTask)));
            }
            text = "Задача удалена";
            return text;
        } else if (!Tables.allEpics.isEmpty() && Tables.allEpics.containsKey(numTask)) {
            for (Epic k : Tables.allEpics.values()) {
                if (k.getID() == numTask) {
                    for (int l : k.getSubtasks()) {
                        if (Tables.deleteData.containsKey(l)) {
                            Tables.allSubTusk.remove(l);
                            historyManager.removeNode
                                    (Tables.tasksHis.get(Tables.deleteData.get(l)));
                        }
                    }
                }
            }
            Tables.allEpics.remove(numTask);
            text = "Эпик удален";
            if(Tables.deleteData.containsKey(numTask)) {
                historyManager.removeNode(Tables.tasksHis.get(Tables.deleteData.get(numTask)));
            }
            return text;
        } else if (!Tables.allEpics.isEmpty() && Tables.allSubTusk.containsKey(numTask)) {
            for (SubTask sub : Tables.allSubTusk.values()) {
                if (sub.getID() == numTask) {
                    Tables.allSubTusk.remove(numTask);
                }
            } if(Tables.deleteData.containsKey(numTask)) {
                historyManager.removeNode(Tables.tasksHis.get(Tables.deleteData.get(numTask)));
            }
            text = "Подзадача удалена";
            return text;
        }else {
            text = "Задача с указанным ID не найдена";
            if(Tables.deleteData.containsKey(numTask)) {
                historyManager.removeNode(Tables.tasksHis.get(Tables.deleteData.get(numTask)));
            }
            return text;
        }

    }

    @Override
    public void removeAllTask() {
        Tables.allTasks.clear();
        Tables.allEpics.clear();
        Tables.allSubTusk.clear();
    }

    @Override
    public String updateTask(Task task) {
        task.validateTime();
        String text;
        if (Tables.allTasks.containsKey(task.getID())) {
            Task oldTask = Tables.allTasks.get(task.getID());
            Tables.allTasks.put(task.getID(), task);
            text = "Задача обновлена";
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
    public String updateEpic(Epic epic) {
        String text;
        if (Tables.allEpics.containsKey(epic.getID())) {
            Epic oldEpic = Tables.allEpics.get(epic.getID());
            Tables.allEpics.put(epic.getID(), epic);
            epic.setSubtasks(oldEpic.getSubtasks());
            text = "Эпик обновлен";
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
                Epic epic = new Epic(k.getID(), k.getName(), k.getDetails(), Status.NEW
                        ,k.getStartTime().toString(), k.getDuration());
                Tables.allEpics.put(epic.getID(), epic);
                epic.setSubtasks(k.getSubtasks());
            } else if (countStatusDone == k.getSubtasks().size()) {
                Epic epic = new Epic(k.getID(), k.getName(), k.getDetails(), Status.DONE
                        ,k.getStartTime().toString(),k.getDuration());
                Tables.allEpics.put(epic.getID(), epic);
                epic.setSubtasks(k.getSubtasks());

            } else if (countStatusProgress >= 1) {
                Epic epic = new Epic(k.getID(), k.getName(), k.getDetails(), Status.TO_PROGRESS
                ,k.getStartTime().toString(),k.getDuration());
                Tables.allEpics.put(epic.getID(), epic);
                epic.setSubtasks(k.getSubtasks());

            }
        }
     showListEpics();
    }

    @Override
    public void updateSubtask(SubTask subTask) throws IOException {
        subTask.validateTime();
        if (Tables.allSubTusk.containsKey(subTask.getID())) {
            Tables.allSubTusk.put(subTask.getID(), subTask);
            subTask.setEpic(subTask.getEpic());
        }
    }

    @Override
    public void doneSubtask(int numberTask) throws IOException {
        showListSubtask();
        int ID = numberTask;
        if (!Tables.allSubTusk.isEmpty()) {
            int count = 0;
            for (SubTask k : Tables.allSubTusk.values()) {

                if (k.getID() == ID) {
                    SubTask subTask = new SubTask(k.getID(), k.getName(), k.getDetails(), Status.DONE
                            ,k.getStartTime().toString(), k.getDuration());
                    addSubtask(subTask);
                    subTask.setEpic(k.getEpic());
                    updateStatusEpic();
                    showListEpics();
                } else {
                    count += 1;
                }
            }

            if (count == Tables.allSubTusk.size()) {
                System.out.println("Задача не найдена");
            }

        } else {
            System.out.println("Список подзадач пуст");
        }

    }

    @Override
    public String toProgressSubtask(int ID) {
        String text;
        if(Tables.allSubTusk.containsKey(ID)){
            SubTask oldSubTask = Tables.allSubTusk.get(ID);
                SubTask subTask = new SubTask(ID, oldSubTask.getName(), oldSubTask.getDetails(), Status.TO_PROGRESS
                        ,oldSubTask.getStartTime().toString(),oldSubTask.getDuration());
            try {
                addSubtask(subTask);
            } catch (IOException e) {
                e.printStackTrace();
            }
            subTask.setEpic(oldSubTask.getEpic());
                text = "Статус успешно изменен";
            updateStatusEpic();
            showListEpics();
                return text;
            }else if (Tables.allSubTusk.isEmpty()){
            text = "Список подзадач пуст";
            return text;
        }else {
            text = "Подзадача с указанным ID не найдена";
            return text;
        }
    }

    @Override
    public Set<Task> getPrioritizedTasks() {
        Set<Task> tasksSubtasks = new TreeSet<>(new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if(o1.getStartTime() == null) {
                    return 1;
                }else if(o2.getStartTime() == null){
                    return -1;
                } else if (o1.getStartTime() ==null && o1.getStartTime() ==null){
                    return 0;
                }else {
                    return o1.getStartTime().toString().compareTo(o2.getStartTime().toString());
                }
            }
        });
        tasksSubtasks.addAll(Tables.allTasks.values());
        tasksSubtasks.addAll(Tables.allSubTusk.values());
        return tasksSubtasks;
    }

    public void addTaskToHistory(Task task) {
        historyManager.add(task);
    }


}








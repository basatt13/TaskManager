import java.util.*;

public class ManagerForTasks {
    HashMap<Integer, Task> allTasks;

    {
        allTasks = new HashMap<>();
    }

    public static void getSomeTaskbyID(HashMap<Integer, Task> allTasks) {
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Введите ID задачи, которую необходимо показать");
                int id = scanner.nextInt();
                int checkNum = 0;
                if (id <= 100) {
                    for (int k : allTasks.keySet()) {
                        if (id == k) {
                            checkNum += 1;
                            System.out.println(allTasks.get(k).toString());
                        }
                    }
                } else if (id <= 100000) {
                    for (int k : allTasks.get(id / 100).getForSubtask().keySet()) {
                        if (id == k) {
                            checkNum += 1;
                            System.out.println(allTasks.get(id / 100).getForSubtask().get(k).toString());
                        }
                    }
                } else if (id <= 1000000000) {
                    int numEpic = id / 100000;
                    int numTask = numEpic / 100;
                    Epic epic = (Epic) allTasks.get(numTask).getForSubtask().get(numEpic);
                    for (int k : epic.getForSubtask().keySet()) {
                        if (id == k) {
                            checkNum += 1;
                            System.out.println(epic.getForSubtask().get(id));
                        }
                    }
                }
                if (checkNum == 0) {
                    System.out.println("Задача с указанным ID не найдена");
                } else {
                    System.out.println("Выполнено");
                    return;
                }
            } catch (NullPointerException | InputMismatchException n) {
                System.out.println("Задача с указанным ID не найдена");
                CreateTasks.showListtasks(allTasks);
            }
        }
    }


    public static void updateTasks(HashMap<Integer, Task> allTasks) {
        System.out.println("Для обновления данных введите ID задачи/эпика/подзадачи");
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                int ID = scanner.nextInt();
                int chekID = 0;
                if (ID <= 100) {
                    for (int k : allTasks.keySet()) {
                        if (k == ID) {
                            chekID += 1;
                        }
                    }
                } else if (ID <= 100000) {
                    for (int j : allTasks.get(ID / 100).getForSubtask().keySet()) {
                        if (j == ID) {
                            chekID += 1;
                        }
                    }
                } else if (ID <= 1000000000) {
                    int numEpic = ID / 100000;
                    int numTask = numEpic / 100;
                    Epic epicOb = (Epic) allTasks.get(numTask).getForSubtask().get(numEpic);
                    for (int g : epicOb.getForSubtask().keySet()) {
                        if (g == ID) {
                            chekID += 1;
                        }
                    }
                }
                if (chekID > 0) {
                    if (ID <= 100) {
                        System.out.println("Введите название задачи");
                        Scanner scan = new Scanner(System.in);
                        String name = scan.nextLine();
                        System.out.println("Введите описание задачи ");
                        String details = scan.nextLine();
                        String status = "NEW";
                        HashMap<Integer, Object> subTask = new HashMap<>();
                        Task task = new Task(ID, name, details, status, subTask);
                        allTasks.put(task.ID, task);
                    } else if (ID <= 100000) {
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Введите название эпика");
                        String name = scan.nextLine();
                        System.out.println("Введите описание эпика ");
                        String details = scan.nextLine();
                        int numTask1 = ID / 100;
                        String status = "NEW";
                        HashMap<Integer, Object> subTask = new HashMap<>();
                        Epic epic = new Epic(ID, name, details, status, subTask);
                        allTasks.get(numTask1).getForSubtask().put(ID, epic);
                    } else {
                        int numberEpic = ID / 100000;
                        int numberTask = numberEpic / 100;
                        Epic epicObject = (Epic) allTasks.get(numberTask).getForSubtask().get(numberEpic);
                        Scanner scan = new Scanner(System.in);
                        System.out.println("Введите название подзадачи");
                        String name = scan.nextLine();
                        System.out.println("Введите описание подзадачи ");
                        String details = scan.nextLine();
                        String status = "NEW";
                        HashMap<Integer, Object> subTask = new HashMap<>();
                        SubTask subTask1 = new SubTask(ID, name, details, status, subTask);
                        epicObject.getForSubtask().put(ID, subTask1);
                    }
                    break;
                } else {
                    System.out.println("Задача с указанным ID не найдена. Введите корректный ID.");
                }
            } catch (Exception e) {
                System.out.println("Введите корректные данные");
            }
        }
    }

    public static void cleanalltasks(HashMap<Integer, Task> allTasks) {
        System.out.println("Очищаю список всех задач");
        if (allTasks.isEmpty()) {
            System.out.println("Список задач пуст");
        } else {
            allTasks.clear();
        }
        System.out.println("Список задач очищен");
    }

    public static void removetaskbyID(HashMap<Integer, Task> allTasks) {
        while (true) try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите ID задачи, которую нужно удалить");
            int id = scanner.nextInt();
            if (id <= 100) {
                for (int k : allTasks.keySet()) {
                    if (id == k) {
                        allTasks.remove(id);
                        System.out.println("Выполнено");
                    }
                }
                break;
            } else if (id <= 100000) {
                for (int k : allTasks.get(id / 100).getForSubtask().keySet()) {
                    if (id == k) {
                        allTasks.get(id / 100).getForSubtask().remove(id);
                        System.out.println("Выполнено");
                    }
                }
                break;
            } else {
                int numEpic = id / 100000;
                int numTask = numEpic / 100;
                Epic epic = (Epic) allTasks.get(numTask).getForSubtask().get(numEpic);
                for (int k : epic.getForSubtask().keySet()) {
                    if (id == k) {
                        epic.getForSubtask().remove(id);
                        System.out.println("Выполнено");
                    }
                }
                break;
            }
        } catch (NullPointerException | InputMismatchException n) {
            System.out.println("Задача с указанным ID не найдена");
            CreateTasks.showListtasks(allTasks);

        } catch (ConcurrentModificationException h) {
            CreateTasks.showListtasks(allTasks);
            break;
        }
    }
}






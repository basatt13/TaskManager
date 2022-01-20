package controller;
import tasks.SubTask;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        InMemoryTasksManager manager = Managers.getDefault();
        while (true) {
            try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Выбирите нужный пункт меню");
            System.out.println(" 1. Создать задачу");
            System.out.println(" 2. Создать epic");
            System.out.println(" 3. Создать подзадачу");
            System.out.println(" 4. Получить список всех задач");
            System.out.println("5. Получить список всех эпиков");
            System.out.println("6. Получить список всех подзадач определенного эпика");
            System.out.println("7. Получение задачи любого типа по идентификатору.");
            System.out.println("8. Обновить задачу");
            System.out.println("9. Удалить задачу по ID");
            System.out.println("10. Удалить все задачи");
            System.out.println("11. Указать подзадачу, которая сейчас в работе");
            System.out.println("12. Указать подзадачу, которая завершена");
            System.out.println("13. Показать историю");
            System.out.println("Введите команду 131313 для выхода из программы");

            int command = sc.nextInt();
            if(command == 1){
                manager.createTask(manager.allTasks);
            }else if (command==2){
                manager.createEpic(manager.allEpics);
            }else if(command==3){
                manager.createSubtask(manager.allSubTusk,manager.allEpics);
            }else if(command==4){
                manager.showListTasks(manager.allTasks);
            }else if(command==5){
                manager.showListEpics(manager.allEpics);
            }else if(command==6){
                manager.getListSubtasksByEpicID(manager.allSubTusk,manager.allEpics);
            }else if(command==7){
                manager.getAnyByID(manager.allTasks,manager.allEpics,manager.allSubTusk,manager.historyList);
            } else if(command==8){
                manager.getUpdateByID(manager.allTasks, manager.allEpics, manager.allSubTusk);
            }else if(command==9){
                manager.removeTaskByID(manager.allTasks);
            }else if(command==10){
                manager.removeAllTask(manager.allTasks);
            }else if(command==11){
                SubTask.toProgressSubtask(manager.allSubTusk,manager.allEpics);
            }else if(command==12){
                SubTask.doneSubtask(manager.allSubTusk,manager.allEpics);
            }
            else if(command==13){
                manager.history();
            }
        } catch (Exception e){
                System.out.println("Введите корректное значение");
            }
        }
    }
}



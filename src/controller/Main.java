package controller;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Manager manager = new Manager();
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
            System.out.println("Введите команду 131313 для выхода из программы");

            int command = sc.nextInt();
            if(command == 1){
                Manager.createTask(manager.allTasks);
            }else if (command==2){
                Manager.createEpic(manager.allEpics);
            }else if(command==3){
                Manager.createSubtask(manager.allSubTusk,manager.allEpics);
            }else if(command==4){
                Manager.showListtasks(manager.allTasks);
            }else if(command==5){
                Manager.showListEpics(manager.allEpics);
            }else if(command==6){
                Manager.getListSubtasksByEpicID(manager.allSubTusk,manager.allEpics);
            }else if(command==7){
                Manager.getAnyByID(manager.allTasks,manager.allEpics,manager.allSubTusk);
            } else if(command==8){
                Manager.getUpdateByID(manager.allTasks, manager.allEpics, manager.allSubTusk);
            }else if(command==9){
                Manager.removeTaskByID(manager.allTasks);
            }else if(command==10){
                Manager.removeAllTask(manager.allTasks);
            }else if(command==11){
                SubTask.toProgressSubtask(manager.allSubTusk,manager.allEpics);
            }else if(command==12){
                SubTask.doneSubtask(manager.allSubTusk,manager.allEpics);
            }
        } catch (Exception e){
                System.out.println("Введите корректное значение");}
        }
    }
}



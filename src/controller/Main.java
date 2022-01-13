package controller;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.HashMap;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        HashMap<Integer, Task> allTasks;
        HashMap<Integer, Epic> allEpics;
        HashMap<Integer, SubTask> allSubTusk;

        {
            allTasks = new HashMap<>();
            allEpics = new HashMap<>();
            allSubTusk = new HashMap<>();
        }
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
            System.out.println("12. Указать подзадачу, 'пик, которая завершена");
            System.out.println("Введите команду 131313 для выхода из программы");

            int command = sc.nextInt();
            if(command == 1){
                Task.createTask(allTasks);
            }else if (command==2){
                Epic.createEpic(allEpics);
            }else if(command==3){
                SubTask.createSubtask(allSubTusk,allEpics);
            }else if(command==4){
                Task.showListtasks(allTasks);
            }else if(command==5){
                Epic.showListEpics(allEpics);
            }else if(command==6){
                SubTask.getListSubtasksByEpicID(allSubTusk,allEpics);
            }else if(command==7){
                System.out.println("Выбирите типа задачи который необходи показать");
                System.out.println("Если нужна задача - введите '1'");
                System.out.println("Если нужен эпик - введите '2'");
                System.out.println("Если нужна подзадача - введите '3'");
                int numType = sc.nextInt();
                if(numType==1){
                    Task.getTaskByID(allTasks);
                }else if(numType==2){
                    Epic.getEpicByID(allEpics);
                }else if(numType==3){
                    SubTask.getSubtaskByID(allSubTusk);
                }
            } else if(command==8){
                Task.updateTask(allTasks);
            }else if(command==9){
                Task.removeTaskByID(allTasks);
            }else if(command==10){
                Task.removeAllTask(allTasks);
            }else if(command==11){
                SubTask.toProgressSubtask(allSubTusk,allEpics);
            }else if(command==12){
                SubTask.doneSubtask(allSubTusk,allEpics);
            }
        } catch (Exception e){
                System.out.println("Введите корректное значение");}
        }
    }
}



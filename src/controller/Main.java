package controller;
import tasks.Task;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
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
                manager.createTask(manager.getAllTasks());
            }else if (command==2){
                manager.createEpic(manager.getAllEpics());
            }else if(command==3){
                manager.createSubtask(manager.getAllSubTusk(),manager.getAllEpics());
            }else if(command==4){
                manager.showListTasks(manager.getAllTasks());
            }else if(command==5){
                manager.showListEpics(manager.getAllEpics());
            }else if(command==6){
                manager.getListSubtasksByEpicID(manager.getAllSubTusk(),manager.getAllEpics());
            }else if(command==7){
                manager.getAnyByID(manager.getAllTasks(),manager.getAllEpics(),manager.getAllSubTusk(),
                        manager.getHistoryList());
            } else if(command==8){
                manager.getUpdateByID(manager.getAllTasks(), manager.getAllEpics(), manager.getAllSubTusk());
            }else if(command==9){
                manager.removeTaskByID();
            }else if(command==10){
                manager.removeAllTask(manager.getAllTasks());
            }else if(command==11){
                manager.toProgressSubtask(manager.getAllSubTusk(),manager.getAllEpics());
            }else if(command==12){
                manager.doneSubtask(manager.getAllSubTusk(),manager.getAllEpics());
            }
            else if(command==13){
             manager.getHistoryList();
            }
        } catch (Exception e){
                System.out.println("Введите корректное значение");
            }
        }
    }
}



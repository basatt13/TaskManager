package controller;

public class HistoryManagerTest extends InMemoryTasksManager {

    public void testHistory() {

        TaskManager manager = Managers.getDefault();

        for (int i = 1; i < 9; ++i) {
            if (i <= 3) {
                System.out.println("СОЗАДЁМ ЗАДАЧУ");
                manager.createTask(manager.getAllTasks());
            } else if (i <= 5) {
                System.out.println("СОЗАДЁМ ЭПИК");
                manager.createEpic(manager.getAllEpics());
            } else {
                System.out.println("СОЗАДЁМ ПОДЗАДАЧУ");
                manager.createSubtask(manager.getAllSubTusk(), manager.getAllEpics());
            }
        }

        for (int k = 0; k < 12; ++k) {
            manager.getAnyByID(manager.getAllTasks(), manager.getAllEpics(), manager.getAllSubTusk());
            System.out.println();
            manager.getHistoryList();
        }
        manager.removeTaskByID();
        manager.getHistoryList();
        manager.removeTaskByID();
        manager.getHistoryList();
    }
}

package api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import controller.FileBackedTaskManager;
import controller.Manager;
import data.Tables;
import tasks.Epic;
import tasks.NODE;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.Objects;

public class HttpTaskServer extends Tables {
    private final int PORT;
    HttpTaskManager manager;

    public HttpTaskServer(int PORT, HttpTaskManager manager) throws IOException {
        this.PORT = PORT;
        this.manager = manager;
    }

    public HttpServer start() throws IOException, InterruptedException {
        HttpServer httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        TasksAPI tasksAPI = new TasksAPI();
        httpServer.createContext("/tasks/", tasksAPI);
        httpServer.createContext("/tasks/task/", tasksAPI);
        httpServer.createContext("/tasks/epic/", tasksAPI);
        httpServer.createContext("/tasks/subtask/", tasksAPI);
        httpServer.createContext("/tasks/subtask/epic/", tasksAPI);
        httpServer.createContext("/tasks/history/", tasksAPI);
        httpServer.start();
        return httpServer;
    }


    static class TasksAPI implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            HttpTaskManager manager = null;
            try {
                manager = Manager.getDefault();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (httpExchange.getHttpContext().getPath()) {
                case "/tasks/":
                    if (httpExchange.getRequestMethod().equals("GET")) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (Task task : manager.getPrioritizedTasks()) {
                            stringBuilder.append(task.toString() + "\n");
                        }
                        String file = stringBuilder.toString();
                        ValidateRequests.validateRequest(httpExchange, file);
                    } else {
                        String response = "Ожидается запрос GET \n" +
                                "Выполнен запрос " + httpExchange.getRequestMethod();
                        System.out.println(response);
                        httpExchange.sendResponseHeaders(405, 0);
                    }
                    break;
                case "/tasks/task/":
                    switch (httpExchange.getRequestMethod()) {
                        case "GET":
                            if (httpExchange.getRequestURI().getRawQuery() == null) {
                                ValidateRequests.validateRequest(httpExchange, manager.showListTasks().toString());
                            } else {
                                String number = httpExchange.getRequestURI().getRawQuery().split("=")[1];
                                ValidateRequests.validateRequest(httpExchange,
                                        manager.getTaskByID(Integer.parseInt(number)).toString());
                            }
                            break;
                        case "POST":
                            InputStream inputStream = httpExchange.getRequestBody();
                            String body = new String(inputStream.readAllBytes());
                            JsonElement jsonElement = JsonParser.parseString(body);
                            JsonObject jsonObject = jsonElement.getAsJsonObject();
                            if (jsonObject.keySet().contains("up")) {
                                String task = jsonObject.get("up").getAsString();
                                manager.updateTask(Objects.requireNonNull(manager.fromString(task)));
                            } else {
                                String task = jsonObject.get("task").getAsString();
                                manager.createTask(manager.fromString(task));
                            }
                            ValidateRequests.validateRequest(httpExchange, "Задача успешно создана");
                            break;
                        case "DELETE":
                            if (httpExchange.getRequestURI().getRawQuery() == null) {
                                manager.removeAllTask();
                                ValidateRequests.validateRequest(httpExchange, "Все задачи удалены");
                            } else {
                                String number = httpExchange.getRequestURI().getRawQuery().split("=")[1];
                                assert manager != null;
                                manager.removeTaskByID(Integer.parseInt(number));
                                //manager.save();
                                ValidateRequests.validateRequest(httpExchange, "Задача успешно удалена");
                            }
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + httpExchange.getRequestMethod());
                    }
                    break;
                case "/tasks/epic/":
                    switch (httpExchange.getRequestMethod()) {
                        case "GET":
                            if (httpExchange.getRequestURI().getRawQuery() == null) {
                                ValidateRequests.validateRequest(httpExchange, manager.showListEpics().toString());
                            } else {
                                String number = httpExchange.getRequestURI().getRawQuery().split("=")[1];
                                ValidateRequests.validateRequest(httpExchange,
                                        manager.getEpicByID(Integer.parseInt(number)).toString());
                            }
                            break;
                        case "POST":
                            InputStream inputStream = httpExchange.getRequestBody();
                            String body = new String(inputStream.readAllBytes());
                            JsonElement jsonElement = JsonParser.parseString(body);
                            JsonObject jsonObject = jsonElement.getAsJsonObject();
                            if (jsonObject.keySet().contains("up")) {
                                String task = jsonObject.get("up").getAsString();
                                manager.updateEpic((Epic) Objects
                                        .requireNonNull(FileBackedTaskManager.fromString(task)));
                            } else {
                                String task = jsonObject.get("epic").getAsString();
                                manager.createEpic((Epic) FileBackedTaskManager.fromString(task));
                            }
                            //manager.save();
                            ValidateRequests.validateRequest(httpExchange, "Задача успешно создана");
                            break;
                        case "DELETE":
                            String number = httpExchange.getRequestURI().getRawQuery().split("=")[1];
                            manager.removeTaskByID(Integer.parseInt(number));
                            //manager.save();
                            ValidateRequests.validateRequest(httpExchange, "Задача успешно удалена");
                    }
                    break;
                case "/tasks/subtask/":
                    switch (httpExchange.getRequestMethod()) {
                        case "GET":
                            if (httpExchange.getRequestURI().getRawQuery() == null) {
                                ValidateRequests.validateRequest(httpExchange, manager.showListEpics().toString());
                            } else {
                                String number = httpExchange.getRequestURI().getRawQuery().split("=")[1];
                                ValidateRequests.validateRequest(httpExchange,
                                        manager.getSubtaskByID(Integer.parseInt(number)).toString());
                            }
                        case "POST":
                            InputStream inputStream = httpExchange.getRequestBody();
                            String body = new String(inputStream.readAllBytes());
                            JsonElement jsonElement = JsonParser.parseString(body);
                            JsonObject jsonObject = jsonElement.getAsJsonObject();
                            String subtask = jsonObject.get("subtask").getAsString();
                            String numEpic = subtask.split(", ")[subtask.split(", ").length - 1];
                            if (jsonObject.keySet().contains("up")) {
                                String task = jsonObject.get("up").getAsString();
                                manager.updateSubtask((SubTask)
                                        Objects.requireNonNull(FileBackedTaskManager.fromString(subtask)));
                            } else {
                                String task = jsonObject.get("subtask").getAsString();
                                manager.createSubtask((SubTask) FileBackedTaskManager.fromString(task)
                                        , Integer.parseInt(numEpic));
                            }
                            try {
                                manager.save();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ValidateRequests.validateRequest(httpExchange, "операция завершена");
                            break;
                        case "DELETE":
                            String number = httpExchange.getRequestURI().getRawQuery().split("=")[1];
                            manager.removeTaskByID(Integer.parseInt(number));
                            try {
                                manager.save();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ValidateRequests.validateRequest(httpExchange, "Задача успешно удалена");
                    }
                    break;
                case "/tasks/subtask/epic/":
                    String number = httpExchange.getRequestURI().getRawQuery().split("=")[1];
                    ValidateRequests.validateRequest(httpExchange,
                            manager.getListSubtasksByEpicID(Integer.parseInt(number)).toString());
                    break;
                case "/tasks/history/":
                    try {
                        manager.save();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    for (NODE node : Tables.tasksHis) {
                        stringBuilder.append(node.data.getID() + ", ");
                    }
                    String file = stringBuilder.toString();
                    ValidateRequests.validateRequest(httpExchange, file);
                    try {
                        manager.save();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Адрес не найден");
            }
        }
    }

}


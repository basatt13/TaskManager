package test;

import api.HttpTaskManager;
import api.HttpTaskServer;
import api.KVServer;
import com.google.gson.Gson;
import controller.Manager;
import controller.Status;
import data.Tables;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Task;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;


public class HttpTest extends Tables {
    final protected HttpClient httpClient = HttpClient.newHttpClient();

    HttpResponse<String> createTask(String URL, String keytask, String task) throws IOException, InterruptedException {
        URI uri = URI.create(URL);
        Gson gson = new Gson();
        Map<String, String> newTask = new HashMap<>();
        newTask.put(keytask, task);
        String json = gson.toJson(newTask);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .POST(body)
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    void getAllTasks(String URL) throws IOException, InterruptedException {
        URI uri = URI.create(URL);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }

    void getTaskByID(String URL) throws IOException, InterruptedException {
        URI uri = URI.create(URL);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }

    void deleteTaskByID(String URL) throws IOException, InterruptedException {
        URI uri = URI.create(URL);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .DELETE()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }

    //тест создания задачи
    @Test
    void shouldCreateDataInTableAfterCreate() throws IOException, InterruptedException {

        KVServer kvServer = new KVServer();
        kvServer.start();

        HttpTaskManager httpTaskManager = Manager.getDefault();
        HttpTaskServer httpTaskServer = new HttpTaskServer(8080, httpTaskManager);
        httpTaskServer.start();

        httpTaskManager.createTask(new Task(1, "name13", "datails", Status.NEW, "07.12.22/01:00", 20));

        Assertions.assertEquals("name13", Tables.allTasks.get(1).getName());

    }

    //тест метода loadData() - метод выгружает данные на сервер KV
    @Test
    void shouldCreateDataInTableAfterLoad() throws IOException, InterruptedException {
        KVServer kvServer = new KVServer();
        kvServer.start();

        HttpTaskManager httpTaskManager = Manager.getDefault();
        HttpTaskServer httpTaskServer = new HttpTaskServer(8080, httpTaskManager);
        httpTaskServer.start();

        httpTaskManager.createTask(new Task(1, "name13", "datails", Status.NEW, "07.12.22/01:00", 20));
        Tables.allTasks.clear();
        httpTaskManager.loadData("13");

        Assertions.assertEquals("name13", Tables.allTasks.get(1).getName());

    }

    //тест создания эпика
    @Test
    void shouldCreateEpicInTableAfterCreate() throws IOException, InterruptedException {
        KVServer kvServer = new KVServer();
        kvServer.start();

        HttpTaskManager httpTaskManager = Manager.getDefault();
        HttpTaskServer httpTaskServer = new HttpTaskServer(8080, httpTaskManager);
        httpTaskServer.start();

        httpTaskManager.createEpic(new Epic(1, "name13", "datails", Status.NEW, "07.12.22/01:00", 20));

        Assertions.assertEquals("name13", Tables.allEpics.get(1).getName());

    }

// тестирую весь функционал в комплексе
    @Test
    void returnTrueHistoryAfterManipulations() throws IOException, InterruptedException {
// запускаем серверы
        KVServer kvServer = new KVServer();
        kvServer.start();
        HttpTaskManager httpTaskManager = Manager.getDefault();
        HttpTaskServer httpTaskServer = new HttpTaskServer(8080, httpTaskManager);
        httpTaskServer.start();
//создаю задачу
        createTask("http://localhost:8080/tasks/task/"
                , "task", "4, TASK, задача 3, NEW, описание, 07.12.22/01:00, 20");
        createTask("http://localhost:8080/tasks/task/"
                , "task", "4, TASK, задача 3, NEW, описание, 01.12.22/09:20, 20");
//создаю эпики
        createTask("http://localhost:8080/tasks/epic/"
                , "epic", "4, EPIC, задача 3, NEW, описание, 08.12.22/04:00, 30");

        createTask("http://localhost:8080/tasks/epic/"
                , "epic", "4, EPIC, задача 3, NEW, описание, 13.12.22/11:00, 40");

//создаю сабтаски
        createTask("http://localhost:8080/tasks/subtask/"
                , "subtask", "4, SUBTASK, задача 3, NEW, описание, 11.12.22/17:00, 20, 3");

        createTask("http://localhost:8080/tasks/subtask/"
                , "subtask", "4, SUBTASK, задача 3, NEW, описание, 18.12.22/15:00, 15, 3");
//вызываю список тасков
        getAllTasks("http://localhost:8080/tasks/task/");
//вызываю список эпиков
        getAllTasks("http://localhost:8080/tasks/epic/");
//вызываю список сабтасков
        getAllTasks("http://localhost:8080/tasks/subtask/");

        Assertions.assertEquals(6, Tables.allTasks.size() + Tables.allEpics.size() + Tables.allSubTusk.size());

        //вызваю задачи по ID
        getTaskByID("http://localhost:8080/tasks/task/?id=1");

        getTaskByID("http://localhost:8080/tasks/epic/?id=3");

        getTaskByID("http://localhost:8080/tasks/task/?id=2");

        getTaskByID("http://localhost:8080/tasks/subtask/?id=5");

        Assertions.assertEquals(4, Tables.tasksHis.size());

        deleteTaskByID("http://localhost:8080/tasks/epic/?id=3");

        Assertions.assertEquals(1, Tables.allEpics.size());
        Assertions.assertEquals(2, Tables.tasksHis.size());

        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks/history/");
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        Assertions.assertEquals(2, Tables.tasksHis.size());
        Assertions.assertEquals(200, response.statusCode());

        HttpClient httpClient1 = HttpClient.newHttpClient();
        URI uri1 = URI.create("http://localhost:8080/tasks/");
        HttpRequest httpRequest1 = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response1 = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        response1.body();

        Assertions.assertEquals(200, response1.statusCode());


    }

}

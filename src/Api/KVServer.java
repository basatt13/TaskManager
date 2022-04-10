package Api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import controller.FileBackedTaskManager;
import data.Tables;
import tasks.Epic;
import tasks.SubTask;
import tasks.Tasks;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KVServer extends Tables{
    public static final int PORT = 8079;
    private final String API_KEY;

    public HttpServer getServer() {
        return server;
    }

    private HttpServer server;

    public Map<String, String> getData() {
        return data;
    }

    public String getAPI_KEY() {
        return API_KEY;
    }

    private Map<String, String> data = new HashMap<>();


    public KVServer() throws IOException, InterruptedException {
        API_KEY = generateApiKey();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/register", (h) -> {
            try {
                System.out.println("\n/register");
                switch (h.getRequestMethod()) {
                    case "GET":
                        sendText(h, API_KEY);
                        break;
                    default:
                        System.out.println("/register ждёт GET-запрос, а получил " + h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                }
            } finally {
                h.close();
            }
        });
        server.createContext("/save", (h) -> {
            try {
                System.out.println("\n/save");
                if (!hasAuth(h)) {
                    System.out.println("Запрос неавторизован, нужен параметр в query API_KEY со значением апи-ключа");
                    h.sendResponseHeaders(403, 0);
                    return;
                }
                switch (h.getRequestMethod()) {
                    case "POST":
                        String key = h.getRequestURI().getPath().substring("/save/".length());
                        if (key.isEmpty()) {
                            System.out.println("Key для сохранения пустой. key указывается в пути: /save/{key}");
                            h.sendResponseHeaders(400, 0);
                            return;
                        }
                        String value = readText(h);
                        if (value.isEmpty()) {
                            System.out.println("Value для сохранения пустой. value указывается в теле запроса");
                            h.sendResponseHeaders(400, 0);
                            return;
                        }
                        data.put(key, value);
                        System.out.println(data.get("13"));
                        System.out.println("Значение для ключа " + key + " успешно обновлено!");
                        h.sendResponseHeaders(200, 0);
                        break;
                    default:
                        System.out.println("/save ждёт POST-запрос, а получил: " + h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                }
            } finally {
                h.close();
            }
        });
        server.createContext("/load", (h) -> {
            // TODO Добавьте получение значения по ключу
            try {
                System.out.println("\n/load");
                if (!hasAuth(h)) {
                    System.out.println("Запрос неавторизован, нужен параметр в query API_KEY со значением апи-ключа");
                    h.sendResponseHeaders(403, 0);
                    return;
                }
                switch (h.getRequestMethod()) {
                    case "GET":
                        String key = h.getRequestURI().getPath().substring("/load/".length());
                        if (key.isEmpty()) {
                            System.out.println("Key для сохранения пустой. key указывается в пути: /load/{key}");
                            h.sendResponseHeaders(400, 0);
                            return;
                        }
                        String result = data.get(key);
                        List<String> fromFiledata = new ArrayList<>();
                        for (int i = 1; i < result.split("\n").length; ++i) {
                            fromFiledata.add(result.split("\n")[i]);
                        }

                        for (int i = 0; i < fromFiledata.size(); ++i) {
                            if (!fromFiledata.get(i).isEmpty()) {
                                if (fromFiledata.get(i).split(", ")[1].equals(String.valueOf(Tasks.TASK))) {
                                    Tables.allTasks.put(FileBackedTaskManager.fromString(fromFiledata.get(i))
                                            .getID(), FileBackedTaskManager.fromString(fromFiledata.get(i)));
                                    Tables.forGenerateID.add(FileBackedTaskManager
                                            .fromString(fromFiledata.get(i)).getID());
                                } else if (fromFiledata.get(i).split(", ")[1].equals(String.valueOf(Tasks.EPIC))) {
                                    Tables.allEpics.put(FileBackedTaskManager
                                                    .fromString(fromFiledata.get(i)).getID()
                                            , (Epic) FileBackedTaskManager.fromString(fromFiledata.get(i)));
                                    Tables.forGenerateID.add(FileBackedTaskManager
                                            .fromString(fromFiledata.get(i)).getID());
                                } else if (fromFiledata.get(i).split(", ")
                                        [1].equals(String.valueOf(Tasks.SUBTASK))) {
                                    Tables.allSubTusk.put(FileBackedTaskManager
                                                    .fromString(fromFiledata.get(i)).getID()
                                            , (SubTask) FileBackedTaskManager.fromString(fromFiledata.get(i)));
                                    Tables.forGenerateID.add(FileBackedTaskManager
                                            .fromString(fromFiledata.get(i)).getID());
                                    Tables.allEpics.get(Integer.parseInt(fromFiledata.get(i).split(", ")[8]))
                                            .getSubtasks().add(FileBackedTaskManager
                                                    .fromString(fromFiledata.get(i)).getID());
                                    Tables.allSubTusk.get(FileBackedTaskManager.fromString(fromFiledata.get(i)).getID())
                                            .setEpic(Integer.parseInt(fromFiledata.get(i).split(", ")[8]));
                                } else {
                                    String[] b = fromFiledata.get(i).split(", ");
                                    for (String j : b) {
                                        if (Tables.allTasks.containsKey(Integer.parseInt(j))) {
                                            Tables.taskHistory.add(Tables.allTasks.get(Integer.parseInt(j)));
                                        } else if (Tables.allEpics.containsKey(Integer.parseInt(j))) {
                                            Tables.taskHistory.add(Tables.allEpics.get(Integer.parseInt(j)));
                                        } else if (Tables.allSubTusk.containsKey(Integer.parseInt(j))) {
                                            Tables.taskHistory.add(Tables.allSubTusk.get(Integer.parseInt(j)));
                                        }
                                    }
                                }
                            }
                        }
                        System.out.println("Значение для ключа " + key + " успешно обновлено!");
                        h.sendResponseHeaders(200, 0);
                        break;
                    default:
                        System.out.println("/load ждёт GET-запрос, а получил: " + h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                }
            } finally {
                h.close();
            }
        });
    }

    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
        System.out.println("API_KEY: " + API_KEY);
        server.start();
    }

    private String generateApiKey() {
        return "" + System.currentTimeMillis();
    }

    protected boolean hasAuth(HttpExchange h) {
        String rawQuery = h.getRequestURI().getRawQuery();
        return rawQuery != null && (rawQuery.contains("API_KEY=" + API_KEY) || rawQuery.contains("API_KEY=DEBUG"));
    }

    protected String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), "UTF-8");
    }

    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes("UTF-8");
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }

    void stop(){

    }

}

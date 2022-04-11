package api;

import data.Tables;
import tasks.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class KVTaskClient extends Tables {
    final protected String url;
    String ApiKey;

    public KVTaskClient(String url) throws IOException, InterruptedException {
        this.url = url;
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create(url + "/register");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url1)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ApiKey = response.body();
    }

    void put(String key, String json) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create(url + "/save/" + key + "?API_KEY=" + ApiKey);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url1)
                .POST(body)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        response.body();
        response.statusCode();
    }

    String save() {
        StringBuilder stringBuilder = new StringBuilder();
        String header = "id,type,name,status,description,startTime, duration, endTime, epic";
        stringBuilder.append(header);

        for (int i : Tables.allTasks.keySet()) {
            stringBuilder.append("\n" + Tables.allTasks.get(i).toString());
        }
        for (int i : Tables.allEpics.keySet()) {
            stringBuilder.append("\n" + Tables.allEpics.get(i).toString());

        }

        for (int i : Tables.allSubTusk.keySet()) {
            stringBuilder.append("\n" + Tables.allSubTusk.get(i).toString());

        }

        stringBuilder.append("\n");

        for(int i=0; i < Tables.tasksHis.size(); i++){
            if(Tables.taskHistory.contains(Tables.tasksHis.get(i).data)) {
                Tables.taskHistory.remove(Tables.tasksHis.get(i).data);
                Tables.taskHistory.add(Tables.tasksHis.get(i).data);
            }else {
                Tables.taskHistory.add(Tables.tasksHis.get(i).data);
            }
        }

        for (NODE node : Tables.tasksHis) {
            stringBuilder.append(node.data.getID() + ", ");
        }
        String file = stringBuilder.toString();
        return file;
    }

    //Метод String load(String key) должен возвращать состояние менеджера задач через запрос GET /load/<ключ>?API_KEY=.
   String load(String key) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url1 = URI.create(url + "/load/" + key + "?API_KEY=" + ApiKey);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url1)
                .GET()
                .build();
       HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
       return response.body();

    }
}

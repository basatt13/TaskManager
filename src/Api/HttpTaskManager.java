package Api;

import controller.FileBackedTaskManager;
import java.io.IOException;

public class HttpTaskManager extends FileBackedTaskManager {
    KVTaskClient kvTaskClient;
    public HttpTaskManager(String file) throws IOException, InterruptedException {
        super(file);
        kvTaskClient = new KVTaskClient(file);

    }

     public void loadData(String key) throws IOException, InterruptedException {
    kvTaskClient.load(key);
}
    @Override
    public void save() throws IOException, InterruptedException {
        kvTaskClient.put("13", kvTaskClient.save());
    }
}

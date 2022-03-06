package data;
import tasks.Epic;
import tasks.NODE;
import tasks.SubTask;
import tasks.Task;
import java.util.*;

public class Tables {

    protected static HashMap<Integer, Task> allTasks = new HashMap<>();
    protected static HashMap<Integer, Epic> allEpics = new HashMap<>();
    protected static HashMap<Integer, SubTask> allSubTusk = new HashMap<>();
    protected static List<Integer> forGenerateID = new ArrayList<>();

    protected static List<NODE> tasksHis = new LinkedList<>();
    protected static Map<Integer,Integer> deleteData = new HashMap<>();
    protected static ArrayList<Task> taskHistory = new ArrayList<>();


}

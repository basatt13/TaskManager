package controller;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private List<NODE> tasksHis = new LinkedList<>();
    private Map<Integer,Integer> deleteData = new HashMap<>();
    private ArrayList<Task> taskHistory = new ArrayList<>();

   public List<NODE> getTasksHis() {
        return tasksHis;
    }

    public Map<Integer, Integer> getDeleteData() {
        return deleteData;
    }

    public ArrayList<Task> getTaskHistory() {
        return taskHistory;
    }

    @Override
    public void add(Task task) {
        if(tasksHis.isEmpty()){
            NODE node = new NODE(task,null,null);
            tasksHis.add(node);
            deleteData.put(node.data.getID(), 0);
        } else {
            linkLast(task);
        }
    }

    @Override
    public void remove(int id) {
    }

    @Override
    public List<Task> getHistory() {
        for(int i=0; i < tasksHis.size(); i++){
            taskHistory.add(tasksHis.get(i).data);
            System.out.println(tasksHis.get(i).data);
        }
        return taskHistory;
    }

     void linkLast(Task task){
        NODE node = new NODE(task, null,tasksHis.get(tasksHis.size()-1).data);
        if(deleteData.containsKey(task.getID())){
            removeNode(node);
            if(tasksHis.isEmpty()) {
                NODE node1 = new NODE(task, null, null);
                tasksHis.add(node1);
                deleteData.put(task.getID(), 0);
            }else  if(tasksHis.size()==1){
                tasksHis.add(node);
                tasksHis.set(1, new NODE(task, null,tasksHis.get(1).data));
                tasksHis.set(0, new NODE(tasksHis.get(0).data,task,
                        null));
            } else {
                tasksHis.add(node);
                tasksHis.set(tasksHis.size()-1, new NODE(task, null,tasksHis.get(tasksHis.size()-2).data));
                tasksHis.set(tasksHis.size()-2, new NODE(tasksHis.get(tasksHis.size()-2).data,task,
                        tasksHis.get(tasksHis.size()-3).data));

            }
            for (int i = 0; i <tasksHis.size(); i++){
                deleteData.put(tasksHis.get(i).data.getID(), i);
            }

        }else {
            tasksHis.add(node);
            if(tasksHis.size()==2){
                tasksHis.set(1, new NODE(tasksHis.get(1).data,
                        null,tasksHis.get(0).data));
                tasksHis.set(0, new NODE(tasksHis.get(0).data,node.data,
                        null));
            } else {
                tasksHis.set(tasksHis.size()-1, new NODE(node.data,null,
                        tasksHis.get(tasksHis.size()-2).data));
                tasksHis.set(tasksHis.size()-2, new NODE(tasksHis.get(tasksHis.size()-2).data,node.data,
                        tasksHis.get(tasksHis.size()-3).data));
            }
            for (int i = 0; i <tasksHis.size(); i++){
                deleteData.put(tasksHis.get(i).data.getID(), i);
            }
        }
    }

    void removeNode(NODE node){
        int i = deleteData.get(node.data.getID());
        if(tasksHis.size()==1) {
            tasksHis.remove(i);
            addDeleteData();
        } else if(tasksHis.size()==2){
            tasksHis.remove(i);
            tasksHis.set(0,new NODE(tasksHis.get(0).data,null,null));
            addDeleteData();
        } else if(tasksHis.size()==3) {
            tasksHis.remove(i);
            tasksHis.set(1,new NODE(tasksHis.get(1).data,null,tasksHis.get(0).data));
            tasksHis.set(0,new NODE(tasksHis.get(0).data,tasksHis.get(1).data,null));
            addDeleteData();
        } else {
            if(i<=1) {
                tasksHis.remove(i);
                tasksHis.set(1, new NODE(tasksHis.get(1).data, tasksHis.get(2).data, tasksHis.get(0).data));
                tasksHis.set(0, new NODE(tasksHis.get(0).data, tasksHis.get(1).data, null));
                addDeleteData();
            }else{
                if(i<=tasksHis.size()-2){
                    tasksHis.remove(i);
                    tasksHis.set(i - 1, new NODE(tasksHis.get(i - 1).data, tasksHis.get(i).data, tasksHis.get(i-2).data));
                    tasksHis.set(i,new NODE(tasksHis.get(i).data,null,tasksHis.get(i - 1).data));
                    addDeleteData();
                } else if(i==tasksHis.size()-1){
                    tasksHis.remove(i);
                    tasksHis.set(i-1, new NODE(tasksHis.get(i-1).data, tasksHis.get(i-2).data, null));
                    tasksHis.set(i-2,new NODE(tasksHis.get(i-2).data,tasksHis.get(i-1).data,tasksHis.get(i - 3).data));
                    addDeleteData();
                }
            }
        }
    }

     void addDeleteData(){
        deleteData.clear();
        for (int k = 0; k <tasksHis.size(); k++){
            deleteData.put(tasksHis.get(k).data.getID(), k);
        }
    }


}

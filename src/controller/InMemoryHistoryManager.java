package controller;
import data.Tables;
import tasks.NODE;
import tasks.Task;
import java.util.*;

public class InMemoryHistoryManager extends Tables implements HistoryManager {


    @Override
    public void add(Task task) {
        if(Tables.tasksHis.isEmpty()){
            NODE node = new NODE(task,null,null);
            Tables.tasksHis.add(node);
            Tables.deleteData.put(node.data.getID(), 0);
            getHistory();
        } else {
            linkLast(task);
            getHistory();
        }
    }


    @Override
    public List<Task> getHistory() {
        for(int i=0; i < Tables.tasksHis.size(); i++){
            if(Tables.taskHistory.contains(Tables.tasksHis.get(i).data)) {
                Tables.taskHistory.remove(Tables.tasksHis.get(i).data);
                Tables.taskHistory.add(Tables.tasksHis.get(i).data);
            }else {
                Tables.taskHistory.add(Tables.tasksHis.get(i).data);
            }
        }
        return Tables.taskHistory;
    }


     void linkLast(Task task){
        NODE node = new NODE(task, null,Tables.tasksHis.get(Tables.tasksHis.size()-1).data);
        if( Tables.deleteData.containsKey(task.getID())){
            removeNode(node);
            if(Tables.tasksHis.isEmpty()) {
                NODE node1 = new NODE(task, null, null);
                Tables.tasksHis.add(node1);
                 Tables.deleteData.put(task.getID(), 0);
            }else  if(Tables.tasksHis.size()==1){
                 Tables.tasksHis.add(node);
                 Tables.tasksHis.set(1, new NODE(task, null, Tables.tasksHis.get(1).data));
                 Tables.tasksHis.set(0, new NODE( Tables.tasksHis.get(0).data,task,
                        null));
            } else {
                 Tables.tasksHis.add(node);
                 Tables.tasksHis.set( Tables.tasksHis.size()-1,
                         new NODE(task, null, Tables.tasksHis.get( Tables.tasksHis.size()-2).data));
                 Tables.tasksHis.set( Tables.tasksHis.size()-2,
                         new NODE( Tables.tasksHis.get( Tables.tasksHis.size()-2).data,task,
                         Tables.tasksHis.get( Tables.tasksHis.size()-3).data));

            }
            for (int i = 0; i < Tables.tasksHis.size(); i++){
                 Tables.deleteData.put( Tables.tasksHis.get(i).data.getID(), i);
            }

        }else {
             Tables.tasksHis.add(node);
            if( Tables.tasksHis.size()==2){
                 Tables.tasksHis.set(1, new NODE( Tables.tasksHis.get(1).data,
                        null, Tables.tasksHis.get(0).data));
                 Tables.tasksHis.set(0, new NODE( Tables.tasksHis.get(0).data,node.data,
                        null));
            } else {
                 Tables.tasksHis.set( Tables.tasksHis.size()-1, new NODE(node.data,null,
                         Tables.tasksHis.get( Tables.tasksHis.size()-2).data));
                 Tables.tasksHis.set(tasksHis.size()-2, new NODE( Tables.tasksHis.get( Tables.tasksHis.size()-2).data
                         ,node.data,
                         Tables.tasksHis.get( Tables.tasksHis.size()-3).data));
            }
            for (int i = 0; i <tasksHis.size(); i++){
                 Tables.deleteData.put( Tables.tasksHis.get(i).data.getID(), i);
            }
        }
    }

    @Override
    public void removeNode(NODE node){
        int i = Tables.deleteData.get(node.data.getID());
        if(Tables.tasksHis.size()==1) {
            Tables.tasksHis.remove(i);
            addDeleteData();
        } else if(Tables.tasksHis.size()==2){
            Tables.tasksHis.remove(i);
            Tables.tasksHis.set(0,new NODE(Tables.tasksHis.get(0).data,null,null));
            addDeleteData();
        } else if(Tables.tasksHis.size()==3) {
            Tables.tasksHis.remove(i);
            Tables.tasksHis.set(1,new NODE(Tables.tasksHis.get(1).data,null,Tables.tasksHis.get(0).data));
            Tables.tasksHis.set(0,new NODE(Tables.tasksHis.get(0).data,Tables.tasksHis.get(1).data,null));
            addDeleteData();
        } else {
            if(i<=1) {
                Tables.tasksHis.remove(i);
                Tables.tasksHis.set(1, new NODE(Tables.tasksHis.get(1).data, Tables.tasksHis.get(2).data
                        , Tables.tasksHis.get(0).data));
                Tables.tasksHis.set(0, new NODE(Tables.tasksHis.get(0).data, Tables.tasksHis.get(1).data, null));
                addDeleteData();
            }else{
                if(i<=Tables.tasksHis.size()-2){
                    Tables.tasksHis.remove(i);
                    Tables.tasksHis.set(i - 1, new NODE(Tables.tasksHis.get(i - 1).data, Tables.tasksHis.get(i).data
                            , Tables.tasksHis.get(i-2).data));
                    Tables.tasksHis.set(i,new NODE(Tables.tasksHis.get(i).data,Tables.tasksHis.get(i + 1).data
                            ,Tables.tasksHis.get(i - 1).data));
                    addDeleteData();
                } else if(i==tasksHis.size()-1){
                    Tables.tasksHis.remove(i);
                    Tables.tasksHis.set(i-1, new NODE(Tables.tasksHis.get(i-1).data,null
                            , Tables.tasksHis.get(i-2).data));
                    Tables.tasksHis.set(i-2,new NODE(Tables.tasksHis.get(i-2).data,Tables.tasksHis.get(i-1).data
                            ,Tables.tasksHis.get(i - 3).data));
                    addDeleteData();
                }
            }
        }
    }

     void addDeleteData(){
         Tables.deleteData.clear();
        for (int k = 0; k <Tables.tasksHis.size(); k++){
            Tables.deleteData.put(Tables.tasksHis.get(k).data.getID(), k);
        }
    }


}

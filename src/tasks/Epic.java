package tasks;
import controller.Status;

import java.util.ArrayList;

    public class Epic extends Task {
        protected ArrayList<Integer> subtasks = new ArrayList<>();

    public Epic(int ID, String name, String details, Status status) {
        super(ID, name, details, status);
    }

    public ArrayList<Integer> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<Integer> subtasks) {
        this.subtasks = subtasks;
    }

        @Override
        public String toString() {

        return getID() +","+ Tasks.EPIC +","+ getName()+","+ getStatus() + ","+getDetails();
        }
}




package tasks;
import controller.Status;
import java.util.HashMap;


    public class SubTask extends Task {
        protected int epic;

        public SubTask(int ID, String name, String details, Status status) {
            super(ID, name, details, status);
    }

    public int getEpic() {
        return epic;
    }

    public void setEpic(int epic) {
        this.epic = epic;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epic=" + epic +
                "} " + super.toString();
    }
}






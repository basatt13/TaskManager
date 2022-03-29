package tasks;
import controller.Status;

import java.util.Optional;


public class SubTask extends Task {
        protected int epic;

    public SubTask(int ID, String name, String details, Status status, String startsTime, long duration) {
        super(ID, name, details, status,startsTime,duration);
    }


    public int getEpic() {
        return epic;
    }

    public void setEpic(int epic) {
        this.epic = epic;
    }

    @Override
    public String toString() {
        return getID() +", "+ Tasks.SUBTASK +", "+getName()+", "+ getStatus() + ", "+getDetails()
                +  ", " + getStartTime() + ", " + getDuration() + ", " + getEndTime() + ", " + epic;
    }
}






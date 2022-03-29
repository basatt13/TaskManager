package tasks;
import controller.Status;
import data.Tables;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Epic extends Task {
    protected ArrayList<Integer> subtasks = new ArrayList<>();

    public Epic(int ID, String name, String details, Status status,String startsTime, long duration) {
        super(ID, name, details, status,startsTime,duration);
    }

    public ArrayList<Integer> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<Integer> subtasks) {
        this.subtasks = subtasks;
    }

    @Override
    public String getEndTime() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yy/HH:mm");
        LocalDateTime startTime1 = LocalDateTime.MIN;
        if (!getSubtasks().isEmpty()) {
            for (Integer sub : getSubtasks()) {
                String startTimeS = Tables.allSubTusk.get(sub).getEndTime();
                LocalDateTime localDateTime = LocalDateTime.parse(startTimeS, format);
                if (startTime1.isBefore(localDateTime) || startTime1.equals(localDateTime)) {
                    startTime1 = localDateTime;
                }
            }
            return startTime1.format(format);

        }else {
            return null;
        }
    }

    @Override
    public long getDuration() {
        long duration = 0;
        if (!getSubtasks().isEmpty()) {
            for (Integer sub : getSubtasks()) {
                duration += Tables.allSubTusk.get(sub).getDuration();
            }
            return duration;
        }else {
            return 0;
        }

    }

    @Override
    public String getStartTime() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yy/HH:mm");
        LocalDateTime startTime = LocalDateTime.MAX;
        if (!getSubtasks().isEmpty()) {
            for (Integer sub : getSubtasks()) {
                String startTimeS = Tables.allSubTusk.get(sub).getStartTime();
                LocalDateTime localDateTime = LocalDateTime.parse(startTimeS, format);
                if (startTime.isAfter(localDateTime) || startTime.equals(localDateTime)) {
                    startTime = localDateTime;
                }
            }
            return startTime.format(format);
        }else {
            return LocalDateTime.MAX.format(format);
        }
    }

    @Override
    public String toString() {
        return getID() +", "+ Tasks.EPIC +", "+getName()+", "+ getStatus() + ", "+getDetails()
                +  ", " + getStartTime() + ", " + getDuration() + ", " + getEndTime();
    }
}




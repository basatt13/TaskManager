package tasks;
import controller.InMemoryTasksManager;
import controller.Status;
import data.Tables;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;
import java.util.Optional;


public class Task extends Tables {
    int ID;
    private String name;
    private String details;
    private Status status;
    private long duration;
    private String startTime;


    public Task(int ID, String name, String details, Status status, String startTime, long duration) {
        this.ID = ID;
        this.name = name;
        this.details = details;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }


    @Override
    public String toString() {

        return ID + ", " + Tasks.TASK + ", " + getName() + ", " + getStatus() + ", " + getDetails() + ", " + getStartTime()
                + ", " + getDuration() + ", " + getEndTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return getID() == task.getID() && Objects.equals(getName(), task.getName()) &&
                Objects.equals(getDetails(), task.getDetails()) &&
                Objects.equals(getStatus(), task.getStatus());
    }

    @Override
    public int hashCode() {
        Objects.hash(getID(), getName(), getDetails());
        int result = Objects.hash(getID(), getName(), getDetails());
        result = result * 31;
        return result;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public Status getStatus() {
        return status;
    }

    public String getStartTime() {
            if(startTime == null){
                return LocalDateTime.MAX.format(DateTimeFormatter.ofPattern("dd.MM.yy/HH:mm"));
            }else {
                return startTime;
            }
    }

    public long getDuration() {
        return duration;
    }

    public String getEndTime() {
        String endTime;
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yy/HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(getStartTime(), format);
                return endTime = startTime.plusMinutes(getDuration()).format(format);
            }

    public boolean validateTime() {
        boolean check = true;
        InMemoryTasksManager manager = new InMemoryTasksManager();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yy/HH:mm");
        LocalDateTime checkTime = LocalDateTime.parse(getStartTime(), format);
        if (checkTime != LocalDateTime.MAX) {
            LocalDateTime endTimer = checkTime.plusMinutes(getDuration());
            for (Task task : manager.getPrioritizedTasks()) {
                if (task.getStartTime() == null) {
                    return check;
                } else {
                    LocalDateTime taskTime = LocalDateTime.parse(task.getStartTime(), format);
                    LocalDateTime taskEndTime = taskTime.plusMinutes(task.getDuration());
                    if (checkTime.equals(taskTime) || checkTime.equals(taskEndTime)) {
                        check = false;
                        break;
                    } else if (checkTime.isAfter(taskTime) && checkTime.isBefore(taskEndTime)) {
                        check = false;
                        break;
                    } else if (endTimer.equals(taskTime) || endTimer.equals(taskEndTime)) {
                        check = false;
                        break;
                    } else if (endTimer.isAfter(taskTime) && endTimer.isBefore(taskEndTime)) {
                        check = false;
                        break;
                    }
                }
            }
        }
        return check;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}



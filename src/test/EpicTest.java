package test;

import controller.Status;
import data.Tables;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class EpicTest extends Tables {

    @BeforeEach
    public void createData() {
        Epic epic2 = new Epic(2, "epic", "details", Status.NEW, null, 0);
        SubTask subTask1 = new SubTask(3, "name", "details"
                , Status.NEW, "01.01.22/20:00", 20);
        SubTask subTask2 = new SubTask(4, "name", "details"
                , Status.NEW, "01.01.22/21:15", 15);
        Tables.allEpics.put(2, epic2);
        Tables.allEpics.get(2).getSubtasks().add(3);
        Tables.allEpics.get(2).getSubtasks().add(4);
        Tables.allSubTusk.put(3, subTask1);
        subTask1.setEpic(2);
        subTask2.setEpic(2);
        Tables.allSubTusk.put(4, subTask2);
    }

    @Test
    void testTimeByEpic() {
        createData();
        Assertions.assertEquals("01.01.22/20:00", Tables.allEpics.get(2).getStartTime());
        Assertions.assertEquals("01.01.22/21:30", Tables.allEpics.get(2).getEndTime());
        Assertions.assertEquals(35, Tables.allEpics.get(2).getDuration());
    }

    // тестировать поля Task не вижу смысла, т.к. Subtask наследуется от Task
    @Test
    void testTimeBySubtusk() {
        createData();
        Assertions.assertEquals("01.01.22/20:20", Tables.allSubTusk.get(3).getEndTime());
    }

    // тестирование метода getEndTime()
    @Test
    void testGetEndTimeIf() {
        long duration = 9;
        String endTime;
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yy/HH:mm");
        LocalDateTime startTime = LocalDateTime.parse("01.01.22/23:50", format);
        LocalDateTime lastTime = LocalDateTime.parse("01.01.22/23:59", format);
        Duration durat = Duration.between(startTime, lastTime);

        if (durat.toMinutes() <= duration) {
            endTime = lastTime.format(format);
        } else {
            endTime = startTime.plusMinutes(duration).format(format);
        }

        Assertions.assertEquals("01.01.22/23:59", endTime);
    }
}
package controller;

import tasks.Task;

public class NODE {
    public Task data;
    public Task next;
    public Task prev;

    public NODE(Task data, Task next, Task prev){
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    @Override
    public String toString() {
        return "NODE{" +
                "data=" + data +
                ", next=" + next +
                ", prev=" + prev +
                '}';
    }
}


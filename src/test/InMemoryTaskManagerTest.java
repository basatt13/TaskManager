package test;

import controller.InMemoryTasksManager;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTasksManager>{

    public InMemoryTaskManagerTest() {
        super(new InMemoryTasksManager());
    }
}

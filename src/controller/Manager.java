package controller;

import Api.HttpTaskManager;

import java.io.IOException;

public class Manager {
    public static HttpTaskManager getDefault() throws IOException, InterruptedException
    {return new HttpTaskManager("http://localhost:8079");}
    }


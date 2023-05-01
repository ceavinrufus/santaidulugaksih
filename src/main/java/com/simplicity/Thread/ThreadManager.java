package com.simplicity.Thread;

import java.util.*;

import com.simplicity.Interface.Leaveable;

public class ThreadManager {
    private static ThreadManager instance = new ThreadManager();
    private List<Leaveable> threadList = new ArrayList<>();

    private ThreadManager() {
    }

    public static ThreadManager getInstance() {
        return instance;
    }

    public void addThread(Leaveable thread) {
        threadList.add(thread);
    }

    public void removeThread(Leaveable thread) {
        threadList.remove(thread);
    }

    public void stopAllThreads() {
        for (Leaveable thread : threadList) {
            thread.stop();
        }
    }

    public void startAllThreads() {
        for (Leaveable thread : threadList) {
            thread.start();
        }
    }
}

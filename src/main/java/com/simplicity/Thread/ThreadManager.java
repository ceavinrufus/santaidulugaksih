package com.simplicity.Thread;

import java.util.*;

import com.simplicity.Interface.Leaveable;

public class ThreadManager {
    private static List<Leaveable> threadList = new ArrayList<>();

    public synchronized static List<Leaveable> getInstance() {
        return threadList;
    }

    public synchronized static void addThread(Leaveable thread) {
        threadList.add(thread);
    }

    public synchronized static void removeThread(Leaveable thread) {
        threadList.remove(thread);
    }

    public static void stopAllThreads() {
        for (Leaveable thread : threadList) {
            thread.stop();
        }
    }

    public static void startAllThreads() {
        for (Leaveable thread : threadList) {
            thread.start();
        }
    }
}

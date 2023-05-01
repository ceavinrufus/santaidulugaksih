package com.simplicity.Thread;

import java.util.concurrent.TimeUnit;

import com.simplicity.Interface.Leaveable;

public class ThreadBeliBarang implements Leaveable {
    private volatile boolean shouldRun = false;
    private int initialCountdown = 20;

    public ThreadBeliBarang() {
    }

    @Override
    public void run() {
        while (initialCountdown != 0) {
            try {
                if (shouldRun) {
                    initialCountdown -= 1;
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        shouldRun = false;
    }

    public void start() {
        shouldRun = true;
    }

}

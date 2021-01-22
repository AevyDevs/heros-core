package net.herospvp.heroscore.objects;

import lombok.Getter;
import net.herospvp.heroscore.HerosCore;

import java.util.LinkedList;
import java.util.Queue;

@SuppressWarnings("BusyWait")
public class GreenThread extends Thread {

    private final HerosCore instance;
    @Getter
    private boolean running;
    private final int executionDelay;
    private final Queue<Runnable> runnableQueue;

    public GreenThread(HerosCore instance) {
        this.instance = instance;
        this.running = true;
        this.executionDelay = 1000;
        this.runnableQueue = new LinkedList<>();
        instance.getThreadsHandler().add(this);
        this.start();
    }

    public GreenThread(HerosCore instance, int executionDelay) {
        this.instance = instance;
        this.running = true;
        this.executionDelay = executionDelay;
        this.runnableQueue = new LinkedList<>();
        instance.getThreadsHandler().add(this);
        this.start();
    }

    public void update(Runnable runnable) {
        runnableQueue.add(runnable);
    }

    public void kill() {
        running = false;
    }

    @Override
    public void run() {
        try {
           while (running) {

               for (Runnable runnable : runnableQueue) {
                   runnable.run();
               }

               Thread.sleep(executionDelay);
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

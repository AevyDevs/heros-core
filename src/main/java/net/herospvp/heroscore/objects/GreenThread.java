package net.herospvp.heroscore.objects;

import lombok.Getter;
import net.herospvp.heroscore.HerosCore;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@SuppressWarnings("BusyWait")
public class GreenThread extends Thread {

    @Getter
    private boolean running;
    private final int executionDelay;
    private BlockingQueue<Runnable> runnableQueue;

    public GreenThread(HerosCore instance) {
        this.executionDelay = 250;
        commonInit(instance);
    }

    public GreenThread(HerosCore instance, int executionDelay) {
        this.executionDelay = executionDelay;
        commonInit(instance);
    }

    private void commonInit(HerosCore instance) {
        this.running = true;
        this.runnableQueue = new LinkedBlockingQueue<>();
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

               runnableQueue.forEach(Runnable::run);

               Thread.sleep(executionDelay);
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

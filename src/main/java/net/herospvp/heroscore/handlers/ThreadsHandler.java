package net.herospvp.heroscore.handlers;

import net.herospvp.heroscore.objects.GreenThread;

import java.util.ArrayList;
import java.util.Collection;

public class ThreadsHandler {

    private final Collection<GreenThread> greenThreadCollection;

    public ThreadsHandler() {
        this.greenThreadCollection = new ArrayList<>();
    }

    public void add(GreenThread greenThread) {
        greenThreadCollection.add(greenThread);
    }

    public void remove(GreenThread greenThread) {
        greenThreadCollection.remove(greenThread);
    }

    public void exterminate() {
        for (GreenThread thread : greenThreadCollection) {
            thread.kill();
        }
    }

    public int size() {
        return greenThreadCollection.size();
    }

}

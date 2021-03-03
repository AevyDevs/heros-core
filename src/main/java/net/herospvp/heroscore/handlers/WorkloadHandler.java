package net.herospvp.heroscore.handlers;

import com.google.common.collect.Lists;
import net.herospvp.heroscore.utils.workload.Workload;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class WorkloadHandler {

    private final List<Workload> workloads;
    private final JavaPlugin plugin;

    public WorkloadHandler(JavaPlugin plugin) {
        this.plugin = plugin;
        this.workloads = Lists.newArrayList();
    }

    /**
     * @param tickTime calculate with (long) (MathHelper.a(MinecraftServer.getServer().h) * 1.0E-6D);
     */
    public void start(long tickTime) {
        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            if (workloads.isEmpty()) {
                return;
            }

            long executeTime = 50 - tickTime;
            if (executeTime < 10)
                executeTime = 10;

            long stopTime = System.currentTimeMillis() + executeTime;
            while (System.currentTimeMillis() < stopTime && !workloads.isEmpty()) {
                Workload workload = workloads.get(0);
                if (workload == null) continue;

                if (workload.execute(stopTime)) {
                    workload.getCallback().run();
                    workloads.remove(workload);
                }
            }
        }, 1, 1);
    }

    /**
     * This method adds a Workload to the List
     *
     * @param workload The Workload to add
     */
    public void addWorkload(Workload workload) {
        workloads.add(workload);
    }

}

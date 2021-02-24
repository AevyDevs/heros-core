package net.herospvp.heroscore.utils;

import net.herospvp.heroscore.HerosCore;

public class LoggerUtils {

    private final HerosCore herosCore;

    public LoggerUtils(HerosCore herosCore) {
        this.herosCore = herosCore;
    }

    public void failedLoadingDependency(String dependency) {
        herosCore.getLogger().severe("######################################");
        herosCore.getLogger().severe("COULD NOT LOAD heros-core");
        herosCore.getLogger().severe("A DEPENDENCY IS MISSING: " + dependency);
        herosCore.getLogger().severe("######################################");
    }

}

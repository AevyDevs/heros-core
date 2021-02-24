package net.herospvp.heroscore.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.util.UUID;

@AllArgsConstructor @Getter @Setter
public class HPlayer {

    private final UUID uuid;
    private int coins;

    private boolean edited;

    public void addCoins(int amount) {
        this.coins += amount;
        this.edited = true;
    }

    public void removeCoins(int amount) {
        this.coins -= amount;
        if (coins < 0) {
            this.coins = 0;
        }
        this.edited = true;
    }

    public void setCoins(int amount) {
        this.coins = amount;
        this.edited = true;
    }

    @Override
    public String toString() {
        return "HPlayer{name="+ Bukkit.getOfflinePlayer(uuid).getName()+", coins="+coins+"}";
    }

}

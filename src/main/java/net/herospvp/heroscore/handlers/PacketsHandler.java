package net.herospvp.heroscore.handlers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import net.herospvp.heroscore.HerosCore;

import java.util.TreeSet;

public class PacketsHandler {

    private final HerosCore instance;

    public PacketsHandler(HerosCore herosCore) {
        this.instance = herosCore;

        // disables TAB_COMPLETE
        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(instance, ListenerPriority.HIGHEST, PacketType.Play.Client.TAB_COMPLETE) {
                    @Override
                    public void onPacketReceiving(PacketEvent event) {
                        PacketContainer packetContainer = event.getPacket();
                        String message = packetContainer.getSpecificModifier(String.class).read(0);

                        if (!message.startsWith("/") || message.contains(" ")) {
                            return;
                        }

                        event.setCancelled(true);
                    }
                }
        );
    }

}
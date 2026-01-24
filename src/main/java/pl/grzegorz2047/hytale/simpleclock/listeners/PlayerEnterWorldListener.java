package pl.grzegorz2047.hytale.simpleclock.listeners;

import com.hypixel.hytale.event.EventRegistry;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.AddPlayerToWorldEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import pl.grzegorz2047.hytale.simpleclock.ClockManager;

public class PlayerEnterWorldListener {
    private final ClockManager clockManager;

    public PlayerEnterWorldListener(ClockManager clockManager) {
        this.clockManager = clockManager;
    }


    public void register(EventRegistry eventBus) {
        eventBus.registerGlobal(AddPlayerToWorldEvent.class, this::onPlayerAddedToWorld);

    }

    private void onPlayerAddedToWorld(AddPlayerToWorldEvent addPlayerToWorldEvent) {
        PlayerRef playerRef = addPlayerToWorldEvent.getHolder().getComponent(PlayerRef.getComponentType());
        if (playerRef == null) {
            return;
        }
        Player player = addPlayerToWorldEvent.getHolder().getComponent(Player.getComponentType());
        if (player == null) {
            return;
        }
        clockManager.addPlayerClock(playerRef, player);
    }
}

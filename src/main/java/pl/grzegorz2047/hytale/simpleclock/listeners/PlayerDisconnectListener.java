package pl.grzegorz2047.hytale.simpleclock.listeners;

import com.hypixel.hytale.event.EventRegistry;
import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
import pl.grzegorz2047.hytale.simpleclock.ClockManager;

public class PlayerDisconnectListener {
    private final ClockManager clockManager;

    public PlayerDisconnectListener(ClockManager clockManager) {
        this.clockManager = clockManager;
    }

    public void register(EventRegistry eventBus) {
        eventBus.registerGlobal(PlayerDisconnectEvent.class, this::onPlayerDisconnect);
    }

    private void onPlayerDisconnect(PlayerDisconnectEvent playerDisconnectEvent) {
        this.clockManager.removePlayerClock(playerDisconnectEvent.getPlayerRef());
    }
}

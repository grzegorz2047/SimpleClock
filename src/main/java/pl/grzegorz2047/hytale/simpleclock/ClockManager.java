package pl.grzegorz2047.hytale.simpleclock;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.hud.HudManager;
import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.Config;
import pl.grzegorz2047.hytale.simpleclock.config.MainConfig;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ClockManager {
    private final Map<PlayerRef, ClockUI> playersHud = new ConcurrentHashMap<>();
    private final Config<MainConfig> config;
    private ScheduledFuture<?> updaterTask;

    public ClockManager(Config<MainConfig> config) {
        this.config = config;
    }

    public void addPlayerClock(PlayerRef playerRef, Player player) {
        HudManager hudManager = player.getHudManager();
        int fontSize = this.config.get().getFontSize();
        ClockUI hud = new ClockUI(playerRef, fontSize);

        hudManager.setCustomHud(playerRef, hud);
        this.playersHud.put(playerRef, hud);
    }

    public void removePlayerClock(PlayerRef playerRef) {
        this.playersHud.remove(playerRef);
    }

    public void startClockScheduler() {
        if (this.updaterTask != null) {
            return;
        }
        this.updaterTask = HytaleServer.SCHEDULED_EXECUTOR.scheduleAtFixedRate(this::updatePlayersClock, 0L, 1L, TimeUnit.SECONDS);
    }

    private void updatePlayersClock() {
        String[] worldsWithClockEnabled = this.config.get().getWorldsWithClockEnabled();
        List<String> list = Arrays.asList(worldsWithClockEnabled);
        Map<String, World> worlds = Universe.get().getWorlds();
        worlds.forEach((worldName, world) -> {
            if (list.contains(worldName)) {
                for (PlayerRef playerRef : world.getPlayerRefs()) {
                    Ref<EntityStore> ref = playerRef.getReference();
                    if (ref == null || !ref.isValid()) {
                        continue;
                    }
                    Runnable updateTask = () -> {
                        ClockUI clockUI = this.playersHud.get(playerRef);
                        clockUI.setTime(readWorldTime(playerRef.getReference().getStore()));
                    };
                    if (world.isInThread()) {
                        updateTask.run();
                    } else {
                        world.execute(updateTask);
                    }
                }
            }
        });
    }

    private String readWorldTime(Store<EntityStore> store) {
        WorldTimeResource worldTimeRes = store.getResource(WorldTimeResource.getResourceType());
        LocalDateTime gameTime = worldTimeRes.getGameDateTime();
        if (gameTime == null) return "00:00";
        return DateTimeFormatter.ofPattern("HH:mm").format(gameTime.toLocalTime());
    }

    public void stop() {
        if (this.updaterTask == null) {
            return;
        }
        this.updaterTask.cancel(false);
        this.playersHud.clear();
    }
}

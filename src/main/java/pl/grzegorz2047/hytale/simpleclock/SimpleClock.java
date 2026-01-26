package pl.grzegorz2047.hytale.simpleclock;

import com.hypixel.hytale.event.EventRegistry;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.util.Config;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import pl.grzegorz2047.hytale.simpleclock.config.MainConfig;
import pl.grzegorz2047.hytale.simpleclock.listeners.PlayerDisconnectListener;
import pl.grzegorz2047.hytale.simpleclock.listeners.PlayerEnterWorldListener;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class SimpleClock extends JavaPlugin {

    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
    private ClockManager clockManager;
    private Config<MainConfig> config;

    public SimpleClock(@Nonnull JavaPluginInit init) {
        super(init);
        LOGGER.atInfo().log(this.getName() + " version " + this.getManifest().getVersion().toString());
    }

    @NullableDecl
    @Override
    public CompletableFuture<Void> preLoad() {
        this.config = loadConfig();
        clockManager = new ClockManager(config, this);
        return super.preLoad();
    }


    @Override
    protected void setup() {
        LOGGER.atInfo().log("Setting up plugin " + this.getName());

        EventRegistry eventBus = getEventRegistry();
        try {
            new PlayerEnterWorldListener(clockManager).register(eventBus);
            new PlayerDisconnectListener(clockManager).register(eventBus);
            LOGGER.at(Level.INFO).log("[SimpleClock] Registered player event listeners");
        } catch (Exception e) {
            LOGGER.at(Level.WARNING).withCause(e).log("[SimpleClock] Failed to register listeners");
        }
    }

    @Override
    protected void shutdown() {
        this.clockManager.stop();
    }

    @Override
    protected void start() {
        this.clockManager.startClockScheduler();
    }

    private Config<MainConfig> loadConfig() {
        config = this.withConfig("SimpleClock", MainConfig.CODEC);
        this.config.load();
        this.config.save();
        return config;
    }
}
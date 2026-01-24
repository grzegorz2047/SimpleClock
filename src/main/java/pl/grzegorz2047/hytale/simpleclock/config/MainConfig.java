package pl.grzegorz2047.hytale.simpleclock.config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.validation.Validators;

public class MainConfig {

    private String clockPosition = "center";
    private String[] worldsWithClockEnabled = new String[]{"default"};

    public int getFontSize() {
        return fontSize;
    }

    private int fontSize = 16;

    public String getClockPosition() {
        return clockPosition;
    }

    public String[] getWorldsWithClockEnabled() {
        return worldsWithClockEnabled;
    }
    public static final BuilderCodec<MainConfig> CODEC = BuilderCodec.builder(MainConfig.class, MainConfig::new)
            .append(new KeyedCodec<>("ClockPosition", Codec.STRING), (config, f) -> config.clockPosition = f, (config) -> config.clockPosition).addValidator(Validators.nonNull()).documentation("clockPosition").add()
            .append(new KeyedCodec<>("FontSize", Codec.INTEGER), (config, f) -> config.fontSize = f, (config) -> config.fontSize).addValidator(Validators.nonNull()).documentation("fontSize").add()
            .append(new KeyedCodec<>("WorldsWithClockEnabled", Codec.STRING_ARRAY), (config, f) -> config.worldsWithClockEnabled = f, (config) -> config.worldsWithClockEnabled).addValidator(Validators.nonNull()).documentation("worldsWithClockEnabled").add()
            .build();


}

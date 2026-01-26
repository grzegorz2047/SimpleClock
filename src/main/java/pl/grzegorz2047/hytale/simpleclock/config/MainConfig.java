package pl.grzegorz2047.hytale.simpleclock.config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.validation.Validators;

public class MainConfig {

    private String clockPosition = "center";
    private String[] worldsWithClockEnabled = new String[]{"default"};
    private String clockPattern = "HH:mm";
    private int widthClockArea = 77;
    private int fontSize = 16;
    private boolean backgroundColorEnabled = true;

    public boolean isBackgroundColorEnabled() {
        return backgroundColorEnabled;
    }

    public int getFontSize() {
        return fontSize;
    }


    public String getClockPosition() {
        return clockPosition;
    }

    public String[] getWorldsWithClockEnabled() {
        return worldsWithClockEnabled;
    }

    public String getClockPattern() {
        return this.clockPattern;
    }

    public int getWidthClockArea() {
        return widthClockArea;
    }

    public static final BuilderCodec<MainConfig> CODEC = BuilderCodec.builder(MainConfig.class, MainConfig::new)
            .append(new KeyedCodec<>("ClockPattern", Codec.STRING), (config, f) -> config.clockPattern = f, (config) -> config.clockPattern).addValidator(Validators.nonNull()).documentation("clock pattern 24 hour is \"hh:mm\" or for a am/pm format like \"hh:mm a\"").add()
            .append(new KeyedCodec<>("ClockPosition", Codec.STRING), (config, f) -> config.clockPosition = f, (config) -> config.clockPosition).addValidator(Validators.nonNull()).documentation("clockPosition").add()
            .append(new KeyedCodec<>("WidthClockArea", Codec.INTEGER), (config, f) -> config.widthClockArea = f, (config) -> config.widthClockArea).addValidator(Validators.nonNull()).documentation("widthClockArea how wide should clock be").add()
            .append(new KeyedCodec<>("FontSize", Codec.INTEGER), (config, f) -> config.fontSize = f, (config) -> config.fontSize).addValidator(Validators.nonNull()).documentation("fontSize").add()
            .append(new KeyedCodec<>("BackgroundColorEnabled", Codec.BOOLEAN), (config, f) -> config.backgroundColorEnabled = f, (config) -> config.backgroundColorEnabled).addValidator(Validators.nonNull()).documentation("backgroundColorEnabled").add()
            .append(new KeyedCodec<>("WorldsWithClockEnabled", Codec.STRING_ARRAY), (config, f) -> config.worldsWithClockEnabled = f, (config) -> config.worldsWithClockEnabled).addValidator(Validators.nonNull()).documentation("worldsWithClockEnabled").add()
            .build();


}

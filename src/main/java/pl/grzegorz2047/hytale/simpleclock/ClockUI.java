package pl.grzegorz2047.hytale.simpleclock;

import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.ui.Anchor;
import com.hypixel.hytale.server.core.ui.Value;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class ClockUI extends CustomUIHud {
    private final int widthClockArea;
    private final int CONTAINER_HEIGHT = 36;
    private final int CONTAINER_TOP = 60;
    private volatile String time = "00:00";
    private final int fontSize;

    public ClockUI(PlayerRef playerRef, int fontSize, int widthClockArea) {
        super(playerRef);
        this.fontSize = fontSize;
        this.widthClockArea = widthClockArea;
    }


    @Override
    protected void build(@NonNullDecl UICommandBuilder uiCommandBuilder) {
        uiCommandBuilder.append("Hud/SimpleClockUI.ui");
        uiCommandBuilder.set("#SimpleHudText.Style.FontSize", this.fontSize);
        uiCommandBuilder.set("#SimpleHudText.Text", this.time);
        Anchor data = new Anchor();
        data.setWidth(Value.of(this.widthClockArea));
        data.setHeight(Value.of(CONTAINER_HEIGHT));
        data.setTop(Value.of(CONTAINER_TOP));
        uiCommandBuilder.setObject(
                "#SimpleHudObj.Anchor",
                data
        );
    }

    public void setTime(String timeText) {
        if (timeText == null || timeText.equals(this.time)) {
            return;
        }
        this.time = timeText;
        UICommandBuilder builder = new UICommandBuilder();
        builder.set("#SimpleHudText.Text", this.time);
        this.update(false, builder);
    }
}

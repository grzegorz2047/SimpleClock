package pl.grzegorz2047.hytale.simpleclock;

import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class ClockUI extends CustomUIHud {
    private volatile String time = "00:00";
    private final int fontSize;

    public ClockUI(PlayerRef playerRef, int fontSize) {
        super(playerRef);
        this.fontSize = fontSize;
    }


    @Override
    protected void build(@NonNullDecl UICommandBuilder uiCommandBuilder) {
        uiCommandBuilder.append("Hud/SimpleClockUI.ui");
        uiCommandBuilder.set("#SimpleHudText.Style.FontSize", this.fontSize);
        uiCommandBuilder.set("#SimpleHudText.Text", this.time);
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

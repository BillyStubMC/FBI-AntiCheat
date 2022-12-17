package fun.billy.fbi.check.checks.autoclicker;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.utils.GraphUtil;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import org.bukkit.GameMode;

import java.util.Deque;
import java.util.LinkedList;

public class AutoClickerH
        extends PacketCheck {
    private double ticks, cps, buffer;
    private final Deque<Double> clickSamples = new LinkedList<>();

    public AutoClickerH() {
        super(Check.CheckType.AUTO_CLICKERH, "H", "AutoClicker", Check.CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInFlying) {
            final double ticks = this.ticks + 1.0;
            this.ticks = ticks;
            if (ticks == 20.0) {
                if (this.cps > 9.0 && this.clickSamples.add(this.cps) && this.clickSamples.size() == 10) {
                    final GraphUtil.GraphResult results = GraphUtil.getGraph(this.clickSamples);
                    final int negatives = results.getNegatives();
                    if (negatives == 1) {
                        final double buffer = this.buffer + 1.0;
                        this.buffer = buffer;
                        if (buffer > 3.0) {
                            //VL.getInstance().handleViolation(playerData, this, "CPS " + cps + ", B" + buffer, false);
                            VL.getInstance().handleViolation(playerData, this, "Ticks", false);

                        }
                    } else {
                        this.buffer = 0.0;
                    }
                    this.clickSamples.clear();
                }
                this.cps = 0.0;
                this.ticks = 0.0;
            }
        } else if (packet instanceof PacketPlayInArmAnimation) {
            if (playerData.isPlace() && !playerData.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                this.buffer = 0.0;
                return;
            }
            ++this.cps;
        }
    }
}
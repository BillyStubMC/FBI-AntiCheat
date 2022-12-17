package fun.billy.fbi.check.checks.autoclicker;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.utils.MathUtils;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import org.bukkit.GameMode;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AutoClickerA
        extends PacketCheck {
    private boolean swing, place;
    private int ticks;
    Queue<Integer> intervals;

    public AutoClickerA() {
        super(Check.CheckType.AUTO_CLICKERA, "A", "AutoClicker", Check.CheckVersion.RELEASE);
        this.violations = -3.0;
        this.ticks = 0;
        this.intervals = new ConcurrentLinkedQueue<>();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInFlying) {
            if (this.swing && !this.place && playerData.getPlayer().getGameMode() != GameMode.CREATIVE && playerData.getLastAttackTicks() < 2400) {
                if (this.ticks < 8) {
                    this.intervals.add(this.ticks);
                    if (this.intervals.size() >= 40) {
                        double deviation = MathUtils.deviation(this.intervals);
                        this.violations += (0.325 - deviation) * 2.0 + 0.675;
                        if (this.violations < -3.0) {
                            this.violations = -3.0;
                        }
                        if (deviation < 0.325) {
                            //VL.getInstance().handleViolation(playerData, this, "D " + deviation + ", T" + this.ticks, false);
                            VL.getInstance().handleViolation(playerData, this, "Deviation", false);
                        }
                        this.intervals.clear();
                    }
                }
                this.ticks = 0;
            }
            this.place = false;
            this.swing = false;
            ++this.ticks;
        } else if (packet instanceof PacketPlayInArmAnimation) {
            this.swing = true;
        } else if (packet instanceof PacketPlayInBlockPlace) {
            this.place = true;
        }
    }
}


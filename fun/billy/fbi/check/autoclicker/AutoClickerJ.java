package fun.billy.fbi.check.checks.autoclicker;

import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.utils.MathUtils;
import net.minecraft.server.v1_8_R3.*;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AutoClickerJ
        extends PacketCheck {
    private Queue<Integer> intervals = new ConcurrentLinkedQueue<Integer>();
    private int ticks;
    private boolean swung;
    private boolean place;

    public AutoClickerJ() {
        super(CheckType.AUTO_CLICKERJ, "J", "AutoClicker", CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInFlying) {
            if (this.swung && !this.place) {
                if (this.ticks < 8) {
                    this.intervals.add(this.ticks);
                    if (this.intervals.size() >= 40) {
                        double deviation = MathUtils.deviation(this.intervals);
                        this.violations += (0.325 - deviation) * 2.0 + 0.675;
                        if (this.violations < -5.0) {
                            this.violations = -5.0;
                        }
                        if (deviation < 0.325) {
                            VL.getInstance().handleViolation(playerData, this, "Deviation", false);
                        }
                        this.intervals.clear();
                    }
                }
                this.ticks = 0;
            }
            this.place = false;
            this.swung = false;
            ++this.ticks;
        } else if (packet instanceof PacketPlayInArmAnimation) {
            this.swung = true;
        } else if (packet instanceof PacketPlayInBlockPlace) {
            this.place = true;
        }
    }
}
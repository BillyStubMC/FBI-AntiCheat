package fun.billy.fbi.check.checks.autoclicker;

import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.*;

import java.util.Deque;
import java.util.LinkedList;

public class AutoClickerR
        extends PacketCheck {
    private final Deque<Integer> recentCounts;
    private int flyingCount;
    private boolean release;

    public AutoClickerR(Deque<Integer> recentCounts) {
        super(CheckType.AUTO_CLICKERR, "R", "AutoClicker", CheckVersion.EXPERIMENTAL);
        this.recentCounts = new LinkedList<>();
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, Packet packet, long timeStamp) {
        if (packet instanceof PacketPlayInArmAnimation) {
            if (this.flyingCount < 10) {
                if (this.release) {
                    this.release = false;
                    this.flyingCount = 0;
                    return;
                }
                this.recentCounts.add(this.flyingCount);
                if (this.recentCounts.size() == 100) {
                    double average = 0.0;
                    for (final int i : this.recentCounts) {
                        average += i;
                    }
                    average /= this.recentCounts.size();
                    double stdDev = 0.0;
                    for (final int j : this.recentCounts) {
                        stdDev += Math.pow(j - average, 2.0);
                    }
                    stdDev /= this.recentCounts.size();
                    stdDev = Math.sqrt(stdDev);
                    if (stdDev < 0.45) {
                        VL.getInstance().handleViolation(playerData, this, "STD " + stdDev, false);
                        }
                    this.recentCounts.clear();
                }
            }
            this.flyingCount = 0;
        } else if (packet instanceof PacketPlayInFlying) {
            ++this.flyingCount;
        } else if (packet instanceof PacketPlayInBlockDig &&
                ((PacketPlayInBlockDig) packet).c() == PacketPlayInBlockDig.EnumPlayerDigType.RELEASE_USE_ITEM) {
            this.release = true;
        }
    }
}
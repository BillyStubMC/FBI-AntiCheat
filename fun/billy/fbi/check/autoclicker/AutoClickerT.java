package fun.billy.fbi.check.checks.autoclicker;

import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

import java.util.Deque;
import java.util.LinkedList;

public class AutoClickerT
        extends PacketCheck {
    private final Deque<Integer> recentCounts;
    private int flyingCount;
    private boolean release;

    public AutoClickerT(Deque<Integer> recentCounts) {
        super(CheckType.AUTO_CLICKERT, "T", "AutoClicker", CheckVersion.EXPERIMENTAL);
        this.recentCounts = new LinkedList<>();
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, Packet packet, long timeStamp) {
        if (packet instanceof PacketPlayInBlockDig && ((PacketPlayInBlockDig) packet).c() == PacketPlayInBlockDig.EnumPlayerDigType.RELEASE_USE_ITEM) {
            if (this.flyingCount < 10) {
                this.recentCounts.add(this.flyingCount);
                if (this.recentCounts.size() == 100) {
                    double average = 0.0;
                    for (final double flyingCount : this.recentCounts) {
                        average += flyingCount;
                    }
                    average /= this.recentCounts.size();
                    double stdDev = 0.0;
                    for (final long l : this.recentCounts) {
                        stdDev += Math.pow(l - average, 2.0);
                    }
                    stdDev /= this.recentCounts.size();
                    stdDev = Math.sqrt(stdDev);
                    if (stdDev < 0.2) {
                        VL.getInstance().handleViolation(playerData, this, "STD " + stdDev + " AVG " + average, false);
                        this.recentCounts.clear();
                    }
                }
            } else if (packet instanceof PacketPlayInBlockPlace && ((PacketPlayInBlockPlace) packet).getItemStack() !=
                    null && ((PacketPlayInBlockPlace) packet).getItemStack().getName().toLowerCase().contains("sword")) {
                this.flyingCount = 0;
            } else if (packet instanceof PacketPlayInFlying) {
                ++this.flyingCount;
            }
        }
    }
}
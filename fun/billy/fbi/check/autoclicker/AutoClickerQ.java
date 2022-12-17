package fun.billy.fbi.check.checks.autoclicker;

import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;

import java.util.Deque;
import java.util.LinkedList;

public class AutoClickerQ
        extends PacketCheck {
    private final Deque<Integer> recentCounts;
    private BlockPosition lastBlock;
    private int flyingCount;

    public AutoClickerQ(Deque<Integer> recentCounts) {
        super(CheckType.AUTO_CLICKERQ, "Q", "AutoClicker", CheckVersion.EXPERIMENTAL);
        this.recentCounts = new LinkedList<>();
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, Packet packet, long timeStamp) {
        if (packet instanceof PacketPlayInBlockDig) {
            final PacketPlayInBlockDig blockDig = (PacketPlayInBlockDig) packet;
            if (blockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                if (this.lastBlock != null && this.lastBlock.equals(blockDig.a())) {
                    this.recentCounts.addLast(this.flyingCount);
                    if (this.recentCounts.size() == 20) {
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
                            this.recentCounts.clear();
                        }
                    }
                    this.flyingCount = 0;
                } else if (blockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                    this.lastBlock = blockDig.a();
                }
            } else if (packet instanceof PacketPlayInFlying) {
                ++this.flyingCount;
            }
        }
    }
}
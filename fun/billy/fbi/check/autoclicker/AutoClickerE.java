package fun.billy.fbi.check.checks.autoclicker;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.utils.MathUtils;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AutoClickerE
        extends PacketCheck {
    private boolean abortedDigging, digging, swing;
    private int lastTicks, done, ticks, vl;
    Queue<Integer> averageTicks;

    public AutoClickerE() {
        super(Check.CheckType.AUTO_CLICKERE, "E", "AutoClicker", CheckVersion.EXPERIMENTAL);
        this.violations = -2.0;
        this.lastTicks = 0;
        this.averageTicks = new ConcurrentLinkedQueue<>();
        this.done = 0;
        this.ticks = 0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInFlying) {
            if (this.swing && !this.digging && playerData.getLastAttackTicks() < 2400) {
                if (this.ticks < 7) {
                    this.averageTicks.add(this.ticks);
                    if (this.averageTicks.size() > 50) {
                        this.averageTicks.poll();
                    }
                }
                if (this.averageTicks.size() > 40) {
                    double average = MathUtils.average(this.averageTicks);
                    if (average < 2.5) {
                        if (this.ticks > 3 && this.ticks < 20 && this.lastTicks < 20) {
                            this.violations -= Math.min(this.violations, 0.25);
                            this.done = 0;
                        } else if (this.done++ > 600.0 / (average * 1.5) && ++vl > 1) {
                            //VL.getInstance().handleViolation(playerData, this, "A " + average, false);
                            VL.getInstance().handleViolation(playerData, this, "Average", false);
                            this.done = 0;
                            vl = 0;
                        }
                    } else {
                        this.done = 0;
                    }
                }
                this.lastTicks = this.ticks;
                this.ticks = 0;
            }
            if (this.abortedDigging) {
                this.digging = false;
                this.abortedDigging = false;
            }
            this.swing = false;
            ++this.ticks;
        } else if (packet instanceof PacketPlayInArmAnimation) {
            this.swing = true;
        } else if (packet instanceof PacketPlayInBlockDig) {
            PacketPlayInBlockDig vPacketPlayInBlockDig = (PacketPlayInBlockDig) packet;
            if (vPacketPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                this.digging = true;
                this.abortedDigging = false;
            } else if (vPacketPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                this.abortedDigging = true;
            }
        }
    }
}


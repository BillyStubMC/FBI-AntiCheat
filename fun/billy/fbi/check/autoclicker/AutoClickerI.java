package fun.billy.fbi.check.checks.autoclicker;

import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;

import java.util.Deque;
import java.util.LinkedList;

public class AutoClickerI
        extends PacketCheck {
    private double ticks, cps, buffer;
    private final Deque<Double> clickSamples = new LinkedList<>();
    private boolean sent;

    public AutoClickerI() {
        super(CheckType.AUTO_CLICKERI, "I", "AutoClicker", CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInBlockDig) {
            PacketPlayInBlockDig.EnumPlayerDigType digType = ((PacketPlayInBlockDig)((Object)packet)).c();
            if (digType == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                this.sent = true;
            } else if (digType == PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                int vl = (int)this.violations;
                if (this.sent) {
                    if (++vl > 10) {
                        //VL.getInstance().handleViolation(playerData, this,  "VL " + vl, false);
                        VL.getInstance().handleViolation(playerData, this,  "Packet Dig", false);
                    }
                } else {
                    vl = 0;
                }
                this.violations = Math.max(0, vl);
            }
        } else if (packet instanceof PacketPlayInArmAnimation) {
            this.sent = false;
        }
    }
}

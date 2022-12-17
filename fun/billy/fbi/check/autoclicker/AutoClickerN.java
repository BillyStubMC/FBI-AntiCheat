package fun.billy.fbi.check.checks.autoclicker;

import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

public class AutoClickerN
        extends PacketCheck {
    private boolean sent;

    public AutoClickerN() {
        super(CheckType.AUTO_CLICKERN, "N", "AutoClicker", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, Packet packet, long timeStamp) {
        if (packet instanceof PacketPlayInBlockDig) {
            final PacketPlayInBlockDig.EnumPlayerDigType digType = ((PacketPlayInBlockDig) packet).c();
            if (digType == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                this.sent = true;
            } else if (digType == PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                if (this.sent) {
                    VL.getInstance().handleViolation(playerData, this, "Dig", false);
                }
            }
        }
    }
}
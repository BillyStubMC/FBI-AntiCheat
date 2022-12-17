package fun.billy.fbi.check.checks.autoclicker;

import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

public class AutoClickerP
        extends PacketCheck {
    private boolean failed;
    private boolean sent;
    private int count;

    public AutoClickerP() {
        super(CheckType.AUTO_CLICKERP, "P", "AutoClicker", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, Packet packet, long timeStamp) {
        if (packet instanceof PacketPlayInArmAnimation) {
            if (this.sent) {
                ++this.count;

                if (!this.failed) {
                    VL.getInstance().handleViolation(playerData, this, "CO " + this.count, false);
                }
                this.failed = true;
            }
        } else {
            this.sent = true;
            this.count = 0;
        }
        if (packet instanceof PacketPlayInFlying) {
            final boolean b = false;
            this.failed = b;
            this.sent = b;
            this.count = 0;
        }
    }
}

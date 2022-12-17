package fun.billy.fbi.check.checks.autoclicker;

import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

public class AutoClickerL
        extends PacketCheck {
    private int swings;
    private int movements;

    public AutoClickerL() {
        super(CheckType.AUTO_CLICKERL, "L", "AutoClicker", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, Packet packet, long timeStamp) {
        if (packet instanceof PacketPlayInArmAnimation) {
            ++this.swings;
        } if (packet instanceof PacketPlayInFlying && ++this.movements == 20) {
            if (this.swings > 20) {
                //VL.getInstance().handleViolation(playerData, this, "S " + this.swings + " M " + this.movements, false);
                VL.getInstance().handleViolation(playerData, this, "Movement", false);
            }
        }
    }
}

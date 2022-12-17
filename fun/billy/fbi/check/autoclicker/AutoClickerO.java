package fun.billy.fbi.check.checks.autoclicker;

import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;

public class AutoClickerO
        extends PacketCheck {
    private int movements;
    private int stage;

    public AutoClickerO() {
        super(CheckType.AUTO_CLICKERO, "O", "AutoClicker", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, Packet packet, long timeStamp) {
        if (this.stage == 0) {
            if (packet instanceof PacketPlayInArmAnimation) {
                ++this.stage;
            }
        } else if (this.stage == 1) {
            if (packet instanceof PacketPlayInBlockDig && ((PacketPlayInBlockDig) packet).c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                ++this.stage;
            } else {
                this.stage = 0;
            }
        } else if (this.stage == 2) {
            if (packet instanceof PacketPlayInBlockDig && ((PacketPlayInBlockDig) packet).c() == PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                try {
                    if (this.movements > 10)
                        VL.getInstance().handleViolation(playerData, this, "M " + this.movements + " ST " + this.stage, false);
                } finally {
                    final boolean movements = false;
                    this.movements = (movements ? 1 : 0);
                }
            }
        }
    }
}
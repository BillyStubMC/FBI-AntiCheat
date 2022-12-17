package fun.billy.fbi.check.checks.killaura;

import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.*;

public class KillAuraH
        extends PacketCheck {
    private boolean sent;
    private boolean failed;
    private int movements;

    public KillAuraH() {
        super(CheckType.KILL_AURAH, "H", "KillAura", CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (playerData.isDigging()) {
            if (packet instanceof PacketPlayInBlockDig &&
                    ((PacketPlayInBlockDig) packet).c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                this.movements = 0;
            } else if (packet instanceof PacketPlayInArmAnimation && this.movements >= 2) {
                if (this.sent) {
                    if (!this.failed) {
                        VL.getInstance().handleViolation(playerData, this, "M " + this.movements + "S" + this.sent, false);
                    }
                } else {
                    this.sent = true;
                }
            } else if (packet instanceof PacketPlayInFlying) {
                final boolean b = false;
                this.failed = b;
                this.sent = b;
                ++this.movements;
            }
        }
    }
}
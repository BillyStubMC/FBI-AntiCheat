package fun.billy.fbi.check.checks.killaura;

import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;

public class KillAuraG
        extends PacketCheck {
    private boolean sent;

    public KillAuraG() {
        super(CheckType.KILL_AURAG, "G", "KillAura", CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInUseEntity && ((PacketPlayInUseEntity) packet).a() == PacketPlayInUseEntity
                .EnumEntityUseAction.ATTACK) {
            if (!this.sent) {
                VL.getInstance().handleViolation(playerData, this, "SENT: " + this.sent, false);
            } else {
                this.sent = false;
            }
        } else if (packet instanceof PacketPlayInArmAnimation) {
            this.sent = true;
        } else if (packet instanceof PacketPlayInFlying) {
            this.sent = false;
        }
    }
}
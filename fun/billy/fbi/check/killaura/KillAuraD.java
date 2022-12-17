package fun.billy.fbi.check.checks.killaura;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;

public class KillAuraD
        extends PacketCheck {
    private int invalidTicks, lastTicks, totalTicks, ticks;

    public KillAuraD() {
        super(Check.CheckType.KILL_AURAD, "D", "KillAura", Check.CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInFlying) {
            ++this.ticks;
        } else if (packet instanceof PacketPlayInUseEntity) {
            ((PacketPlayInUseEntity) packet).a();
            if (this.ticks <= 8) {
                if (this.lastTicks == this.ticks) {
                    ++this.invalidTicks;
                }
                if (this.totalTicks++ >= 25) {
                    if (this.invalidTicks > 22) {
                        VL.getInstance().handleViolation(playerData, this, String.format("%s/%s", this.invalidTicks, this.totalTicks) + 1.0 + (this.invalidTicks - 22) / 6.0, false);
                    } else {
                        this.violations -= Math.min(this.violations + 1.0, 1.0);
                    }
                    this.totalTicks = 0;
                    this.invalidTicks = 0;
                }
                this.lastTicks = this.ticks;
            }
            this.ticks = 0;
        }
    }
}

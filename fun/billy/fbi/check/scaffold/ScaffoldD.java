package fun.billy.fbi.check.checks.scaffold;

import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.CustomLocation;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

public class ScaffoldD
        extends PacketCheck {
    private int looks;
    private int stage;

    public ScaffoldD() {
        super(CheckType.MISCF, "D", "Scaffold", CheckVersion.RELEASE);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInFlying.PacketPlayInLook) {
            if (this.stage == 0) {
                ++this.stage;
            } else if (this.stage == 4) {
                VL.getInstance().handleViolation(playerData, this, "S " + this.stage, false);

                this.stage = 0;
            } else {
                final boolean b = false;
                this.looks = (b ? 1 : 0);
                this.stage = (b ? 1 : 0);
            }
        } else if (packet instanceof PacketPlayInBlockPlace) {
            if (this.stage == 1) {
                ++this.stage;
            } else {
                final boolean b2 = false;
                this.looks = (b2 ? 1 : 0);
                this.stage = (b2 ? 1 : 0);
            }
        } else if (packet instanceof PacketPlayInArmAnimation) {
            if (this.stage == 2) {
                ++this.stage;
            } else {
                final boolean b3 = false;
                this.looks = (b3 ? 1 : 0);
                this.stage = (b3 ? 1 : 0);
            }
        } else if (packet instanceof PacketPlayInFlying.PacketPlayInPositionLook || packet instanceof PacketPlayInFlying.PacketPlayInPosition) {
            if (this.stage == 3) {
                if (++this.looks == 3) {
                    this.stage = 4;
                    this.looks = 0;
                }
            } else {
                final boolean b4 = false;
                this.looks = (b4 ? 1 : 0);
                this.stage = (b4 ? 1 : 0);
            }
        }
    }
}
package fun.billy.fbi.check.checks.autoclicker;

import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.*;

public class AutoClickerK
        extends PacketCheck {
    private int stage;
    private boolean swing;
    private boolean initalSwing;

    public AutoClickerK() {
        super(CheckType.AUTO_CLICKERK, "K", "AutoClicker", CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInArmAnimation) {
            if (this.stage == 1) {
                this.stage = 2;
            } else if (this.stage == 3 || this.stage == 0) {
                this.swing = false;
            }
        } else if (packet instanceof PacketPlayInBlockDig) {
            PacketPlayInBlockDig blockDig = (PacketPlayInBlockDig)((Object)packet);
            if (blockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK) {
                if (this.stage == 0) {
                    this.stage = 1;
                    this.initalSwing = this.swing;
                } else {
                    this.stage = 0;
                }
            } else if (blockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                if (this.stage == 3) {
                    if (this.swing) {
                        VL.getInstance().handleViolation(playerData, this,  "Aborted Destroy", false);
                    } else if (this.initalSwing) {
                        this.violations -= Math.min(this.violations + 5.0, 0.2);
                    }
                }
                this.stage = 0;
            }
        } else if (packet instanceof PacketPlayInFlying) {
            if (this.stage == 2) {
                this.stage = 3;
            }
            this.swing = false;
        }
    }
}

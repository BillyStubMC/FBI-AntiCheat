package fun.billy.fbi.check.checks.autoclicker;

import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

public class AutoClickerM
        extends PacketCheck {
    private int clicks;
    private int outliers;
    private int flyingCount;
    private boolean release;

    public AutoClickerM() {
        super(CheckType.AUTO_CLICKERM, "M", "AutoClicker", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, Packet packet, long timeStamp) {
        if (packet instanceof PacketPlayInArmAnimation) {

            if (this.flyingCount < 10) {
                if (this.release) {
                    this.release = false;
                    this.flyingCount = 0;
                    return;
                }

                if (this.flyingCount > 3) {
                    ++this.outliers;
                } else if (this.flyingCount == 0) {
                    return;
                }

                if (++this.clicks == 1000) {
                    if (this.outliers <= 7) {
                        VL.getInstance().handleViolation(playerData, this, "Outliers", false);
                    }
                }
            }
            this.flyingCount = 0;
        } else if (packet instanceof PacketPlayInFlying) {
            ++this.flyingCount;
        }
    }
}
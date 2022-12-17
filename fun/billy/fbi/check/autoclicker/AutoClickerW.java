package fun.billy.fbi.check.checks.autoclicker;

import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import org.bukkit.GameMode;

public class AutoClickerW
        extends PacketCheck {
    private int clicks, outliers, flyingCount;
    private boolean release;
    private double buffer;

    public AutoClickerW() {
        super(CheckType.AUTO_CLICKERW, "W", "AutoClicker", CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInFlying) {
            ++flyingCount;
        } else if (packet instanceof PacketPlayInBlockDig) {
            PacketPlayInBlockDig packetPlayInBlockDig = (PacketPlayInBlockDig) packet;
            if (packetPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.RELEASE_USE_ITEM) {
                release = true;
            }
        } else if (packet instanceof PacketPlayInArmAnimation) {
            if (!playerData.isPlace() && !playerData.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
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
                    if (++this.clicks == 30) {
                        if (this.outliers == 0) {
                            final double buffer = this.buffer + 1.0;
                            this.buffer = buffer;
                                VL.getInstance().handleViolation(playerData, this, "O " + this.outliers, false);
                        } else {
                            this.buffer -= ((this.buffer > 0.0) ? 1.5 : 0.0);
                        }
                        this.outliers = 0;
                        this.clicks = 0;
                    }
                }
                this.flyingCount = 0;
            }
        }
    }
}
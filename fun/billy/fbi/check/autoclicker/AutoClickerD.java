package fun.billy.fbi.check.checks.autoclicker;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.utils.MathUtils;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import org.bukkit.GameMode;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AutoClickerD
        extends PacketCheck {
    private Integer releaseTime;
    Queue<Integer> clickQueue;
    private int start;

    public AutoClickerD() {
        super(Check.CheckType.AUTO_CLICKERD, "D", "AutoClicker", Check.CheckVersion.RELEASE);
        this.releaseTime = null;
        this.clickQueue = new ConcurrentLinkedQueue<>();
        this.start = 0;
        this.violations = -1.0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInFlying) {
            if (playerData.isDigging()) {
                ++this.start;
            }
            if (this.releaseTime != null) {
                ++this.releaseTime;
            }
        } else if (packet instanceof PacketPlayInBlockDig) {
            PacketPlayInBlockDig packetPlayInBlockDig = (PacketPlayInBlockDig) packet;
            if (packetPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                this.releaseTime = 0;
            } else if (packetPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.START_DESTROY_BLOCK && this.releaseTime != null && this.releaseTime < 4 && this.releaseTime > 0) {
                this.clickQueue.add(this.releaseTime);
                if (this.clickQueue.size() > 10) {
                    double value = MathUtils.variance(1, this.clickQueue) / this.start;
                    if (value > 0.2) {
                        if (playerData.getPlayer().getGameMode() != GameMode.CREATIVE) {
                            //VL.getInstance().handleViolation(playerData, this, "C/S:" + value, false);
                            VL.getInstance().handleViolation(playerData, this, "Constant", false);
                        }
                    } else {
                        this.violations -= Math.min(this.violations + 1.0, 0.05);
                    }
                    this.clickQueue.clear();
                    this.start = 0;
                }
                this.releaseTime = null;
            }
        }
    }
}

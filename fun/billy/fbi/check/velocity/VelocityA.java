package fun.billy.fbi.check.checks.velocity;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

import java.util.Deque;
import java.util.LinkedList;

public class VelocityA
        extends PacketCheck {
    private int vl;
    private final Deque<Double> kb = new LinkedList<>();

    public VelocityA() {
        super(Check.CheckType.VELOCITYA, "A", "Velocity", Check.CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInFlying) {
            if (playerData.getLastVelY() > 0.0 && !playerData.isTeleporting() && !playerData.hasLag()) {

                double deltaY = playerData.getDeltaY();
                double min = playerData.getLastVelY() * 0.99F;

                kb.add(deltaY);

                if (kb.size() > 2) {
                    double max = kb.stream().mapToDouble(d -> d).max().getAsDouble();

                    if (max <= min) {
                        if (++vl >= playerData.getPingTicks() + 5)
                            VL.getInstance().handleViolation(playerData, this, "t=" + max + ", p=" + min, false);
                    } else vl = 0;

                    kb.clear();
                    playerData.setLastVelY(0.0);

                }
            }
        }
    }
}


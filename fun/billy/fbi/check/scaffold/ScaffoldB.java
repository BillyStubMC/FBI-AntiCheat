package fun.billy.fbi.check.checks.scaffold;

import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.CustomLocation;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import net.minecraft.server.v1_8_R3.PacketPlayInHeldItemSlot;

import java.util.Objects;

public class ScaffoldB
        extends PacketCheck {
    private BlockPosition lastBlock;
    private float lastYaw;
    private float lastPitch;
    private float lastX;
    private float lastY;
    private float lastZ;

    public ScaffoldB() {
        super(CheckType.MISCD, "B", "Scaffold", CheckVersion.RELEASE);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInBlockPlace) {
            final PacketPlayInBlockPlace blockPlace = (PacketPlayInBlockPlace) packet;
            final BlockPosition blockPosition = blockPlace.a();
            final float x = blockPlace.d();
            final float y = blockPlace.e();
            final float z = blockPlace.f();

            if (this.lastBlock != null && (blockPosition.getX() != this.lastBlock.getX() || blockPosition.getY() != this.lastBlock.getY() || blockPosition.getZ() != this.lastBlock.getZ())) {
                final CustomLocation location = playerData.getLastMovePacket();

                if (this.lastX == x && this.lastY == y && this.lastZ == z) {
                    final float deltaAngle = Math.abs(this.lastYaw - location.getYaw()) + Math.abs(this.lastPitch - location.getPitch());

                    if (deltaAngle > 4.0f) {
                        VL.getInstance().handleViolation(playerData, this, "D " + deltaAngle + " P " + this.lastPitch, false);
                    }

                    this.lastX = x;
                    this.lastY = y;
                    this.lastZ = z;
                    this.lastYaw = location.getYaw();
                    this.lastPitch = location.getPitch();
                }

                this.lastBlock = blockPosition;
            }
        }
    }
}
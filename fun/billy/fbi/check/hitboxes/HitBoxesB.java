package fun.billy.fbi.check.checks.hitboxes;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.GameMode;

public class HitBoxesB
        extends PacketCheck {

    public HitBoxesB() {
        super(CheckType.HITBOXESB, "B", "HitBoxes", Check.CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInUseEntity && playerData.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
            PacketPlayInUseEntity packetPlayInUseEntity = (PacketPlayInUseEntity) packet;
            if (packetPlayInUseEntity.a() == PacketPlayInUseEntity.EnumEntityUseAction.INTERACT_AT) {
                World world = playerData.getEntityPlayer().getWorld();
                Entity ent = ((PacketPlayInUseEntity) packet).a(world);
                Vec3D vec3D = packetPlayInUseEntity.b();
                if (ent instanceof EntityPlayer && (Math.abs(vec3D.a) > 0.4f || Math.abs(vec3D.b) > 1.902 || Math.abs(vec3D.c) > 0.4f)) {
                    VL.getInstance().handleViolation(playerData, this, "INTERACT ", false);
                }
            }
        }
    }
}

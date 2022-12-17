package fun.billy.fbi.check.checks.autoclicker.badpackets;

import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import org.bukkit.GameMode;

public class ACBadPackets
        extends PacketCheck {
    private boolean attack, dig;
    private int vl;

    public ACBadPackets() {
        super(CheckType.AUTO_CLICKERBP, "BadPackets", "AutoClicker", CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInFlying) {
            this.attack = false;
            this.dig = false;
            vl = 0;
        } else if (packet instanceof PacketPlayInBlockDig) {
            PacketPlayInBlockDig vPacketPlayInBlockDig = (PacketPlayInBlockDig) packet;
            if ((vPacketPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.DROP_ITEM || vPacketPlayInBlockDig.c() == PacketPlayInBlockDig.EnumPlayerDigType.DROP_ALL_ITEMS) && this.attack && playerData.getPlayer().getGameMode() != GameMode.CREATIVE) {
                VL.getInstance().handleViolation(playerData, this, "drop item", false);
            }
            if (vPacketPlayInBlockDig.c() != PacketPlayInBlockDig.EnumPlayerDigType.DROP_ITEM && vPacketPlayInBlockDig.c() != PacketPlayInBlockDig.EnumPlayerDigType.DROP_ALL_ITEMS) {
                this.dig = true;
            }
        } else if (packet instanceof PacketPlayInUseEntity && playerData.getPlayer().getGameMode() != GameMode.CREATIVE && ((PacketPlayInUseEntity) packet).a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK) {
            this.attack = true;
            if (dig) {
                if (++vl > 2) {
                    vl = 0;
                    VL.getInstance().handleViolation(playerData, this, "attack & dig", false);
                }
            } else {
                vl = 0;
            }
        }
    }
}

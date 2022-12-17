package fun.billy.fbi.check.checks.killaura.badpackets;

import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.*;

public class KABadPackets
        extends PacketCheck {
    private boolean interactAt, attack, interact, sentUseEntity, sentClose, sentAttack, sentInteract, place;

    public KABadPackets() {
        super(CheckType.KILL_AURABP, "BadPackets", "KillAura", CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInFlying) {
            this.interact = false;
            this.interactAt = false;
            this.attack = false;
            this.sentUseEntity = false;
            this.sentClose = false;
            this.sentInteract = false;
            this.sentAttack = false;
            this.place = false;
        } else if (packet instanceof PacketPlayInCloseWindow) {
            this.sentClose = true;
        } else if (packet instanceof PacketPlayInUseEntity) {
            PacketPlayInUseEntity packetPlayInUseEntity = (PacketPlayInUseEntity) packet;
            if (!this.attack && (this.interact || this.interactAt)) {
                VL.getInstance().handleViolation(playerData, this, (this.interactAt ? "interact at " : "") + (this.interact ? "interact" : ""), false);
                this.interactAt = false;
                this.interact = false;
            }
            if (packetPlayInUseEntity.a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK) {
                this.sentAttack = true;
                if (place && playerData.getVersion() != 1.9) {
                    VL.getInstance().handleViolation(playerData, this, "attack & place", false);
                }
            }
            if (packetPlayInUseEntity.a() == PacketPlayInUseEntity.EnumEntityUseAction.INTERACT) {
                this.sentInteract = true;
            }
            if (packetPlayInUseEntity.a() == PacketPlayInUseEntity.EnumEntityUseAction.ATTACK && sentClose) {
                VL.getInstance().handleViolation(playerData, this, "attack & close", false);
            }
            this.attack = true;
            this.sentUseEntity = true;
        } else if (packet instanceof PacketPlayInBlockPlace) {
            PacketPlayInBlockPlace packetPlayInBlockPlace = (PacketPlayInBlockPlace) packet;
            this.place = false;
            if (packetPlayInBlockPlace.getFace() != 255 && sentUseEntity) {
                VL.getInstance().handleViolation(playerData, this, "face:" + packetPlayInBlockPlace.getFace(), false);
            }
            if (sentAttack & !sentInteract) {
                VL.getInstance().handleViolation(playerData, this, "attack & interact", false);
            }
        }
    }
}


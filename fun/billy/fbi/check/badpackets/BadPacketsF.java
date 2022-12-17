package fun.billy.fbi.check.checks.badpackets;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.Settings;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInCustomPayload;

public class BadPacketsF
        extends PacketCheck {

    private int windowClick, useEntity, entityAction, updateSign, tabComplete, armAnimation, blockDig, blockPlace, heldItem, customPayload;

    public BadPacketsF() {
        super(CheckType.BADPACKETSF, "F", "BadPackets", Check.CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (Settings.getInstance().isPacketEnabled()) {
            final String className = packet.getClass().getSimpleName();
            switch (className) {
                case "PacketPlayInFlying": {
                    this.windowClick = 0;
                    this.useEntity = 0;
                    this.entityAction = 0;
                    this.updateSign = 0;
                    this.tabComplete = 0;
                    this.armAnimation = 0;
                    this.blockDig = 0;
                    this.blockPlace = 0;
                    this.heldItem = 0;
                    this.customPayload -= Math.min(this.customPayload, 1);
                    break;
                }
                case "PacketPlayInWindowClick": {
                    if (++this.windowClick > 170)
                        this.flag_and_kick(playerData, className);
                    break;
                }
                case "PacketPlayInUseEntity": {
                    if (++this.useEntity > 400)
                        this.flag_and_kick(playerData, className);
                    break;
                }
                case "PacketPlayInEntityAction": {
                    if (++this.entityAction > 250)
                        this.flag_and_kick(playerData, className);
                    break;
                }
                case "PacketPlayInUpdateSign": {
                    if (++this.updateSign > 6)
                        this.flag_and_kick(playerData, className);
                    break;
                }
                case "PacketPlayInTabComplete": {
                    if (++this.tabComplete > 45)
                        this.flag_and_kick(playerData, className);
                    break;
                }
                case "PacketPlayInArmAnimation": {
                    if (++this.armAnimation > 350)
                        this.flag_and_kick(playerData, className);
                    break;
                }
                case "PacketPlayInBlockDig": {
                    if (++this.blockDig > 120)
                        this.flag_and_kick(playerData, className);
                    break;
                }
                case "PacketPlayInBlockPlace": {
                    if (++this.blockPlace > 45)
                        this.flag_and_kick(playerData, className);
                    break;
                }
                case "PacketPlayInHeldItemSlot": {
                    if (++this.heldItem > 400)
                        this.flag_and_kick(playerData, className);
                    break;
                }
                case "PacketPlayInCustomPayload": {
                    PacketPlayInCustomPayload packetPlayInCustomPayload = (PacketPlayInCustomPayload) packet;
                    String channel = packetPlayInCustomPayload.a();
                    if ((channel.equals("MC|BOpen") || channel.equals("MC|BEdit")) && (this.customPayload += 2) > 4)
                        this.flag_and_kick(playerData, className);
                    break;
                }
            }
        }
    }

    private void flag_and_kick(PlayerData playerData, String classname) {
        playerData.fuckOff(playerData, classname);
    }
}


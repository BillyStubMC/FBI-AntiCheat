package fun.billy.fbi.check.checks.mods;


import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInKeepAlive;

public class ModsB
        extends PacketCheck {

    public ModsB() {
        super(CheckType.MODSB, "Raven", "Client", CheckVersion.RELEASE);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void handle(PlayerData playerData, Packet packet, long timestamp) {
        if (packet instanceof PacketPlayInKeepAlive && playerData.getForgeMods().keySet().contains("keystrokesmod")) {
            VL.getInstance().handleViolation(playerData, this, "id(keystrokesmod)", false);
        }
    }
}


package fun.billy.fbi.check.checks.noslow;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.PacketCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;

public class NoSlowA
        extends PacketCheck {
    private boolean attack, dig;
    private int vl;

    public NoSlowA() {
        super(CheckType.NOSLOWA, "A", "NoSlow", Check.CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, Packet packet, long timeStamp) {
        if (packet instanceof PacketPlayInBlockDig && ((PacketPlayInBlockDig)((Object)packet)).c() == PacketPlayInBlockDig.EnumPlayerDigType.RELEASE_USE_ITEM) {
            VL.getInstance().handleViolation(playerData, this, "no info D:" + "", false);
        }
    }
}

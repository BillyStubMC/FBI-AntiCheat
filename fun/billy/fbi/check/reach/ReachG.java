package fun.billy.fbi.check.checks.reach;

import fun.billy.fbi.check.type.ClientCheck;
import fun.billy.fbi.data.ClientData;
import fun.billy.fbi.data.DistanceData;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInTransaction;

import java.nio.file.DirectoryStream;
import java.text.DecimalFormat;

public class ReachG
        extends ClientCheck {
    private static final ThreadLocal<DecimalFormat> REACH_FORMAT = ThreadLocal.withInitial(() -> new DecimalFormat("#.###"));
    private int vl;

    public ReachG() {
        super(CheckType.REACHG, "G", "Reach", CheckVersion.EXPERIMENTAL);
        this.violations = -1.0;
    }

    @Override
    public void handle(PlayerData playerData, ClientData clientData, long timestamp) {
        final double reach = clientData.getReach();
        final double extra = clientData.getExtra();
        final double horizontal = clientData.getHorizontal();
        //final double dist = distanceData.getDist();

        if (reach >= 3.01) {
            //VL.getInstance().handleViolation(playerData, this, "R " + reach, false);
        }
    }
}
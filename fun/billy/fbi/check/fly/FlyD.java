package fun.billy.fbi.check.checks.fly;

import fun.billy.fbi.check.type.MovementCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.util.PlayerLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class FlyD
        extends MovementCheck {
    private boolean lastOnGround;
    private boolean lastLastOnGround;
    private double lastDistY;
    private int vl;


    public FlyD() {
        super(CheckType.FLYD, "D", "Fly", CheckVersion.RELEASE);
        this.violations = -1.0;
    }

    public boolean isRoughlyEqual(final double d1, final double d2) {
        return Math.abs(d1 - d2) < 0.001;
    }

    @Override
    public void handle(PlayerData playerData, PlayerLocation playerLocation, PlayerLocation playerLocation2, long timestamp) {
        final boolean loc1 = playerLocation.getOnGround();
        final boolean loc2 = playerLocation2.getOnGround();
        if(loc1 != loc2){
            //Bukkit.broadcastMessage("flagged");
        }

    }
}
package fun.billy.fbi.check.checks.fly;

import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.check.type.MovementCheck;
import fun.billy.fbi.data.PlayerData;
import fun.billy.fbi.data.manager.violations.VL;
import fun.billy.fbi.util.utils.CollisionUtil;
import fun.billy.fbi.util.PlayerLocation;
import org.bukkit.GameMode;
import org.bukkit.Material;

    public class FlyA
            extends MovementCheck {
        private double lastDeltaY;
        private int vl;

        public FlyA() {
            super(Check.CheckType.FLYA, "A", "Fly", Check.CheckVersion.RELEASE);
            this.violations = -1.0;
        }

        @Override
        public void handle(PlayerData playerData, PlayerLocation from, PlayerLocation to, long timestamp) {
            if (!playerData.getPlayer().getAllowFlight() && playerData.getPlayer().getGameMode().equals(GameMode.SURVIVAL) && !playerData.isTeleporting()) {

                final double deltaY = playerData.getDeltaY();
                final boolean onGround = !CollisionUtil.isOnGround(playerData.getPlayer()) && !to.getOnGround();
                final boolean inWeb = (CollisionUtil.isOnChosenBlock(playerData.getPlayer(), 1.5, Material.WEB) ||
                        CollisionUtil.isOnChosenBlock(playerData.getPlayer(), 0.5, Material.WEB));

                double predictedDist = (this.lastDeltaY - 0.08) * 0.9800000190734863D;

                if (Math.abs(predictedDist) <= 0.005) predictedDist = 0;
                if (Math.abs(predictedDist - deltaY) > 0.001 && playerData.getKbticks() > ((playerData.getPlayer().getMaximumNoDamageTicks() < 15) ? 20 : 10)) {

                    if (playerData.isPlace()) {
                        playerData.setPlace(false);
                        this.vl = 0;
                        return;
                    }

                    if (inWeb && deltaY < 1E-3) {
                        this.vl = 0;
                        return;
                    }

                    if (onGround) {

                        if (++this.vl > 1)
                            VL.getInstance().handleViolation(playerData, this, "d=" + deltaY + ", p=" + (this.lastDeltaY - 0.08) * 0.9800000190734863D, false);
                    } else this.vl = 0;

                }
                this.lastDeltaY = deltaY;
            }
        }
    }
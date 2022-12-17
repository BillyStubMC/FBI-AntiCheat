package fun.billy.fbi.util.license;

import fun.billy.fbi.check.checks.aimassist.*;
import fun.billy.fbi.check.checks.aimassist.combined.CombinedA;
import fun.billy.fbi.check.checks.aimassist.gcd.GcdA;
import fun.billy.fbi.check.checks.autoclicker.*;
import fun.billy.fbi.check.checks.autoclicker.badpackets.ACBadPackets;
import fun.billy.fbi.check.checks.fly.*;
import fun.billy.fbi.check.checks.hitboxes.HitBoxesA;
import fun.billy.fbi.check.checks.hitboxes.HitBoxesB;
import fun.billy.fbi.check.checks.inventory.InventoryA;
import fun.billy.fbi.check.checks.inventory.InventoryB;
import fun.billy.fbi.check.checks.inventory.InventoryC;
import fun.billy.fbi.check.checks.killaura.*;
import fun.billy.fbi.check.checks.killaura.badpackets.KABadPackets;
import fun.billy.fbi.check.checks.mods.ModsA;
import fun.billy.fbi.check.checks.mods.ModsB;
import fun.billy.fbi.check.checks.noslow.NoSlowA;
import fun.billy.fbi.check.checks.scaffold.ScaffoldB;
import fun.billy.fbi.check.checks.scaffold.ScaffoldC;
import fun.billy.fbi.check.checks.scaffold.ScaffoldD;
import fun.billy.fbi.check.checks.speed.*;
import fun.billy.fbi.check.checks.motion.MotionA;
import fun.billy.fbi.check.checks.motion.MotionB;
import fun.billy.fbi.check.checks.motion.MotionC;
import fun.billy.fbi.check.checks.motion.MotionD;
import fun.billy.fbi.check.checks.nofall.NoFallA;
import fun.billy.fbi.check.checks.nofall.NoFallB;
import fun.billy.fbi.check.checks.scaffold.ScaffoldA;
import fun.billy.fbi.check.checks.velocity.VelocityA;
import fun.billy.fbi.check.checks.velocity.VelocityB;
import fun.billy.fbi.check.checks.velocity.VelocityC;
import fun.billy.fbi.check.checks.badpackets.*;
import fun.billy.fbi.check.checks.pingspoof.PingSpoofA;
import fun.billy.fbi.check.checks.timer.TimerA;
import fun.billy.fbi.check.checks.timer.TimerB;
import fun.billy.fbi.check.checks.timer.TimerC;
import fun.billy.fbi.check.checks.reach.*;
import fun.billy.fbi.check.type.Check;

import java.util.ArrayList;
import java.util.List;

public class Loader {
    private List<Class<? extends Check>> checkClasses = null;

    private List<Class<? extends Check>> getCheckClasses() {
        if (this.checkClasses == null) {
            this.checkClasses = new ArrayList<>();
            this.checkClasses.add(AimA.class);
            this.checkClasses.add(AimB.class);
            this.checkClasses.add(AimC.class);
            this.checkClasses.add(AimD.class);
            this.checkClasses.add(AimE.class);
            this.checkClasses.add(AimF.class);
            this.checkClasses.add(AimG.class);
            this.checkClasses.add(AimH.class);
            this.checkClasses.add(AimI.class);
            this.checkClasses.add(AimJ.class);
            this.checkClasses.add(AimK.class);
            this.checkClasses.add(AimL.class);
            this.checkClasses.add(CombinedA.class);
            this.checkClasses.add(GcdA.class);
            this.checkClasses.add(AutoClickerA.class);
            this.checkClasses.add(AutoClickerB.class);
            this.checkClasses.add(AutoClickerC.class);
            this.checkClasses.add(AutoClickerD.class);
            this.checkClasses.add(AutoClickerE.class);
            this.checkClasses.add(AutoClickerF.class);
            this.checkClasses.add(AutoClickerG.class);
            this.checkClasses.add(AutoClickerH.class);
            this.checkClasses.add(AutoClickerI.class);
            this.checkClasses.add(AutoClickerJ.class);
            this.checkClasses.add(AutoClickerK.class);
            this.checkClasses.add(AutoClickerL.class);
            this.checkClasses.add(AutoClickerM.class);
            this.checkClasses.add(AutoClickerN.class);
            this.checkClasses.add(AutoClickerO.class);
            this.checkClasses.add(AutoClickerP.class);
            this.checkClasses.add(AutoClickerQ.class);
            this.checkClasses.add(AutoClickerR.class);
            this.checkClasses.add(AutoClickerS.class);
            this.checkClasses.add(AutoClickerT.class);
            this.checkClasses.add(AutoClickerU.class);
            this.checkClasses.add(AutoClickerV.class);
            this.checkClasses.add(AutoClickerW.class);
            this.checkClasses.add(AutoClickerX.class);
            this.checkClasses.add(AutoClickerZ.class);
            this.checkClasses.add(ACBadPackets.class);
            this.checkClasses.add(ReachA.class);
            this.checkClasses.add(ReachB.class);
            this.checkClasses.add(ReachC.class);
            this.checkClasses.add(ReachD.class);
            this.checkClasses.add(ReachE.class);
            this.checkClasses.add(ReachF.class);
            this.checkClasses.add(ReachG.class);
            this.checkClasses.add(FlyA.class);
            this.checkClasses.add(FlyB.class);
            this.checkClasses.add(FlyC.class);
            this.checkClasses.add(FlyD.class);
            this.checkClasses.add(FlyE.class);
            this.checkClasses.add(NoFallA.class);
            this.checkClasses.add(NoFallB.class);
            this.checkClasses.add(MotionA.class);
            this.checkClasses.add(MotionB.class);
            this.checkClasses.add(MotionC.class);
            this.checkClasses.add(MotionD.class);
            this.checkClasses.add(InventoryA.class);
            this.checkClasses.add(InventoryB.class);
            this.checkClasses.add(InventoryC.class);
            this.checkClasses.add(KillAuraA.class);
            this.checkClasses.add(KillAuraB.class);
            this.checkClasses.add(KillAuraC.class);
            this.checkClasses.add(KillAuraD.class);
            this.checkClasses.add(KillAuraE.class);
            //this.checkClasses.add(KillAuraF.class);
            this.checkClasses.add(KillAuraG.class);
            this.checkClasses.add(KillAuraH.class);
            this.checkClasses.add(KABadPackets.class);
            this.checkClasses.add(KillAuraE.class);
            this.checkClasses.add(HitBoxesA.class);
            this.checkClasses.add(HitBoxesB.class);
            this.checkClasses.add(ScaffoldA.class);
            this.checkClasses.add(ScaffoldB.class);
            this.checkClasses.add(ScaffoldC.class);
            this.checkClasses.add(ScaffoldD.class);
            this.checkClasses.add(SpeedA.class);
            this.checkClasses.add(SpeedB.class);
            this.checkClasses.add(SpeedC.class);
            this.checkClasses.add(SpeedD.class);
            this.checkClasses.add(SpeedE.class);
            this.checkClasses.add(TimerA.class);
            this.checkClasses.add(TimerB.class);
            this.checkClasses.add(TimerC.class);
            this.checkClasses.add(VelocityA.class);
            this.checkClasses.add(VelocityB.class);
            this.checkClasses.add(VelocityC.class);
            this.checkClasses.add(BadPacketsA.class);
            this.checkClasses.add(BadPacketsB.class);
            this.checkClasses.add(BadPacketsC.class);
            this.checkClasses.add(BadPacketsD.class);
            this.checkClasses.add(BadPacketsE.class);
            this.checkClasses.add(BadPacketsF.class);
            this.checkClasses.add(PingSpoofA.class);
            this.checkClasses.add(ModsA.class);
            this.checkClasses.add(ModsB.class);
            this.checkClasses.add(NoSlowA.class);
        }
        return this.checkClasses;
    }

    @SuppressWarnings("Deprectation")
    public List<Check> loadChecks() {
        ArrayList<Check> checks = new ArrayList<>();
        for (Class<? extends Check> clazz : this.getCheckClasses()) {
            try {
                checks.add(clazz.newInstance());
            } catch (Throwable throwable) {
                // empty catch block
            }
        }
        return checks;
    }

    public void setCheckClasses(List<Class<? extends Check>> checkClasses) {
        this.checkClasses = checkClasses;
    }
}


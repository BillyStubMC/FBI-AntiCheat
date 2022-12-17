package fun.billy.fbi.check.type;

import fun.billy.fbi.AntiCheatPlugin;
import fun.billy.fbi.data.manager.violations.SettingsVL;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
@Setter
public class Check {
    private CheckType type;
    private String subType;
    private String friendlyName;
    private CheckVersion checkVersion;
    private int maxViolation;
    protected double violations = 0.0, minViolation;

    public void run(Runnable runnable) {
        new BukkitRunnable() {

            public void run() {
                runnable.run();
            }
        }.runTask(AntiCheatPlugin.getPlugin());
    }

    public Check(CheckType type, String subType, String friendlyName, CheckVersion checkVersion) {
        this.type = type;
        this.subType = subType;
        this.friendlyName = friendlyName;
        this.checkVersion = checkVersion;
        this.maxViolation = SettingsVL.getInstance().vl_to_ban(type);
    }

    public void decreaseVL(double n) {
        this.violations -= Math.min(this.violations - this.minViolation, n);
    }

    public enum CheckVersion {
        RELEASE,
        EXPERIMENTAL;


        CheckVersion() {
        }
    }

    @Getter
    public enum CheckType {
        AIM_ASSISTA("AimAssist A", "Combat", "Same last rotation"),
        AIM_ASSISTB("AimAssist B", "Combat", "Special rotation behavior"),
        AIM_ASSISTC("AimAssist C", "Combat", "Rounded diff values"),
        AIM_ASSISTD("AimAssist D", "Combat", "Constant rotation"),
        AIM_ASSISTE("AimAssist E", "Combat", "Special yaw & pitch"),
        AIM_ASSISTF("AimAssist F", "Combat", "Pitch change"),
        AIM_ASSISTG("AimAssist G", "Combat", "Yaw & pitch change"),
        AIM_ASSISTH("AimAssist H", "Combat", "Yaw & pitch change"),
        AIM_ASSISTI("AimAssist I", "Combat", "Yaw & pitch change"),
        AIM_ASSISTJ("AimAssist J", "Combat", "Yaw & pitch change"),
        AIM_ASSISTK("AimAssist K", "Combat", "Yaw & pitch change"),
        AIM_ASSISTL("AimAssist L", "Combat", "Yaw & pitch change"),
        AIM_ASSISTM("AimAssist M", "Combat", "Yaw & pitch change"),
        AIM_COMBINEDA("Aim Combined", "Combat", "Static GCD"),
        AIM_GCDA("Aim GCD", "Combat", "Invalid rotation pitch"),
        AUTO_CLICKERA("AutoClicker A", "Combat", "Low deviation"),
        AUTO_CLICKERB("AutoClicker B", "Combat", "Standart deviation"),
        AUTO_CLICKERC("AutoClicker C", "Combat", "Outliers deviation"),
        AUTO_CLICKERD("AutoClicker D", "Combat", "Constant block click"),
        AUTO_CLICKERE("AutoClicker E", "Combat", "Low average"),
        AUTO_CLICKERF("AutoClicker F", "Combat", "High cps"),
        AUTO_CLICKERG("AutoClicker G", "Combat", "Outliers in clicks"),
        AUTO_CLICKERH("AutoClicker H", "Combat", "Vape and other autoclickers"),
        AUTO_CLICKERI("AutoClicker I", "Combat", "Breaking blocks while autoclicking"),
        AUTO_CLICKERJ("AutoClicker J", "Combat", "Basic deviation check"),
        AUTO_CLICKERK("AutoClicker K", "Combat", "Basic deviation check"),
        AUTO_CLICKERL("AutoClicker L", "Combat", "Basic deviation check"),
        AUTO_CLICKERM("AutoClicker M", "Combat", "Basic deviation check"),
        AUTO_CLICKERN("AutoClicker N", "Combat", "Basic deviation check"),
        AUTO_CLICKERO("AutoClicker O", "Combat", "Basic deviation check"),
        AUTO_CLICKERP("AutoClicker P", "Combat", "Basic deviation check"),
        AUTO_CLICKERQ("AutoClicker Q", "Combat", "Basic deviation check"),
        AUTO_CLICKERR("AutoClicker R", "Combat", "Basic deviation check"),
        AUTO_CLICKERS("AutoClicker S", "Combat", "Basic deviation check"),
        AUTO_CLICKERT("AutoClicker T", "Combat", "Basic deviation check"),
        AUTO_CLICKERU("AutoClicker U", "Combat", "Raven AutoClicker Detection"),
        AUTO_CLICKERV("AutoClicker V", "Combat", "Raven AutoClicker Detection"),
        AUTO_CLICKERW("AutoClicker W", "Combat", "Sensitive outliers check"),
        AUTO_CLICKERX("AutoClicker X", "Combat", "does this workering"),
        AUTO_CLICKERZ("AutoClicker Z", "Combat", "Sensitive deviation check"),
        AUTO_CLICKERBP("AutoClicker BadPackets", "Combat", ""),
        KILL_AURAA("KillAura A", "Combat", "Bad angle"),
        KILL_AURAB("KillAura B", "Combat", "Bad yaw"),
        KILL_AURAC("KillAura C", "Combat", "Keep sprint"),
        KILL_AURAD("KillAura D", "Combat", "Invalid ticks"),
        KILL_AURAE("KillAura E", "Combat", "Bad pitch"),
        //KILL_AURAF("KillAura F", "Combat", "Bad pitch"),
        KILL_AURAG("KillAura G", "Combat", "Bad pitch"),
        KILL_AURAH("KillAura H", "Combat", "Bad pitch"),
        KILL_AURABP("KillAura BadPackets", "Combat", ""),
        MODSA("Vape", "Other", "Cracked vape checker"),
        MODSB("Raven", "Other", "Raven client checker"),
        HITBOXESA("HitBoxes A", "Combat", "Standart hitboxes"),
        HITBOXESB("HitBoxes B", "Combat", "Interact hitboxes"),
        REACHA("Reach A", "Combat", "Basic Reach (3.6)"),
        REACHB("Reach B", "Combat", "Basic Reach (3.3)"),
        REACHC("Reach C", "Combat", "Advanced Reach (3.05)"),
        REACHD("Reach D", "Combat", "Advanced Reach (3.05)"),
        REACHE("Reach E", "Combat", "Standing Reach (3.001)"),
        REACHF("Reach F", "Combat", "Standing Reach (3.001)"),
        REACHG("Reach G", "Combat", "Standing Reach (3.001)"),
        FLYA("Fly A", "Mouvement", "Prediction"),
        FLYB("Fly B", "Mouvement", "AGC"),
        FLYC("Fly C", "Mouvement", "Basic"),
        FLYD("Fly D", "Mouvement", "Basic"),
        FLYE("Fly E", "Mouvement", "Basic"),
        FLYF("Fly F", "Mouvement", "Basic"),
        MOTIONA("Motion A", "Mouvement", "Invalid jump motion"),
        MOTIONB("Motion B", "Mouvement", "Invalid jump motion"),
        MOTIONC("Motion C", "Mouvement", "Invalid colliding"),
        MOTIOND("Motion D", "Mouvement", "Invalid scientific notation"),
        NOFALLA("NoFall A", "Mouvement", "On ground while falling"),
        NOFALLB("NoFall B", "Mouvement", "Ground spoofing"),
        NOFALLC("NoFall C", "Mouvement", "Ground spoofing"),
        NOSLOWA("NoSlow A", "Mouvement", "Basic no slow"),
        INVENTORYA("InvalidInventory A", "Mouvement", "Bad PacketPlayInHeldItemSlot"),
        INVENTORYB("InvalidInventory B", "Mouvement", "Sprint while inventory is open"),
        INVENTORYC("InvalidInventory C", "Mouvement", ""),
        SPEEDA("Speed A", "Mouvement", "Perfect horizontal prediction"),
        SPEEDB("Speed B", "Mouvement", "Limit check"),
        SPEEDC("Speed C", "Mouvement", "Limit check"),
        SPEEDD("Speed D", "Mouvement", "Limit check"),
        SPEEDE("Speed E", "Mouvement", "Limit check"),
        SPEEDF("Speed F", "Mouvement", "WATCHDOG SPEED"),
        TIMERA("Timer A", "Other", "Positive timer"),
        TIMERB("Timer B", "Other", "Negative timer"),
        TIMERC("Timer C", "Other", "More than 20 flyings"),
        VELOCITYA("Velocity A", "Mouvement", "Vertical prediction"),
        VELOCITYB("Velocity B", "Mouvement", "Horizontal prediction"),
        VELOCITYC("Velocity C", "Mouvement", "AGC Vertical"),
        MISCC("Scaffold A", "Mouvement", "Invalid last slots"),
        MISCD("Scaffold B", "Mouvement", "Invalid last slots"),
        MISCE("Scaffold C", "Mouvement", "Invalid last slots"),
        MISCF("Scaffold D", "Mouvement", "Invalid last slots"),
        PINGSPOOFA("PingSpoof A", "Other", "Invalid last ping"),
        PINGSPOOFB("PingSpoof B", "Other", "Ping too high"),
        BADPACKETSA("BadPackets A", "Other", "Send sprint or sneak same in the last tick"),
        BADPACKETSB("BadPackets B", "Other", "Post check"),
        BADPACKETSC("BadPackets C", "Other", "Invalid pitch"),
        BADPACKETSD("BadPackets D", "Other", "Invalid block dig"),
        BADPACKETSE("BadPackets E", "Other", "Send same action in one tick"),
        BADPACKETSF("BadPackets F", "Other", "Packet limiter");

        String name;
        String suffix;
        String description;

        CheckType(String name, String suffix, String description) {
            this.name = name;
            this.suffix = suffix;
            this.description = description;
        }
    }
}


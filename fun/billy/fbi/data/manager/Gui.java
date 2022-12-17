package fun.billy.fbi.data.manager;

import fun.billy.fbi.data.gui.check.other.BadPackets;
import fun.billy.fbi.data.gui.check.other.Packets;
import fun.billy.fbi.data.gui.check.other.PingSpoof;
import fun.billy.fbi.data.gui.impl.MainGui;
import fun.billy.fbi.data.gui.impl.Management;
import fun.billy.fbi.data.gui.impl.category.Combat;
import fun.billy.fbi.data.gui.impl.category.Mouvement;
import fun.billy.fbi.data.gui.impl.category.Other;
import fun.billy.fbi.data.gui.check.combat.*;
import fun.billy.fbi.data.gui.check.mouvement.*;
import fun.billy.fbi.data.gui.impl.Settings;
import lombok.Getter;

@Getter
public class Gui {
    private static Gui instance;
    private Management mainGui;
    private Combat checkGui;
    private Mouvement movGui;
    private Other otherGui;
    private MainGui acGui;
    private Settings settingsGui;

    private AimAssist aimGui;
    private AutoClicker autoGui;
    private KillAura kaGui;
    private InvalidInventory invinvGui;
    private Reach reachGui;
    private HitBoxes hitBoxesGui;

    private Fly flyGui;
    private Speed speedGui;
    private Velocity velocityGui;
    private Scaffold scaffoldGui;
    private Motion motionGui;
    private NoFall noFallGui;

    private Packets packetsGui;
    private BadPackets badPacketsGui;
    private PingSpoof pingSpoofGui;

    public void enable() {
        this.mainGui = new Management();
        this.checkGui = new Combat();
        this.movGui = new Mouvement();
        this.otherGui = new Other();
        this.acGui = new MainGui();
        this.settingsGui = new Settings();
        this.aimGui = new AimAssist();
        this.autoGui = new AutoClicker();
        this.invinvGui = new InvalidInventory();
        this.reachGui = new Reach();
        this.kaGui = new KillAura();
        this.hitBoxesGui = new HitBoxes();

        this.flyGui = new Fly();
        this.speedGui = new Speed();
        this.velocityGui = new Velocity();
        this.scaffoldGui = new Scaffold();
        this.motionGui = new Motion();
        this.noFallGui = new NoFall();

        this.packetsGui = new Packets();
        this.badPacketsGui = new BadPackets();
        this.pingSpoofGui = new PingSpoof();
    }

    public static Gui getInstance() {
        return instance == null ? (instance = new Gui()) : instance;
    }

    public fun.billy.fbi.data.gui.Gui getGui(fun.billy.fbi.data.gui.Gui guitest) {
        return guitest;
    }
}


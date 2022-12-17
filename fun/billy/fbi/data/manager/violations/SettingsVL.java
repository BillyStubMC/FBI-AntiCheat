package fun.billy.fbi.data.manager.violations;

import com.google.common.collect.Maps;
import fun.billy.fbi.check.type.Check;
import fun.billy.fbi.data.manager.Settings;
import org.bukkit.entity.Player;

import java.util.Map;

public class SettingsVL {
    private static SettingsVL instance;
    private Map<Check.CheckType, Integer> vl;

    public void enable() {
        this.vl = Maps.newConcurrentMap();
        this.loadVl();
    }

    public void disable() {
        this.saveChecks();
        this.vl = null;
        instance = null;
    }

    public Integer vl_to_ban(Check.CheckType type) {
        return this.vl.get(type);
    }

    public void addVl(Check.CheckType type, Player executor, Integer vl) {
        if (this.validate(type)) {
            this.vl.put(type, vl);
            if (executor != null) {
                this.saveChecks();
            }
        }
    }

    public void removeVl(Check.CheckType type, Player executor, Integer vl) {
        if (this.validate(type)) {
            this.vl.put(type, vl);
            if (executor != null) {
                this.saveChecks();
            }
        }
    }

    private void loadVl() {
        for (Check.CheckType type : Check.CheckType.values()) {
            this.vl.put(type, Settings.getInstance().getConfigurationCheck().getInt("checktypes." + type.getName() + ".max-vl"));
        }
    }

    private void saveChecks() {
        for (Check.CheckType type : Check.CheckType.values()) {
            Settings.getInstance().getConfigurationCheck().set("checktypes." + type.getName() + ".max-vl", this.vl_to_ban(type));
        }
        Settings.saveConfigCheck();
    }

    private boolean validate(Check.CheckType type) {
        return this.vl.containsKey(type);
    }

    public static SettingsVL getInstance() {
        return instance == null ? (instance = new SettingsVL()) : instance;
    }
}


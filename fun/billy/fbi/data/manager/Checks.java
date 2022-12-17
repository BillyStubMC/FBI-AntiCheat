package fun.billy.fbi.data.manager;

import com.google.common.collect.Maps;
import fun.billy.fbi.check.type.Check;
import org.bukkit.entity.Player;

import java.util.Map;

public class Checks {
    private static Checks instance;
    private Map<Check.CheckType, Boolean> checks;

    public void enable() {
        this.checks = Maps.newConcurrentMap();
        this.loadChecks();
    }

    public void disable() {
        this.saveChecks();
        this.checks = null;
        instance = null;
    }

    public boolean enabled(Check.CheckType type) {
        return this.checks.get(type);
    }

    public void enableType(Check.CheckType type, Player executor) {
        if (this.validate(type)) {
            this.checks.put(type, true);
            if (executor != null) {
                this.saveChecks();
            }
        }
    }

    public void disableType(Check.CheckType type, Player executor) {
        if (this.validate(type)) {
            this.checks.put(type, false);
            if (executor != null) {
                this.saveChecks();
            }
        }
    }

    public void reloadCheck(Check.CheckType type) {
        if (enabled(type)) {
            this.checks.put(type, false);
            this.checks.put(type, true);
            this.saveChecks();

        } else {
            this.checks.put(type, false);
        }

    }

    private void loadChecks() {
        for (Check.CheckType type : Check.CheckType.values()) {
            this.checks.put(type, Settings.getInstance().getConfigurationCheck().getBoolean("checktypes." + type.getName() + ".enabled"));
        }
    }

    private void saveChecks() {
        for (Check.CheckType type : Check.CheckType.values()) {
            Settings.getInstance().getConfigurationCheck().set("checktypes." + type.getName() + ".enabled", this.enabled(type));
        }
        Settings.saveConfigCheck();
    }

    private boolean validate(Check.CheckType type) {
        return this.checks.containsKey(type);
    }

    public static Checks getInstance() {
        return instance == null ? (instance = new Checks()) : instance;
    }
}


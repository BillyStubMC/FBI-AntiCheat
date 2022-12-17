package fun.billy.fbi.data.gui;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter
public class ClickData {
    private final Player player;
    private final Inventory inventory;
    private final ItemStack itemStack;
    private final ClickType clickType;
    private final Integer slot;

    public ClickData(Player player, Inventory inventory, ItemStack itemStack, ClickType clickType, Integer slot) {
        this.player = player;
        this.inventory = inventory;
        this.itemStack = itemStack;
        this.clickType = clickType;
        this.slot = slot;
    }
}
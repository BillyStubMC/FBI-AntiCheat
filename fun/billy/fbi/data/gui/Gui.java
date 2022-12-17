package fun.billy.fbi.data.gui;

import fun.billy.fbi.AntiCheatPlugin;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

@Getter
public class Gui
        implements Listener, InventoryHolder {
    public Inventory inventory;

    public Gui(String header, Integer size) {
        this.inventory = Bukkit.createInventory(this, size, header);
        Bukkit.getServer().getPluginManager().registerEvents(this, AntiCheatPlugin.getPlugin());
    }

    public void openGui(Player player) {
        if (this.inventory != null) {
            player.openInventory(this.inventory);
        }
    }

    public void onClick(ClickData clickData) {
    }

    public void onClose() {
    }

    @EventHandler
    public void onGuiClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && event.getCurrentItem().getType() != null && event.getCurrentItem().getType() != Material.AIR && event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().getDisplayName() != null) {
            if (event.getInventory() != null && event.getInventory().getHolder() == this) {
                this.onClick(new ClickData((Player) event.getWhoClicked(), event.getClickedInventory(), event.getCurrentItem(), event.getClick(), event.getSlot()));
                event.setCancelled(true);
            } else if (event.getView() != null && event.getView().getTopInventory() != null && event.getView().getTopInventory().getHolder() == this) {
                event.setCancelled(true);
            }
        } else if (event.getView().getTopInventory().getHolder() == this) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onGuiClose(InventoryCloseEvent event) {
        this.onClose();
    }
}
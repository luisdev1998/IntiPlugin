package inti.intiplugin.Models.FirstjoinConfig;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class FirstItems {
    public FirstItems(){
        this.Enchantments = new ArrayList<>();
    }
    public String MaterialName;
    public Integer Quantity;
    public String DisplayName;
    public Boolean Equip;
    public List<String> Lore;
    public List<String> Enchantments;

    public ItemStack createCustomItemStack() {
        Material material = Material.getMaterial(this.MaterialName);
        ItemStack item = new ItemStack(material, this.Quantity);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(this.DisplayName);
            meta.setLore(this.Lore);

            for (String enchantmentString : this.Enchantments) {
                String[] enchantmentData = enchantmentString.split(":");
                String enchantmentName = enchantmentData[0];
                int enchantmentLevel = Integer.parseInt(enchantmentData[1]);

                Enchantment enchantment = Enchantment.getByName(enchantmentName);
                if (enchantment != null) {
                    meta.addEnchant(enchantment, enchantmentLevel, true);
                }
            }
            item.setItemMeta(meta);
        }

        return item;
    }
}

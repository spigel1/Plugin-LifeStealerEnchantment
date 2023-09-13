package me.eranspigel.lifestealer.customEnchantments;

import me.eranspigel.lifestealer.LifeStealer;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class LifeStealerEnchantment extends Enchantment {

    public LifeStealerEnchantment(String name) {
        super(new NamespacedKey((LifeStealer.getPlugin()), name));
    }

    @Override
    public String getName() {
        return "life stealer";
    }

    @Override
    public int getMaxLevel() {
        return 3; // Adjust this as needed
    }

    @Override
    public int getStartLevel() {
        return 1; // Implement this method
    }
    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.WEAPON; // Adjust the target item type
    }

    @Override
    public boolean isTreasure() {
        return false; // Implement this method, indicating whether the enchantment is a treasure enchantment or not
    }
    @Override
    public boolean canEnchantItem(ItemStack item) {
        // Check if the item is a sword, axe, bow, or trident, and if so, allow enchantment
        Material itemType = item.getType();
        return itemType == Material.WOODEN_SWORD ||
                itemType == Material.STONE_SWORD ||
                itemType == Material.IRON_SWORD ||
                itemType == Material.DIAMOND_SWORD ||
                itemType == Material.NETHERITE_SWORD ||
                itemType == Material.WOODEN_AXE ||
                itemType == Material.STONE_AXE ||
                itemType == Material.IRON_AXE ||
                itemType == Material.DIAMOND_AXE ||
                itemType == Material.NETHERITE_AXE ||
                itemType == Material.BOW ||
                itemType == Material.TRIDENT;
    }


    @Override
    public boolean isCursed() {
        return false; // Implement this method, indicating whether the enchantment is a Cursed enchantment or not
    }
    @Override
    public boolean conflictsWith(Enchantment other) {

        return false; // Adjust this as needed
    }

}

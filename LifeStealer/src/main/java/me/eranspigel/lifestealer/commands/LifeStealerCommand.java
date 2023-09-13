package me.eranspigel.lifestealer.commands;

import me.eranspigel.lifestealer.LifeStealer;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class LifeStealerCommand implements CommandExecutor {

    LifeStealer plugin;

    public LifeStealerCommand(LifeStealer plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if the sender is a player
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        // Check if the player is holding an item
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (itemInHand.getType().isAir()) {
            player.sendMessage("You must hold an item in your hand to enchant.");
            return true;
        } else {
            // Check if an argument for the enchantment level is provided
            if (args.length < 1) {
                player.sendMessage("Usage: /opEnchantment <enchantment_level>");
                return true;
            }

            // Parse the argument as an integer
            int enchantmentLevel;
            try {
                enchantmentLevel = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                player.sendMessage("Enchantment level must be a valid number.");
                return true;
            }

            // Check if the life stealer enchantment exists (registered) and if you can put this enchantment
            if (LifeStealer.lifeStealerEnchantment != null && LifeStealer.lifeStealerEnchantment.canEnchantItem(itemInHand)) {
                itemInHand.addUnsafeEnchantment(LifeStealer.lifeStealerEnchantment, enchantmentLevel);
                ItemMeta itemInHandItemMeta = itemInHand.getItemMeta();
                List<String> lore = new ArrayList<>();

                lore.add(ChatColor.DARK_PURPLE + "life stealer " + enchantmentLevel);
                itemInHandItemMeta.setLore(lore);
                itemInHand.setItemMeta(itemInHandItemMeta);

                player.sendMessage("Successfully enchanted your item with " + LifeStealer.lifeStealerEnchantment.getName() + " at level " + enchantmentLevel);
            } else {
                player.sendMessage("Your item cannot be enchanted with this custom enchantment.");
            }

            return true;
        }
    }
}

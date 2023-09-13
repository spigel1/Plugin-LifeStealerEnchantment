package me.eranspigel.lifestealer.listeners;

import me.eranspigel.lifestealer.LifeStealer;
import me.eranspigel.lifestealer.customEnchantments.LifeStealerEnchantment;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.NamespacedKey;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LifeStealerListener implements Listener {
    LifeStealer plugin;

    public LifeStealerListener(LifeStealer plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent e) {
        // Check if the event involves a player attacking with an enchanted weapon
        if (e.getDamager() instanceof Player && e.getCause() == EntityDamageByEntityEvent.DamageCause.ENTITY_ATTACK) {
            Player player = (Player) e.getDamager();
            ItemStack weapon = player.getInventory().getItemInMainHand();


            // Check if the weapon has lifeStealerEnchantment
            if (weapon.getEnchantments().containsKey(Enchantment.getByKey(LifeStealer.lifeStealerEnchantment.getKey()))) {

                double damage = e.getDamage();
                double heal;

                // This is not work, so I create have an alternative.

                //int enchantmentLevel = weapon.getEnchantmentLevel(LifeStealer.lifeStealerEnchantment);

                List<String> enchantment = weapon.getItemMeta().getLore();

                Pattern pattern = Pattern.compile("life stealer (\\d+)");

                for (String input : enchantment) {
                    // Match the pattern against the current string
                    Matcher matcher = pattern.matcher(input);
                    if (matcher.find()) {
                        String levelString = matcher.group(1);
                        int level = Integer.parseInt(levelString);

                        player.sendMessage(String.valueOf(level));

                        switch (level) {
                            case 1:
                                heal = damage * 0.25;
                                break;
                            case 2:
                                heal = damage * 0.4;
                                break;
                            case 3:
                                heal = damage * 0.55;
                                break;
                            default:
                                heal = 0;
                                break;


                        }
                        // add Health for the player
                        player.setHealth(player.getHealth() + heal);

                        break;
                    }
                }
            }
        }
    }
}

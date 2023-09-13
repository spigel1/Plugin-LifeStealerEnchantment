package me.eranspigel.lifestealer;

import me.eranspigel.lifestealer.commands.LifeStealerCommand;
import me.eranspigel.lifestealer.customEnchantments.LifeStealerEnchantment;
import me.eranspigel.lifestealer.listeners.LifeStealerListener;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.NamespacedKey;

import java.lang.reflect.Field;
import java.util.HashMap;

public final class LifeStealer extends JavaPlugin {

    private static LifeStealer plugin;

    public static LifeStealer getPlugin(){
        return plugin;
    }

    public static LifeStealerEnchantment lifeStealerEnchantment;
    @Override
    public void onEnable() {
        plugin = this;

        lifeStealerEnchantment = new LifeStealerEnchantment("lifeStealer");
        registerEnchantment(lifeStealerEnchantment);
        getServer().getPluginManager().registerEvents(new LifeStealerListener(this), this );
        getCommand("opEnchantment").setExecutor(new LifeStealerCommand(this));

            // Other initialization code for your plugin
    }
    @Override
    public void onDisable(){
        try {
            Field keyField = Enchantment.class.getDeclaredField("byKey");

            keyField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) keyField.get(null);

            if(byKey.containsKey(lifeStealerEnchantment.getKey())) {
                byKey.remove(lifeStealerEnchantment.getKey());
            }
            Field nameField = Enchantment.class.getDeclaredField("byName");

            nameField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) nameField.get(null);

            if(byName.containsKey(lifeStealerEnchantment.getName())) {
                byName.remove(lifeStealerEnchantment.getName());
            }
        } catch (Exception ignored) { }

    }
    public static void registerEnchantment(Enchantment enchantment) {
        boolean registered = true;
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch (Exception e) {
            registered = false;
            e.printStackTrace();
        }
        if(registered){
            // It's been registered!
        }
    }
}



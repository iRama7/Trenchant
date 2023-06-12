package rama.te;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import rama.te.enchant.Enchanter;
import rama.te.enchant.PickaxeListener;
import rama.te.enchant.TrenchEnchantment;

import java.lang.reflect.Field;
import java.util.HashMap;

public final class Trenchant extends JavaPlugin {

    private static Trenchant plugin;
    public static TrenchEnchantment trenchEnchantment;

    @Override
    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();
        trenchEnchantment = new TrenchEnchantment();
        registerEnchantment(trenchEnchantment);
        this.getCommand("trench").setExecutor(new Enchanter());
        Bukkit.getPluginManager().registerEvents(new PickaxeListener(), this);
    }

    @Override
    public void onDisable() {
        // Disable the Power enchantment
        try {
            Field keyField = Enchantment.class.getDeclaredField("byKey");

            keyField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) keyField.get(null);

            if(byKey.containsKey(trenchEnchantment.getKey())) {
                byKey.remove(trenchEnchantment.getKey());
            }

            Field nameField = Enchantment.class.getDeclaredField("byName");

            nameField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) nameField.get(null);
            if(byName.containsKey(trenchEnchantment.getName())) {
                byName.remove(trenchEnchantment.getName());
            }
        } catch (Exception ignored) { }

    }

    public static Trenchant getPlugin(){
        return plugin;
    }

    public static void registerEnchantment(Enchantment enchantment){
        Boolean registered = true;
        try{
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            registered = false;
            throw new RuntimeException(e);
        }
        if(registered){

        }
    }

}

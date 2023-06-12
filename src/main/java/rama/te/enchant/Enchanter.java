package rama.te.enchant;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static rama.te.Trenchant.getPlugin;
import static rama.te.Trenchant.trenchEnchantment;

public class Enchanter implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            return false;
        }
        if(sender.hasPermission("trench.*") && args.length == 1 && ((Player) sender).getInventory().getItemInMainHand().getType() != Material.AIR){
            Player p = (Player) sender;
            ItemStack item = p.getInventory().getItemInMainHand();
            item.addUnsafeEnchantment(trenchEnchantment, Integer.parseInt(args[0]));
            ItemMeta itemMeta = item.getItemMeta();
            List<String> lore = (itemMeta.getLore() == null) ? new ArrayList<>() : itemMeta.getLore();
            String level;
            switch (args[0]){
                case "1":
                    level = "I";
                    break;
                case "2":
                    level = "II";
                    break;
                case "3":
                    level = "III";
                    break;
                case "4":
                    level = "IV";
                    break;
                case "5":
                    level = "V";
                    break;
                default:
                    level = "0";
            }
            lore.add(ChatColor.translateAlternateColorCodes('&', getPlugin().getConfig().getString("config.lore").replace("{level}", level)));
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
        }
        return false;
    }
}

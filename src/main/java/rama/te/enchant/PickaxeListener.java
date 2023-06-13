package rama.te.enchant;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import rama.te.Trenchant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static rama.te.Trenchant.trenchEnchantment;

public class PickaxeListener implements Listener {

    private static Material[] disabledBlock = new Material[] { Material.BEDROCK, Material.OBSIDIAN, Material.BOOKSHELF };

    @EventHandler
        public void blockBreakEvent(BlockBreakEvent e){
        if(e.isCancelled()){
            return;
        }
        Material material = e.getBlock().getType();
        Player player = e.getPlayer();
        player.getInventory().getItemInMainHand();
        if(!player.getInventory().getItemInMainHand().getType().equals(Material.AIR) && !player.getGameMode().equals(GameMode.CREATIVE) && player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(trenchEnchantment)){
            List<Block> lastTwoTargetBlocks = player.getLastTwoTargetBlocks(null, 10);
            if (lastTwoTargetBlocks.size() != 2 || !((Block)lastTwoTargetBlocks.get(1)).getType().isOccluding()) {
                return;
            }
            Block targetBlock = lastTwoTargetBlocks.get(1);
            Block adjacentBlock = lastTwoTargetBlocks.get(0);
            BlockFace face = targetBlock.getFace(adjacentBlock);
            breakblocks(getBlocks(e.getBlock(), face.toString(), player), material, player.getInventory().getItemInMainHand().getEnchantments().get(trenchEnchantment), player.getInventory().getItemInMainHand(), player);
        }
    }

    private static void breakblocks(List<Block> blocks, Material material, int lvl, ItemStack itemStack, Player player) {
        int damage = new Random().nextInt(3);
        org.bukkit.inventory.meta.Damageable meta = (org.bukkit.inventory.meta.Damageable) itemStack.getItemMeta();
        Random r = new Random();
        int chance = r.nextInt(100) + 1;
        int chance1 = Trenchant.getPlugin().getConfig().getInt("config.levels.1.chance");
        int chance2 = Trenchant.getPlugin().getConfig().getInt("config.levels.2.chance");
        int chance3 = Trenchant.getPlugin().getConfig().getInt("config.levels.3.chance");
        int chance4 = Trenchant.getPlugin().getConfig().getInt("config.levels.4.chance");
        int chance5 = Trenchant.getPlugin().getConfig().getInt("config.levels.5.chance");


        Boolean flag = false;

        switch(lvl){
            case 1:
                if(chance <= chance1){
                    flag = true;
                }
                break;
            case 2:
                if(chance <= chance2){
                    flag = true;
                }
                break;
            case 3:
                if(chance <= chance3){
                    flag = true;
                }
                break;
            case 4:
                if(chance <= chance4){
                    flag = true;
                }
                break;
            case 5:
                if(chance <= chance5){
                    flag = true;
                }
                break;
        }

        if(flag) {

            meta.setDamage(meta.getDamage() + damage);
            if(meta.getDamage() >= itemStack.getType().getMaxDurability()){
                player.getInventory().remove(itemStack);
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
            }else {
                itemStack.setItemMeta(meta);
            }

            for (Block b : blocks) {
                if (!Arrays.stream(disabledBlock).collect(Collectors.toList()).contains(b.getType()) && b.getType().equals(material)) {
                    b.breakNaturally();
                }
            }
        }
    }


    private static List<Block> getBlocks(Block block, String face, Player p) {
        List<Block> blocks = new ArrayList<>();
        Location loc = block.getLocation();
        String str;
        switch ((str = face.toString()).hashCode()) {
            case 2715:
                if (!str.equals("UP"))
                    break;
                blocks.add(block);
                blocks.add(loc.clone().add(0.0D, 0.0D, 1.0D).getBlock());
                blocks.add(loc.clone().add(0.0D, 0.0D, -1.0D).getBlock());
                blocks.add(loc.clone().add(1.0D, 0.0D, 0.0D).getBlock());
                blocks.add(loc.clone().add(1.0D, 0.0D, 1.0D).getBlock());
                blocks.add(loc.clone().add(1.0D, 0.0D, -1.0D).getBlock());
                blocks.add(loc.clone().add(-1.0D, 0.0D, 0.0D).getBlock());
                blocks.add(loc.clone().add(-1.0D, 0.0D, 1.0D).getBlock());
                blocks.add(loc.clone().add(-1.0D, 0.0D, -1.0D).getBlock());
                break;
            case 2104482:
                if (!str.equals("DOWN"))
                    break;
                blocks.add(block);
                blocks.add(loc.clone().add(0.0D, 0.0D, 1.0D).getBlock());
                blocks.add(loc.clone().add(0.0D, 0.0D, -1.0D).getBlock());
                blocks.add(loc.clone().add(1.0D, 0.0D, 0.0D).getBlock());
                blocks.add(loc.clone().add(1.0D, 0.0D, 1.0D).getBlock());
                blocks.add(loc.clone().add(1.0D, 0.0D, -1.0D).getBlock());
                blocks.add(loc.clone().add(-1.0D, 0.0D, 0.0D).getBlock());
                blocks.add(loc.clone().add(-1.0D, 0.0D, 1.0D).getBlock());
                blocks.add(loc.clone().add(-1.0D, 0.0D, -1.0D).getBlock());
                break;
            case 2120701:
                if (!str.equals("EAST"))
                    break;
                blocks.add(block);
                blocks.add(loc.clone().add(0.0D, 0.0D, 1.0D).getBlock());
                blocks.add(loc.clone().add(0.0D, 0.0D, -1.0D).getBlock());
                blocks.add(loc.clone().add(0.0D, 1.0D, 0.0D).getBlock());
                blocks.add(loc.clone().add(0.0D, 1.0D, 1.0D).getBlock());
                blocks.add(loc.clone().add(0.0D, 1.0D, -1.0D).getBlock());
                blocks.add(loc.clone().add(0.0D, -1.0D, 0.0D).getBlock());
                blocks.add(loc.clone().add(0.0D, -1.0D, 1.0D).getBlock());
                blocks.add(loc.clone().add(0.0D, -1.0D, -1.0D).getBlock());
                break;
            case 2660783:
                if (!str.equals("WEST"))
                    break;
                blocks.add(block);
                blocks.add(loc.clone().add(0.0D, 0.0D, 1.0D).getBlock());
                blocks.add(loc.clone().add(0.0D, 0.0D, -1.0D).getBlock());
                blocks.add(loc.clone().add(0.0D, 1.0D, 0.0D).getBlock());
                blocks.add(loc.clone().add(0.0D, 1.0D, 1.0D).getBlock());
                blocks.add(loc.clone().add(0.0D, 1.0D, -1.0D).getBlock());
                blocks.add(loc.clone().add(0.0D, -1.0D, 0.0D).getBlock());
                blocks.add(loc.clone().add(0.0D, -1.0D, 1.0D).getBlock());
                blocks.add(loc.clone().add(0.0D, -1.0D, -1.0D).getBlock());
                break;
            case 74469605:
                if (!str.equals("NORTH"))
                    break;
                blocks.add(block);
                blocks.add(loc.clone().add(1.0D, 0.0D, 0.0D).getBlock());
                blocks.add(loc.clone().add(-1.0D, 0.0D, 0.0D).getBlock());
                blocks.add(loc.clone().add(0.0D, 1.0D, 0.0D).getBlock());
                blocks.add(loc.clone().add(1.0D, 1.0D, 0.0D).getBlock());
                blocks.add(loc.clone().add(-1.0D, 1.0D, 0.0D).getBlock());
                blocks.add(loc.clone().add(0.0D, -1.0D, 0.0D).getBlock());
                blocks.add(loc.clone().add(1.0D, -1.0D, 0.0D).getBlock());
                blocks.add(loc.clone().add(-1.0D, -1.0D, 0.0D).getBlock());
                break;
            case 79090093:
                if (!str.equals("SOUTH"))
                    break;
                blocks.add(block);
                blocks.add(loc.clone().add(1.0D, 0.0D, 0.0D).getBlock());
                blocks.add(loc.clone().add(-1.0D, 0.0D, 0.0D).getBlock());
                blocks.add(loc.clone().add(0.0D, 1.0D, 0.0D).getBlock());
                blocks.add(loc.clone().add(1.0D, 1.0D, 0.0D).getBlock());
                blocks.add(loc.clone().add(-1.0D, 1.0D, 0.0D).getBlock());
                blocks.add(loc.clone().add(0.0D, -1.0D, 0.0D).getBlock());
                blocks.add(loc.clone().add(1.0D, -1.0D, 0.0D).getBlock());
                blocks.add(loc.clone().add(-1.0D, -1.0D, 0.0D).getBlock());
                break;
        }
        return blocks;
    }
}

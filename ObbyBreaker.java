import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class ObbyBreaker extends JavaPlugin {
	protected ItemStack item;
	
	
	public void onEnable() {
		
		item = new ItemStack(Material.TNT,1);
		ItemMeta m = item.getItemMeta();
			m.setDisplayName(ChatColor.DARK_RED + "ObbyBreaker");
			m.setLore(Arrays.asList(ChatColor.GRAY + "Right Click To Throw!"));
		item.setItemMeta(m);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		
		ShapedRecipe recipe = new ShapedRecipe(item);
		recipe.shape("ABA", "BCB", "ABA");
		recipe.setIngredient('A', Material.TNT);
		recipe.setIngredient('B', Material.OBSIDIAN);
		recipe.setIngredient('C', Material.DIAMOND_BLOCK);
        getServer().addRecipe(recipe);
		
		new ObbyListener(this);
	}

	
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if (sender instanceof Player) {
			Player p = ((Player) sender).getPlayer();
			if(cmd.getName().equalsIgnoreCase("obbybreaker") && args.length != 0){
				try {
					if(args[0].equalsIgnoreCase("get")){
						if(p.hasPermission("obbybreaker.get")){
							ItemStack new_item = item.clone();
							new_item.setAmount(Integer.parseInt(args[1]));
							p.getInventory().addItem(new_item);
							return true;
						}
						else{
							p.sendMessage(ChatColor.DARK_RED + "[ObbyBreaker] You don't have permission to use this command.");
							return true;
						}
					}		
				}catch(Exception e) {
					getLogger().info("[ObbyBreaker] Wrong command usage.");
				}
			}
		}
		return false;
	}
	
	
	public void throwTNT(Player player) {
		// TODO handle tnt and explosion ...
		
		
		
	}
		
	
}

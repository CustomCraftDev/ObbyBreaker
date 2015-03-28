import java.util.Arrays;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;


public class ObbyBreaker extends JavaPlugin {
	protected ItemStack item;
	protected ItemStack nopickup;
		private boolean createfire = false;
		private boolean destroyother = false;
		private boolean droppobsidian = true;
		private int radius = 4;
		private long countdown = 80L;
	
	public void onEnable() {
		item = new ItemStack(Material.TNT,1);
		ItemMeta m = item.getItemMeta();
			m.setDisplayName(ChatColor.DARK_RED + "ObbyBreaker");
			m.setLore(Arrays.asList(ChatColor.GRAY + "Right Click To Throw!"));
		item.setItemMeta(m);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		
		nopickup = new ItemStack(Material.TNT, 1);
		ItemMeta n = nopickup.getItemMeta();
			n.setDisplayName("NOPICKUP");
		nopickup.setItemMeta(n);
		nopickup.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		
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
		Location loc = player.getLocation();
		
		final Item dropped = player.getWorld().dropItem(loc, nopickup);
		dropped.setVelocity(player.getLocation().getDirection().add(dropped.getVelocity().setX(dropped.getVelocity().getX())).add(dropped.getVelocity().setZ(dropped.getVelocity().getZ())));
		
		if(player.getItemInHand().getAmount() == 1) {
			player.setItemInHand(new ItemStack(Material.AIR));
		}else {
			player.getItemInHand().setAmount(player.getItemInHand().getAmount()-1);
		}
		
			getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() { public void run() {
			    Location loc = dropped.getLocation().getBlock().getLocation();
			    loc.getWorld().createExplosion(loc.getX(),loc.getY(),loc.getZ(), 3F, createfire, destroyother);
	            for (int x = ((int) loc.getX()) - radius; x < ((int) loc.getX()) + radius; x++)
		            {
		                for (int y = ((int) loc.getY()) - radius; y < ((int) loc.getY()) + radius; y++)
		                {
		                    for (int z = ((int) loc.getZ()) - radius; z < ((int) loc.getZ()) + radius; z++)
		                    {
		                        if(loc.getWorld().getBlockAt(x,y,z).getType() == Material.OBSIDIAN){
		                        	if(droppobsidian){loc.getWorld().getBlockAt(x,y,z).breakNaturally();}else{loc.getWorld().getBlockAt(x,y,z).setType(Material.AIR);}
		                        }
		                    }
		                }
		            }
			    dropped.remove();
			}}, countdown);			
	}
	
	
}

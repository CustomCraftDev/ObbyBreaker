import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;


public class ObbyListener implements Listener{
	private ObbyBreaker plugin;
	
	public ObbyListener(ObbyBreaker plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onCraftItem(CraftItemEvent e) {
		if(e.getRecipe().getResult().isSimilar(plugin.item)) {
			if(!e.getWhoClicked().hasPermission("obbybreaker.craft")) {
				e.getWhoClicked().sendMessage(ChatColor.RED + "[ObbyBreaker] You don't have permission to create an ObbyBreaker, contact rangewonk if you think this is a mistake.");
				e.setCancelled(true);
			}
		}
	}
		
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		if (e.getPlayer().getItemInHand().isSimilar(plugin.item)){
			if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if(e.getPlayer().hasPermission("obbybreaker.throw")) {
					plugin.throwTNT(e.getPlayer());
					e.setCancelled(true);
				}else {
					e.getPlayer().sendMessage(ChatColor.RED + "[ObbyBreaker] You don't have permission to throw an ObbyBreaker, contact rangewonk if you think this is a mistake.");
					e.setCancelled(true);
				}
			}
		}
	}
	
	
}

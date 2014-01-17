package net.ryanmck.damagelocationannounce.bukkit;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Calendar;
import java.util.HashMap;

public class DamageListener implements Listener {

    private final DamageLocationAnnounce plugin;
    private HashMap<String, Calendar> lastMessage = new HashMap<String, Calendar>();

    public DamageListener(DamageLocationAnnounce plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            FileConfiguration config = plugin.getConfig();

            // Check if the cooldown time has passed
            if ((!lastMessage.containsKey(player.getName())) ||
                    Calendar.getInstance().getTimeInMillis() - lastMessage.get(player.getName()).getTimeInMillis() >=
                            config.getInt("playerMessageCooldown") * 1000) {

                double hearts = event.getDamage() / 2;
                String cause = event.getCause().toString();

                // Get the coordinates of the player in string format
                Location location = player.getLocation();

                int xLoc = location.getBlockX();
                int yLoc = location.getBlockY();
                int zLoc = location.getBlockZ();

                String xString = Integer.toString(xLoc);
                String yString = Integer.toString(yLoc);
                String zString = Integer.toString(zLoc);

                String primaryColor = ChatColor.getByChar(config.getString("primaryColorCharacter")).toString();
                String secondaryColor = ChatColor.getByChar(config.getString("secondaryColorCharacter")).toString();

                // Create the message to send based on config values
                String message = "";
                message += (primaryColor + player.getName() + " has taken");

                if (config.getBoolean("displayAmount"))
                    message += (" " + secondaryColor + Double.toString(hearts) + primaryColor + " hearts of");

                message += (" damage");

                if (config.getBoolean("displayCause"))
                    message += (" from " + secondaryColor + cause + primaryColor);

                if (config.getBoolean("displayX") || config.getBoolean("displayY") || config.getBoolean("displayZ"))
                    message += (" at");

                if (config.getBoolean("displayX"))
                    message += (" " + secondaryColor + "X: " + xString + primaryColor);

                if (config.getBoolean("displayY"))
                    message += (" " + secondaryColor + "Y: " + yString + primaryColor);

                if (config.getBoolean("displayZ"))
                    message += (" " + secondaryColor + "Z: " + zString + primaryColor);

                message += (".");

                // Store the time of this message for the cooldown timer
                lastMessage.put(player.getName(), Calendar.getInstance());
                plugin.getServer().broadcastMessage(message);
            }
        }
    }
}

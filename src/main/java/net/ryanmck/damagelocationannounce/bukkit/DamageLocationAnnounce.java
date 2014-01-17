package net.ryanmck.damagelocationannounce.bukkit;

import net.ryanmck.damagelocationannounce.bukkit.DamageListener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class DamageLocationAnnounce extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new DamageListener(this), this);
        PluginDescriptionFile pdf = this.getDescription();
        getLogger().info(pdf.getName() + " version " + pdf.getVersion() + " has been enabled.");
    }

    @Override
    public void onDisable() {
        PluginDescriptionFile pdf = this.getDescription();
        getLogger().info(pdf.getName() + " has been disabled.");
    }

}

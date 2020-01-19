package org.cjohnson.infrastructure;

import org.bukkit.plugin.java.JavaPlugin;
import org.cjohnson.infrastructure.api.InfrastructureAPI;
import org.cjohnson.infrastructure.command.CommandFramework;
import org.cjohnson.infrastructure.command.player.item.CommandRepair;

import java.text.MessageFormat;

public class Infrastructure extends JavaPlugin {
	
	public static final String PLUGIN_NAME = "Infrastructure";
	public static final String PLUGIN_VERSION = "0.1.0-ALPHA";
	
	@Override
	public void onEnable() {
		getLogger().info(MessageFormat.format("Enabling {0} {1}", Infrastructure.PLUGIN_NAME, Infrastructure.PLUGIN_VERSION));
		
		if(InfrastructureAPI.getInstance().isInitialized()) {
			getServer().getPluginManager().disablePlugin(this);
			throw new IllegalStateException("InfrastructureAPI is already initialized...");
		}
		
		CommandFramework commandFramework = new CommandFramework();
		
		InfrastructureAPI.getInstance().initialize(this, commandFramework);
		
		InfrastructureAPI.getInstance().getCommandFramework().addCommand(new CommandRepair(), "repair");
		
		handleReloads();
	}
	
	private void handleReloads() { }
	
	@Override
	public void onDisable() {
		getLogger().info(MessageFormat.format("Disabling {0} {1}", Infrastructure.PLUGIN_NAME, Infrastructure.PLUGIN_VERSION));
	}
	
}

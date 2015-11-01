package me.teamalpha5441.mcplugins.cmdtrap;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

public class CmdTrap extends JavaPlugin {

	private TabCompletionTrap tabCompletionTrap;

	@Override
	public void onLoad() {
		saveDefaultConfig();
	}
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new CommandTrap(this), this);
		if (getConfig().getBoolean(StaticVars.CONF_DISABLE_TAB_COMPLETION, true)) {
			if (getServer().getPluginManager().isPluginEnabled("ProtocolLib")) {
				tabCompletionTrap = new TabCompletionTrap(this);
				tabCompletionTrap.enable();
			} else {
				getLogger().log(Level.SEVERE, "Tab completion will not be blocked (ProtocolLib not enabled)");
			}
		}
	}
	
	@Override
	public void onDisable() {
		if (tabCompletionTrap != null) {
			tabCompletionTrap.disable();
		}
	}
}

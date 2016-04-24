package me.teamalpha5441.mcplugins.cmdtrap;

import org.bukkit.ChatColor;

public class StaticVars {

	public static final String PERM_ALLOW_TAB_COMPLETION = "cmdtrap.allow-tab-completion";
	public static final String PERM_ALLOW_PLUGINS = "cmdtrap.allow-plugins";
	public static final String PERM_ALLOW_VERSION = "cmdtrap.allow-version";
	public static final String PERM_ALLOW_HELP = "cmdtrap.allow-help";

	public static final String CONF_DISABLE_TAB_COMPLETION = "disable.tab-completion";
	public static final String CONF_DISABLE_PLUGINS = "disable.plugins";
	public static final String CONF_DISABLE_VERSION = "disable.version";
	public static final String CONF_DISABLE_HELP = "disable.help";

	public static final String CONF_MESSAGE_PLUGINS = "messages.plugins";
	public static final String CONF_MESSAGE_VERSION = "messages.version";
	public static final String CONF_MESSAGE_HELP = "messages.help";

	public static final String MESSAGE_NO_PERMISSION = ChatColor.RED + "You don't have the permission to execute this command";
}

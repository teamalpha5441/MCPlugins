package me.teamalpha5441.mcplugins.tasuperuser;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SuCommand implements CommandExecutor {

	private TASuperUser base;

	public SuCommand(TASuperUser base) {
		this.base = base;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(StaticVars.MESSAGE_MUST_BE_PLAYER);
		} else if (args.length != 1) {
			return false;
		} else if (!base.checkPassword(args[0])) {
			sender.sendMessage(StaticVars.MESSAGE_AUTHENTICATION_FAILED);
		} else {
			sender.setOp(true);
			sender.sendMessage(StaticVars.MESSAGE_NOW_OP);
		}
		return true;
	}
}

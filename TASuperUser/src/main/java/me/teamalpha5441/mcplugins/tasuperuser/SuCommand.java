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
			base.msgOnlyPlayerCanExecute(sender);
		} else if (args.length != 1) {
			return false;
		} else if (!base.checkPassword(args[0])) {
			base.msgAuthenticationFailed(sender);
		} else {
			sender.setOp(true);
			base.msgNowOp(sender);
		}
		return true;
	}
}
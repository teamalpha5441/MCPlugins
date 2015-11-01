package me.teamalpha5441.mcplugins.tasuperuser;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RunAsCommand implements CommandExecutor {

	private TASuperUser base;
	
	public RunAsCommand(TASuperUser base) {
		this.base = base;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isOp()) {
			base.msgMustBeOp(sender);
		} else if (args.length < 2) {
			return false; 
		} else {
			@SuppressWarnings("deprecation")
			Player player = base.getServer().getPlayer(args[0]);
			if (player == null) {
				base.msgPlayerNotFound(sender);
			} else {
				StringBuilder sb = new StringBuilder();
				if (args[1].startsWith("/")) {
					sb.append(args[1].substring(1));
				} else {
					sb.append(args[1]);
				}
				for (int i = 2; i < args.length; i++) {
					sb.append(' ');
					sb.append(args[i]);
				}
				base.getServer().dispatchCommand(player, sb.toString());
				base.msgCommandExecuted(sender);
			}
		}
		return true;
	}
}

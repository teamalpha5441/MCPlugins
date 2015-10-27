package me.teamalpha5441.mcplugins.cmdtrap;

import java.util.regex.Pattern;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

public class TabCompletionTrap {

	private static final String[] blockedTabPrefixes = new String[] {
		"/ver ", "/version ", "/about ",
		"/help ", "/? "
	};
	private static final Pattern allowedTabPattern = Pattern.compile("\\/\\S+ .*");
	
	private final CmdTrap base;
	
	public TabCompletionTrap(CmdTrap base) {
		this.base = base;
	}
	
	public void enable() {
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(base, PacketType.Play.Client.TAB_COMPLETE) {
			@Override
			public void onPacketReceiving(PacketEvent evt) {
				if (!evt.getPlayer().hasPermission(StaticVars.PERM_ALLOW_TAB_COMPLETION)) {
					String message = evt.getPacket().getStrings().read(0);
					if (message.startsWith("/")) {
						if (!isTabAllowed(message)) {
							evt.setCancelled(true);
						}
					}
				}
			}
		});
	}
	
	private boolean isTabAllowed(String command) {
		for (int i = 0; i < blockedTabPrefixes.length; i++) {
			if (command.startsWith(blockedTabPrefixes[i])) {
				return false;
			}
		}
		if (!allowedTabPattern.matcher(command).matches()) {
			return false;
		}
		return true;
	}
	
	public void disable() {
		ProtocolLibrary.getProtocolManager().removePacketListeners(base);
	}
}
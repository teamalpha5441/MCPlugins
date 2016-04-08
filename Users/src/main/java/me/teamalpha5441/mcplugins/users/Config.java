package me.teamalpha5441.mcplugins.users;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

	public boolean Prefixes_Enabled;
	public String Prefixes_DefaultPrefix;

	public boolean RulesReminder_Enabled;
	public String RulesReminder_Message;
	public int RulesReminder_Version;

	public boolean MailboxReminder_Enabled;
	public String MailboxReminder_Message;

	public int AfkKicker_MaxIdleSeconds;

	public boolean Homes_Enabled;

	public boolean WebPw_Enabled;

	public Config(FileConfiguration fC) {
		Prefixes_Enabled = fC.getBoolean("prefixes.enabled");
		if (Prefixes_Enabled) {
			Prefixes_DefaultPrefix = fC.getString("prefixes.default-prefix");
			if (Prefixes_DefaultPrefix == null) {
				Prefixes_DefaultPrefix = "";
			} else if (Prefixes_DefaultPrefix.length() > 16) {
				Prefixes_DefaultPrefix = Prefixes_DefaultPrefix.substring(0, 16);
			}
		}

		RulesReminder_Enabled = fC.getBoolean("rules-reminder.enabled");
		if (RulesReminder_Enabled) {
			RulesReminder_Message = ChatColor.translateAlternateColorCodes('&', fC.getString("rules-reminder.message"));
			RulesReminder_Version = fC.getInt("rules-reminder.version");
		}

		MailboxReminder_Enabled = fC.getBoolean("mailbox-reminder.enabled");
		if (MailboxReminder_Enabled) {
			MailboxReminder_Message = ChatColor.translateAlternateColorCodes('&', fC.getString("mailbox-reminder.message"));
		}

		AfkKicker_MaxIdleSeconds = fC.getInt("afk-kicker.max-idle-seconds");

		Homes_Enabled = fC.getBoolean("homes.enabled");

		WebPw_Enabled = fC.getBoolean("webpw.enabled");
	}
}

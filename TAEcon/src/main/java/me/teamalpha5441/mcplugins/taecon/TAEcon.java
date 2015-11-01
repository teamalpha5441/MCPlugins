package me.teamalpha5441.mcplugins.taecon;

import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class TAEcon extends JavaPlugin {

	private final ReentrantLock _Lock = new ReentrantLock();
	
	private int defaultBalance = 0;
	private String currencySingular = null;
	private String currencyPlural = null;

	@Override
	public void onLoad() {
		saveDefaultConfig();
	}
	
	@Override
	public void onEnable() {
		reloadPlugin();
		getCommand("money").setExecutor(new MoneyCommand(this));
		if (getServer().getPluginManager().isPluginEnabled("Vault")) {
			final VaultImplementation vault = new VaultImplementation(this);
			getServer().getServicesManager().register(Economy.class, vault, this, ServicePriority.High);
			getLogger().log(Level.INFO, "Hooked into Vault");
		}
	}
	
	@Override
	public void onDisable() {
		getServer().getServicesManager().unregisterAll(this);
	}
	
	public void reloadPlugin() {
		_Lock.lock();
		try {
			reloadConfig();
			defaultBalance = getConfig().getInt("config.default_balance", 0);
			currencySingular = getConfig().getString("config.currency.singular", "Dollar");
			currencyPlural = getConfig().getString("config.currency.plural", "Dollars");
		} finally {
			_Lock.unlock();
		}
	}
	
	public int getBalance(OfflinePlayer Player) {
		_Lock.lock();
		try {
			String playerUUID = Player.getUniqueId().toString();
			return getConfig().getInt("accounts." + playerUUID, defaultBalance);	
		} finally {
			_Lock.unlock();
		}
	}
	
	public Boolean setBalance(OfflinePlayer Player, int NewBalance) {
		_Lock.lock();
		try {
			String playerUUID = Player.getUniqueId().toString();
			if (NewBalance < 0) {
				return false;
			} else {
				getConfig().set("accounts." + playerUUID, NewBalance);
				saveConfig();
				return true;
			}
		} finally {
			_Lock.unlock();
		}
	}
	
	public Boolean addBalance(OfflinePlayer Player, int Amount) {
		_Lock.lock();
		try {
			String playerUUID = Player.getUniqueId().toString();
			if (Amount < 0) {
				return false;
			} else {
				int newBalance = getConfig().getInt("accounts." + playerUUID, defaultBalance) + Amount;
				getConfig().set("accounts." + playerUUID, newBalance);
				saveConfig();
				return true;
			}
		} finally {
			_Lock.unlock();
		}
	}
	
	public Boolean removeBalance(OfflinePlayer Player, int Amount) {
		_Lock.lock();
		try {
			String playerUUID = Player.getUniqueId().toString();
			if (Amount < 0) {
				return false;
			} else {
				int newBalance = getConfig().getInt("accounts." + playerUUID, defaultBalance) - Amount;
				if (newBalance < 0) {
					return false;
				} else {
					getConfig().set("accounts." + playerUUID, newBalance);
					saveConfig();
					return true;
				}
			}
		} finally {
			_Lock.unlock();
		}
	}
	
	public String getCurrencyName(Boolean Plural) {
		return Plural ? currencyPlural : currencySingular;
	}
	
	public Boolean payPlayer(OfflinePlayer Player, OfflinePlayer PaidPlayer, int Amount) {
		_Lock.lock();
		try {
			if (Amount > 0) {
				String playerUUID = Player.getUniqueId().toString();
				String paidPlayerUUID = PaidPlayer.getUniqueId().toString();
				if (Player != PaidPlayer) {
					int playerBalance = getConfig().getInt("accounts." + playerUUID, defaultBalance) - Amount;
					if (playerBalance < 0) {
						return false;
					} else {
						int paidPlayerBalance = getConfig().getInt("accounts." + paidPlayerUUID, defaultBalance) + Amount;
						getConfig().set("accounts." + playerUUID, playerBalance);
						getConfig().set("accounts." + paidPlayerUUID, paidPlayerBalance);
						saveConfig();
						return true;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		} finally {
			_Lock.unlock();
		}
	}
}

package me.teamalpha5441.mcplugins.taecon;

import java.util.concurrent.locks.ReentrantLock;

import org.bukkit.plugin.java.JavaPlugin;

public class TAEcon extends JavaPlugin {

	private final ReentrantLock _Lock = new ReentrantLock();
	
	private int defaultBalance = 0;
	private String currencySingular = null;
	private String currencyPlural = null;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		reloadPlugin();
		getCommand("money").setExecutor(new MoneyCommand(this));
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
	
	public int getBalance(String Player) {
		_Lock.lock();
		try {
			String playerName = Player.toLowerCase();
			return getConfig().getInt("accounts." + playerName, defaultBalance);	
		} finally {
			_Lock.unlock();
		}
	}
	
	public Boolean setBalance(String Player, int NewBalance) {
		_Lock.lock();
		try {
			String playerName = Player.toLowerCase();
			if (NewBalance < 0) {
				return false;
			} else {
				getConfig().set("accounts." + playerName, NewBalance);
				saveConfig();
				return true;
			}
		} finally {
			_Lock.unlock();
		}
	}
	
	public Boolean addBalance(String Player, int Amount) {
		_Lock.lock();
		try {
			String playerName = Player.toLowerCase();
			if (Amount < 0) {
				return false;
			} else {
				int newBalance = getConfig().getInt("accounts." + playerName, defaultBalance) + Amount;
				getConfig().set("accounts." + playerName, newBalance);
				saveConfig();
				return true;
			}
		} finally {
			_Lock.unlock();
		}
	}
	
	public Boolean removeBalance(String Player, int Amount) {
		_Lock.lock();
		try {
			String playerName = Player.toLowerCase();
			if (Amount < 0) {
				return false;
			} else {
				int newBalance = getConfig().getInt("accounts." + playerName, defaultBalance) - Amount;
				if (newBalance < 0) {
					return false;
				} else {
					getConfig().set("accounts." + playerName, newBalance);
					saveConfig();
					return true;
				}
			}
		} finally {
			_Lock.unlock();
		}
	}
	
	public String getCurrencyName(Boolean Plural) {
		_Lock.lock();
		try {
			return Plural ? currencyPlural : currencySingular;
		} finally {
			_Lock.unlock();
		}
	}
	
	public Boolean payPlayer(String Player, String PaidPlayer, int Amount) {
		_Lock.lock();
		try {
			if (Amount > 0) {
				String playerName = Player.toLowerCase();
				String paidPlayerName = PaidPlayer.toLowerCase();
				if (playerName.equals(paidPlayerName)) {
					return false;
				} else {
					int playerBalance = getConfig().getInt("accounts." + playerName, defaultBalance) - Amount;
					if (playerBalance < 0) {
						return false;
					} else {
						int paidPlayerBalance = getConfig().getInt("accounts." + paidPlayerName, defaultBalance) + Amount;
						getConfig().set("accounts." + playerName, playerBalance);
						getConfig().set("accounts." + paidPlayerName, paidPlayerBalance);
						saveConfig();
						return true;
					}
				}
			} else {
				return false;
			}
		} finally {
			_Lock.unlock();
		}
	}
}
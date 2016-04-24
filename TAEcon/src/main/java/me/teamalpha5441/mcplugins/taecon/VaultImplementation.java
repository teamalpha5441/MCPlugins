package me.teamalpha5441.mcplugins.taecon;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;

import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;

public class VaultImplementation implements net.milkbowl.vault.economy.Economy {

	private TAEcon plugin = null;

	public VaultImplementation(TAEcon plugin){
		this.plugin = plugin;
	}

	@Override
	public EconomyResponse bankBalance(String name) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "TAEcon does not support banks");
	}

	@Override
	public EconomyResponse bankDeposit(String name, double amount) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "TAEcon does not support banks");
	}

	@Override
	public EconomyResponse bankHas(String name, double amount) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "TAEcon does not support banks");
	}

	@Override
	public EconomyResponse bankWithdraw(String name, double amount) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "TAEcon does not support banks");
	}

	@Override
	public EconomyResponse createBank(String name, String player) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "TAEcon does not support banks");
	}

	@Override
	public EconomyResponse createBank(String name, OfflinePlayer player) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "TAEcon does not support banks");
	}

	@Override
	@Deprecated
	public boolean createPlayerAccount(String playerName) {
		return true;
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player) {
		return true;
	}

	@Override
	@Deprecated
	public boolean createPlayerAccount(String playerName, String worldName) {
		return true;
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
		return true;
	}

	@Override
	public String currencyNamePlural() {
		return plugin.getCurrencyName(true);
	}

	@Override
	public String currencyNameSingular() {
		return plugin.getCurrencyName(false);
	}

	@Override
	public EconomyResponse deleteBank(String name) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "TAEcon does not support banks");
	}

	@Override
	@Deprecated
	public EconomyResponse depositPlayer(String playerName, double amount) {
		OfflinePlayer player = plugin.getServer().getOfflinePlayer(playerName);
		return depositPlayer(player, amount);
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
		ResponseType rt;
		String message;
		int iamount = (int)Math.floor(amount);

		if (plugin.addBalance(player, iamount)) {
			rt = ResponseType.SUCCESS;
			message = null;
		} else {
			rt = ResponseType.SUCCESS;
			message = "ERROR";
		}

		return new EconomyResponse(iamount, getBalance(player), rt, message);
	}

	@Override
	@Deprecated
	public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
		return depositPlayer(playerName, amount);
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
		return depositPlayer(player, amount);
	}

	@Override
	public String format(double amount) {
		int iAmount = (int)Math.ceil(amount);
		return String.format("%d %s", iAmount, plugin.getCurrencyName(iAmount != 1));
	}

	@Override
	public int fractionalDigits() {
		return 0;
	}

	@Override
	@Deprecated
	public double getBalance(String playerName) {
		OfflinePlayer player = plugin.getServer().getOfflinePlayer(playerName);
		return getBalance(player);
	}

	@Override
	public double getBalance(OfflinePlayer player) {
		return plugin.getBalance(player);
	}

	@Override
	@Deprecated
	public double getBalance(String playerName, String world) {
		return getBalance(playerName);
	}

	@Override
	public double getBalance(OfflinePlayer player, String world) {
		return getBalance(player);
	}

	@Override
	public List<String> getBanks() {
		return new ArrayList<String>(0);
	}

	@Override
	public String getName() {
		return plugin.getName();
	}

	@Override
	@Deprecated
	public boolean has(String playerName, double amount) {
		OfflinePlayer player = plugin.getServer().getOfflinePlayer(playerName);
		return has(player, amount);
	}

	@Override
	public boolean has(OfflinePlayer player, double amount) {
		return getBalance(player) >= amount;
	}

	@Override
	@Deprecated
	public boolean has(String playerName, String worldName, double amount) {
		return has(playerName, amount);
	}

	@Override
	public boolean has(OfflinePlayer player, String worldName, double amount) {
		return has(player, amount);
	}

	@Override
	@Deprecated
	public boolean hasAccount(String playerName) {
		return true;
	}

	@Override
	public boolean hasAccount(OfflinePlayer player) {
		return true;
	}

	@Override
	@Deprecated
	public boolean hasAccount(String playerName, String worldName) {
		return true;
	}

	@Override
	public boolean hasAccount(OfflinePlayer player, String worldName) {
		return true;
	}

	@Override
	public boolean hasBankSupport() {
		return false;
	}

	@Override
	public EconomyResponse isBankMember(String name, String playerName) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "TAEcon does not support banks");
	}

	@Override
	public EconomyResponse isBankMember(String name, OfflinePlayer player) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "TAEcon does not support banks");
	}

	@Override
	public EconomyResponse isBankOwner(String name, String playerName) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "TAEcon does not support banks");
	}

	@Override
	public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "TAEcon does not support banks");
	}

	@Override
	public boolean isEnabled() {
		return plugin.isEnabled();
	}

	@Override
	@Deprecated
	public EconomyResponse withdrawPlayer(String playerName, double amount) {
		OfflinePlayer player = plugin.getServer().getOfflinePlayer(playerName);
		return withdrawPlayer(player, amount);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
		ResponseType rt;
		String message;
		int iamount = (int)Math.ceil(amount);

		if (has(player, amount)) {
			if (plugin.removeBalance(player, iamount)) {
				rt = ResponseType.SUCCESS;
				message = null;
			} else {
				rt = ResponseType.SUCCESS;
				message = "ERROR";
			}
		} else {
			rt = ResponseType.FAILURE;
			message = "Not enough money";
		}

		return new EconomyResponse(iamount, getBalance(player), rt, message);
	}

	@Override
	@Deprecated
	public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
		return withdrawPlayer(playerName, amount);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
		return withdrawPlayer(player, amount);
	}
}

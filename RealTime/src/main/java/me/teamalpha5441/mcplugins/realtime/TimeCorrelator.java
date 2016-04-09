package me.teamalpha5441.mcplugins.realtime;

public abstract class TimeCorrelator {

	public int convertRealHoursToMinecraftTime(float realHours) {
		int mcTime = (int)(realHours * 1000 + 0.5f) - 6000;
		return mcTime < 0 ? mcTime + 24000 : mcTime;
	}

	public abstract int getMinecraftTime();
}

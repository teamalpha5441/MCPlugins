package me.teamalpha5441.mcplugins.realtime;

import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class SimpleTimeCorrelator extends TimeCorrelator {
	
	private DateTimeZone dateTimeZone = null; // default time zone
	
	public SimpleTimeCorrelator() {
	}
	
	public SimpleTimeCorrelator(TimeZone timeZone) {
		if (timeZone != null) {
			this.dateTimeZone = DateTimeZone.forTimeZone(timeZone);
		}
	}
	
	@Override
	public int getMinecraftTime() {
		DateTime now = new DateTime(this.dateTimeZone);
		return convertRealHoursToMinecraftTime(now.getSecondOfDay() / 3600f);
	}
}

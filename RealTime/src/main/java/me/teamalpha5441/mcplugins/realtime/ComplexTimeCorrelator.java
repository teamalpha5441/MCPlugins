package me.teamalpha5441.mcplugins.realtime;

import java.util.Calendar;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;

import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;

public class ComplexTimeCorrelator extends TimeCorrelator {

	private SunriseSunsetCalculator calculator;
	private DateTimeZone dateTimeZone;
	private DateTime sunriseDateTime, sunsetDateTime;
	private Period dayPeriod, nightPeriod;
	
	public ComplexTimeCorrelator(double latitude, double longitude) {
		this(latitude, longitude, TimeZone.getDefault());
	}
	
	public ComplexTimeCorrelator(double latitude, double longitude, TimeZone timeZone) {
		Location location = new Location(latitude, longitude);
		this.calculator = new SunriseSunsetCalculator(location, timeZone);
		this.dateTimeZone = DateTimeZone.forTimeZone(timeZone);
	}
	
	private void recalculateSunriseSunset(DateTime dateTime) {
		Calendar calendar = dateTime.toCalendar(null);
		if (!dateTime.toLocalDate().isEqual(this.sunriseDateTime.toLocalDate())) {
			Calendar sunrise = this.calculator.getOfficialSunriseCalendarForDate(calendar);
			Calendar sunset = this.calculator.getOfficialSunsetCalendarForDate(calendar);
			this.sunriseDateTime = new DateTime(sunrise.getTimeInMillis(), this.dateTimeZone);
			this.sunsetDateTime = new DateTime(sunset.getTimeInMillis(), this.dateTimeZone);
			this.dayPeriod = new Period(this.sunriseDateTime, this.sunsetDateTime);
			this.nightPeriod = new Period(0, 0, 0, 0, 24, 0, 0, 0).minus(this.dayPeriod);
		}
	}
	
	@Override
	public int getMinecraftTime() {
		DateTime now = new DateTime(this.dateTimeZone);
		recalculateSunriseSunset(now);
		
		boolean isDay = now.isAfter(this.sunriseDateTime) && now.isBefore(this.sunsetDateTime);
		if (isDay) {
			// double 
		} else {
			
		}
		
		return 0;
	}
}

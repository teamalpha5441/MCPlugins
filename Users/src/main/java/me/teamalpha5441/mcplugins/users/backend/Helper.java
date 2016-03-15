package me.teamalpha5441.mcplugins.users.backend;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.mindrot.jbcrypt.BCrypt;

public class Helper {

	public static String yawToString(float yaw) {
    	//Convert the yaw to a 0-360 degree value
    	double rotation = (yaw + 180) % 360;
    	//Get the string value for it
    	if (0 <= rotation && rotation < 22.5) {
    		return "N";
    	} else if (22.5 <= rotation && rotation < 67.5) {
    		return "NE";
    	} else if (67.5 <= rotation && rotation < 112.5) {
    		return "E";
    	} else if (112.5 <= rotation && rotation < 157.5) {
    		return "SE";
    	} else if (157.5 <= rotation && rotation < 202.5) {
    		return "S";
    	} else if (202.5 <= rotation && rotation < 247.5) {
    		return "SW";
    	} else if (247.5 <= rotation && rotation < 292.5) {
    		return "W";
    	} else if (292.5 <= rotation && rotation < 337.5) {
    		return "NW";
    	} else if (337.5 <= rotation && rotation < 360.0) {
    		return "N";
    	//Something failed, so use NORTH
    	} else {
    		return "N";
    	}
    }

	public static float yawFromString(String direction) {
    	//Get the direction string
    	direction = direction.trim().toUpperCase();
    	double rotation = 0;
    	if (direction.equals("N")) {
    		rotation = 0;
    	} else if (direction.equals("NE")) {
    		rotation = 45;
    	} else if (direction.equals("E")) {
    		rotation = 90;
    	} else if (direction.equals("SE")) {
    		rotation = 135;
    	} else if (direction.equals("S")) {
    		rotation = 180;
    	} else if (direction.equals("SW")) {
    		rotation = 225;
    	} else if (direction.equals("W")) {
    		rotation = 270;
    	} else if (direction.equals("NW")) {
    		rotation = 315;
    	} else {
    		rotation = 360;
    	}
    	return (float)(rotation - 180);
	}

	public static String hashPassword(String password) {
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		return "$2y" + hashedPassword.substring(3); // fix for PHP
	}

	public static long getUnixTime() {
		return System.currentTimeMillis() / 1000;
	}

	public static String locationToString(Location location, boolean saveYaw) {
		return location.getWorld().getName() + ":" +
				location.getBlockX() + ":" + 
				location.getBlockY() + ":" +
				location.getBlockZ() + ":" +
				yawToString(location.getYaw());
	}

	public static Location locationFromString(String locationString) {
		String[] parts = locationString.split(":");
		float yaw = 0;
		if (parts.length > 4) {
			yaw = yawFromString(parts[4]);
		}
		return new Location(
				Bukkit.getWorld(parts[0]),
				Integer.parseInt(parts[1]) + 0.5,
				Integer.parseInt(parts[2]),
				Integer.parseInt(parts[3]) + 0.5,
				yaw, 0);
	}
}

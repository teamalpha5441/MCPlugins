package me.teamalpha5441.mcplugins.taping;

import java.util.regex.Pattern;

public class TextHelper {

	private static Pattern chatColorPattern = Pattern.compile("(?i)&([0-9A-F])");
	private static Pattern chatMagicPattern = Pattern.compile("(?i)&([K])");
	private static Pattern chatBoldPattern = Pattern.compile("(?i)&([L])");
	private static Pattern chatStrikethroughPattern = Pattern.compile("(?i)&([M])");
	private static Pattern chatUnderlinePattern = Pattern.compile("(?i)&([N])");
	private static Pattern chatItalicPattern = Pattern.compile("(?i)&([O])");
	private static Pattern chatResetPattern = Pattern.compile("(?i)&([R])");
	private static Pattern newLinePattern = Pattern.compile("(?i)&([X])");
	private static Pattern ampersandPattern = Pattern.compile("(?i)&([S])");

	public static String translateColorCodes(String string) {
		String newstring = string;
		newstring = chatColorPattern.matcher(newstring).replaceAll("\u00A7$1");
		newstring = chatMagicPattern.matcher(newstring).replaceAll("\u00A7$1");
		newstring = chatBoldPattern.matcher(newstring).replaceAll("\u00A7$1");
		newstring = chatStrikethroughPattern.matcher(newstring).replaceAll("\u00A7$1");
		newstring = chatUnderlinePattern.matcher(newstring).replaceAll("\u00A7$1");
		newstring = chatItalicPattern.matcher(newstring).replaceAll("\u00A7$1");
		newstring = chatResetPattern.matcher(newstring).replaceAll("\u00A7$1");
		newstring = newLinePattern.matcher(newstring).replaceAll("\n");
		return ampersandPattern.matcher(newstring).replaceAll("&");
	}
}

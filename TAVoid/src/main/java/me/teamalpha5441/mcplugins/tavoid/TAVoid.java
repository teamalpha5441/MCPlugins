package me.teamalpha5441.mcplugins.tavoid;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class TAVoid extends JavaPlugin {
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
	}
	
	@Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
    	Boolean generateBedrockBlock = getConfig().getBoolean("generate-bedrock-block", true);
        return new NullGenerator(generateBedrockBlock);
    }
}
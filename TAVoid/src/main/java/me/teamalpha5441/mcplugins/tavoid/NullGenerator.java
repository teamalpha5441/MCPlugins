package me.teamalpha5441.mcplugins.tavoid;

import java.util.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class NullGenerator extends ChunkGenerator {

	private Boolean generateBedrockBlock;

	public NullGenerator(Boolean generateBedrockBlock) {
		this.generateBedrockBlock = generateBedrockBlock;
	}

	@Override
	public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
		ChunkData data = createChunkData(world);
		if (generateBedrockBlock && x == 0 && z == 0) {
			data.setBlock(0, 63, 0, Material.BEDROCK);
		}
		return data;
	}

	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
	    return new ArrayList<BlockPopulator>(0);
	}

	@Override
	public Location getFixedSpawnLocation(World world, Random random) {
		if (generateBedrockBlock) {
			return new Location(world, 0.5, 64, 0.5);
		} else {
			return new Location(world, 0, 64, 0);
		}
	}
}

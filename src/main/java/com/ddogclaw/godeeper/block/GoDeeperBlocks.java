package com.ddogclaw.godeeper.block;

import net.minecraft.block.Block;

public class GoDeeperBlocks
{
	// Block declaration variables start here.

	public static Block blockCuilGenerator;
	public static Block darkRealityBlock;
	public static Block antiRealityBlock;
	public static Block blockRealityCompressor;
	public static BlockRealityCompressor blockRealityCompressorCore;
	public static BlockRealityCompressor blockRealityCompressorInput;
	public static BlockRealityCompressor blockRealityCompressorOutput;
	public static BlockRealityCompressor blockRealityCompressorGlass;
	public static BlockMassPulverizer blockMassPulverizerCore;
	public static BlockMassPulverizer blockMassPulverizer;
	public static BlockMassPulverizer blockMassPulverizerOutput;
	public static BlockMassPulverizer blockMassPulverizerInput;
	public static BlockMassPulverizer blockMassPulverizerGlass;
	
	public static void init()
	{
		blockCuilGenerator = new BlockCuilGenerator();
		darkRealityBlock = new BlockDarkRealityBlock();
		antiRealityBlock = new BlockAntiRealityBlock();
		blockRealityCompressor = new BlockRealityCompressor("Main");
		blockRealityCompressorCore = new BlockRealityCompressor("Core");
		blockRealityCompressorInput = new BlockRealityCompressor("Input");
		blockRealityCompressorOutput = new BlockRealityCompressor("Output");
		blockRealityCompressorGlass = new BlockRealityCompressor("Glass");
		blockMassPulverizer = new BlockMassPulverizer("Main");
		blockMassPulverizerCore = new BlockMassPulverizer("Core");
		blockMassPulverizerInput = new BlockMassPulverizer("Input");
		blockMassPulverizerOutput = new BlockMassPulverizer("Output");
		blockMassPulverizerGlass = new BlockMassPulverizer("Glass");
	}
}

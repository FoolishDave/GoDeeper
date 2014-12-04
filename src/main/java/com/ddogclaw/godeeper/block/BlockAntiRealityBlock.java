package com.ddogclaw.godeeper.block;

import com.ddogclaw.godeeper.GoDeeper;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockAntiRealityBlock extends Block
{
	public static String name = "antiRealityBlock";

	protected BlockAntiRealityBlock()
	{
		super(Material.rock);
		
		setBlockName(GoDeeper.MODID + "." + name);
		setBlockTextureName(GoDeeper.MODID + ":" + name);
		setCreativeTab(CreativeTabs.tabBlock);
		GameRegistry.registerBlock(this, name);
		// TODO Auto-generated constructor stub
	}

}

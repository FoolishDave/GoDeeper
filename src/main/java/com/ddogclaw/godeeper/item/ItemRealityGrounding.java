package com.ddogclaw.godeeper.item;

import com.ddogclaw.godeeper.GoDeeper;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemRealityGrounding extends Item
{
	public static String name = "realityGrounding";
	
	public ItemRealityGrounding()
	{
		// Register the item with the game.
		setUnlocalizedName(GoDeeper.MODID + "." + name);
		setTextureName(GoDeeper.MODID + ":" + name);
		GameRegistry.registerItem(this, name);
		setCreativeTab(CreativeTabs.tabMaterials);
	}
}

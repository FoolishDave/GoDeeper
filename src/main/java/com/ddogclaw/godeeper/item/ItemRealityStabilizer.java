package com.ddogclaw.godeeper.item;

import java.util.List;

import com.ddogclaw.godeeper.GoDeeper;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemRealityStabilizer extends Item
{
	public static String name = "realityStabilizer";
	public static int level;
	
	public ItemRealityStabilizer (int num)
	{
		setUnlocalizedName(GoDeeper.MODID + "." + name + "Mk" + num);
		setTextureName(GoDeeper.MODID + ":" + name + "Mk" + num);
		GameRegistry.registerItem(this, name + "Mk" + num);
		setCreativeTab(CreativeTabs.tabMaterials);
		this.maxStackSize = 1;
		this.setMaxDamage(num * 1000);
		level = num;
	}
	
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean active)
	{
		list.add("\u00A74Binding to reality.");
	}
}

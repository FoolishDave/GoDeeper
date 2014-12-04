package com.ddogclaw.unhumanities.item;

import com.ddogclaw.unhumanities.UnHumanities;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHealthIncreaser extends Item 
{
	public static String name = "healthIncreaserTEMP";
	
	public ItemHealthIncreaser()
	{
		setUnlocalizedName(UnHumanities.MODID + "." + name);
		setTextureName(UnHumanities.MODID + ":" + name);
		GameRegistry.registerItem(this, name);
		setCreativeTab(CreativeTabs.tabMaterials);
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}
	
	@Override
	public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		float curHealth = player.getMaxHealth();
		player.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((double) curHealth + 0.5);
		return true;
	}
}

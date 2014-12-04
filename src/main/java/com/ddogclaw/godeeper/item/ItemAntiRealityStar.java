package com.ddogclaw.godeeper.item;

import com.ddogclaw.godeeper.GoDeeper;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemAntiRealityStar extends Item
{
	public static String name = "antiRealityStar";
	
	public ItemAntiRealityStar()
	{
		// Register the item with the game.
		setUnlocalizedName(GoDeeper.MODID + "." + name);
		setTextureName(GoDeeper.MODID + ":" + name);
		GameRegistry.registerItem(this, name);
		setCreativeTab(CreativeTabs.tabMaterials);
	}
	
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack p_77636_1_)
    {
        return true;
    }
}

package com.ddogclaw.godeeper;

import com.ddogclaw.godeeper.item.GoDeeperItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class GoDeeperTab extends CreativeTabs
{

	public GoDeeperTab(String lable)
	{
		super(lable);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		return GoDeeperItems.antiRealityStar;
	}
	
	

}

package com.ddogclaw.godeeper.crafting;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.ddogclaw.godeeper.item.GoDeeperItems;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RealityRecipes
{
	private static final RealityRecipes realityCompressionBase = new RealityRecipes();
	
	private Map realityCompressionList = new HashMap();
	
	public static RealityRecipes realityCompression()
	{
		return realityCompressionBase;
	}
	
	public RealityRecipes ()
	{
		addRealityCompression(new ItemStack(Items.nether_star, 1), new ItemStack(GoDeeperItems.darkRealityStar, 1));
	}
	
	public void addRealityCompression(ItemStack itemIn, ItemStack itemOut)
	{
		realityCompressionList.put(itemIn, itemOut);
	}
	
	public ItemStack getRealityCompressionResult(ItemStack item)
	{
		Iterator iterator = realityCompressionList.entrySet().iterator();
		Entry entry;
		
		do
		{
			if (iterator.hasNext() == false)
			{
				return null;
		
			}
			
			entry = (Entry)iterator.next();
		}
		
		while (!checkItems(item, (ItemStack)entry.getKey()));
		
		return (ItemStack)entry.getValue();
	}
	
	private boolean checkItems(ItemStack item1, ItemStack item2)
    {
        return item1.getItem() == item2.getItem() && (item2.getItemDamage() == 32767 || item2.getItemDamage() == item1.getItemDamage());
    }

	public Map getRealityCompressionList ()
	{
		return this.realityCompressionList;
	}
	
	
}

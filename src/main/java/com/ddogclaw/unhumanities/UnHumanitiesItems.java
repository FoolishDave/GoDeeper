package com.ddogclaw.unhumanities;

import com.ddogclaw.unhumanities.item.ItemHealthIncreaser;

import net.minecraft.item.Item;

public class UnHumanitiesItems 
{
	// Item variable decs
	public static Item healthIncreaserTEMP;
	
	public static void init()
	{
		healthIncreaserTEMP = new ItemHealthIncreaser();
	}
}

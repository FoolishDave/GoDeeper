package com.ddogclaw.unhumanities;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid = UnHumanities.MODID, version = UnHumanities.VERSION)
public class UnHumanities 
{
	public static final String MODID = "unhumanities";
	public static final String VERSION = "-1";
	
	@EventHandler
	public void init(FMLInitializationEvent e)
	{
		UnHumanitiesItems.init();
	}
}

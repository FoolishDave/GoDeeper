package com.ddogclaw.godeeper.item;

import net.minecraft.item.Item;

public class GoDeeperItems
{
	// Item variable declarations start here.
	public static Item realityStabilizerMk1;
	public static Item realityStabilizerMk2;
	public static Item realityStabilizerMk3;
	public static Item realityStabilizerMk4;
	public static Item realityStabilizerMk5;
	public static Item realityStabilizerMk6;
	public static Item realityStabilizerMk7;
	public static Item darkRealityStar;
	//public static Item darkRealityBlock;
	public static Item realityInverter;
	public static Item antiRealityStar;
	public static Item realityGrounding;
	public static Item abstractionHarvester;
	//public static Item antiRealityBlock;
	
	public static void init()
	{
		darkRealityStar = new ItemDarkRealityStar();
		realityInverter = new ItemRealityInverter();
		antiRealityStar = new ItemAntiRealityStar();
		realityGrounding = new ItemRealityGrounding();
		abstractionHarvester = new ItemAbstractionHarvester();
		
		realityStabilizerMk1 = new ItemRealityStabilizer(1);
		realityStabilizerMk2 = new ItemRealityStabilizer(2);
		realityStabilizerMk3 = new ItemRealityStabilizer(3);
		realityStabilizerMk4 = new ItemRealityStabilizer(4);
		realityStabilizerMk5 = new ItemRealityStabilizer(5);
		realityStabilizerMk6 = new ItemRealityStabilizer(6);
		realityStabilizerMk7 = new ItemRealityStabilizer(7);
	}
}

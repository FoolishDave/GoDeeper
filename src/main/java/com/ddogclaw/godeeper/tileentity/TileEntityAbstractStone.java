package com.ddogclaw.godeeper.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public class TileEntityAbstractStone extends TileEntity
{
	private String name = "tileAbstractStone";
	
	public TileEntityAbstractStone ()
	{
		
	}
	
	
	public boolean doCrafting()
	{ 
		if (worldObj.getTileEntity(xCoord, yCoord - 1, zCoord) instanceof TileEntityChest)
		{
			
		}
		return false;
	}
}

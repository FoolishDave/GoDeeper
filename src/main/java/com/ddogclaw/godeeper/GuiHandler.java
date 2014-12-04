package com.ddogclaw.godeeper;

import com.ddogclaw.godeeper.container.ContainerCuilGenerator;
import com.ddogclaw.godeeper.container.ContainerMassPulverizer;
import com.ddogclaw.godeeper.container.ContainerRealityCompressor;
import com.ddogclaw.godeeper.gui.GuiCuilGenerator;
import com.ddogclaw.godeeper.gui.GuiMassPulverizer;
import com.ddogclaw.godeeper.gui.GuiRealityCompressor;
import com.ddogclaw.godeeper.tileentity.TileEntityCuilGeneratorEntity;
import com.ddogclaw.godeeper.tileentity.TileEntityMassPulverizer;
import com.ddogclaw.godeeper.tileentity.TileEntityRealityCompressor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x,y,z);
		if(tileEntity instanceof TileEntityCuilGeneratorEntity)
		{
			return new ContainerCuilGenerator(player.inventory, (TileEntityCuilGeneratorEntity) tileEntity);
		}
		else if (tileEntity instanceof TileEntityRealityCompressor)
		{
			return new ContainerRealityCompressor(player.inventory, (TileEntityRealityCompressor) tileEntity);
		}
		else if (tileEntity instanceof TileEntityMassPulverizer)
		{
			return new ContainerMassPulverizer(player.inventory, (TileEntityMassPulverizer) tileEntity);
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity instanceof TileEntityCuilGeneratorEntity)
		{
			return new GuiCuilGenerator(player.inventory, (TileEntityCuilGeneratorEntity) tileEntity);
		}
		else if (tileEntity instanceof TileEntityRealityCompressor)
		{
			return new GuiRealityCompressor(player.inventory, (TileEntityRealityCompressor) tileEntity);
		}
		else if (tileEntity instanceof TileEntityMassPulverizer)
		{
			return new GuiMassPulverizer(player.inventory, (TileEntityMassPulverizer) tileEntity);
		}
		return null;
	}

}

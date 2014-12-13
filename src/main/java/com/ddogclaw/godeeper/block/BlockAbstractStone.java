package com.ddogclaw.godeeper.block;

import com.ddogclaw.godeeper.GoDeeper;
import com.ddogclaw.godeeper.GoDeeperTab;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAbstractStone extends Block
{
	private String name = "abstractStone";
	public BlockAbstractStone()
	{
		super(Material.rock);
		setCreativeTab(GoDeeper.tabGoDeeper);
		GameRegistry.registerBlock(this, name);
		setBlockName(GoDeeper.MODID + "." + name);
		setBlockTextureName(GoDeeper.MODID + ":" + name);
	}
	
	@Override
	public boolean hasTileEntity()
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int integer)
	{
		return null;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		if (!world.isRemote)
		{
			
		}
		return false;
	}
	
	
}

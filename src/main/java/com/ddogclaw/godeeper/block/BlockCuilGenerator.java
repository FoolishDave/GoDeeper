package com.ddogclaw.godeeper.block;

import com.ddogclaw.godeeper.GoDeeper;
import com.ddogclaw.godeeper.gui.GuiCuilGenerator;
import com.ddogclaw.godeeper.tileentity.TileEntityCuilGeneratorEntity;

import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCuilGenerator extends BlockContainer
{
	private String name = "cuilGenerator";
	public BlockCuilGenerator()
	{
		super(Material.rock);
		setCreativeTab(CreativeTabs.tabBlock);
		setBlockBounds(0, 0, 0, 1, 1, 1);
		System.out.println("REGISTERING CUIL GENERATOR. JUST THOUGHT YOU SHOULD KNOW");
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
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		// TODO Auto-generated method stub
		return new TileEntityCuilGeneratorEntity();
	}
	
	@Override
	public int getRenderType()
	{
		return -1;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		TileEntity tileEntity = world.getTileEntity(x,y,z);
		
		if (tileEntity == null)
		{
			return false;
		}
		
		if (!world.isRemote)
		{
			System.out.println("Opening GUI");
			player.openGui(GoDeeper.instance, 1, world, x, y, z);

			return true;
		}
		return false;
	}

}

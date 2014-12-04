package com.ddogclaw.godeeper.block;

import com.ddogclaw.godeeper.GoDeeper;
import com.ddogclaw.godeeper.tileentity.TileEntityCoreDrill;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class BlockCoreDrill extends BlockContainer
{
	private String name = "blockCoreDrill";
	public BlockCoreDrill ()
	{
		// Declare material
		super(Material.rock);
		
		setBlockName(GoDeeper.MODID + "_" + name);
		setBlockTextureName(GoDeeper.MODID + ":" + name);
		setCreativeTab(CreativeTabs.tabBlock);
		GameRegistry.registerBlock(this, name);
	}
	
	public boolean hasTileEntity(int metadata)
	{
		// Why yes! I do have a tile entity!
		return true;
	}
	
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		// Remember that tile entity I mentioned earlier? This is it.
		System.out.println("Making tile entity for core drill");
		return new TileEntityCoreDrill();
	}
	
	public boolean onBlockActivated(World world, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		TileEntityCoreDrill ent = (TileEntityCoreDrill) world.getTileEntity(par2, par3, par4);
		ent.processInChat(par5EntityPlayer, world);
		//par5EntityPlayer.addChatComponentMessage(new ChatComponentText("B"));
		return true;
	}
}

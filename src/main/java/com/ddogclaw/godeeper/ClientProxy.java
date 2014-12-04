package com.ddogclaw.godeeper;

import com.ddogclaw.godeeper.block.GoDeeperBlocks;
import com.ddogclaw.godeeper.block.BlockCuilGenerator;
import com.ddogclaw.godeeper.tileentity.BlockCuilGeneratorItemRenderer;
import com.ddogclaw.godeeper.tileentity.TileEntityCuilGeneratorEntity;
import com.ddogclaw.godeeper.tileentity.TileEntityRenderCuilGenerator;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{
	TileEntitySpecialRenderer render;
	
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
	}
	
	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event)
	{
		super.postInit(event);
	}
	
	@Override
	public void registerRenderers()
	{
		//System.out.println("Registering renderer proxies");
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCuilGeneratorEntity.class, new TileEntityRenderCuilGenerator());
		render = new TileEntityRenderCuilGenerator();
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(GoDeeperBlocks.blockCuilGenerator), new BlockCuilGeneratorItemRenderer(render, new TileEntityCuilGeneratorEntity()));
		
		
	}
}

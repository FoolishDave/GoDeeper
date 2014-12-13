package com.ddogclaw.godeeper;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;

import com.ddogclaw.godeeper.achievement.AchievementGoDeeper;
import com.ddogclaw.godeeper.block.GoDeeperBlocks;
import com.ddogclaw.godeeper.block.BlockCuilGenerator;
import com.ddogclaw.godeeper.crafting.PulverizerRecipes;
import com.ddogclaw.godeeper.fluid.GoDeeperFluids;
import com.ddogclaw.godeeper.gui.GuiCuilGenerator;
import com.ddogclaw.godeeper.item.GoDeeperItems;
import com.ddogclaw.godeeper.tileentity.TileEntityCoreDrill;
import com.ddogclaw.godeeper.tileentity.TileEntityCuilGeneratorEntity;
import com.ddogclaw.godeeper.tileentity.TileEntityMassPulverizer;
import com.ddogclaw.godeeper.tileentity.TileEntityRealityCompressor;
import com.ddogclaw.godeeper.PacketHandler;
import com.ddogclaw.godeeper.CuilMessage;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = GoDeeper.MODID, name = "Go Deeper", version = GoDeeper.VERSION)

public class GoDeeper
{
	@SidedProxy(clientSide="com.ddogclaw.godeeper.ClientProxy", serverSide="com.ddogclaw.godeeper.ServerProxy")
	public static CommonProxy proxy;
	
	public static final String MODID = "godeeper";
	public static final String VERSION = "a0.1";
	
	public static CreativeTabs tabGoDeeper = new GoDeeperTab("Go Deeper");
	
	@Instance(value = MODID)
	public static GoDeeper instance;
	
	public static SimpleNetworkWrapper net;
	//public static PulverizerRecipes pulverizerHandler;
	
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(this);
		
		net = NetworkRegistry.INSTANCE.newSimpleChannel(MODID.toUpperCase());
		net.registerMessage(PacketHandler.class, CuilMessage.class, 0, Side.SERVER);
		
		GoDeeperItems.init();
		GoDeeperBlocks.init();
		GoDeeperFluids.init();
		TileEntity.addMapping(TileEntityCoreDrill.class, "coreDrillTile");
		TileEntity.addMapping(TileEntityCuilGeneratorEntity.class, "cuilGeneratorTile");
		TileEntity.addMapping(TileEntityRealityCompressor.class, "realityCompressorTile");
		TileEntity.addMapping(TileEntityMassPulverizer.class, "massPulverizer");
		//TileEntity.addMapping(BlockCuilGenerator.class, "cuilGeneratorBlockTile");
		
		//REGISTER DAT RENDERER BRO
		System.out.println("Registering renderers");
		proxy.registerRenderers();
		
		//Register Gui Handlers now
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event)
	{
		AchievementGoDeeper.init();
		
		ItemStack realityInverterStack = new ItemStack(GoDeeperItems.realityInverter);
		ItemStack abstractionHarvesterStack = new ItemStack(GoDeeperItems.abstractionHarvester);
		ItemStack realityGroundingStack = new ItemStack(GoDeeperItems.realityGrounding);
		ItemStack antiRealityStarStack = new ItemStack(GoDeeperItems.antiRealityStar);
		ItemStack darkRealityStarStack = new ItemStack(GoDeeperItems.darkRealityStar);
		ItemStack darkRealityBlockStack = new ItemStack(GoDeeperBlocks.darkRealityBlock);
		ItemStack antiRealityBlockStack = new ItemStack(GoDeeperBlocks.antiRealityBlock);
		ItemStack cuilGeneratorStack = new ItemStack(GoDeeperBlocks.blockCuilGenerator);
		ItemStack netherStarStack = new ItemStack(Items.nether_star);
		ItemStack stoneStack = new ItemStack(Blocks.stone);
		ItemStack glassStack = new ItemStack(Blocks.glass);
		
		GameRegistry.addRecipe(new ItemStack(GoDeeperItems.darkRealityStar, 1), new Object[]{
				"XXX",
				"XYX",
				"XXX",
				'X', Items.nether_star,
				'Y', GoDeeperItems.realityInverter});
		
		GameRegistry.addRecipe(new ItemStack(GoDeeperBlocks.darkRealityBlock, 1), new Object[]{
				" X ",
				"XYX",
				" X ",
				'X', Items.nether_star,
				'Y', GoDeeperItems.darkRealityStar});
		
		GameRegistry.addRecipe(new ItemStack(GoDeeperItems.antiRealityStar, 1), new Object[]{
				" X ",
				"XYX",
				" X ",
				'X', GoDeeperBlocks.darkRealityBlock,
				'Y', GoDeeperItems.realityInverter});
		
		GameRegistry.addRecipe(new ItemStack(GoDeeperBlocks.antiRealityBlock, 1), new Object[]{
				" X ",
				"XYX",
				" X ",
				'X', Items.nether_star,
				'Y', GoDeeperItems.antiRealityStar});
		
		GameRegistry.addRecipe(new ItemStack(GoDeeperItems.abstractionHarvester, 1), new Object[]{
				"XYX",
				"YZY",
				"XYX",
				'X', Blocks.stone,
				'Y', GoDeeperBlocks.darkRealityBlock,
				'Z', GoDeeperItems.antiRealityStar});
		
		GameRegistry.addRecipe(new ItemStack(GoDeeperItems.realityGrounding, 1), new Object[]{
				"XYX",
				"YZY",
				"XYX",
				'X', GoDeeperBlocks.antiRealityBlock,
				'Y', Blocks.stone,
				'Z', GoDeeperItems.darkRealityStar});
		
		GameRegistry.addRecipe(new ItemStack(GoDeeperBlocks.blockCuilGenerator, 1), new Object[]{
				"WXW",
				"YZY",
				"WXW",
				'W', GoDeeperBlocks.antiRealityBlock,
				'X', GoDeeperItems.realityGrounding,
				'Y', Blocks.glass,
				'Z', GoDeeperItems.abstractionHarvester});
	}
	
	@EventHandler
	public static void postinit(FMLPostInitializationEvent event)
	{
		//pulverizerHandler = new PulverizerRecipes();
	}
}

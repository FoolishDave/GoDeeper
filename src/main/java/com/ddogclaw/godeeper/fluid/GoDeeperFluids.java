package com.ddogclaw.godeeper.fluid;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumRarity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class GoDeeperFluids
{
	public static Fluid fluidSuperheatedWater;
	
	public static void init()
	{

		
		fluidSuperheatedWater = new Fluid("superheatedWater").setLuminosity(0).setDensity(1200).setViscosity(1500).setTemperature(350).setRarity(EnumRarity.uncommon);
		FluidRegistry.registerFluid(fluidSuperheatedWater);
		
		BlockFluidSuperheatedWater blockFluidSuperheatedWater = new BlockFluidSuperheatedWater();
		GameRegistry.registerBlock(blockFluidSuperheatedWater, "superheatedWater");

		fluidSuperheatedWater.setBlock(blockFluidSuperheatedWater);
		//fluidSuperheatedWater.setUnlocalizedName(blockFluidSuperheatedWater.getUnlocalizedName());
	}

}

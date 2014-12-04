package com.ddogclaw.godeeper.fluid;

import javax.swing.Icon;

import com.ddogclaw.godeeper.GoDeeper;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class BlockFluidSuperheatedWater extends BlockFluidClassic
{
	public static final Material materialSuperheatedWater = new MaterialLiquid(MapColor.blueColor);
	public static final int flows = 16;
	public static String name = "superheatedWater";
	
	public BlockFluidSuperheatedWater()
	{
		super(GoDeeperFluids.fluidSuperheatedWater, Material.water);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		setQuantaPerBlock(flows);
		setTickRate(4);
		
		//setHardness(.5F);
		setLightOpacity(1);
		
		
		//setRenderPass(1);
		setBlockName(GoDeeper.MODID + ".fluid." + name);
		displacements.put(this, false);
		
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon stillIcon;
	@SideOnly(Side.CLIENT)
	public IIcon flowIcon;
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconReg)
	{
		//icons = new IIcon[2];
		stillIcon = iconReg.registerIcon(GoDeeper.MODID + ":fluid/Fluid_" + name + "_Still");
		flowIcon =  iconReg.registerIcon(GoDeeper.MODID + ":fluid/Fluid_" + name + "_Flow");
		GoDeeperFluids.fluidSuperheatedWater.setIcons(stillIcon, flowIcon);
	}
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		return side <= 1 ? stillIcon : flowIcon;
	}
	
	@Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
            if (world.getBlock(x,  y,  z).getMaterial().isLiquid()) return false;
            return super.canDisplace(world, x, y, z);
    }
	
	 @Override
     public boolean displaceIfPossible(World world, int x, int y, int z) {
             if (world.getBlock(x,  y,  z).getMaterial().isLiquid()) return false;
             return super.displaceIfPossible(world, x, y, z);
     }

	 
	 public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	 {
		 entity.attackEntityFrom(DamageSource.inFire, 5);
	 }
}

package com.ddogclaw.godeeper.tileentity;

import com.ddogclaw.godeeper.fluid.GoDeeperFluids;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

public class TileEntityCoreDrill extends TileEntity implements IEnergyHandler, IFluidHandler
{
	
	// Energy Things
	protected double drillLevel = 0;
	int neededLevel = 10000;
	protected boolean drilling = true;
	
	// Tank Things
    protected FluidStack fluid;
    protected int capacity;
	
	protected EnergyStorage storage = new EnergyStorage(10000000, 10000000, 0);
	protected FluidTank tank = new FluidTank(GoDeeperFluids.fluidSuperheatedWater, 0, 50000);
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		storage.readFromNBT(nbt);
		tank.readFromNBT(nbt);
		this.drillLevel = nbt.getDouble("drillLevel");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{	
		nbt.setDouble("drillLevel",drillLevel);
		
		nbt.setInteger("x",this.xCoord);
		nbt.setInteger("y",this.yCoord);
		nbt.setInteger("z", this.yCoord);

		super.writeToNBT(nbt);
		storage.writeToNBT(nbt);
		tank.writeToNBT(nbt);
	}

	public void energyToDepth()
	{
		double drillMod;
		
		double curPower = (double) storage.getEnergyStored();
		
		drillMod = Math.pow(curPower, (1/(drillLevel+25)));
		
		if (storage.getEnergyStored() > 0)
		{
			drillLevel = drillLevel + drillMod;
			storage.modifyEnergyStored(-storage.getEnergyStored());
		}
	}
	
	public void depthToFluid()
	{
		int fluidAm = (int) (drillLevel/100);
		fill(ForgeDirection.UNKNOWN, new FluidStack(GoDeeperFluids.fluidSuperheatedWater,fluidAm), true);
	}
	
	@Override
	public boolean canConnectEnergy(ForgeDirection from)
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive,
			boolean simulate)
	{
		return storage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract,
			boolean simulate)
	{
		return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection from)
	{
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from)
	{
		return storage.getMaxEnergyStored();
	}
	
	@Override
	public void updateEntity()
	{
		energyToDepth();
		depthToFluid();
		autoPumpFluid(tank,worldObj);
	}
	
	public void processInChat(EntityPlayer player, World world)
	{
		if (drillLevel >= 10000000)
		{
			player.addChatComponentMessage(new ChatComponentText("I give you a hamburger..."));
		}
		else
		{
			player.addChatComponentMessage(new ChatComponentText("<GoDeeper> " + "Current Depth: " + drillLevel));
		}
		
		
		//player.addChatComponentMessage(new ChatComponentText("Current Power: " + storage.getEnergyStored() + " / " + storage.getMaxEnergyStored()));
		world.notifyBlockChange(this.xCoord, this.yCoord, this.zCoord, blockType);
		//System.out.println("Doing something?");
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
	{
		// TODO Auto-generated method stub
		return tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	public void autoPumpFluid(IFluidTank fTank, World world)
	{
		System.out.println("<GoDeeper><Drill> Trying to pump out fluid.");
		if (fTank != null && fTank.getFluid().amount > 0)
		{
			System.out.println("<GoDeeper><Drill> Have fluid to pump.");
			FluidStack l = tank.getFluid().copy();
			l.amount = Math.min(l.amount, FluidContainerRegistry.BUCKET_VOLUME);
			for (ForgeDirection t : ForgeDirection.VALID_DIRECTIONS)
			{
				TileEntity tile = world.getTileEntity(this.xCoord + t.offsetX, this.yCoord + t.offsetY, this.zCoord + t.offsetZ);
				if (tile instanceof IFluidHandler)
				{
					System.out.println("<GoDeeper><Drill> Found thing to output into.");
					((IFluidHandler) tile).fill(t.getOpposite(), l, true);
					tank.drain(l.amount, true);
					System.out.println("<GoDeeper><Drill> Filled thing.");
					break;
				}
			}
		}
	}
}

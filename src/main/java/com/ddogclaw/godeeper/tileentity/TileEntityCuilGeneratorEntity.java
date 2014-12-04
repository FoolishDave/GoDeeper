package com.ddogclaw.godeeper.tileentity;

import java.util.Random;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyStorage;

import com.ddogclaw.godeeper.GoDeeper;
import com.ddogclaw.godeeper.item.GoDeeperItems;
import com.ddogclaw.godeeper.item.ItemRealityStabilizer;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCuilGeneratorEntity extends TileEntity implements IInventory, IEnergyHandler
{
	private String name = "cuilGeneratorTile";
	public int customField;
	private ItemStack[] inv = new ItemStack[1];
	public int cuilLevel;
	
	public EnergyStorage storage = new EnergyStorage(100000000, 0, 100000000);

	
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
	{		
		super.writeToNBT(tagCompound);
		NBTTagList itemList = new NBTTagList();
		for(int i = 0; i < inv.length; ++i)
		{
			ItemStack stack = inv[i];
			if (stack != null)
			{
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		tagCompound.setTag("Inventory", itemList);
		tagCompound.setInteger("Cuil", cuilLevel);
		
		
		storage.writeToNBT(tagCompound);
		

	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
		
		NBTTagList tagList = tagCompound.getTagList("Inventory", 0);
		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound tag = tagList.getCompoundTagAt(i);
			byte slot = tag.getByte("Slot");
			if (slot >= 0 && slot < inv.length)
			{
				inv[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
		cuilLevel = tagCompound.getInteger("Cuil");
		
		storage.readFromNBT(tagCompound);
	}
	
	@Override
	public int getSizeInventory()
	{
		// TODO Auto-generated method stub
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		// TODO Auto-generated method stub
		return inv[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt)
	{
		ItemStack stack = getStackInSlot(slot);
		if (stack != null)
		{
			if (stack.stackSize <= amt)
			{
				setInventorySlotContents(slot, null);
			} 
			else
			{
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0)
				{
					setInventorySlotContents(slot, null);
				}
			}
			onInventoryChanged();
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		ItemStack stack = getStackInSlot(slot);
		if (stack != null)
		{
			ItemStack itemStack = inv[slot];
			inv[slot] = null;
			return stack;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		inv[slot] = stack;
		if (stack != null && stack.stackSize > getInventoryStackLimit())
		{
			stack.stackSize = getInventoryStackLimit();
		}

		this.onInventoryChanged();
	}
	
	public void onInventoryChanged()
	{
		for (int i = 0; i < this.getSizeInventory(); ++i)
		{
			if (this.getStackInSlot(i) != null && this.getStackInSlot(i).stackSize == 0)
				this.setInventorySlotContents(i, null);
		}
	}


	
	@Override
	public String getInventoryName()
	{
		return GoDeeper.MODID + "." + name;
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		// TODO Auto-generated method stub
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	@Override
	public void openInventory()
	{
		
	}

	@Override
	public void closeInventory()
	{
		
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack)
	{
		// TODO Auto-generated method stub
		return true;
	}	
	
	int pastCuil = -1;
	@Override
	public void updateEntity()
	{

		
		if (!(pastCuil == cuilLevel))
		{
			pastCuil = cuilLevel;
			System.out.println("Cuil Generator at: " + cuilLevel);
		}
		
		
		ItemStack curStack = getStackInSlot(0);
		
		int eAdd = cuilLevel * 20000;
		
		ItemRealityStabilizer stableItem = (ItemRealityStabilizer) curStack.getItem();
		
		if (curStack != null)
		{
			damageItem();
			
			if (cuilLevel == 1 && curStack.getItem() instanceof ItemRealityStabilizer && stableItem.level <= cuilLevel)
			{
				storage.setEnergyStored(storage.getEnergyStored() + eAdd);
			}
			else if (cuilLevel == 2 && curStack.getItem() == GoDeeperItems.realityStabilizerMk2 && stableItem.level <= cuilLevel)
			{
				storage.setEnergyStored(storage.getEnergyStored() + eAdd);
			}
			else if (cuilLevel == 3 && curStack.getItem() == GoDeeperItems.realityStabilizerMk3 && stableItem.level <= cuilLevel)
			{
				storage.setEnergyStored(storage.getEnergyStored() + eAdd);
			}
			else if (cuilLevel == 4 && curStack.getItem() == GoDeeperItems.realityStabilizerMk4 && stableItem.level <= cuilLevel)
			{
				storage.setEnergyStored(storage.getEnergyStored() + eAdd);
			}
			else if (cuilLevel == 5 && curStack.getItem() == GoDeeperItems.realityStabilizerMk5 && stableItem.level <= cuilLevel)
			{
				storage.setEnergyStored(storage.getEnergyStored() + eAdd);
			}
			else if (cuilLevel == 6 && curStack.getItem() == GoDeeperItems.realityStabilizerMk6 && stableItem.level <= cuilLevel)
			{
				storage.setEnergyStored(storage.getEnergyStored() + eAdd);
			}
			else if (cuilLevel == 7 && curStack.getItem() == GoDeeperItems.realityStabilizerMk7 && stableItem.level <= cuilLevel)
			{
				storage.setEnergyStored(storage.getEnergyStored() + eAdd);
			}
		}
		for (ForgeDirection t : ForgeDirection.VALID_DIRECTIONS)
		{
			autoOutputEnergy(storage, 1000000, t);
		}
	}
	
	
	public void damageItem()
	{
		ItemStack curStack = getStackInSlot(0);
		if (curStack != null)
		{
			int dam = curStack.getItemDamage() + 1;
			if (dam > curStack.getMaxDamage())
			{
				setInventorySlotContents(0, null);
			}
			else
			{
				curStack.setItemDamage(dam);
			}
		}
	}
	

	@SideOnly(Side.CLIENT)
	public int getCuilLevel()
	{
		return cuilLevel;
		
	}
	
	// Energy Storage

	@Override
	public boolean canConnectEnergy(ForgeDirection from)
	{
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive,
			boolean simulate)
	{
		// TODO Auto-generated method stub
		return storage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract,
			boolean simulate)
	{
		// TODO Auto-generated method stub
		return storage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from)
	{
		// TODO Auto-generated method stub
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from)
	{
		// TODO Auto-generated method stub
		return storage.getMaxEnergyStored();
	}
	
	public void autoOutputEnergy(EnergyStorage eStorage, int amount, ForgeDirection to)
	{

		TileEntity te = worldObj.getTileEntity(xCoord + to.offsetX, yCoord + to.offsetY, zCoord + to.offsetZ);
		if (te instanceof IEnergyHandler)
		{
			IEnergyHandler eTile = (IEnergyHandler) te;
			if (eStorage.getEnergyStored() >= amount)
			{
				if (eTile.canConnectEnergy(to.getOpposite()))
				{
					int received = eTile.receiveEnergy(to.getOpposite(), amount, false);
					eStorage.extractEnergy(received, false);
				}
			}
		}

	}	
}

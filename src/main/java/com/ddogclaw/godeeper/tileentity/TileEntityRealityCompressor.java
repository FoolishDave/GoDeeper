package com.ddogclaw.godeeper.tileentity;

import com.ddogclaw.godeeper.GoDeeper;
import com.ddogclaw.godeeper.block.BlockRealityCompressor;
import com.ddogclaw.godeeper.crafting.RealityRecipes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityRealityCompressor extends TileEntity implements
		ISidedInventory
{
	public boolean isInMultiblock;
	public boolean isMaster;
	public TileEntity master;
	public ItemStack[] inv;
	String name;
	public String machineType;
	public int crushTime;
	public int currentItemCrushTime;
	public ItemStack crushing = null;

	public TileEntityRealityCompressor(String type)
	{
		machineType = type;
		if (type == "Core")
		{
			isMaster = true;
			inv = new ItemStack[2];
		} else
		{
			isMaster = false;
			inv = new ItemStack[1];
		}
	}

	public void breakMultiblock()
	{
		master = null;
		isInMultiblock = false;
		isMaster = false;
		inv = null;
	}

	public boolean getValid(World world, int x, int y, int z)
	{

		for (int i = 0; i < 3; i++)
		{
			for (int k = 0; k < 3; k++)
			{
				for (int l = 0; l < 3; l++)
				{
					if (world.getBlock(x + k, y + i, z + l) instanceof BlockRealityCompressor)
					{

					} else
					{
						return false;
					}
				}
			}
		}

		return true;
	}

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
		
		//storage.writeToNBT(tagCompound);
		

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
		
		//storage.readFromNBT(tagCompound);
	}	
	
	@Override
	public int getSizeInventory()
	{
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
			} else
			{
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0)
				{
					setInventorySlotContents(slot, null);
				}
			}
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
		} else
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
		// TODO Auto-generated method stub
		if (!player.isSneaking())
		{
			return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this
					&& player.getDistanceSq(xCoord + 0.5, yCoord + 0.5,
							zCoord + 0.5) < 64;
		} else
		{
			return false;
		}
	}

	@Override
	public void openInventory()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void closeInventory()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_)
	{
		// TODO Auto-generated method stub
		return new int[] { 1 };
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_,
			int p_102007_3_)
	{
		if (isInMultiblock && (machineType == "Input"))
		{
			return true;
		}

		return false;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_,
			int p_102008_3_)
	{
		if (isInMultiblock && (machineType == "Output"))
		{
			return true;
		}

		return false;
	}

	/**
	 * Returns an integer between 0 and the passed value representing how close
	 * the current item is to being completely cooked
	 */
	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int passedTime)
	{
		return this.crushTime * passedTime / 200;
	}

	/**
	 * Furnace isBurning
	 */
	public boolean isBurning()
	{
		return this.crushTime > 0;
	}

	public boolean canRealityCompress()
	{
		if (this.inv[0] == null)
		{
			return false;
		} else
		{
			ItemStack itemStack = RealityRecipes.realityCompression()
					.getRealityCompressionResult(inv[0]);
			if (itemStack == null)
				return false;
			if (inv[1] == null)
				return true;
			if (!inv[1].isItemEqual(itemStack))
				return true;
			int result = inv[1].stackSize + itemStack.stackSize;
			return result <= getInventoryStackLimit()
					&& result <= inv[1].getMaxStackSize();
		}
	}

	public void realityCompressItem()
	{
		if (canRealityCompress())
		{
			System.out.println("Reality compressing something");

			ItemStack itemStack = RealityRecipes.realityCompression()
					.getRealityCompressionResult(inv[0]);
			
			if (inv[1] == null)
			{
				inv[1] = itemStack.copy();
			} else if (inv[1].getItem() == itemStack.getItem())
			{
				inv[1].stackSize += itemStack.stackSize;
			}

			--inv[0].stackSize;

			if (inv[0].stackSize <= 0)
			{
				inv[0] = null;
			}
		}
	}

	@Override
	public void updateEntity()
	{
		boolean flag = crushTime > 0;
		boolean flag1 = false;

		if (!worldObj.isRemote)
		{
			if (canRealityCompress())
			{
				System.out.println("Compressing Item, currently at: " + crushTime);
				++this.crushTime;
				
				if (this.crushTime == 100)
				{
					crushTime = 0;
					realityCompressItem();
					flag1 = true;
				}
			}
			else
			{
				crushTime = 0;
			}
		}
		if (flag1)
		{
			this.markDirty();
		}
	}

}

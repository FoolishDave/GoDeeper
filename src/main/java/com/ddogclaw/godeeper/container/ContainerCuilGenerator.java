package com.ddogclaw.godeeper.container;

import com.ddogclaw.godeeper.tileentity.TileEntityCuilGeneratorEntity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class ContainerCuilGenerator extends Container
{
	protected TileEntityCuilGeneratorEntity tileEntity;

	private int lastCuilLevel;
	
	
	public ContainerCuilGenerator(InventoryPlayer inventoryPlayer,
			TileEntityCuilGeneratorEntity te)
	{
		this.tileEntity = te;
		int i = 0;
		int j = 0;

		addSlotToContainer(new SlotCuil(tileEntity, 0, 80, 35));
		
		bindPlayerInventory(inventoryPlayer);

	}

	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer)
	{
		int i;
		int j;
		
		for (i = 0; i < 3; ++i)
		{
			for (j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		// TODO Auto-generated method stub
		return tileEntity.isUseableByPlayer(player);
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);
	}

	// For shift-clicking, without this, it would cause massive errors
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot)
	{
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);

		// null checks and checks if the item can be stacked (maxStackSize > 1)
		if (slotObject != null && slotObject.getHasStack())
		{
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();

			// Merges the item into player inventory since its in the tileEntity
			if (slot < 9)
			{
				if (!this.mergeItemStack(stackInSlot, 0, 35, true))
				{
					return null;

				}

				// places it into the tileEntity is possible since its in the
				// player inventory
			} else if (!this.mergeItemStack(stackInSlot, 0, 9, false))
			{
				return null;

			}

			if (stackInSlot.stackSize == 0)
			{
				slotObject.putStack(null);

			} else
			{
				slotObject.onSlotChanged();

			}

			if (stackInSlot.stackSize == stack.stackSize)
			{
				return null;
			}

			slotObject.onPickupFromSlot(player, stackInSlot);
		}

		return stack;
	}
    
	@Override
	public void addCraftingToCrafters(ICrafting crafter)
    {
        super.addCraftingToCrafters(crafter);
        crafter.sendProgressBarUpdate(this, 0, this.tileEntity.cuilLevel);
    }
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int var1, int var2)
	{
		if (var1 == 0)
			this.tileEntity.cuilLevel = var2;
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
        for (int i = 0; i < this.crafters.size(); ++i)
        {		
        	ICrafting icrafting = (ICrafting)this.crafters.get(i);
        	
        	if (this.lastCuilLevel != this.tileEntity.cuilLevel)
        	{
        		icrafting.sendProgressBarUpdate(this, 0, this.tileEntity.cuilLevel);
        	}
        }
		
		this.lastCuilLevel = tileEntity.cuilLevel;
	}
	

}

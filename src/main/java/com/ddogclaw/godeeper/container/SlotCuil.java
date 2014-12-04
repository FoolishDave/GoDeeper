package com.ddogclaw.godeeper.container;

import com.ddogclaw.godeeper.item.ItemRealityStabilizer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCuil extends Slot
{

	public SlotCuil(IInventory inventory, int p_i1824_2_, int p_i1824_3_,
			int p_i1824_4_)
	{
		super(inventory, p_i1824_2_, p_i1824_3_, p_i1824_4_);

	}
	
	@Override
	public boolean isItemValid(ItemStack itemStack)
	{
		return itemStack.getItem() instanceof ItemRealityStabilizer;
	}

}

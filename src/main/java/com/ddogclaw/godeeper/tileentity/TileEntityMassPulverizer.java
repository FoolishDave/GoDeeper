package com.ddogclaw.godeeper.tileentity;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import thermalexpansion.util.crafting.PulverizerManager;
import cofh.api.energy.EnergyStorage;
import cofh.lib.util.helpers.ItemHelper;

import com.ddogclaw.godeeper.GoDeeper;
import com.ddogclaw.godeeper.block.BlockMassPulverizer;
import com.ddogclaw.godeeper.block.BlockRealityCompressor;
import com.ddogclaw.godeeper.crafting.PulverizerRecipes;
import com.ddogclaw.godeeper.crafting.RealityRecipes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMassPulverizer extends TileEntity implements ISidedInventory
{
	public String name;
	public String type;

	public boolean isInMultiblock;
	public boolean isMaster;
	protected boolean toScan = true;
	protected boolean tryFormMultiblock = false;
	protected int[] bottomLeft;
	protected int[] masterPos;
	protected List<TileEntity> upgrades;
	protected TileEntityMassPulverizer master;

	public ItemStack[] inv;
	public int pulverizeTime;
	public int currentItemPulverizeTime;

	protected Method getRecipe;

	public EnergyStorage energyStorage;

	public TileEntityMassPulverizer()
	{
		inv = new ItemStack[4];
		energyStorage = new EnergyStorage(5000000, 3000000, 0);
	}

	public TileEntityMassPulverizer(String machineType)
	{
		/* Grab TE */

		/* Decide what block it is! */

		type = machineType;
		if (type == "Core")
		{
			isMaster = true;
			isInMultiblock = true;
			inv = new ItemStack[4];
			energyStorage = new EnergyStorage(5000000, 3000000, 0);
			master = this;

			upgrades = new ArrayList<TileEntity>();
			toScan = true;
		} else if (type == "Input" || type == "Output")
		{
			isMaster = false;
			// isInMultiblock = true;
			if (type == "Input")
			{
				inv = new ItemStack[2];
			} else
			{
				inv = new ItemStack[3];
			}

			energyStorage = null;
		} else if (type == "Power")
		{
			inv = new ItemStack[0];
			energyStorage = new EnergyStorage(5000000, 3000000, 0);
		} else
		{
			isMaster = false;
			// isInMultiblock = true;
		}
		// master = (TileEntityMassPulverizer)
		// worldObj.getTileEntity(masterPos[0], masterPos[1], masterPos[2]);
	}

	public int[] getBottomLeft(World world, int x, int y, int z)
	{
		int[] returnValues = new int[3];
		int previousX = 0;
		int previousY = 0;
		int previousZ = 0;

		for (int i = 2; i >= 0; i--)
		{

			for (int k = 2; k >= 0; k--)
			{

				for (int l = 2; l >= 0; l--)
				{

					Block block = this.worldObj.getBlock(x - i, y - k, z - l);
					if (block != null)
					{
						if (world.getBlock(x - i, y - k, z - l) instanceof BlockMassPulverizer)
						{
							previousX = x - i;
							previousY = y - k;
							previousZ = z - l;

							returnValues[0] = previousX;
							returnValues[1] = previousY;
							returnValues[2] = previousZ;
							// System.out.println("Bottom left is at: "
							// + previousX + "," + previousY + ","
							// + previousZ);
							return returnValues;
						}
					}
				}
			}
		}

		return null;
	}

	public void breakMultiblock()
	{
		isInMultiblock = false;
		isMaster = false;
		// inv = null;
		// master = null;
	}

	public void formMultiblock()
	{
		isInMultiblock = true;
		if (master == null && bottomLeft != null && masterPos != null)
		{
			TileEntityMassPulverizer tile = (TileEntityMassPulverizer) worldObj.getTileEntity(masterPos[0], masterPos[1], masterPos[2]);
			if (tile != null)
			{
				this.tryFormMultiblock = false;
				master = tile;
			} else
			{
				this.tryFormMultiblock = true;
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		System.out.println("Reading from NBT");
		super.readFromNBT(tagCompound);

		this.isInMultiblock = tagCompound.getBoolean("multiblock");

		type = tagCompound.getString("Type");

		// System.out.println("Got type: " + type);

		if (isInMultiblock)
		{
			System.out.println("Getting bottomleft and masterpos");
			bottomLeft = tagCompound.getIntArray("BottomLeft");
			masterPos = tagCompound.getIntArray("MasterPos");
			// master = (TileEntityMassPulverizer)
			// worldObj.getTileEntity(masterPos[0], masterPos[1], masterPos[2]);
		}

		if (type.contains("Core"))
		{
			System.out.println("I HIT THIS LINE!");
			isMaster = true;
			isInMultiblock = true;
			inv = new ItemStack[4];
			energyStorage = new EnergyStorage(5000000, 3000000, 0);
			master = this;
			toScan = true;
		} else if (type.contains("Input") || type.contains("Output"))
		{
			isMaster = false;
			if (type == "Input")
			{
				inv = new ItemStack[2];
			} else
			{
				inv = new ItemStack[2];
			}

			energyStorage = null;
		} else if (type == "Power")
		{
			inv = new ItemStack[0];
			energyStorage = new EnergyStorage(5000000, 3000000, 0);
		} else
		{
			isMaster = false;
		}

		if (isInMultiblock)
		{
			formMultiblock();
		}

		NBTTagList tagList = tagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound tag = tagList.getCompoundTagAt(i);
			byte slot = tag.getByte("Slot");
			if (slot >= 0 && slot < inv.length)
			{
				inv[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
		if (energyStorage != null)
		{
			energyStorage.readFromNBT(tagCompound);
		}

	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
	{
		System.out.println("Writing to NBT");
		super.writeToNBT(tagCompound);
		tagCompound.setString("Type", type);
		if (isInMultiblock)
		{
			tagCompound.setIntArray("BottomLeft", bottomLeft);
			tagCompound.setIntArray("MasterPos", masterPos);
		}

		NBTTagList itemList = new NBTTagList();
		for (int i = 0; i < inv.length; ++i)
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
		tagCompound.setTag("Items", itemList);
		tagCompound.setBoolean("multiblock", isInMultiblock);

		if (energyStorage != null)
		{
			energyStorage.writeToNBT(tagCompound);
		}

	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
	{
		readFromNBT(packet.func_148857_g());
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
			return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
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
	public boolean canInsertItem(int p_102007_1_, ItemStack itemStack, int p_102007_3_)
	{
		// System.out.println("Check 0");
		if (type.contains("Input") && isInMultiblock)
		{
			// System.out.println("Check 1");
			if (master != null && this != null)
			{
				// System.out.println("Check 2");
				if (master.inv[0] == null && this.inv[1] == null)
				{
					// System.out.println("Check 3");
					return true;
				}

				// System.out.println("Trying to insert: "
				// + itemStack.getDisplayName());
				// System.out.println("Checking: " + itemStack.getDisplayName()
				// + " against: " + master.inv[0].getDisplayName());

				try
				{
					if (master.inv[0].getItem() == itemStack.getItem())
					{
						if (master.inv[0].stackSize + itemStack.stackSize > master.inv[0].getMaxStackSize())
						{
							System.out.println("Failed insert check");
							return false;
						} else
						{
							// System.out.println("Check 3");
							return true;
						}
					}
				} catch (NullPointerException e)
				{
					System.err.println("NullPointer'd when trying to insert");
				}
			}
		}

		return false;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_)
	{
		if (isInMultiblock && (type == "Output"))
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
		return this.pulverizeTime * passedTime / 200;
	}

	/**
	 * Furnace isBurning
	 */
	public boolean isPulverizing()
	{
		return this.pulverizeTime > 0;
	}

	public boolean canPulverize()
	{
		// System.out.println("Checking for item in slot 0");

		if (this.inv[0] == null)
		{
			// System.out.println("Couldn't find item");
			return false;
		}

		PulverizerManager.RecipePulverizer localRecipePulverizer = PulverizerManager.getRecipe(this.inv[0]);

		if ((localRecipePulverizer == null) || energyStorage.getEnergyStored() > localRecipePulverizer // CHANGE
																										// THIS
																										// BACK
																										// TO
																										// A
																										// LESS
																										// THAN
																										// DAVID
		.getEnergy())
		{
			// System.out.println("Couldn't get pulverizer recipe");
			return false;
		}

		ItemStack stack1 = localRecipePulverizer.getPrimaryOutput();
		ItemStack stack2 = localRecipePulverizer.getSecondaryOutput();

		if ((stack2 != null) && (this.inv[3] != null))
		{
			if (!this.inv[3].isItemEqual(stack2))
			{
				return false;
			}
			if (this.inv[3].stackSize + stack2.stackSize > stack2.getMaxStackSize())
			{
				return false;
			}
		}

		if ((this.inv[1] == null) || (this.inv[2] == null))
		{
			return true;
		}

		if (!this.inv[1].isItemEqual(stack1))
		{
			return this.inv[2].stackSize + stack1.stackSize <= stack1.getMaxStackSize();
		}

		if (!this.inv[2].isItemEqual(stack1))
		{
			return this.inv[1].stackSize + stack1.stackSize <= stack1.getMaxStackSize();
		}

		return this.inv[1].stackSize + this.inv[2].stackSize + stack1.stackSize <= stack1.getMaxStackSize() * 2;
	}

	protected boolean hasValidInput()
	{
		PulverizerManager.RecipePulverizer localRecipePulverizer = PulverizerManager.getRecipe(this.inv[0]);
		return localRecipePulverizer != null;
	}

	protected int pulverizeEnergy;

	protected void processStart()
	{
		this.pulverizeEnergy = PulverizerManager.getRecipe(this.inv[0]).getEnergy();
	}

	protected void processFinish()
	{
		PulverizerManager.RecipePulverizer localRecipePulverizer = PulverizerManager.getRecipe(this.inv[0]);
		ItemStack stack1 = localRecipePulverizer.getPrimaryOutput();
		ItemStack stack2 = localRecipePulverizer.getSecondaryOutput();

		int i;

		if (this.inv[1] == null)
		{
			// If there is no item in the first output slot, output there.
			this.inv[1] = stack1;
		} else if (this.inv[1].isItemEqual(stack1))
		{
			// If the output is the same as the item in the first output slot,
			// output there.
			i = this.inv[1].stackSize + stack1.stackSize;
			if (i <= stack1.getMaxStackSize())
			{
				this.inv[1].stackSize += stack1.stackSize;
			} else
			{
				int j = stack1.getMaxStackSize() - this.inv[1].stackSize;
				this.inv[1].stackSize += j;
				if (this.inv[2] == null)
				{
					this.inv[2] = stack1;
					this.inv[2].stackSize = (stack1.stackSize - j);
				} else
				{
					this.inv[2].stackSize += stack1.stackSize - j;
				}
			}
		} else if (this.inv[2] == null)
		{
			this.inv[2] = stack1;
		} else if (this.inv[2].isItemEqual(stack1))
		{
			// System.out.println("Adding a stuff to slot 2");
			this.inv[2].stackSize += stack1.stackSize;
		}

		if (stack2 != null)
		{
			i = localRecipePulverizer.getSecondaryOutputChance();
			if ((i >= 200) || (this.worldObj.rand.nextInt() < i))
			{
				if (this.inv[3] == null)
				{
					this.inv[3] = stack2;
				} else
				{
					this.inv[3].stackSize += stack2.stackSize;
				}
			}
		}
		this.inv[0].stackSize -= 1;
		if (this.inv[0].stackSize <= 0)
		{
			this.inv[0] = null;
		}
	}

	protected void analyzeStructure()
	{
		int blX = xCoord - 1;
		int blY = yCoord - 1;
		int blZ = zCoord - 1;
		TileEntity tile;

		System.out.println("I AM ANALYZING");

		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				for (int k = 0; k < 3; k++)
				{
					tile = worldObj.getTileEntity(i + blX, j + blY, k + blZ);
					if (tile != null)
					{
						// System.out.println("Getting tile entity: " +
						// tile.toString());
					} else
					{
						// System.out.println("Couldn't find a thing");
					}
					if (tile != null && tile instanceof TileEntityMassPulverizer)
					{
						if (!((TileEntityMassPulverizer) tile).isInMultiblock)
						{
							((TileEntityMassPulverizer) tile).isInMultiblock = true;
						}

						if (!((TileEntityMassPulverizer) tile).type.contains("Core"))
						{
							try
							{
								upgrades.add(tile);
							} catch (NullPointerException e)
							{
								System.err.println("NullPointer'd when trying to add thing");
							}
						}

						// System.out.println("Found at thing");
						System.out.println("Found thingy with: " + ((TileEntityMassPulverizer) tile).type);

					}
				}
			}
		}

		this.toScan = false;
	}

	public void getStuff()
	{
		if (!upgrades.isEmpty())
		{
			for (int i = 0; i < upgrades.size(); ++i)
			{
				TileEntityMassPulverizer tile = (TileEntityMassPulverizer) upgrades.get(i);

				if (tile.type.contains("Input"))
				{
					// System.out.println("Found input");
					if (((TileEntityMassPulverizer) tile).inv[1] != null)
					{
						System.out.println("Input has item");
						if (inv[0] == null)
						{
							// System.out.println("Grabbing items from input");
							inv[0] = ((TileEntityMassPulverizer) tile).inv[1];
							((TileEntityMassPulverizer) tile).inv[1] = null;
						} else if (inv[0].getItem() == ((TileEntityMassPulverizer) tile).inv[1].getItem())
						{
							if (((TileEntityMassPulverizer) tile).inv[1].stackSize + inv[0].stackSize > inv[0].getMaxStackSize())
							{
								inv[0].stackSize = inv[0].getMaxStackSize();
								// Write something here if needed,
								// hopefully the input should never let
								// this much in.
							} else
							{
								inv[0].stackSize = inv[0].stackSize + ((TileEntityMassPulverizer) tile).inv[1].stackSize;
							}

							((TileEntityMassPulverizer) tile).inv[1] = null;
						}
					} else if (((TileEntityMassPulverizer) tile).inv[0] != null)
					{
						System.out.println("Input has item");
						if (inv[0] == null)
						{
							// System.out.println("Grabbing items from input");
							inv[0] = ((TileEntityMassPulverizer) tile).inv[0];
							((TileEntityMassPulverizer) tile).inv[0] = null;
						} else if (inv[0].getItem() == ((TileEntityMassPulverizer) tile).inv[0].getItem())
						{
							if (((TileEntityMassPulverizer) tile).inv[0].stackSize + inv[0].stackSize > inv[0].getMaxStackSize())
							{
								inv[0].stackSize = inv[0].getMaxStackSize();
								// Write something here if needed,
								// hopefully the input should never let
								// this much in.
							} else
							{
								inv[0].stackSize = inv[0].stackSize + ((TileEntityMassPulverizer) tile).inv[0].stackSize;
							}

							((TileEntityMassPulverizer) tile).inv[0] = null;
						}
					}
				}

				if (tile.type.contains("Power"))
				{

				}

				if (tile.type.contains("Output"))
				{

				}
			}
		}
	}

	public int exportItems(ItemStack item)
	{
		List exportTiles = new ArrayList<TileEntityMassPulverizer>();
		for (int i = 0; i < upgrades.size(); i++)
		{
			if (upgrades.get(i) instanceof TileEntityMassPulverizer)
			{
				if (((TileEntityMassPulverizer) upgrades.get(i)).type.contains("Output"))
				{
					exportTiles.add(upgrades.get(i));
				}
			}
		}

		List outputInto = new ArrayList<TileEntity>();
		if (!exportTiles.isEmpty())
		{
			for (int i = 0; i < exportTiles.size(); i++)
			{
				TileEntity outputtingFrom = (TileEntity) exportTiles.get(i);

				for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
				{
					TileEntity te = worldObj.getTileEntity(outputtingFrom.xCoord + dir.offsetX, outputtingFrom.yCoord + dir.offsetY, outputtingFrom.zCoord + dir.offsetZ);
					if (te != null && te instanceof IInventory)
					{
						if (te instanceof TileEntityMassPulverizer)
						{
							if ((((TileEntityMassPulverizer)te).type).contains("Core") || ((TileEntityMassPulverizer)te).type.contains("Output"))
							{
							}
							else
							{
								outputInto.add(te);
							}
						}
						else
						{
							outputInto.add(te);
						}
					}
				}
			}
		}

		if (!outputInto.isEmpty() && item != null && item.getItem() != null && item.stackSize != 0)
		{
			int failCount = 0;
			insertionloop: while (item.stackSize > 0)
			{
				failCount = 0;
				for (int i = 0; i < outputInto.size(); i++)
				{
					if (outputInto.get(i) instanceof ISidedInventory)
					{
						int dir = 6;
						TileEntity curInv = (TileEntity) outputInto.get(i);
						System.out.println("Trying to export into thing at: " + curInv.xCoord + ", " + curInv.yCoord + ", " + curInv.zCoord);
						
						
						if (curInv.xCoord - this.xCoord == 2)
						{
							dir = 5;
						} 
						else if (curInv.yCoord - this.yCoord == 2)
						{
							dir = 1;
						} 
						else if (curInv.zCoord - this.zCoord == 2)
						{
							dir = 2;
						} 
						else if (curInv.zCoord - this.zCoord == -2)
						{
							dir = 3;
						} 
						else if (curInv.yCoord - this.yCoord == -2)
						{
							dir = 0;
						} 
						else if (curInv.xCoord - this.xCoord == -2)
						{
							dir = 4;
						}

						System.out.println("Dir is: " + dir);
						
						if (tryInsertion((ISidedInventory) outputInto.get(i), ItemHelper.cloneStack(item, 1), dir))
						{
							item.stackSize--;
						} 
						else
						{
							failCount++;
						}
					} else
					{
						if (tryInsertion((IInventory) outputInto.get(i), ItemHelper.cloneStack(item, 1)))
						{
							item.stackSize--;
							System.out.println("Stack size is: " + item.stackSize);
						} else
						{
							failCount++;
						}
					}
					if (item.stackSize <= 0)
					{
						break insertionloop;
					}
				}

				if (failCount >= outputInto.size())
				{
					break insertionloop;
				}
			}
		}

		if (item != null)
		{
			return item.stackSize;
		} else
		{
			return 0;
		}

	}

	public boolean tryInsertion(IInventory te, ItemStack item)
	{
		int slotWithItem = inventoryHasItem(te, item);
		int slotFirstEmpty = getFirstEmptyStackInInventory(te);
		if (slotWithItem != -1)
		{
			te.getStackInSlot(slotWithItem).stackSize++;
			return true;
		} else if (slotFirstEmpty != -1)
		{
			te.setInventorySlotContents(slotFirstEmpty, item);
			return true;
		}

		return false;
	}

	public boolean tryInsertion(ISidedInventory te, ItemStack item, int side)
	{
		int slotWithItem = inventoryHasItem(te, item);
		int slotFirstEmpty = getFirstEmptyStackInInventory(te);
		if (slotWithItem != -1 && te.canInsertItem(slotWithItem, item, side))
		{
			te.getStackInSlot(slotWithItem).stackSize++;
			return true;
		} else if (slotFirstEmpty != -1 && te.canInsertItem(slotFirstEmpty, item, side))
		{
			te.setInventorySlotContents(slotFirstEmpty, item);
			return true;
		}

		return false;
	}

	public int getFirstEmptyStackInInventory(IInventory te)
	{
		for (int i = 0; i < te.getSizeInventory(); i++)
		{
			if (te.getStackInSlot(i) == null)
			{
				return i;
			}
		}
		return -1;
	}

	public int inventoryHasItem(IInventory te, ItemStack item)
	{
		for (int i = 0; i < te.getSizeInventory(); i++)
		{
			if (te != null && item != null)
			{
				if (te.getStackInSlot(i) != null)
				{
					if (te.getStackInSlot(i).getItem() == item.getItem() && te.getStackInSlot(i).stackSize != te.getStackInSlot(i).getMaxStackSize())
					{
						if (ItemHelper.itemsEqualWithMetadata(te.getStackInSlot(i), item))
							return i;
					}
				}
			}
		}

		return -1;
	}

	@Override
	public void updateEntity()
	{
		if (bottomLeft == null)
		{
			bottomLeft = getBottomLeft(worldObj, xCoord, yCoord, zCoord);
		}

		if (masterPos == null)
		{
			masterPos = new int[] { bottomLeft[0] + 1, bottomLeft[1] + 1, bottomLeft[2] + 1 };
		}

		if (master == null && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 1)
		{
			master = (TileEntityMassPulverizer) this.worldObj.getTileEntity(masterPos[0], masterPos[1], masterPos[2]);
			if (master != null)
			{
				isInMultiblock = true;
			}
		}

		// System.out.println("checking type: " + type);
		if (tryFormMultiblock)
		{
			formMultiblock();
		}

		if (type.contains("Core") && toScan == true)
		{
			if (upgrades == null)
			{
				upgrades = new ArrayList<TileEntity>();
			}
			analyzeStructure();
		}

		if (type.equals("Core"))
		{
			boolean flag = pulverizeTime > 0;
			boolean flag1 = false;
			// System.out.println("Getting server thingy");

			if (!worldObj.isRemote)
			{
				if (canPulverize())
				{
					processStart();
					++this.pulverizeTime;

					if (this.pulverizeTime == 200)
					{
						pulverizeTime = 0;
						processFinish();
						flag1 = true;
					}
				} else
				{
					pulverizeTime = 0;
				}
			} else
			{

			}
			if (flag1)
			{
				this.markDirty();
			}
		} else if (type.contains("Power") && master != null)
		{
			int energyHad = this.energyStorage.getEnergyStored();
			int energyNeeded = master.energyStorage.getMaxEnergyStored() - master.energyStorage.getEnergyStored();

			if (energyNeeded <= energyHad)
			{
				master.energyStorage.modifyEnergyStored(+energyNeeded);
				this.energyStorage.modifyEnergyStored(-energyNeeded);
			} else
			{
				master.energyStorage.modifyEnergyStored(+energyHad);
				this.energyStorage.modifyEnergyStored(-energyHad);
			}
		}

		if (type.contains("Core") && isInMultiblock)
		{
			getStuff();

			for (int i = 1; i <= 3; i++)
			{
				if (inv[i] != null)
				{
					inv[i].stackSize = exportItems(inv[i]);
					if (inv[i].stackSize <= 0)
					{
						inv[i] = null;
					}
				}
			}
		}
	}
}

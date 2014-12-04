package com.ddogclaw.godeeper.block;

import com.ddogclaw.godeeper.GoDeeper;
import com.ddogclaw.godeeper.tileentity.TileEntityCuilGeneratorEntity;
import com.ddogclaw.godeeper.tileentity.TileEntityRealityCompressor;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockRealityCompressor extends BlockContainer
{
	public String name = "realityCompressor";
	private String blockType;
	public TileEntity tile;
	public int[] centerBlock;
	public int[] bottomLeft;
	public boolean partOfMultiblock;

	public BlockRealityCompressor(String type)
	{
		super(Material.rock);
		blockType = type;
		name = name + type;

		setBlockName(GoDeeper.MODID + "." + name);
		setBlockTextureName(GoDeeper.MODID + ":" + name);
		GameRegistry.registerBlock(this, name);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		bottomLeft = getBottomLeft(world, x, y, z);
		if (getValid(world, bottomLeft[0], bottomLeft[1], bottomLeft[2]))
		{
			System.out.println("CHANGIN BLOCKS");
			world.setBlock(bottomLeft[0] + 1, bottomLeft[1] + 1,
					bottomLeft[2] + 1,
					GoDeeperBlocks.blockRealityCompressorCore);
			world.markBlockForUpdate(bottomLeft[0] + 1, bottomLeft[1] + 1,
					bottomLeft[2] + 1);
			world.getBlock(bottomLeft[0] + 1, bottomLeft[1] + 1, bottomLeft[2] + 1).createTileEntity(world, 0);
			
			startMultiblock(world, bottomLeft[0] + 1, bottomLeft[1] + 1,
					bottomLeft[2] + 1);
			// centerBlock[0] = bottomLeft[0] + 1;
			// centerBlock[1] = bottomLeft[1] + 1;
			// centerBlock[2] = bottomLeft[2] + 1;

		}
	}

	@Override
	public boolean isOpaqueCube()
	{
		if (blockType == "Glass" || blockType == "Core")
		{
			return false;
		} else
		{
			return true;
		}
	}

	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass()
	{
		if (blockType == "Glass" || blockType == "Core")
		{
			return 1;
		} else
		{
			return 0;
		}
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z,
			int neighbor)
	{
		System.out.println("SOMEBODY DONE BROKED IT!");
		if (bottomLeft == null)
		{
			bottomLeft = getBottomLeft(world, x, y, z);
		}
		if (partOfMultiblock)
		{
			world.setBlock(bottomLeft[0] + 1, bottomLeft[1] + 1,
					bottomLeft[2] + 1, Blocks.air);
			world.markBlockForUpdate(bottomLeft[0] + 1, bottomLeft[1] + 1,
					bottomLeft[2] + 1);
		}
		terminateMultiblock(world, bottomLeft[0], bottomLeft[1], bottomLeft[2]);

	}

	public int[] getBottomLeft(World world, int x, int y, int z)
	{
		int[] returnValues = new int[3];
		int previousX = 0;
		int previousY = 0;
		int previousZ = 0;

		for (int i = 2; i >= 0; i--)
		{
			System.out.println("Checking x:" + (x - i));
			for (int k = 2; k >= 0; k--)
			{
				System.out.println("Checking y:" + (y - k));
				for (int l = 2; l >= 0; l--)
				{
					System.out.println("Checking z:" + (z - l));
					if (world.getBlock(x - i, y - k, z - l) instanceof BlockRealityCompressor)
					{
						previousX = x - i;
						previousY = y - k;
						previousZ = z - l;

						returnValues[0] = previousX;
						returnValues[1] = previousY;
						returnValues[2] = previousZ;
						System.out.println("Bottom left is at: " + previousX
								+ "," + previousY + "," + previousZ);
						return returnValues;
					}
				}
			}
		}

		return null;
	}

	public void terminateMultiblock(World world, int x, int y, int z)
	{
		for (int i = 0; i < 3; i++)
		{
			for (int k = 0; k < 3; k++)
			{
				for (int l = 0; l < 3; l++)
				{
					TileEntity tile = world.getTileEntity(x + k, y + i, z + l);
					Block block = world.getBlock(x + k, y + i, z + l);
					if (tile instanceof TileEntityRealityCompressor)
					{
						((TileEntityRealityCompressor) tile).breakMultiblock();
						if (((TileEntityRealityCompressor) tile).machineType == "Core")
						{

						}
					}

					if (block instanceof BlockRealityCompressor)
					{
						((BlockRealityCompressor) block).partOfMultiblock = false;
					}
				}
			}
		}
	}

	public void startMultiblock(World world, int x, int y, int z)
	{
		for (int i = 0; i < 3; i++)
		{
			for (int k = 0; k < 3; k++)
			{
				for (int l = 0; l < 3; l++)
				{
					TileEntity tile = world.getTileEntity(x + k, y + i, z + l);
					Block block = world.getBlock(x + k, y + i, z + l);
					if (tile instanceof TileEntityRealityCompressor)
					{
					} else if (block instanceof BlockRealityCompressor)
					{
						((BlockRealityCompressor) block).partOfMultiblock = true;
					}
				}
			}
		}
	}

	public boolean getValid(World world, int x, int y, int z)
	{
		Block block = this;

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
						if (!(k == 1 && i ==1 && l == 1 && world.getBlock(x + k, y + i, z + l) == Blocks.air))
						return false;
					}
				}
			}
		}

		if (!world.getBlock(x + 1, y + 1, z + 1).isAir(world, x, y, z))
		{
			return false;
		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_)
	{
		// TODO Auto-generated method stub
		if (blockType == "Core")
		{
			return new TileEntityRealityCompressor(blockType);
		} else if (blockType == "Input" || blockType == "Output")
		{
			return new TileEntityRealityCompressor(blockType);
		} else
		{
			return null;
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		if (bottomLeft == null)
		{
			bottomLeft = getBottomLeft(world, x, y, z);
		}

		System.out.println("Opening Compression GUI");

		TileEntity tileEntity = world.getTileEntity(bottomLeft[0] + 1,
				bottomLeft[1] + 1, bottomLeft[2] + 1);

		if (tileEntity == null)
		{
			System.out.println("No tileEntity found");
			return false;
		}

		if (!world.isRemote)
		{
			System.out.println("Opening GUI");
			player.openGui(GoDeeper.instance, 2, world, bottomLeft[0] + 1,
					bottomLeft[1] + 1, bottomLeft[2] + 1);

			return true;
		}
		return false;

	}

}

package com.ddogclaw.godeeper.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.ddogclaw.godeeper.GoDeeper;
import com.ddogclaw.godeeper.tileentity.TileEntityMassPulverizer;
import com.ddogclaw.godeeper.tileentity.TileEntityRealityCompressor;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMassPulverizer extends BlockContainer
{
	public String name = "massPulverizer";
	private String blockType;
	public TileEntity tile;
	public int[] centerBlock;
	public int[] bottomLeft;
	public boolean partOfMultiblock = false;
	protected int xPos;
	protected int yPos;
	protected int zPos;

	public BlockMassPulverizer(String type)
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
		xPos = x;
		yPos = y;
		zPos = z;
		
		bottomLeft = getBottomLeft(world, x, y, z);
		System.out.println("Running valid check");
		
		if (bottomLeft == null)
		{
			bottomLeft = this.getBottomLeft(world, x, y, z);
		}
		
		//try{
		if (getValid(world, bottomLeft[0], bottomLeft[1], bottomLeft[2]))
		{
			System.out.println("CHANGIN BLOCKS");
			world.setBlock(bottomLeft[0] + 1, bottomLeft[1] + 1,
					bottomLeft[2] + 1,
					GoDeeperBlocks.blockMassPulverizerCore);
			world.markBlockForUpdate(bottomLeft[0] + 1, bottomLeft[1] + 1,
					bottomLeft[2] + 1);
			world.getBlock(bottomLeft[0] + 1, bottomLeft[1] + 1, bottomLeft[2] + 1).createTileEntity(world, 0);
			
			startMultiblock(world, bottomLeft[0], bottomLeft[1],
					bottomLeft[2]);
			// centerBlock[0] = bottomLeft[0] + 1;
			// centerBlock[1] = bottomLeft[1] + 1;
			// centerBlock[2] = bottomLeft[2] + 1;

		}
		//} catch (NullPointerException err)
		//{
		//	System.err.println("Nullpointered");
		//}
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
		if (world.getBlockMetadata(x, y, z) == 1 || world.getBlockMetadata(x, y, z) == 2)
		{
			System.out.println("SOMEBODY DONE BROKED IT!");
			if (bottomLeft == null)
			{
				bottomLeft = getBottomLeft(world, x, y, z);
			}
			if (partOfMultiblock)
			{
				System.out.println("Doing the breaking thing");
				world.removeTileEntity(bottomLeft[0] + 1, bottomLeft[1] + 1, bottomLeft[2] + 1);
				world.func_147480_a(bottomLeft[0] + 1, bottomLeft[1] + 1,
						bottomLeft[2] + 1, false);
			
			}
			terminateMultiblock(world, bottomLeft[0], bottomLeft[1], bottomLeft[2]);
		}
	}
	
	@Override
	public void breakBlock(World world, int i, int j, int k, Block par5, int par6)
	{
		TileEntityMassPulverizer tileEntity = (TileEntityMassPulverizer)world.getTileEntity(i, j, k);
		if (tileEntity != null)
		{
			
			world.func_147480_a(i, j, k, false);
			world.removeTileEntity(i, j, k);

		}
		world.removeTileEntity(i, j, k);
	}

	public int[] getBottomLeft(World world, int x, int y, int z)
	{
		int[] returnValues = new int[3];
		int previousX = 0;
		int previousY = 0;
		int previousZ = 0;

		for (int i = 2; i >= 0; i--)
		{
			//System.out.println("Checking x:" + (x - i));
			for (int k = 2; k >= 0; k--)
			{
				//System.out.println("Checking y:" + (y - k));
				for (int l = 2; l >= 0; l--)
				{
					//System.out.println("Checking z:" + (z - l));
					Block currentBlock = world.getBlock(x - i, y - k, z - l);

					if (world.getBlock(x - i, y - k, z - l) instanceof BlockMassPulverizer)
					{
						if (world.getBlockMetadata(x - i, y - k, z - l) == 0)
						{
							BlockMassPulverizer block = (BlockMassPulverizer) world.getBlock(x - i, y - k, z - l);

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
		}

		return null;
	}
	
	public int[] getMetaBottomLeft(World world, int x, int y, int z)
	{
		int[] returnValues = new int[3];
		int previousX = 0;
		int previousY = 0;
		int previousZ = 0;

		for (int i = 2; i >= 0; i--)
		{
			//System.out.println("Checking x:" + (x - i));
			for (int k = 2; k >= 0; k--)
			{
				//System.out.println("Checking y:" + (y - k));
				for (int l = 2; l >= 0; l--)
				{
					//System.out.println("Checking z:" + (z - l));
					Block currentBlock = world.getBlock(x - i, y - k, z - l);

					if (world.getBlock(x - i, y - k, z - l) instanceof BlockMassPulverizer)
					{
						if (world.getBlockMetadata(x - i, y - k, z - l) == 2)
						{
							BlockMassPulverizer block = (BlockMassPulverizer) world.getBlock(x - i, y - k, z - l);

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
					//TileEntity tile = world.getTileEntity(x + k, y + i, z + l);
					Block block = world.getBlock(x + k, y + i, z + l);
					
					world.setBlockMetadataWithNotify(x + k, y + i, z + l, 0, 2);
					
					if (block instanceof BlockMassPulverizer)
					{
						if (((BlockMassPulverizer) block).blockType == "Core")
						{
							world.removeTileEntity(x + k, y + i, z + l);
							world.func_147480_a(x + k, y + i, z + l, false);
						} else {
							
						}
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
					if (tile instanceof TileEntityMassPulverizer)
					{
						((TileEntityMassPulverizer)tile).formMultiblock();
					}
					


					if (world.setBlockMetadataWithNotify(x + k, y + i, z + l, 1, 2))
					{
						System.out.println("Succeeded meta change");
					} else
					{
						System.out.println("Falied meta change");
					}
					
					if (i == 0 && k == 0 && l == 0)
					{
						System.out.println("Setting corner meta");
						world.setBlockMetadataWithNotify(x, y, z, 2, 2);
					}
				}
			}
		}
	}

	public boolean getValid(World world, int x, int y, int z)
	{
		Block block = this;

		System.out.println("Beginning testing");
		for (int i = 0; i < 3; i++)
		{
			for (int k = 0; k < 3; k++)
			{
				for (int l = 0; l < 3; l++)
				{
					Block curBlock = world.getBlock(x + k, y + i, z + l);

					if (!(k == 1 && i ==1 && l == 1 && curBlock == Blocks.air) && !(curBlock instanceof BlockMassPulverizer))
					{
						System.out.println("False by way of found air");
						return false;
					}
					
					if (world.getBlockMetadata(x + k, y + i, z + l) == 1)
					{
						return false;
					}
				}
			}
		}

		if (!world.getBlock(x + 1, y + 1, z + 1).isAir(world, x, y, z))
		{
			if (!(world.getBlock(x + 1, y + 1, z + 1) == GoDeeperBlocks.blockMassPulverizerCore))
			{
				System.out.println("False by way of no open space");
				return false;
			}
		}

		System.out.println("returning true");
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_)
	{
		if (bottomLeft == null)
		{
			bottomLeft = getBottomLeft(world, xPos, yPos, zPos);
		}
		
		// TODO Auto-generated method stub
		if (blockType == "Core")
		{
			return new TileEntityMassPulverizer(blockType);
		} else if (blockType == "Input" || blockType == "Output")
		{
			return new TileEntityMassPulverizer(blockType);
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
			this.getBottomLeft(world, x, y, z);
		}
		
		System.out.println("Right clicked with meta: " + world.getBlockMetadata(x,y,z));
		

		if (world.getBlockMetadata(x, y, z) == 1)
		{
			int[] metaBottomLeft = getMetaBottomLeft(world, x, y, z);
			System.out.println("Multiblock is valid");


			System.out.println("Opening pulverizer GUI");

			TileEntity tileEntity = world.getTileEntity(metaBottomLeft[0] + 1,
					metaBottomLeft[1] + 1, metaBottomLeft[2] + 1);

			TileEntityMassPulverizer tile = (TileEntityMassPulverizer) tileEntity;
			if (tileEntity == null)
			{
				System.out.println("No tileEntity found");
				return false;
			}

			if (!world.isRemote && tile.isInMultiblock)
			{
				System.out.println("Opening GUI");
				player.openGui(GoDeeper.instance, 3, world, metaBottomLeft[0] + 1,
						metaBottomLeft[1] + 1, metaBottomLeft[2] + 1);

				return true;
			}
			return false;
		}

		return false;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess iblockacess, int i, int j, int k, int par5)
	{
		if (blockType == "Core")
		{
			return false;
		}
		return true;
	}
	

	
	
}
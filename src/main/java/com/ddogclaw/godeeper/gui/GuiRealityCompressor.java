package com.ddogclaw.godeeper.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import com.ddogclaw.godeeper.GoDeeper;
import com.ddogclaw.godeeper.container.ContainerCuilGenerator;
import com.ddogclaw.godeeper.container.ContainerRealityCompressor;
import com.ddogclaw.godeeper.tileentity.TileEntityCuilGeneratorEntity;
import com.ddogclaw.godeeper.tileentity.TileEntityRealityCompressor;

public class GuiRealityCompressor extends GuiContainer
{
	public static final int guiID = 2;
	
	private int teX;
	private int teY;
	private int teZ;
	
	private TileEntityRealityCompressor tileReality;
	
	public GuiRealityCompressor(InventoryPlayer inventoryPlayer, TileEntityRealityCompressor tileEntity)
	{
		super(new ContainerRealityCompressor(inventoryPlayer, tileEntity));
		teX = tileEntity.xCoord;
		teY = tileEntity.yCoord;
		teZ = tileEntity.zCoord;
		tileReality = tileEntity;
	}
	
	public int xSize = 176;
	public int ySize = 166;
	
	@Override
	public void initGui()
	{
		super.initGui();
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
	
	protected void drawGuiContainerForegroundLayer(int param1, int param2)
	{
		//Text and stuff
		String title = "Mass Pulverizer";
		fontRendererObj.drawString(title, xSize / 2 - fontRendererObj.getStringWidth(title) / 2, 7, 8421504);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1,
			int par2, int par3)
	{
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.renderEngine.bindTexture(new ResourceLocation(GoDeeper.MODID + ":textures/gui/realityCompressorGuiBG.png"));
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, 247, ySize);
		
	}
}

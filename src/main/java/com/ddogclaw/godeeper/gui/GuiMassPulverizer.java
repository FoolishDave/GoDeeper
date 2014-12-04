package com.ddogclaw.godeeper.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.ddogclaw.godeeper.GoDeeper;
import com.ddogclaw.godeeper.container.ContainerMassPulverizer;
import com.ddogclaw.godeeper.container.ContainerRealityCompressor;
import com.ddogclaw.godeeper.tileentity.TileEntityMassPulverizer;
import com.ddogclaw.godeeper.tileentity.TileEntityRealityCompressor;

public class GuiMassPulverizer extends GuiContainer
{
public static final int guiID = 3;
	
	private int teX;
	private int teY;
	private int teZ;
	
	private static final ResourceLocation pulverizerGuiTextures = new ResourceLocation(GoDeeper.MODID + ":textures/gui/realityCompressorGuiBG.png");
	
	private TileEntityMassPulverizer tilePulverizer;
	
	public GuiMassPulverizer(InventoryPlayer inventoryPlayer, TileEntityMassPulverizer tileEntity)
	{
		super(new ContainerMassPulverizer(inventoryPlayer, tileEntity));
		teX = tileEntity.xCoord;
		teY = tileEntity.yCoord;
		teZ = tileEntity.zCoord;
		tilePulverizer = tileEntity;
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
		this.mc.renderEngine.bindTexture(pulverizerGuiTextures);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		
		if (this.tilePulverizer.isPulverizing())
		{
			//int i1 = this.tilePulverizer.getPulverizeTimeRemainingScaled(13);
			//this.drawTexturedModalRect(x + 56, y + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 1);
			int i1 = this.tilePulverizer.getCookProgressScaled(24);
			this.drawTexturedModalRect(x + 76, y + 34, 176, 14, i1 + 1, 16);
		}
	}
}



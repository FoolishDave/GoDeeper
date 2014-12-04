package com.ddogclaw.godeeper.gui;

import io.netty.buffer.ByteBuf;

import org.lwjgl.opengl.GL11;

import com.ddogclaw.godeeper.GoDeeper;
import com.ddogclaw.godeeper.container.ContainerCuilGenerator;
import com.ddogclaw.godeeper.tileentity.TileEntityCuilGeneratorEntity;
import com.ddogclaw.godeeper.CuilMessage;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiCuilGenerator extends GuiContainer
{
	public static final int guiID = 1;
	public int cuilLevel;
	
	private int teX;
	private int teY;
	private int teZ;
	
	private TileEntityCuilGeneratorEntity tileCuil;
	
	
	public GuiCuilGenerator(InventoryPlayer inventoryPlayer, TileEntityCuilGeneratorEntity tileEntity)
	{
		super(new ContainerCuilGenerator(inventoryPlayer, tileEntity));
		teX = tileEntity.xCoord;
		teY = tileEntity.yCoord;
		teZ = tileEntity.zCoord;
		tileCuil = tileEntity;
		cuilLevel = tileCuil.getCuilLevel();
	}
	
	public int xSize = 176;
	public int ySize = 166;
	
	@Override
	public void initGui()
	{
		super.initGui();

		cuilLevel = tileCuil.getCuilLevel();
		
		buttonList.clear();
		
		buttonList.add(new GuiButton(1, (width + xSize / 3) / 2, ((height / 2) - 10) - ySize / 3, 20, 20, "+"));
		buttonList.add(new GuiButton(2, (width + xSize / 3) / 2, ((height / 2) + 25) - ySize / 3, 20, 20, "-"));
	}
	
	@Override
	protected void actionPerformed (GuiButton guiButton)
	{

		cuilLevel = tileCuil.getCuilLevel();
		switch(guiButton.id)
		{
		case 1: 
			cuilLevel++;
			break;
		case 2: cuilLevel--;
			break;
		default:
			break;
		}
		GoDeeper.net.sendToServer(new CuilMessage(this.teX, this.teY, this.teZ, "buttonPress", this.cuilLevel));	
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
	
	protected void drawGuiContainerForegroundLayer(int param1, int param2)
	{
		//Text and stuff
		String title = "Cuil Generator";
		fontRendererObj.drawString(title, xSize / 2 - fontRendererObj.getStringWidth(title) / 2, 7, 8421504);
		
		String s = "Current Cuil Level: " + cuilLevel;
		fontRendererObj.drawString("Current: " + cuilLevel, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 15, 5592405);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1,
			int par2, int par3)
	{
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.renderEngine.bindTexture(new ResourceLocation(GoDeeper.MODID + ":textures/gui/cuilGeneratorGuiBG.png"));
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, 247, ySize);
		
	}
	


}

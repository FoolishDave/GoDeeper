package com.ddogclaw.godeeper.tileentity;

import org.lwjgl.opengl.GL11;

import com.ddogclaw.godeeper.GoDeeper;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TileEntityRenderCuilGenerator extends TileEntitySpecialRenderer
{
	public static final ResourceLocation texture = new ResourceLocation(GoDeeper.MODID, "/textures/model/blockCuilGenerator.png");

	public ModelCuilGenerator model;
	
	public TileEntityRenderCuilGenerator()
	{
		this.model = new ModelCuilGenerator();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x,
			double y, double z, float var8)
	{
		//Tell renderer to start something!
		GL11.glPushMatrix();
		//Set Location
		GL11.glTranslatef((float) x + .5f, (float) y - .5f, (float) z + .5f);
		//Block Texture binding
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		//Push that matrix one more time and rotate
		GL11.glPushMatrix();
		GL11.glRotatef(0.0f, 0.0f, 0.0f, 0.0f);
		//Reference model file
		this.model.render((Entity) null, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
		//Stop popping them matrix
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
	//set lighting
	private void adjustLightFixture(World world, int i, int j, int k, Block block)
	{
		Tessellator tess = Tessellator.instance;
		float brightness = block.getLightOpacity(world, i, j, k);
		int skyLight = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
		int modulousModifier = skyLight % 65536;
		int divModifier = skyLight / 65536;
		tess.setColorOpaque_F(brightness, brightness, brightness);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) modulousModifier, divModifier);
	}
}

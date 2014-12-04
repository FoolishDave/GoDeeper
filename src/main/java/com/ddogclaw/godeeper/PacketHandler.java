package com.ddogclaw.godeeper;

import com.ddogclaw.godeeper.gui.GuiCuilGenerator;
import com.ddogclaw.godeeper.tileentity.TileEntityCuilGeneratorEntity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;


public class PacketHandler implements IMessageHandler<CuilMessage, IMessage>
{
	TileEntityCuilGeneratorEntity te;
	GuiCuilGenerator gui;

	@Override
	public IMessage onMessage(CuilMessage message, MessageContext ctx)
	{
		System.out.println("PACKET HANDLING Received Message to Server");
		if(message.message != null)
		{
			te = (TileEntityCuilGeneratorEntity) ctx.getServerHandler().playerEntity.getEntityWorld().getTileEntity(message.x, message.y, message.z);
			System.out.println("PACKET HANDLING Has message: " + message.message);
			if (message.message.contains("level"))
			{

			}
			else if (message.message.contains("buttonPress"))
			{
				System.out.println("PACKET HANDLING Setting tile entity cuil level");
				
				te.cuilLevel = message.cuilLevel;
			}
		}


		
		return null;
	}
}



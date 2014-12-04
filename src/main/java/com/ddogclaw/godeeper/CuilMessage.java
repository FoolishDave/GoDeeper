package com.ddogclaw.godeeper;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class CuilMessage implements IMessage
{
	public int cuilLevel;
	
	public int x;
	public int y;
	public int z;

	public String message;
	
	public CuilMessage() {}
	
	public CuilMessage(int x, int y, int z, String message, int cuilLevel)
	{
		this.cuilLevel = cuilLevel;
		this.x = x;
		this.y = y;
		this.z = z;
		this.message = message;
	}
	
	public CuilMessage(int x, int y, int z, String message)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.message = message;
	}
	

	@Override
	public void fromBytes(ByteBuf buf)
	{		
		x = ByteBufUtils.readVarInt(buf, 5);
		y = ByteBufUtils.readVarInt(buf, 5);
		z = ByteBufUtils.readVarInt(buf, 5);
		cuilLevel = ByteBufUtils.readVarInt(buf, 5);
		message = ByteBufUtils.readUTF8String(buf);
		
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeVarInt(buf, x, 5);
		ByteBufUtils.writeVarInt(buf, y, 5);
		ByteBufUtils.writeVarInt(buf, z, 5);
		ByteBufUtils.writeVarInt(buf, cuilLevel, 5);
		ByteBufUtils.writeUTF8String(buf, message);
	}
}
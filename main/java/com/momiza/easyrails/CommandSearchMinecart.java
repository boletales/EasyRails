package com.momiza.easyrails;
import java.util.ArrayList;
import java.util.List;

import com.momiza.easyrails.minecarts.EntityNewMinecart;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
public class CommandSearchMinecart extends CommandBase {
	@Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "searchminecart";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "usage:/searchminecart";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		List<String> results = new ArrayList<String>();
		for(Entity e : sender.getEntityWorld().loadedEntityList){
			if(e instanceof EntityNewMinecart){
				results.add(
						((EntityNewMinecart)e).getShortName()
						+" §r at "+Math.round(e.posX)+","+Math.round(e.posY)+","+Math.round(e.posZ)

						+" ("+getDirectionStrFromVec3(e.getPositionVector().subtract(sender.getPositionVector()))
						+" "+Math.round(sender.getPositionVector().distanceTo(e.getPositionVector()))+"m"+")"

						+" speedLv:"+((EntityNewMinecart)e).speedLevel);
			}
		}
		if(results.size()==0){
			sender.sendMessage(new TextComponentString("No Results"));
		}else{
			sender.sendMessage(new TextComponentString("Results:"+results.size()));
			sender.sendMessage(new TextComponentString(String.join("\n", results)));
		}
	}

	private String getDirectionStrFromVec3(Vec3d v){
		double direction=Math.atan2(v.z, v.x);
		if(Double.isNaN(direction)){
			return "NaN";
		}else{
			return new String[]{"W","NW","N","NE","E","SE","S","SW","W"}[(int) Math.round(direction/Math.PI*4)+4];
		}
	}

}
package com.momiza.easyrails.utilitems;
import com.momiza.easyrails.EasyRails;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandRailWand extends CommandBase {

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "rw";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "usage:"+usage();
	}

	private String usage(){
		return "/rw len|dy|mul|tun|enabled|set|sett|setl [value]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length>=2){
			if(args[0].equals("len")){
				if(Integer.parseInt(args[1])!=0){
					EasyRails.rw_length=Integer.parseInt(args[1]);
				}
			}else if(args[0].equals("dy")){
				if(Math.abs(Integer.parseInt(args[1]))<=1){
					EasyRails.rw_dy=Integer.parseInt(args[1]);
				}
			}else if(args[0].equals("mul")){
				if(args[1].equals("true")){
					EasyRails.rw_isMultiTrack=true;
				}else if(args[1].equals("false")){
					EasyRails.rw_isMultiTrack=false;
				}
			}else if(args[0].equals("tun")){
				if(args[1].equals("true")){
					EasyRails.rw_isTunnel=true;
				}else if(args[1].equals("false")){
					EasyRails.rw_isTunnel=false;
				}
			}else if(args[0].equals("enabled")){
				if(args[1].equals("true")){
					EasyRails.rw_enabled=true;
				}else if(args[1].equals("false")){
					EasyRails.rw_enabled=false;
				}
			}else if(args[0].equals("set")){
				EasyRails.rw_block=CommandBase.getBlockByText(sender, args[1]).getDefaultState();
			}else if(args[0].equals("sett")){
				EasyRails.rw_tblock=CommandBase.getBlockByText(sender, args[1]).getDefaultState();
			}else if(args[0].equals("setl")){
				EasyRails.rw_lblock=CommandBase.getBlockByText(sender, args[1]).getDefaultState();
			}
		}else if(args.length==1){
			if(args[0].equals("enable")){
				EasyRails.rw_enabled=true;
			}else if(args[0].equals("diable")){
				EasyRails.rw_enabled=false;
			}else{
				sender.sendMessage(new TextComponentString(usage()));
			}
		}else{
			sender.sendMessage(new TextComponentString(usage()));
		}
		sender.sendMessage(new TextComponentString(
				(EasyRails.rw_enabled?"enabled,":"disabled,")
				+"length:"+EasyRails.rw_length
				+",dy:"+EasyRails.rw_dy
				+","+(EasyRails.rw_isMultiTrack?"MultiTrack":"SingleTrack")
				+(EasyRails.rw_isTunnel?",Tunneled":"")
			));
	}

}
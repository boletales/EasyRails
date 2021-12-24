package com.momiza.easyrails.utilblocks;

import com.momiza.easyrails.PacketHandler;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
public class GuiContainerI2O1 extends GuiContainer {
	private static final ResourceLocation TEXTURE = new ResourceLocation("remotemod","textures/gui/gui.png");
	private TileEntityI2O1 tile;
	public GuiContainerI2O1(Container inventorySlotsIn,TileEntity t) {
		super(inventorySlotsIn);
		tile=(TileEntityI2O1)t;
	}



	@Override
	public void initGui() {
		super.initGui();
		for(EnumFacing f:EnumFacing.values()){
			this.buttonList.add(new GuiButton(f.getIndex(), f.getIndex()*50+70, 60, 40, 20,f.toString()));
		}
		for(EnumFacing f:EnumFacing.values()){
			this.buttonList.add(new GuiButton(f.getIndex()+6, f.getIndex()*50+70, 120, 40, 20,f.toString()));
		}
		for(EnumFacing f:EnumFacing.values()){
			this.buttonList.add(new GuiButton(f.getIndex()+12, f.getIndex()*50+70, 180, 40, 20,f.toString()));
		}
		refreshButton();
	}

	private void refreshButton(){
		for(GuiButton b:this.buttonList){
			b.enabled=true;
		}
		this.buttonList.get(tile.IN1.getIndex()).enabled=false;
		this.buttonList.get(tile.IN2.getIndex()+6).enabled=false;
		this.buttonList.get(tile.OUT.getIndex()+12).enabled=false;
	}

	public void actionPerformed(GuiButton guibutton) {
		if(guibutton.id<6){
			BlockI2O1.setIN1(tile.getWorld(), tile.getPos(),tile.getWorld().getBlockState(tile.getPos()),EnumFacing.values()[guibutton.id]);
		}else if(guibutton.id<12){
			BlockI2O1.setIN2(tile.getWorld(), tile.getPos(),tile.getWorld().getBlockState(tile.getPos()),EnumFacing.values()[guibutton.id-6]);
		}else if(guibutton.id<24){
			BlockI2O1.setOUT(tile.getWorld(), tile.getPos(),tile.getWorld().getBlockState(tile.getPos()),EnumFacing.values()[guibutton.id-12]);
		}

		refreshButton();

		BlockPos p=tile.getPos();
		PacketHandler.INSTANCE.sendToServer(new MessageI2O1(p.getX() , p.getY(), p.getZ(),tile.getIN(),tile.getIN1(),tile.getIN2(),tile.getOUT()));
	}


	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseZ) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseZ);
		this.drawString(fontRenderer, "↓ IN1 ↓", 70, 0, 0xFFFFFF);
		this.drawString(fontRenderer, "↓ IN2 ↓", 70, 60, 0xFFFFFF);
		this.drawString(fontRenderer, "↓ OUT ↓", 70, 120, 0xFFFFFF);
	}


	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		// TODO 自動生成されたメソッド・スタブ
		super.drawDefaultBackground();
		//this.mc.renderEngine.bindTexture(TEXTURE);
		//this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, xSize, ySize);
	}


}

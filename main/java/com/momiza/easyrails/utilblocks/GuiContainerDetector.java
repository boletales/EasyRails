package com.momiza.easyrails.utilblocks;

import com.momiza.easyrails.PacketHandler;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;


public class GuiContainerDetector extends GuiContainer {
	private static final ResourceLocation TEXTURE = new ResourceLocation("remotemod","textures/gui/gui.png");
	private TileEntityMinecartDetector tile;
	boolean[] detect=new boolean[16];
	boolean detectChest,detectRidable,defaultOut;
	public GuiContainerDetector(Container inventorySlotsIn,TileEntity t) {
		super(inventorySlotsIn);
		tile=(TileEntityMinecartDetector)t;
		detect=tile.detect;
		detectChest = tile.detectChest;
		detectRidable = tile.detectRidable;
		defaultOut = tile.defaultOut;
	}




	@Override
	public void initGui() {
		super.initGui();
		for(int y=0;y<4;y++){
			for(int x=0;x<4;x++){
				int color = y*4+x;
				this.buttonList.add(new GuiButton(color, 50+x*100, 50+y*50, 45, 20, colorButtonStr(color,detect[color])));
			}
		}
		this.buttonList.add(new GuiButton(16, 250, 10, 45, 20,typeButtonStr("ridable",detectRidable)));
		this.buttonList.add(new GuiButton(17, 300, 10, 45, 20,typeButtonStr("chest",detectChest)));
		this.buttonList.add(new GuiButton(18, 375, 10, 45, 20,"All §a ✓"));
		this.buttonList.add(new GuiButton(19, 425, 10, 45, 20,"All §c ✘"));
		this.buttonList.add(new GuiButton(20, 150, 10, 45, 20,typeButtonStr("default",defaultOut)));
	}

	private String colorButtonStr(int color,boolean YorN){
		String colorStr="§"+Integer.toHexString(color);
		String detectStr = YorN ?"§a ✓":"§c ✘";
		return colorStr+"■ "+color+" ■ §r"+detectStr;
	}

	private String typeButtonStr(String type,boolean YorN){
		String detectStr = YorN ?"§a ✓":"§c ✘";
		return type+detectStr;
	}

	public void actionPerformed(GuiButton guibutton) {
		if(guibutton.id<=15){
			int color=guibutton.id;
			tile.detect[color] = !tile.detect[color];
		}else if(guibutton.id==16){
			tile.detectRidable = !tile.detectRidable;
		}else if(guibutton.id==17){
			tile.detectChest = !tile.detectChest;
		}else if(guibutton.id==18){
			for(int i=0;i<16;i++)tile.detect[i]=true;
		}else if(guibutton.id==19){
			for(int i=0;i<16;i++)tile.detect[i]=false;
		}else if(guibutton.id==20){
			tile.defaultOut = !tile.defaultOut;
		}

		detect = tile.detect;
		detectChest = tile.detectChest;
		detectRidable = tile.detectRidable;
		defaultOut = tile.defaultOut;

		for(int c=0;c<16;c++){
			this.buttonList.get(c).displayString = colorButtonStr(c,tile.detect[c]);
		}
		this.buttonList.get(16).displayString = typeButtonStr("ridable",detectRidable);
		this.buttonList.get(17).displayString = typeButtonStr("chest",detectChest);
		this.buttonList.get(20).displayString = typeButtonStr("default",defaultOut);

		BlockPos p=tile.getPos();
		PacketHandler.INSTANCE.sendToServer(new MessageMinecartDetector(detect, detectChest, detectRidable, defaultOut, p.getX(), p.getY(), p.getZ()));
	}


	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseZ) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseZ);
	}


	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		// TODO 自動生成されたメソッド・スタブ
		super.drawDefaultBackground();
		//this.mc.renderEngine.bindTexture(TEXTURE);
		//this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, xSize, ySize);
	}


}

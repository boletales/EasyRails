package com.momiza.easyrails.rails;

import com.momiza.easyrails.EasyRails;
import com.momiza.easyrails.PacketHandler;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class GuiContainerConverterRail extends GuiContainer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(EasyRails.MOD_ID,"textures/gui/gui.png");
    int convertTo;
    World world;
    BlockPos pos;
    public GuiContainerConverterRail(Container inventorySlotsIn,World world,BlockPos pos) {
        super(inventorySlotsIn);
        convertTo=EasyRails.ConverterRail.getMetaFromState(world.getBlockState(pos));
        this.world=world;
        this.pos=pos;
    }

    @Override
    public void initGui() {
        super.initGui();
        for(int y=0;y<4;y++){
            for(int x=0;x<4;x++){
                int color = y*4+x;
                this.buttonList.add(new GuiButton(color, 50+x*100, 50+y*50, 40, 20, buttonStr(color)));
            }
        }

        refreshButton();
    }

    private String buttonStr(int color){
        String colorStr="§"+Integer.toHexString(color);
        return colorStr+"■ "+color+" ■ §r";
    }


    private void refreshButton(){
        for(GuiButton b:this.buttonList){
            b.enabled=true;
        }
        this.buttonList.get(convertTo).enabled=false;
    }


    public void actionPerformed(GuiButton guibutton) {
        int color=guibutton.id;
        world.setBlockState(pos,((BlockConverterRail)EasyRails.ConverterRail).getStateFromMeta(color));
        convertTo = color;

        refreshButton();

        PacketHandler.INSTANCE.sendToServer(new MessageConverterRail(pos.getX() , pos.getY(), pos.getZ(),color));
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

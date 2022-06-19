package com.momiza.easyrails.utilblocks;

import com.momiza.easyrails.EasyRails;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public abstract class TESRI2O1 extends TileEntitySpecialRenderer {
    public abstract ResourceLocation getNormalTexture();
    @Override
    public void render(TileEntity te, double x, double y, double z, float partialTick, int destroyStage, float alpha) {
            TileEntityI2O1 ot=(TileEntityI2O1)te;
            /** EnumFacingより:Ordering index for D-U-N-S-W-E */
            double[][][] l={
                            {{1, 0, 0},{1, 0, 1},{0, 0, 1},{0, 0, 0}},
                            {{0, 1, 0},{0, 1, 1},{1, 1, 1},{1, 1, 0}},
                            {{0, 0, 0},{0, 1, 0},{1, 1, 0},{1, 0, 0}},
                            {{1, 0, 1},{1, 1, 1},{0, 1, 1},{0, 0, 1}},
                            {{0, 0, 1},{0, 1, 1},{0, 1, 0},{0, 0, 0}},
                            {{1, 0, 0},{1, 1, 0},{1, 1, 1},{1, 0, 1}},

                            {{0, 0, 0},{0, 0, 1},{1, 0, 1},{1, 0, 0}},
                            {{1, 1, 0},{1, 1, 1},{0, 1, 1},{0, 1, 0}},
                            {{1, 0, 0},{1, 1, 0},{0, 1, 0},{0, 0, 0}},
                            {{0, 0, 1},{0, 1, 1},{1, 1, 1},{1, 0, 1}},
                            {{0, 0, 0},{0, 1, 0},{0, 1, 1},{0, 0, 1}},
                            {{1, 0, 1},{1, 1, 1},{1, 1, 0},{1, 0, 0}}
                        };

            //GL11.glEnable(GL11.GL_ALPHA_TEST);
            GlStateManager.enableLighting();
            for (int i=0;i<6;i++){
                GlStateManager.pushMatrix();
                GlStateManager.translate(x, y, z);
                BufferBuilder wr = Tessellator.getInstance().getBuffer();
                wr.begin(7, DefaultVertexFormats.POSITION_TEX);
                // Base
                // -Z
                wr.normal(0, -1, 0);

                wr.normal(0, 0, 0);
                //up
                     if(ot.getIN1().getIndex()==i)Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(EasyRails.MOD_ID,"textures/blocks/in_connector_1.png"));
                else if(ot.getIN2().getIndex()==i)Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(EasyRails.MOD_ID,"textures/blocks/in_connector_2.png"));
                else if(ot.getOUT().getIndex()==i)Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(EasyRails.MOD_ID,"textures/blocks/out_connector.png"));
                else Minecraft.getMinecraft().getTextureManager().bindTexture(getNormalTexture());
                wr.pos(l[i][0][0],l[i][0][1],l[i][0][2]).tex(1, 1).endVertex();
                wr.pos(l[i][1][0],l[i][1][1],l[i][1][2]).tex(1, 0).endVertex();
                wr.pos(l[i][2][0],l[i][2][1],l[i][2][2]).tex(0, 0).endVertex();
                wr.pos(l[i][3][0],l[i][3][1],l[i][3][2]).tex(0, 1).endVertex();
                Tessellator.getInstance().draw();
                GlStateManager.popMatrix();
            }

            for (int i=6;i<12;i++){
                GlStateManager.pushMatrix();
                GlStateManager.translate(x, y, z);
                BufferBuilder wr = Tessellator.getInstance().getBuffer();
                wr.begin(7, DefaultVertexFormats.POSITION_TEX);
                // Base
                // -Z
                wr.normal(0, -1, 0);

                wr.normal(0, 0, 0);
                //up
                     if(ot.getIN1().getIndex()==i-6)Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(EasyRails.MOD_ID,"textures/blocks/in_connector_1.png"));
                else if(ot.getIN2().getIndex()==i-6)Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(EasyRails.MOD_ID,"textures/blocks/in_connector_2.png"));
                else if(ot.getOUT().getIndex()==i-6)Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(EasyRails.MOD_ID,"textures/blocks/out_connector.png"));
                else Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(EasyRails.MOD_ID,"textures/blocks/no_connector.png"));
                wr.pos(l[i][0][0],l[i][0][1],l[i][0][2]).tex(-1, 1).endVertex();
                wr.pos(l[i][1][0],l[i][1][1],l[i][1][2]).tex(-1, 0).endVertex();
                wr.pos(l[i][2][0],l[i][2][1],l[i][2][2]).tex(0, 0).endVertex();
                wr.pos(l[i][3][0],l[i][3][1],l[i][3][2]).tex(0, 1).endVertex();
                Tessellator.getInstance().draw();
                GlStateManager.popMatrix();
            }
            GlStateManager.disableLighting();
            //GL11.glDisable(GL11.GL_ALPHA_TEST);

    }
}
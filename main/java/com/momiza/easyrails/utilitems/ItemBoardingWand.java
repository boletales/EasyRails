package com.momiza.easyrails.utilitems;

import java.util.List;

import com.momiza.easyrails.minecarts.EntityNewMinecartRidable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
public class ItemBoardingWand extends Item {
    public float efficiencyOnProperMaterial=25;

    public ItemBoardingWand() {
        super();
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn){
        List<Entity> loaded=((World)worldIn).loadedEntityList;
        for(Entity e : loaded){
            if(e instanceof EntityNewMinecartRidable && e.getDistance(playerIn)<3){
                EntityNewMinecartRidable cart = (EntityNewMinecartRidable) e;
                AxisAlignedBB box;
                if (cart.getCollisionHandler() != null) box = cart.getCollisionHandler().getMinecartCollisionBox(cart);
                else                                    box = cart.getEntityBoundingBox().grow(0.20000000298023224D, 0.0D, 0.20000000298023224D);
                if (cart.canBeRidden() )
                    {
                        List<Entity> list = cart.world.getEntitiesInAABBexcluding(cart, box, EntitySelectors.getTeamCollisionPredicate(cart));

                        if (!list.isEmpty())
                        {
                            for (int j1 = 0; j1 < list.size(); ++j1)
                            {
                                Entity entity1 = list.get(j1);

                                if (!(entity1 instanceof EntityPlayer) && !(entity1 instanceof EntityIronGolem) && !(entity1 instanceof EntityMinecart) && !cart.isBeingRidden() && !entity1.isRiding())
                                {
                                    entity1.startRiding(cart);
                                }
                                else
                                {
                                    entity1.applyEntityCollision(cart);
                                }
                            }
                        }
                    }
            }
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }

}
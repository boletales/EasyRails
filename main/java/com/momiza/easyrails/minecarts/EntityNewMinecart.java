package com.momiza.easyrails.minecarts;

import java.util.ArrayList;
import java.util.List;

import com.momiza.easyrails.EasyRails;
import com.momiza.easyrails.rails.BlockConverterRail;
import com.momiza.easyrails.rails.BlockCrossRail;
import com.momiza.easyrails.rails.BlockDepartureRail;
import com.momiza.easyrails.rails.ISlowdownRail;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailBase.EnumRailDirection;
import net.minecraft.block.BlockRailPowered;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import scala.Console;


public abstract class EntityNewMinecart extends EntityMinecart{
	int mukix,mukiz=0;
	public int speedLevel=0;
	boolean swich=false;
	public int color=0;
	protected int curveCount=0;
	Ticket chunkloadTicket;
	List<ChunkPos> loaded= new ArrayList<ChunkPos>();
	ChunkPos lastTickChunk;
	private static final DataParameter<Integer> COLOR = EntityDataManager.<Integer>createKey(EntityNewMinecart.class, DataSerializers.VARINT);
	private static final DataParameter<Float> X = EntityDataManager.<Float>createKey(EntityNewMinecart.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> Y = EntityDataManager.<Float>createKey(EntityNewMinecart.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> Z = EntityDataManager.<Float>createKey(EntityNewMinecart.class, DataSerializers.FLOAT);
	private static final DataParameter<Boolean> UPDATED = EntityDataManager.<Boolean>createKey(EntityNewMinecart.class, DataSerializers.BOOLEAN);
	private static final int[][][] matrix = new int[][][] {{{0, 0, -1}, {0, 0, 1}}, {{ -1, 0, 0}, {1, 0, 0}}, {{ -1, -1, 0}, {1, 0, 0}}, {{ -1, 0, 0}, {1, -1, 0}}, {{0, 0, -1}, {0, -1, 1}}, {{0, -1, -1}, {0, 0, 1}}, {{0, 0, 1}, {1, 0, 0}}, {{0, 0, 1}, { -1, 0, 0}}, {{0, 0, -1}, { -1, 0, 0}}, {{0, 0, -1}, {1, 0, 0}}};


	public EntityNewMinecart(World worldIn,double x,double y,double z,int color)
	{
		super(worldIn,x,y,z);
		this.color=color;
		this.setAlwaysRenderNameTag(true);
		if (!worldIn.isRemote) {
			this.chunkloadTicket=ForgeChunkManager.requestTicket(EasyRails.INSTANCE, worldIn, ForgeChunkManager.Type.ENTITY);
			//this.chunkloadTicket=EasyRails.chunkloadTicket;
			if (this.chunkloadTicket!=null) {
				this.chunkloadTicket.bindEntity(this);
			}
		}
	}

	public EntityNewMinecart(World worldIn)
	{
		super(worldIn);
        this.preventEntitySpawning = true;
		this.setAlwaysRenderNameTag(true);
		if (!worldIn.isRemote) {
			this.chunkloadTicket=ForgeChunkManager.requestTicket(EasyRails.INSTANCE, worldIn, ForgeChunkManager.Type.ENTITY);
			//this.chunkloadTicket=EasyRails.chunkloadTicket;
			if (this.chunkloadTicket!=null) {
				this.chunkloadTicket.bindEntity(this);
			}
		}

	}

    @Override
    public abstract ItemStack getCartItem();
    

    public void killMinecart(DamageSource source)
    {
        this.setDead();

        if (this.world.getGameRules().getBoolean("doEntityDrops"))
        {
            ItemStack itemstack = this.getCartItem();
            this.entityDropItem(itemstack, 0.0F);
        }
    }


	@Override
	public void entityInit(){
		super.entityInit();
		this.dataManager.register(COLOR, this.color);
		this.dataManager.register(X, (float)this.posX);
		this.dataManager.register(Y, (float)this.posY);
		this.dataManager.register(Z, (float)this.posZ);
		this.dataManager.register(UPDATED, false);
	}

	@Override
    public AxisAlignedBB getCollisionBox(Entity entityIn)
    {
        if (getCollisionHandler() != null) return getCollisionHandler().getCollisionBox(this, entityIn);
        if(entityIn==null || !(entityIn instanceof EntityPlayer || (EasyRails.colideEachOther && entityIn instanceof EntityNewMinecart))){
        	return null;
        }
        return !entityIn.isRiding() ? entityIn.getEntityBoundingBox() : null;
    }


    /**
     * Applies a velocity to each of the entities pushing them away from each other. Args: entity
     */
	public void applyEntityCollision(Entity entityIn)
    {
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.minecart.MinecartCollisionEvent(this, entityIn));
        if (getCollisionHandler() != null)
        {
            getCollisionHandler().onEntityCollision(this, entityIn);
            return;
        }
        if (!this.world.isRemote)
        {
            if (!entityIn.noClip && !this.noClip)
            {
                if (!this.isPassenger(entityIn) && entityIn instanceof EntityPlayer)
                {
                    double d0 = entityIn.posX - this.posX;
                    double d1 = entityIn.posZ - this.posZ;
                    double d2 = d0 * d0 + d1 * d1;

                    if (d2 >= 9.999999747378752E-5D)
                    {
                        d2 = (double)MathHelper.sqrt(d2);
                        d0 = d0 / d2;
                        d1 = d1 / d2;
                        double d3 = 1.0D / d2;

                        if (d3 > 1.0D)
                        {
                            d3 = 1.0D;
                        }

                        d0 = d0 * d3;
                        d1 = d1 * d3;
                        d0 = d0 * 0.10000000149011612D;
                        d1 = d1 * 0.10000000149011612D;
                        d0 = d0 * (double)(1.0F - this.entityCollisionReduction);
                        d1 = d1 * (double)(1.0F - this.entityCollisionReduction);
                        d0 = d0 * 0.5D;
                        d1 = d1 * 0.5D;

                        if (entityIn instanceof EntityMinecart)
                        {
                            double d4 = entityIn.posX - this.posX;
                            double d5 = entityIn.posZ - this.posZ;
                            Vec3d vec3d = (new Vec3d(d4, 0.0D, d5)).normalize();
                            Vec3d vec3d1 = (new Vec3d((double)MathHelper.cos(this.rotationYaw * 0.017453292F), 0.0D, (double)MathHelper.sin(this.rotationYaw * 0.017453292F))).normalize();
                            double d6 = Math.abs(vec3d.dotProduct(vec3d1));

                            if (d6 < 0.800000011920929D)
                            {
                                return;
                            }

                            double d7 = entityIn.motionX + this.motionX;
                            double d8 = entityIn.motionZ + this.motionZ;

                            if (((EntityMinecart)entityIn).isPoweredCart() && !isPoweredCart())
                            {
                                this.motionX *= 0.20000000298023224D;
                                this.motionZ *= 0.20000000298023224D;
                                this.addVelocity(entityIn.motionX - d0, 0.0D, entityIn.motionZ - d1);
                                entityIn.motionX *= 0.949999988079071D;
                                entityIn.motionZ *= 0.949999988079071D;
                            }
                            else if (!((EntityMinecart)entityIn).isPoweredCart() && isPoweredCart())
                            {
                                entityIn.motionX *= 0.20000000298023224D;
                                entityIn.motionZ *= 0.20000000298023224D;
                                entityIn.addVelocity(this.motionX + d0, 0.0D, this.motionZ + d1);
                                this.motionX *= 0.949999988079071D;
                                this.motionZ *= 0.949999988079071D;
                            }
                            else
                            {
                                d7 = d7 / 2.0D;
                                d8 = d8 / 2.0D;
                                this.motionX *= 0.20000000298023224D;
                                this.motionZ *= 0.20000000298023224D;
                                this.addVelocity(d7 - d0, 0.0D, d8 - d1);
                                entityIn.motionX *= 0.20000000298023224D;
                                entityIn.motionZ *= 0.20000000298023224D;
                                entityIn.addVelocity(d7 + d0, 0.0D, d8 + d1);
                            }
                        }
                        else
                        {
                            this.addVelocity(-d0, 0.0D, -d1);
                            entityIn.addVelocity(d0 / 4.0D, 0.0D, d1 / 4.0D);
                        }
                    }
                }
            }
        }
    }


	@Override
	public void onUpdate(){
		if(true) {
			this.motionY += -Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ)*(1);
		}
		super.onUpdate();
		handleDepartureRail();

		if(this.world.isRemote){
			this.color=this.getDataManager().get(COLOR);
			this.setCustomNameTag("§"+Integer.toHexString(this.color)+"■■§r type "+this.color+"§"+Integer.toHexString(this.color)+"■■");
			
            int k = MathHelper.floor(this.posX);
            int l = MathHelper.floor(this.posY);
            int i1 = MathHelper.floor(this.posZ);

            if (BlockRailBase.isRailBlock(this.world, new BlockPos(k, l - 1, i1)))
            {
                --l;
            }

            BlockPos blockpos = new BlockPos(k, l, i1);
            IBlockState iblockstate = this.world.getBlockState(blockpos);
            
            boolean curve = false;
            if(BlockRailBase.isRailBlock(iblockstate)) {
            	EnumRailDirection dir = ((BlockRailBase)(iblockstate.getBlock())).getRailDirection(getEntityWorld(), blockpos, iblockstate, this);
            	curve = (dir==EnumRailDirection.NORTH_EAST) || (dir==EnumRailDirection.NORTH_WEST) || (dir==EnumRailDirection.SOUTH_EAST) || (dir==EnumRailDirection.SOUTH_WEST);
            }
        	if(curve) {
        		curveCount=20;
        	}else {
        		curveCount=Math.max(0, curveCount-1);
        	}
        	//double ratio = 1-(double)curveCount/20;
        	double ratio = 1;
        	
        	EntityPlayer closest = this.world.getClosestPlayerToEntity(this, 5);
        	if(this.getDataManager().get(UPDATED) && (closest == null || closest.getDistance(this)>5)) {
    			this.setPosition(this.posX*(1-ratio)+this.getDataManager().get(X)*ratio, this.posY*(1-ratio)+this.getDataManager().get(Y)*ratio, this.posZ*(1-ratio)+this.getDataManager().get(Z)*ratio);
        	}
		}else{
			
			this.getDataManager().set(COLOR, this.color);
			this.getDataManager().set(X, (float)this.posX);
			this.getDataManager().set(Y, (float)this.posY);
			this.getDataManager().set(Z, (float)this.posZ);
			this.getDataManager().set(UPDATED, true);
		}
		
		if(this.lastTickChunk!=new ChunkPos(this.chunkCoordX,this.chunkCoordZ)) {
			this.reloadChunks();
		}
	}

	protected void reloadChunks() {
		if(this.world.isRemote || this.chunkloadTicket==null) return;
		List<ChunkPos> loading= new ArrayList<ChunkPos>();
		for(int x=-1;x<=1;x++){
			for(int z=-1;z<=1;z++){
				loading.add(new ChunkPos(this.chunkCoordX+x,this.chunkCoordZ+z));
			}
		}
		for(ChunkPos chunk : this.loaded){
			if(!loading.contains(chunk)){
				ForgeChunkManager.unforceChunk(this.chunkloadTicket, chunk);
				//Console.println("unforce"+chunk);
			}
		}
		for(ChunkPos chunk : loading){
			if(!this.loaded.contains(chunk)){
				ForgeChunkManager.forceChunk(this.chunkloadTicket, chunk);
				//Console.println("force"+chunk);
			}
		}
		this.loaded=loading;
		this.lastTickChunk=new ChunkPos(this.chunkCoordX,this.chunkCoordZ);
	}

	protected void reloadChunksWithPos() {
		if(this.world.isRemote || this.chunkloadTicket==null) return;
		int ccx = (int)this.posX/16;
		int ccz = (int)this.posZ/16;
		List<ChunkPos> loading= new ArrayList<ChunkPos>();
		for(int x=-1;x<=1;x++){
			for(int z=-1;z<=1;z++){
				loading.add(new ChunkPos(ccx+x,ccz+z));
			}
		}
		for(ChunkPos chunk : this.loaded){
			if(!loading.contains(chunk)){
				ForgeChunkManager.unforceChunk(this.chunkloadTicket, chunk);
				//Console.println("unforce"+chunk);
			}
		}
		for(ChunkPos chunk : loading){
			if(!this.loaded.contains(chunk)){
				ForgeChunkManager.forceChunk(this.chunkloadTicket, chunk);
				//Console.println("force"+chunk);
			}
		}
		this.loaded=loading;
		this.lastTickChunk=new ChunkPos(ccx,ccz);
	}
	
	public void handleDepartureRail(){
        int k = MathHelper.floor(this.posX);
        int l = MathHelper.floor(this.posY);
        int i1 = MathHelper.floor(this.posZ);

        if (BlockRailBase.isRailBlock(this.world, new BlockPos(k, l - 1, i1)))
        {
            --l;
        }

        BlockPos blockpos = new BlockPos(k, l, i1);
        IBlockState iblockstate = this.world.getBlockState(blockpos);

        if (canUseRail() && BlockRailBase.isRailBlock(iblockstate))
        {
            if (iblockstate.getBlock() == EasyRails.DepartureRail)
            {
                EnumRailDirection shape = iblockstate.getValue(BlockDepartureRail.SHAPE);
                boolean plus = iblockstate.getValue(BlockDepartureRail.PLUS);
            	changeMuki(shape ,plus);
                this.onDepartureRailPass(k, l, i1, ((Boolean)iblockstate.getValue(BlockDepartureRail.POWERED)).booleanValue());
            	changeMuki(shape ,plus);

            }
        }
	}
	private void changeMuki(EnumRailDirection shape ,boolean plus){
		if(shape==EnumRailDirection.EAST_WEST && plus){
        	this.mukix=1;
        	this.mukiz=0;
        }else if(shape==EnumRailDirection.EAST_WEST && !plus){
        	this.mukix=-1;
        	this.mukiz=0;
        }else if(shape==EnumRailDirection.NORTH_SOUTH && plus){
        	this.mukix=0;
        	this.mukiz=1;
        }else if(shape==EnumRailDirection.NORTH_SOUTH && !plus){
        	this.mukix=0;
        	this.mukiz=-1;
        }
	}
	public String getShortName(){
		return "§"+Integer.toHexString(this.color)+"■■";
	}

	@Override
	protected void applyDrag() {
		this.motionX *= 1;
		//this.motionY *= 0.0D;
		this.motionZ *= 1;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund) {
		mukix=tagCompund.getInteger("mukix");
		mukiz=tagCompund.getInteger("mukiz");
		swich=tagCompund.getBoolean("swich");
		speedLevel=tagCompund.getInteger("speedLevel");
		color=tagCompund.getInteger("color");
		super.readEntityFromNBT(tagCompund);
		this.reloadChunksWithPos();
	}


	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound) {
		// TODO 自動生成されたメソッド・スタブ
		tagCompound.setInteger("mukix", mukix);
		tagCompound.setInteger("mukiz", mukiz);
		tagCompound.setBoolean("swich", swich);
		tagCompound.setInteger("speedLevel", speedLevel);
		tagCompound.setInteger("color", color);
		super.writeEntityToNBT(tagCompound);
	}


	/*@Override
	protected double getMaximumSpeed() {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println(super.getMaximumSpeed()*this.bairitsu+","+this.bairitsu);
		return super.getMaximumSpeed()*this.bairitsu;
	}*/
	//坂加速の消去
	@SuppressWarnings("incomplete-switch")
	@Override
    public double getSlopeAdjustment()
    {
        return -0.1D;
    }

	@Override
	protected double getMaxSpeed()
    {
        if (!canUseRail()) return getMaximumSpeed();
        BlockPos pos = this.getCurrentRailPosition();
        IBlockState state = this.world.getBlockState(pos);
        if (!BlockRailBase.isRailBlock(state)) return getMaximumSpeed();
        return getMaxSpeedFromLevel(this.speedLevel);

    }
	private double getMaxSpeedFromLevel(int level){
		if(level==2){
			return super.getMaxSpeed()*EasyRails.highspeed;
		}else if(level==1){
			return super.getMaxSpeed()*EasyRails.midspeed;
		}else{
			return super.getMaxSpeed()*EasyRails.lowspeed;
		}
	}
	private BlockPos getCurrentRailPosition()
    {
        int x = MathHelper.floor(this.posX);
        int y = MathHelper.floor(this.posY);
        int z = MathHelper.floor(this.posZ);

        if (BlockRailBase.isRailBlock(this.world, new BlockPos(x, y - 1, z))) y--;
        return new BlockPos(x, y, z);
    }

	public EntityNewMinecart(World worldIn, double p_i1723_2_, double p_i1723_4_, double p_i1723_6_)
	{
		super(worldIn, p_i1723_2_, p_i1723_4_, p_i1723_6_);
	}


	/**
	 * Called every tick the minecart is on an activator rail. Args: x, y, z, is the rail receiving power
	 */
	@Override
	public void onActivatorRailPass(int x, int y, int z, boolean receivingPower)
	{
		if(Math.abs(this.motionX)>=0.1){
			mukix=(int)Math.signum(this.motionX);
		}
		if(Math.abs(this.motionZ)>=0.1){
			mukiz=(int)Math.signum(this.motionZ);
		}
		onDepartureRailPass(x, y, z, receivingPower);
	}

	public void onDepartureRailPass(int x, int y, int z, boolean receivingPower)
	{
		if (receivingPower)
		{
			swich=true;
			if(Math.abs(this.motionX*this.motionX + this.motionZ*this.motionZ)<=0.25){
				this.motionX+=mukix*2;
				this.motionZ+=mukiz*2;
			}
		}else{
			if(Math.abs(this.motionX)>=0.1){
				mukix=(int)Math.signum(this.motionX);
			}
			if(Math.abs(this.motionZ)>=0.1){
				mukiz=(int)Math.signum(this.motionZ);
			}
			this.motionX*=0.1;
			this.motionZ*=0.1;
			swich=false;
		}
	}

    @SuppressWarnings("incomplete-switch")
    protected void moveAlongTrack(BlockPos pos, IBlockState state)
    {
        this.fallDistance = 0.0F;
        Vec3d vec3d = this.getPos(this.posX, this.posY, this.posZ);
        this.posY = (double)pos.getY();
        boolean flag = false;
        boolean flag1 = false;
        BlockRailBase blockrailbase = (BlockRailBase)state.getBlock();

        if (blockrailbase == Blocks.GOLDEN_RAIL)
        {
            flag = ((Boolean)state.getValue(BlockRailPowered.POWERED)).booleanValue();
            flag1 = !flag;
        }

        double slopeAdjustment = getSlopeAdjustment();
        BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = blockrailbase.getRailDirection(world, pos, state, this);

        switch (blockrailbase$enumraildirection)
        {
            case ASCENDING_EAST:
                this.motionX -= slopeAdjustment;
                ++this.posY;
                break;
            case ASCENDING_WEST:
                this.motionX += slopeAdjustment;
                ++this.posY;
                break;
            case ASCENDING_NORTH:
                this.motionZ += slopeAdjustment;
                ++this.posY;
                break;
            case ASCENDING_SOUTH:
                this.motionZ -= slopeAdjustment;
                ++this.posY;
        }

        final int[][][] MATRIX = new int[][][] {{{0, 0, -1}, {0, 0, 1}}, {{ -1, 0, 0}, {1, 0, 0}}, {{ -1, -1, 0}, {1, 0, 0}}, {{ -1, 0, 0}, {1, -1, 0}}, {{0, 0, -1}, {0, -1, 1}}, {{0, -1, -1}, {0, 0, 1}}, {{0, 0, 1}, {1, 0, 0}}, {{0, 0, 1}, { -1, 0, 0}}, {{0, 0, -1}, { -1, 0, 0}}, {{0, 0, -1}, {1, 0, 0}}};
        int[][] aint = MATRIX[blockrailbase$enumraildirection.getMetadata()];
        double d1 = (double)(aint[1][0] - aint[0][0]);
        double d2 = (double)(aint[1][2] - aint[0][2]);
        double d3 = Math.sqrt(d1 * d1 + d2 * d2);
        double d4 = this.motionX * d1 + this.motionZ * d2;

        if (d4 < 0.0D)
        {
            d1 = -d1;
            d2 = -d2;
        }

        double d5 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

        if (d5 > this.getMaxSpeed())
        {
            d5 = this.getMaxSpeed();
        }

        this.motionX = d5 * d1 / d3;
        this.motionZ = d5 * d2 / d3;
        Entity entity = this.getPassengers().isEmpty() ? null : (Entity)this.getPassengers().get(0);

        if (entity instanceof EntityLivingBase)
        {
            double d6 = (double)((EntityLivingBase)entity).moveForward;

            if (d6 > 0.0D)
            {
                double d7 = -Math.sin((double)(entity.rotationYaw * 0.017453292F));
                double d8 = Math.cos((double)(entity.rotationYaw * 0.017453292F));
                double d9 = this.motionX * this.motionX + this.motionZ * this.motionZ;

                if (d9 < 0.01D)
                {
                    this.motionX += d7 * 0.1D;
                    this.motionZ += d8 * 0.1D;
                    flag1 = false;
                }
            }
        }

        if (flag1 && shouldDoRailFunctions())
        {
            double d17 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

            if (d17 < 0.03D)
            {
                this.motionX *= 0.0D;
                this.motionY *= 0.0D;
                this.motionZ *= 0.0D;
            }
            else
            {
                this.motionX *= 0.5D;
                this.motionY *= 0.0D;
                this.motionZ *= 0.5D;
            }
        }

        double d18 = (double)pos.getX() + 0.5D + (double)aint[0][0] * 0.5D;
        double d19 = (double)pos.getZ() + 0.5D + (double)aint[0][2] * 0.5D;
        double d20 = (double)pos.getX() + 0.5D + (double)aint[1][0] * 0.5D;
        double d21 = (double)pos.getZ() + 0.5D + (double)aint[1][2] * 0.5D;
        d1 = d20 - d18;
        d2 = d21 - d19;
        double d10;

        if (d1 == 0.0D)
        {
            this.posX = (double)pos.getX() + 0.5D;
            d10 = this.posZ - (double)pos.getZ();
        }
        else if (d2 == 0.0D)
        {
            this.posZ = (double)pos.getZ() + 0.5D;
            d10 = this.posX - (double)pos.getX();
        }
        else
        {
            double d11 = this.posX - d18;
            double d12 = this.posZ - d19;
            d10 = (d11 * d1 + d12 * d2) * 2.0D;
        }

        this.posX = d18 + d1 * d10;
        this.posZ = d19 + d2 * d10;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.moveMinecartOnRail(pos);

        if (aint[0][1] != 0 && MathHelper.floor(this.posX) - pos.getX() == aint[0][0] && MathHelper.floor(this.posZ) - pos.getZ() == aint[0][2])
        {
            this.setPosition(this.posX, this.posY + (double)aint[0][1], this.posZ);
        }
        else if (aint[1][1] != 0 && MathHelper.floor(this.posX) - pos.getX() == aint[1][0] && MathHelper.floor(this.posZ) - pos.getZ() == aint[1][2])
        {
            this.setPosition(this.posX, this.posY + (double)aint[1][1], this.posZ);
        }

        this.applyDrag();
        Vec3d vec3d1 = this.getPos(this.posX, this.posY, this.posZ);

        if (vec3d1 != null && vec3d != null)
        {
            double d14 = (vec3d.y - vec3d1.y) * 0.05D;
            d5 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

            if (d5 > 0.0D)
            {
                this.motionX = this.motionX / d5 * (d5 + d14);
                this.motionZ = this.motionZ / d5 * (d5 + d14);
            }

            this.setPosition(this.posX, vec3d1.y, this.posZ);
        }

        int j = MathHelper.floor(this.posX);
        int i = MathHelper.floor(this.posZ);

        if (j != pos.getX() || i != pos.getZ())
        {
            d5 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.motionX = d5 * (double)(j - pos.getX());
            this.motionZ = d5 * (double)(i - pos.getZ());
        }


        if(shouldDoRailFunctions())
        {
            ((BlockRailBase)state.getBlock()).onMinecartPass(world, this, pos);
        }

        if (flag && shouldDoRailFunctions())
        {
            double d15 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

            if (d15 > 0.01D)
            {
                double d16 = 0.06D;
                this.motionX += this.motionX / d15 * 0.06D;
                this.motionZ += this.motionZ / d15 * 0.06D;
            }
            else if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.EAST_WEST)
            {
                if (this.world.getBlockState(pos.west()).isNormalCube())
                {
                    this.motionX = 0.02D;
                }
                else if (this.world.getBlockState(pos.east()).isNormalCube())
                {
                    this.motionX = -0.02D;
                }
            }
            else if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.NORTH_SOUTH)
            {
                if (this.world.getBlockState(pos.north()).isNormalCube())
                {
                    this.motionZ = 0.02D;
                }
                else if (this.world.getBlockState(pos.south()).isNormalCube())
                {
                    this.motionZ = -0.02D;
                }
            }
        }
    }
    
	public void moveMinecartOnRail(BlockPos pos)
    {
		//Console.println(this.getHorizontalFacing());

        //double max = canGoFast(pos, 2, pos.offset(this.getHorizontalFacing().getOpposite()))?this.getMaxSpeed():Math.min(this.getMaxSpeed(),super.getMaxSpeed());
		double maxHigh = Math.max(this.getMaxSpeed(),super.getMaxSpeed());
		double maxLow  = Math.min(this.getMaxSpeed(),super.getMaxSpeed());
		int seek = 12;
		int mergin =2;
		EnumFacing facing = EnumFacing.getFacingFromVector((float)this.motionX, 0f, (float)this.motionZ);
		/*
		Console.print(this.motionX);
		Console.print(",");
		Console.print(this.motionZ);
		Console.print("\n");*/
        int distance = Math.min(getDistanceToNextSlowDown(pos, seek, pos.offset(this.getAdjustedHorizontalFacing().getOpposite())),
        						getDistanceToNextSlowDown(pos, seek, pos.offset(this.getAdjustedHorizontalFacing())));
        
        //Console.println(distance);
        //Console.println(facing);
        double max = distance>mergin ? maxLow + (maxHigh-maxLow)*((double)(distance-mergin)/(double)(seek-mergin)) : maxLow;
		if(swich){
	        //if(EasyRails.debug)Console.println(this.motionX*this.motionX+this.motionZ*this.motionZ+","+swich+","+this.mukix*this.getMaxSpeed()+","+this.mukiz*this.getMaxSpeed());
	        double d15 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

            if (d15 > 0)
            {
            	double d16=1D;
            	if(d15<this.getMaxSpeedFromLevel(0)){
                    d16 = 3D;
            	}else{
                  d16 = 0.2D;
            	}
                if(EasyRails.debug)Console.println("before:"+this.motionZ);
                this.motionX += this.motionX / d15 * d16;
                this.motionZ += this.motionZ / d15 * d16;
                double _max = max;
                //double _max = this.getMaxSpeed();
                this.motionX = MathHelper.clamp(this.motionX, -_max, _max);
                this.motionZ = MathHelper.clamp(this.motionZ, -_max, _max);
                if(EasyRails.debug)Console.println("after :"+this.motionZ);
            }
		}else{

			this.motionX*=0.1;
			this.motionZ*=0.1;
		}
		
        
        double mX = this.motionX;
        double mZ = this.motionZ;
        if(EasyRails.debug)Console.println(max+","+Math.sqrt(mX*mX+mZ*mZ)+","+swich);
        mX = MathHelper.clamp(mX, -max, max);
        mZ = MathHelper.clamp(mZ, -max, max);
        this.move(MoverType.SELF,mX, 0.0D, mZ);
    }
	
	private int getAxis(EnumRailDirection direction) {
		if(direction == EnumRailDirection.NORTH_SOUTH || direction == EnumRailDirection.ASCENDING_NORTH || direction == EnumRailDirection.ASCENDING_SOUTH) {
			return 2; // ZAxis
		}else if(direction == EnumRailDirection.EAST_WEST || direction == EnumRailDirection.ASCENDING_EAST || direction == EnumRailDirection.ASCENDING_WEST) {
			return 1; // XAxis
		}
		return 0; // noAxis
	}
	
	private int getDistanceToNextSlowDown(BlockPos pos, int count, BlockPos from) {
        boolean canGoFast = true;
        IBlockState state = this.world.getBlockState(pos);
        Block block = state.getBlock();
        if(!(block instanceof BlockRailBase)) {
        	pos = pos.offset(EnumFacing.DOWN);
            state = this.world.getBlockState(pos);
            block = state.getBlock();
        }
        if((block instanceof BlockRailBase)) {
        	EnumRailDirection direction = ((BlockRailBase)block).getRailDirection(this.world, pos, state , this);
        	canGoFast &=  (direction != EnumRailDirection.NORTH_EAST) && (direction != EnumRailDirection.SOUTH_EAST) && (direction != EnumRailDirection.NORTH_WEST) && (direction != EnumRailDirection.SOUTH_WEST)
        					&& ((pos.getX() == from.getX() && getAxis(direction) == 2 ) || (pos.getZ() == from.getZ() && getAxis(direction) == 1 ) || block instanceof BlockCrossRail || block instanceof BlockConverterRail)
        					&& !( block == Blocks.ACTIVATOR_RAIL && !state.getValue(BlockRailPowered.POWERED)) && !( block instanceof ISlowdownRail && ((ISlowdownRail)block).shouldSlowdown(state, this.speedLevel));
            
        	if(canGoFast && count>0) {
            	BlockPos[] next = {pos,pos};
            	switch(direction) {
            	case NORTH_SOUTH:
            		next[0] = pos.north(); next[1] = pos.south();
            		break;
            	case EAST_WEST:
            		next[0] = pos.east(); next[1] = pos.west();
            		break;
            	case ASCENDING_NORTH:
            		next[0] = pos.north().up(); next[1] = pos.south();
            		break;
            	case ASCENDING_SOUTH:
            		next[0] = pos.north(); next[1] = pos.south().up();
            		break;
            	case ASCENDING_EAST:
            		next[0] = pos.east().up(); next[1] = pos.west();
            		break;
            	case ASCENDING_WEST:
            		next[0] = pos.east(); next[1] = pos.west().up();
            		break;
            	default:
            	}

            	
            	if(!next[0].equals(from) && !next[0].equals(from.up())) {
            		return getDistanceToNextSlowDown(next[0], count-1, pos)+1;
            	}else if(!next[1].equals(from) && !next[1].equals(from.up())) {
            		return getDistanceToNextSlowDown(next[1], count-1, pos)+1;
            	}
            }else {
            	int a=1;
            }
        }
        
        return 0;
	}
	
    public float getMaxCartSpeedOnRail()
    {
        return (float)this.getMaxSpeed();
    }

}
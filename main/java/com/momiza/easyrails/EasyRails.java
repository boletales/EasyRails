package com.momiza.easyrails;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.momiza.easyrails.minecarts.EntityNewMinecartChest;
import com.momiza.easyrails.minecarts.EntityNewMinecartRidable;
import com.momiza.easyrails.minecarts.ItemNewMinecart;
import com.momiza.easyrails.minecarts.ItemNewMinecartBase;
import com.momiza.easyrails.minecarts.ItemNewMinecartChest;
import com.momiza.easyrails.minecarts.RenderNewMinecart;
import com.momiza.easyrails.otherblocks.BlockBuilding;
import com.momiza.easyrails.otherblocks.BlockBuildingLight;
import com.momiza.easyrails.otherblocks.BlockCharcoal;
import com.momiza.easyrails.otherblocks.BlockDarkGlass;
import com.momiza.easyrails.otherblocks.BlockHyperGlass;
import com.momiza.easyrails.otherblocks.BlockMachineBase;
import com.momiza.easyrails.otherblocks.BlockMagicStone;
import com.momiza.easyrails.otherblocks.BlockSolidGlass;
import com.momiza.easyrails.otherblocks.BlockXrayGlass;
import com.momiza.easyrails.otherblocks.ItemBlockCharcoal;
import com.momiza.easyrails.rails.BlockConverterRail;
import com.momiza.easyrails.rails.BlockCrossRail;
import com.momiza.easyrails.rails.BlockDepartureRail;
import com.momiza.easyrails.rails.BlockHSRail;
import com.momiza.easyrails.rails.BlockLilyRail;
import com.momiza.easyrails.rails.BlockMSRail;
import com.momiza.easyrails.rails.BlockNewDetector;
import com.momiza.easyrails.rails.BlockSlowdownRail;
import com.momiza.easyrails.rails.BlockSwitchRailL;
import com.momiza.easyrails.rails.BlockSwitchRailR;
import com.momiza.easyrails.rails.ItemBlockLilyRail;
import com.momiza.easyrails.utilblocks.BlockAnd;
import com.momiza.easyrails.utilblocks.BlockAutoLumber;
import com.momiza.easyrails.utilblocks.BlockHopperPlus;
import com.momiza.easyrails.utilblocks.BlockLoader;
import com.momiza.easyrails.utilblocks.BlockLogic;
import com.momiza.easyrails.utilblocks.BlockLongRepeater;
import com.momiza.easyrails.utilblocks.BlockMinecartConverter;
import com.momiza.easyrails.utilblocks.BlockMinecartDetector;
import com.momiza.easyrails.utilblocks.BlockMinecartDetectorInsulated;
import com.momiza.easyrails.utilblocks.BlockMinecartKiller;
import com.momiza.easyrails.utilblocks.BlockOr;
import com.momiza.easyrails.utilblocks.BlockRedstoneExtender;
import com.momiza.easyrails.utilblocks.BlockRedstoneStoper;
import com.momiza.easyrails.utilblocks.BlockUnloader;
import com.momiza.easyrails.utilblocks.TESRAnd;
import com.momiza.easyrails.utilblocks.TESRLogic;
import com.momiza.easyrails.utilblocks.TESROr;
import com.momiza.easyrails.utilblocks.TileEntityAnd;
import com.momiza.easyrails.utilblocks.TileEntityAutoLumber;
import com.momiza.easyrails.utilblocks.TileEntityHopperPlus;
import com.momiza.easyrails.utilblocks.TileEntityLoader;
import com.momiza.easyrails.utilblocks.TileEntityLogic;
import com.momiza.easyrails.utilblocks.TileEntityMinecartConverter;
import com.momiza.easyrails.utilblocks.TileEntityMinecartDetector;
import com.momiza.easyrails.utilblocks.TileEntityMinecartKiller;
import com.momiza.easyrails.utilblocks.TileEntityOr;
import com.momiza.easyrails.utilblocks.TileEntityUnloader;
import com.momiza.easyrails.utilitems.CommandRailWand;
import com.momiza.easyrails.utilitems.ItemBoardingWand;
import com.momiza.easyrails.utilitems.ItemIOController;
import com.momiza.easyrails.utilitems.ItemInController;
import com.momiza.easyrails.utilitems.ItemMStoneAxe;
import com.momiza.easyrails.utilitems.ItemMStoneHoe;
import com.momiza.easyrails.utilitems.ItemMStonePickaxe;
import com.momiza.easyrails.utilitems.ItemMStonePickaxePlus;
import com.momiza.easyrails.utilitems.ItemMStoneShovel;
import com.momiza.easyrails.utilitems.ItemMStoneSword;
import com.momiza.easyrails.utilitems.ItemOutController;
import com.momiza.easyrails.utilitems.ItemRailWand;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

@Mod(modid = EasyRails.MOD_ID,
		name = EasyRails.MOD_NAME,
		version = EasyRails.MOD_VERSION,
		dependencies = EasyRails.MOD_DEPENDENCIES,
		acceptedMinecraftVersions = EasyRails.MOD_ACCEPTED_MC_VERSIONS,
		useMetadata = true)
public class EasyRails {

	public static final boolean debug=false;
    public static final int GUI_ID_CHANNEL = 0;
    public static final int GUI_ID_DETECTOR = 1;
    public static final int GUI_ID_I2O1 = 2;
    public static final int GUI_ID_LOGIC = 3;
    public static final int GUI_ID_MINECARTCONVERTER = 4;
    public static final int GUI_ID_CONVERTERRAIL = 5;



    @Mod.Instance("easyrails")
    public static EasyRails INSTANCE;
	public static final String MOD_ID = "easyrails";
	public static final String MOD_NAME = "EasyRails";
	public static final String MOD_VERSION = "3.0.0";
	public static final String MOD_DEPENDENCIES = "";
	public static final String MOD_ACCEPTED_MC_VERSIONS = "[1.12.2]";
	public static Block AndBlock;
	public static Block OrBlock;
	public static Block LogicBlock;
	public static Block NewDetector;
	public static Block DepartureRail;
	public static Block SlowdownRail;
	public static Block HSRail;
	public static Block MSRail;
	public static Block SwitchRailR;
	public static Block SwitchRailL;
	public static Block CrossRail;
	public static Block ConverterRail;
    public static Block LilyRail;
	public static Block CharcoalBlock;
	public static Block BuildingBlock;
	public static Block BuildingLight;
	public static Block MinecartDetector;
	public static Block MinecartDetectorInsulated;
    public static Block HopperPlus;
    public static Block Unloader;
    public static Block MinecartConverter;
    public static Block Loader;
    public static Block MinecartKiller;
    public static Block DarkGlass;
    public static Block HyperGlass;
	public static Block SolidGlass;
	public static Block XrayGlass;
    public static Block AutoLumber;
    public static Block PoweredLongRepeater;
    public static Block UnpoweredLongRepeater;
    public static Block PoweredStoper;
    public static Block UnpoweredStoper;
    public static Block PoweredExtender;
    public static Block UnpoweredExtender;
	public static Block MachineBase;
	public static Block MagicStoneBlock;
    public static Item InController;
	public static Item OutController;
	public static Item IOController;
	public static Item TNT2;
	public static Item ItemNewMinecart;
	public static Item ItemNewMinecartChest;
	public static Item Tntmine2;
	public static Item RailWand;
	public static Item MagicStone;
	public static Item Redstone3;
	public static Item MStonePickaxe;
	public static Item MStonePickaxePlus;
	public static Item MStoneShovel;
	public static Item MStoneAxe;
	public static Item MStoneSword;
	public static Item MStoneHoe;
	public static Item UnrefinedQuartz;
	public static Item BoardingWand;
	
	public static float highspeed=1.0f;
	public static float midspeed=1.0f;
	public static float lowspeed=1.0f;
	public static int longrepeaterMult=10;
	public static boolean allowYutoriRecipe=true;
	public static boolean colideEachOther=false;
	
	
	public static String lumberingBlocksStr,plantingBlocksStr;
	public static ArrayList<Block> lumberingBlocks = new ArrayList<Block>();
	public static ArrayList<Block> plantingBlocks = new ArrayList<Block>();
	
	public static ToolMaterial MSTONE;
	public static ToolMaterial MSTONEPLUS;

	public static int rw_length=6;
	public static int rw_dy=0;
	public static boolean rw_isMultiTrack=false;
	public static boolean rw_isTunnel=false;
	public static boolean rw_enabled=false;
	public static IBlockState rw_block=Blocks.STONE.getDefaultState();
	public static IBlockState rw_tblock=Blocks.GLASS.getDefaultState();
	public static IBlockState rw_lblock=Blocks.SEA_LANTERN.getDefaultState();

	public static Ticket chunkloadTicket;


    public static final KeyBinding keyPipeOpposite = new KeyBinding("Invert Sides", Keyboard.KEY_O, "");
    public boolean pipeOpposite=false;
    
    private static ArrayList<Block> toBlocks(String str) {
    	ArrayList<Block> list = new ArrayList();
        String[] splitted = str.split(",");
        for(int i = 0; i < splitted.length; i++) {
        	String id = splitted[i].trim();
            if (!id.isEmpty()) {
                Block block = Block.getBlockFromName(id);
                if (block != null) {
                    list.add(block);
                }
            }
        }
        return list;
    }
    
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		PacketHandler.init();

		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try
		{
			cfg.load();
			highspeed = (float)cfg.get("minecart", "highspeed", 6.0f).getDouble();
			midspeed  = (float)cfg.get("minecart", "midspeed", 3.0f).getDouble();
			lowspeed  = (float)cfg.get("minecart", "lowspeed", 1.0f).getDouble();
			colideEachOther  = (boolean)cfg.get("minecart", "colideeachother", false).getBoolean();
			allowYutoriRecipe  = (boolean)cfg.get("recipe", "arrowyutori", true).getBoolean();
			lumberingBlocksStr = (String) cfg.get("autolumber", "cutting" , "minecraft:log, minecraft:log2, minecraft:leaves, minecraft:leaves2").getString();
			plantingBlocksStr  = (String) cfg.get("autolumber", "planting", "minecraft:sapling").getString();
			longrepeaterMult   = (int) cfg.get("longrepeater", "multiplier", "10").getInt();
		}
		catch (Exception e)
		{
		}
		finally
		{
			cfg.save();
		}

		MSTONE = EnumHelper.addToolMaterial("MSTONE", 2, 5000, 15.0F, 8.0F, 10).setRepairItem(new ItemStack(EasyRails.MagicStone));
		MSTONEPLUS = EnumHelper.addToolMaterial("MSTONEPLUS", 3, 40000, 80.0F, 10.0F, 10).setRepairItem(new ItemStack(EasyRails.MagicStone));
		
		LogicBlock = new BlockLogic()					.setRegistryName("logic_block");
		AndBlock = new BlockAnd()						.setRegistryName("and_block");
		OrBlock = new BlockOr()							.setRegistryName("or_block");
		NewDetector = new BlockNewDetector()			.setRegistryName("new_detector");
		DepartureRail = new BlockDepartureRail()		.setRegistryName("departure_rail");
		SlowdownRail = new BlockSlowdownRail()			.setRegistryName("slowdown_rail");
		HSRail = new BlockHSRail()						.setRegistryName("hs_rail");
		MSRail = new BlockMSRail()						.setRegistryName("ms_rail");
		SwitchRailR = new BlockSwitchRailR()			.setRegistryName("switch_rail_r");
		SwitchRailL = new BlockSwitchRailL()			.setRegistryName("switch_rail_l");
		CrossRail = new BlockCrossRail()				.setRegistryName("cross_rail");
		ConverterRail = new BlockConverterRail()		.setRegistryName("converter_rail");
		LilyRail = new BlockLilyRail()					.setRegistryName("lily_rail");
		CharcoalBlock = new BlockCharcoal()				.setRegistryName("charcoal_block");
		BuildingBlock = new BlockBuilding()				.setRegistryName("building_block");
		BuildingLight = new BlockBuildingLight()		.setRegistryName("building_light");
		MinecartDetector = new BlockMinecartDetector()	.setRegistryName("minecart_detector");
		MinecartDetectorInsulated = new BlockMinecartDetectorInsulated()	.setRegistryName("minecart_detector_insulated");
        HopperPlus = new BlockHopperPlus()				.setRegistryName("hopper_plus")			.setUnlocalizedName("HopperPlus");
        Unloader = new BlockUnloader()					.setRegistryName("unloader")			.setUnlocalizedName("Unloader");
        MinecartConverter = new BlockMinecartConverter().setRegistryName("minecart_converter")	.setUnlocalizedName("MinecartConverter");
        Loader = new BlockLoader()						.setRegistryName("loader")				.setUnlocalizedName("Loader");
        MinecartKiller = new BlockMinecartKiller()		.setRegistryName("minecart_killer")		.setUnlocalizedName("MinecartKiller");
        AutoLumber = new BlockAutoLumber()				.setRegistryName("auto_lumber")			.setUnlocalizedName("AutoLumber");
        UnpoweredLongRepeater	= new BlockLongRepeater(false)		.setRegistryName("unpowered_long_repeater")	.setUnlocalizedName("LongRepeater");
        PoweredLongRepeater		= new BlockLongRepeater(true)		.setRegistryName("powered_long_repeater")	.setUnlocalizedName("LongRepeater");
        UnpoweredStoper			= new BlockRedstoneStoper(false)	.setRegistryName("unpowered_redstone_stoper")	.setUnlocalizedName("RedstoneStoper");
        PoweredStoper			= new BlockRedstoneStoper(true)		.setRegistryName("powered_redstone_stoper")		.setUnlocalizedName("RedstoneStoper");
        UnpoweredExtender		= new BlockRedstoneExtender(false)	.setRegistryName("unpowered_redstone_extender")	.setUnlocalizedName("RedstoneExtender");
        PoweredExtender			= new BlockRedstoneExtender(true)	.setRegistryName("powered_redstone_extender")	.setUnlocalizedName("RedstoneExtender");
        MachineBase				= new BlockMachineBase().setRegistryName("machine_base")	.setUnlocalizedName("MachineBase");
        MagicStoneBlock			= new BlockMagicStone().setRegistryName("magic_stone_block").setUnlocalizedName("MagicStoneBlock");
        
        DarkGlass  = new BlockDarkGlass()		.setRegistryName("dark_glass")		.setUnlocalizedName("DarkGlass");
        HyperGlass = new BlockHyperGlass()		.setRegistryName("hyper_glass")		.setUnlocalizedName("HyperGlass");
        SolidGlass = new BlockSolidGlass()		.setRegistryName("solid_glass")		.setUnlocalizedName("SolidGlass");
        XrayGlass  = new BlockXrayGlass()		.setRegistryName("xray_glass")		.setUnlocalizedName("XrayGlass");
        
		InController = new ItemInController().setUnlocalizedName("InController");
		OutController = new ItemOutController().setUnlocalizedName("OutController");
		IOController 		= new ItemIOController().setUnlocalizedName("IOController");
		MStonePickaxe 		= new ItemMStonePickaxe(MSTONE)			.setUnlocalizedName("MStonePickaxe")	;
		MStonePickaxePlus 	= new ItemMStonePickaxePlus(MSTONEPLUS)	.setUnlocalizedName("MStonePickaxePlus");
		MStoneShovel 		= new ItemMStoneShovel(MSTONE)			.setUnlocalizedName("MStoneShovel")		;
		MStoneAxe 			= new ItemMStoneAxe(MSTONE)				.setUnlocalizedName("MStoneAxe")		;
		MStoneSword 		= new ItemMStoneSword(MSTONE)			.setUnlocalizedName("MStoneSword")		;
		MStoneHoe 			= new ItemMStoneHoe(MSTONE)				.setUnlocalizedName("MStoneHoe")		;
		Tntmine2=(new ItemMinecart(EntityMinecart.Type.TNT)).setUnlocalizedName("minecartTnt2").setMaxStackSize(64).setCreativeTab(CreativeTabs.TRANSPORTATION);
		ItemNewMinecart=(new ItemNewMinecart())			 .setUnlocalizedName("newminecart")		.setMaxStackSize(64).setCreativeTab(CreativeTabs.TRANSPORTATION);
		ItemNewMinecartChest=(new ItemNewMinecartChest()).setUnlocalizedName("newminecartchest").setMaxStackSize(64).setCreativeTab(CreativeTabs.TRANSPORTATION);
		RailWand		= new ItemRailWand().setCreativeTab(CreativeTabs.TRANSPORTATION).setUnlocalizedName("RailWand");
        MagicStone      = new Item().setCreativeTab(CreativeTabs.MATERIALS).setUnlocalizedName("MagicStone");
        Redstone3       = new Item().setCreativeTab(CreativeTabs.MATERIALS).setUnlocalizedName("Redstone3");
        UnrefinedQuartz = new Item().setCreativeTab(CreativeTabs.MATERIALS).setUnlocalizedName("UnrefinedQuartz");
        BoardingWand    = new ItemBoardingWand().setCreativeTab(CreativeTabs.TRANSPORTATION).setUnlocalizedName("BoardingWand");

        ForgeRegistries.BLOCKS.registerAll(
        		AndBlock,
        		OrBlock,
        		LogicBlock,
        		NewDetector,
        		DepartureRail,
        		SlowdownRail,
        		HSRail,
        		MSRail,
        		SwitchRailR,
        		SwitchRailL,
        		CrossRail,
        		ConverterRail,
        		LilyRail,
        		CharcoalBlock,
        		BuildingBlock,
        		BuildingLight,
        		MinecartDetector,
        		MinecartDetectorInsulated,
        		HopperPlus,
        		Unloader,
        		MinecartConverter,
        		Loader,
        		MinecartKiller,
        		DarkGlass,
        		HyperGlass,
        		SolidGlass,
        		XrayGlass,
        		AutoLumber,
        		UnpoweredLongRepeater,
        		PoweredLongRepeater,
        		UnpoweredStoper,
        		PoweredStoper,
        		UnpoweredExtender,
        		PoweredExtender,
        		MachineBase,
        		MagicStoneBlock
        );
        
        ForgeRegistries.ITEMS.registerAll(
        		new ItemBlock(AndBlock)         .setRegistryName(MOD_ID, "and_block"),
        		new ItemBlock(OrBlock)          .setRegistryName(MOD_ID, "or_block"),
        		new ItemBlock(LogicBlock)       .setRegistryName(MOD_ID, "logic_block"),
        		new ItemBlock(NewDetector)      .setRegistryName(MOD_ID, "new_detector"),
        		new ItemBlock(DepartureRail)    .setRegistryName(MOD_ID, "departure_rail"),
        		new ItemBlock(SlowdownRail)     .setRegistryName(MOD_ID, "slowdown_rail"),
        		new ItemBlock(HSRail)           .setRegistryName(MOD_ID, "hs_rail"),
        		new ItemBlock(MSRail)           .setRegistryName(MOD_ID, "ms_rail"),
        		new ItemBlock(SwitchRailR)      .setRegistryName(MOD_ID, "switch_rail_r"),
        		new ItemBlock(SwitchRailL)      .setRegistryName(MOD_ID, "switch_rail_l"),
        		new ItemBlock(CrossRail)        .setRegistryName(MOD_ID, "cross_rail"),
        		new ItemBlock(ConverterRail)    .setRegistryName(MOD_ID, "converter_rail"),
        		new ItemBlockLilyRail(LilyRail) .setRegistryName(MOD_ID, "lily_rail"),
        		new ItemBlockCharcoal(CharcoalBlock)    .setRegistryName(MOD_ID, "charcoal_block"),
        		new ItemBlock(BuildingBlock)    .setRegistryName(MOD_ID, "building_block"),
        		new ItemBlock(BuildingLight)    .setRegistryName(MOD_ID, "building_light"),
        		new ItemBlock(MinecartDetector) .setRegistryName(MOD_ID, "minecart_detector"),
        		new ItemBlock(MinecartDetectorInsulated) .setRegistryName(MOD_ID, "minecart_detector_insulated"),
        		new ItemBlock(HopperPlus)       .setRegistryName(MOD_ID, "hopper_plus"),
        		new ItemBlock(Unloader)         .setRegistryName(MOD_ID, "unloader"),
        		new ItemBlock(MinecartConverter).setRegistryName(MOD_ID, "minecart_converter"),
        		new ItemBlock(Loader)           .setRegistryName(MOD_ID, "loader"),
        		new ItemBlock(MinecartKiller)   .setRegistryName(MOD_ID, "minecart_killer"),
        		new ItemBlock(AutoLumber)       .setRegistryName(MOD_ID, "auto_lumber"),
        		new ItemBlock(UnpoweredLongRepeater).setRegistryName(MOD_ID, "long_repeater"),
        		new ItemBlock(UnpoweredStoper)  .setRegistryName(MOD_ID, "redstone_stoper"),
        		new ItemBlock(UnpoweredExtender).setRegistryName(MOD_ID, "redstone_extender"),
        		new ItemBlock(DarkGlass)        .setRegistryName(MOD_ID, "dark_glass"),
        		new ItemBlock(HyperGlass)       .setRegistryName(MOD_ID, "hyper_glass"),
        		new ItemBlock(SolidGlass)       .setRegistryName(MOD_ID, "solid_glass"),
        		new ItemBlock(XrayGlass)        .setRegistryName(MOD_ID, "xray_glass"),
        		new ItemBlock(MachineBase)      .setRegistryName(MOD_ID, "machine_base"),
        		new ItemBlock(MagicStoneBlock)  .setRegistryName(MOD_ID, "magic_stone_block"),
        		InController		.setRegistryName("in_controller"),
        		OutController		.setRegistryName("out_controller"),
        		IOController		.setRegistryName("io_controller"),
        		Tntmine2			.setRegistryName("tnt_minecart_2"),
        		ItemNewMinecart		.setRegistryName("new_minecart"),
        		ItemNewMinecartChest.setRegistryName("new_minecart_chest"),
        		RailWand 			.setRegistryName("railwand"),
        		MagicStone			.setRegistryName("magicstone"),
        		Redstone3			.setRegistryName("redstone3"),
        		MStonePickaxe		.setRegistryName("mstone_pickaxe"),
        		MStonePickaxePlus	.setRegistryName("mstone_pickaxe_plus"),
        		MStoneShovel		.setRegistryName("mstone_shovel"),
        		MStoneAxe			.setRegistryName("mstone_axe"),
        		MStoneSword			.setRegistryName("mstone_sword"),
        		MStoneHoe			.setRegistryName("mstone_hoe"),
        		UnrefinedQuartz		.setRegistryName("unrefined_quartz"),
        		BoardingWand		.setRegistryName("boarding_wand")
        );
        
    	//String groupMaterial = "material";
    	//String groupYutori = "yutori";
    	//String groupNewItem = "newitem";
        ForgeRegistries.RECIPES.registerAll(																																													
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"redstone3"), new ItemStack(Redstone3), Items.REDSTONE, Items.REDSTONE, Items.REDSTONE)								.setRegistryName("redstone3"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"redstone_block_alt"), new ItemStack(Blocks.REDSTONE_BLOCK), Redstone3, Redstone3, Redstone3)						.setRegistryName("redstone_block_alt"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"redstone3_rev"), new ItemStack(Items.REDSTONE,3), Redstone3)														.setRegistryName("redstone3_rev"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"magicstone"), new ItemStack(MagicStone), Items.IRON_INGOT,Blocks.COAL_BLOCK,Items.REDSTONE)	.setRegistryName("magicstone"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"magicstone"), new ItemStack(MagicStone), Items.IRON_INGOT,CharcoalBlock,Items.REDSTONE)	.setRegistryName("magicstonech"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"cross_rail"), new ItemStack(CrossRail), Blocks.RAIL,Blocks.RAIL)													.setRegistryName("cross_rail"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"switch_rail_conv"), new ItemStack(SwitchRailL), SwitchRailR)														.setRegistryName("switch_rail_l_conv"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"switch_rail_conv"), new ItemStack(SwitchRailR), SwitchRailL)														.setRegistryName("switch_rail_r_conv"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"slowdown_rail"), new ItemStack(SlowdownRail,64), MagicStone,Blocks.SOUL_SAND)										.setRegistryName("slowdown_rail"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"ms_rail"), new ItemStack(MSRail,64), MagicStone,new ItemStack(Items.COAL,1,32767))									.setRegistryName("ms_rail"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"hs_ms_conv"), new ItemStack(HSRail), MSRail)																		.setRegistryName("hs_rail_conv"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"hs_ms_conv"), new ItemStack(MSRail), HSRail)																		.setRegistryName("ms_rail_conv"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"departure_rail"), new ItemStack(DepartureRail), Blocks.ACTIVATOR_RAIL,Blocks.LEVER)									.setRegistryName("departure_rail"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"new_detector"), new ItemStack(NewDetector),   Blocks.DETECTOR_RAIL,Blocks.LEVER)									.setRegistryName("new_detector"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"new_minecart"), ((ItemNewMinecartBase) ItemNewMinecart).genStack(16,0), MagicStone,Items.MINECART)					.setRegistryName("new_minecart"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"new_minecart_chest"), ((ItemNewMinecartBase) ItemNewMinecartChest).genStack(16,0), MagicStone,Items.CHEST_MINECART)	.setRegistryName("new_minecart_chest"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"new_minecart_chest"), ((ItemNewMinecartBase) ItemNewMinecartChest).genStack(16,0), MagicStone,Items.MINECART,Blocks.CHEST)  .setRegistryName("new_minecart_chest_alt"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"logic_block"), new ItemStack(LogicBlock), MachineBase,Items.REDSTONE)												.setRegistryName("logic_block"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"or_block"), new ItemStack(OrBlock), MachineBase,Blocks.REDSTONE_TORCH)												.setRegistryName("or_block"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"and_or_conv"), new ItemStack(AndBlock), OrBlock)																	.setRegistryName("and_block_conv"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"and_or_conv"), new ItemStack(OrBlock), AndBlock)																	.setRegistryName("or_block_conv"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"minecart_detector"), new ItemStack(MinecartDetector), MachineBase,Blocks.WOODEN_PRESSURE_PLATE)						.setRegistryName("minecart_detector_w"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"minecart_detector"), new ItemStack(MinecartDetector), MachineBase,Blocks.STONE_PRESSURE_PLATE)						.setRegistryName("minecart_detector_s"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"minecart_detector_insulated"), new ItemStack(MinecartDetectorInsulated), MinecartDetector,new ItemStack(Blocks.CARPET,1,32767))	.setRegistryName("minecart_detector_insulated"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"hopper_plus"), new ItemStack(HopperPlus), MachineBase,Items.IRON_INGOT)												.setRegistryName("hopper_plus"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"loader"), new ItemStack(Loader), HopperPlus,Blocks.WOODEN_PRESSURE_PLATE)											.setRegistryName("loader_w"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"loader"), new ItemStack(Loader), HopperPlus,Blocks.STONE_PRESSURE_PLATE)											.setRegistryName("loader_S"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"loader_unloader_conv"), new ItemStack(Loader), Unloader)															.setRegistryName("loader_conv"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"loader_unloader_conv"), new ItemStack(Unloader), Loader)															.setRegistryName("unloader_conv"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"minecart_killer"), new ItemStack(MinecartKiller), Unloader,Items.IRON_INGOT)										.setRegistryName("minecart_killer"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"minecart_converter"), new ItemStack(MinecartConverter), HopperPlus,new ItemStack(Items.DYE,1,32767))				.setRegistryName("minecart_converter"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"converter_rail"), new ItemStack(ConverterRail), Blocks.RAIL,MinecartConverter)										.setRegistryName("converter_rail"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"lily_rail"), new ItemStack(LilyRail), Blocks.RAIL,Blocks.WATERLILY)													.setRegistryName("lily_rail"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"long_repeater"), new ItemStack(UnpoweredLongRepeater), Items.REPEATER , Items.REDSTONE)								.setRegistryName("long_repeater"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"redstone_stoper"), new ItemStack(UnpoweredStoper), UnpoweredLongRepeater, Blocks.REDSTONE_TORCH)					.setRegistryName("redstone_stoper"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"redstone_extender"), new ItemStack(UnpoweredExtender), UnpoweredLongRepeater, Items.REDSTONE)						.setRegistryName("redstone_extender"),
        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"magic_stone_unpack"), new ItemStack(MagicStone, 9), MagicStoneBlock)												.setRegistryName("magic_stone_unpack"),

				new ShapedOreRecipe(new ResourceLocation(MOD_ID,"magic_stone_pack"), new ItemStack(MagicStoneBlock,1),
				                            "MMM",
				                            "MMM",
				                            "MMM",
				                        'M',MagicStone)   .setRegistryName("magic_stone_pack"),

				new ShapedOreRecipe(new ResourceLocation(MOD_ID,"charcoal_pack"), new ItemStack(CharcoalBlock,1),
				                            "CCC",
				                            "CCC",
				                            "CCC",
				                        'C',new ItemStack(Items.COAL, 1, 1))   .setRegistryName("charcoal_pack"),
				
				new ShapedOreRecipe(new ResourceLocation(MOD_ID,"machine_base"),new ItemStack(MachineBase,16),
				                            "IGI",
				                            "G G",
				                            "IGI",
				                        'I',Items.IRON_INGOT,
				                        'G',Blocks.GLASS)   .setRegistryName("machine_base"),


				new ShapedOreRecipe(new ResourceLocation(MOD_ID,"hyper_glass"),new ItemStack(HyperGlass,8),
											"GGG",
											"GIG",
											"GGG",
										'I',Blocks.COBBLESTONE,
										'G',Blocks.GLASS)   .setRegistryName("hyper_glass"),

        		new ShapedOreRecipe(new ResourceLocation(MOD_ID,"xray_glass"),new ItemStack(XrayGlass,8),
											"GGG",
											"GIG",
											"GGG",
										'I',Items.ENDER_PEARL,
										'G',Blocks.GLASS)   .setRegistryName("xray_glass"),

        		new ShapedOreRecipe(new ResourceLocation(MOD_ID,"solid_glass"),new ItemStack(SolidGlass,8),
											"GGG",
											"GIG",
											"GGG",
										'I',Items.REDSTONE,
										'G',Blocks.GLASS)   .setRegistryName("solid_glass"),

        		new ShapedOreRecipe(new ResourceLocation(MOD_ID,"dark_glass"),new ItemStack(DarkGlass,8),
											"GGG",
											"GIG",
											"GGG",
										'I',new ItemStack(Items.COAL,1,32767),
										'G',Blocks.GLASS)   .setRegistryName("dark_glass"),
				
				//new ShapedOreRecipe(new ResourceLocation(MOD_ID,groupNewItem)  ,new ItemStack(IOController),"M","T", 'M',MagicStone,'T',Blocks.REDSTONE_TORCH) .setRegistryName("io_controller"),
				new ShapedOreRecipe(new ResourceLocation(MOD_ID,"in_controller") ,new ItemStack(InController),"M","T", 'M',MagicStone,'T',Blocks.REDSTONE_TORCH) .setRegistryName("in_controller"),
				new ShapedOreRecipe(new ResourceLocation(MOD_ID,"in_controller") ,new ItemStack(InController),"C" ,'C' , OutController)                          .setRegistryName("in_controller_conv"),
				new ShapedOreRecipe(new ResourceLocation(MOD_ID,"out_controller"),new ItemStack(OutController),"C",'C' , InController)                           .setRegistryName("out_controller_conv"),
				new ShapedOreRecipe(new ResourceLocation(MOD_ID,"switch_rail_r"),new ItemStack(SwitchRailR),  "RL", 'R',Blocks.RAIL,'L',Blocks.LEVER)            .setRegistryName("switch_rail_r"),
				new ShapedOreRecipe(new ResourceLocation(MOD_ID,"switch_rail_l"),new ItemStack(SwitchRailL),  "LR", 'R',Blocks.RAIL,'L',Blocks.LEVER)            .setRegistryName("switch_rail_l"),
				new ShapedOreRecipe(new ResourceLocation(MOD_ID,"boarding_wand"),new ItemStack(BoardingWand),"L","S",'L', Loader, 'S', Items.STICK)              .setRegistryName("boarding_wand")
            );
        if(allowYutoriRecipe) {
	        ForgeRegistries.RECIPES.registerAll(		
					new ShapedOreRecipe(new ResourceLocation(MOD_ID,"torch_yutori") ,new ItemStack(Blocks.TORCH,64),"M","S", 'M',MagicStone,'S',Items.STICK)					.setRegistryName("torch_yutori"),
	        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"rail_yutori"), new ItemStack(Blocks.RAIL,64), MagicStone)												.setRegistryName("rail_yutori"),
	        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"activator_rail_m_yutori"), new ItemStack(Blocks.ACTIVATOR_RAIL,64), MagicStone,Items.REDSTONE)				.setRegistryName("activator_rail_yutori"),
	        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"detector_rail_m_yutori"), new ItemStack(Blocks.DETECTOR_RAIL,64), MagicStone,Blocks.WOODEN_PRESSURE_PLATE)	.setRegistryName("detector_rail_yutori_w"),
	        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"detector_rail_m_yutori"), new ItemStack(Blocks.DETECTOR_RAIL,64), MagicStone,Blocks.STONE_PRESSURE_PLATE)	.setRegistryName("detector_rail_yutori_s"),
	        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"activator_rail_yutori"), new ItemStack(Blocks.ACTIVATOR_RAIL), Blocks.RAIL,Items.REDSTONE)				.setRegistryName("activator_rail_alt"),
	        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"detector_rail_alt"), new ItemStack(Blocks.DETECTOR_RAIL), Blocks.RAIL,Blocks.WOODEN_PRESSURE_PLATE)	.setRegistryName("detector_rail_alt_w"),
	        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"detector_rail_alt"), new ItemStack(Blocks.DETECTOR_RAIL), Blocks.RAIL,Blocks.STONE_PRESSURE_PLATE)	.setRegistryName("detector_rail_alt_s"),
	        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"repeater_yutori"),new ItemStack(Items.REPEATER,64) ,Blocks.COBBLESTONE, Items.REDSTONE, MagicStone)		.setRegistryName("repeater_yutori"),
	        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"building_block"), new ItemStack(BuildingBlock,64), MagicStone,new ItemStack(Blocks.PLANKS,1,32767))		.setRegistryName("building_block_p"),
	        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"building_block"), new ItemStack(BuildingBlock,64), MagicStone,Blocks.COBBLESTONE)						.setRegistryName("building_block_c"),
	        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"building_block"), new ItemStack(BuildingBlock,64), MagicStone,Blocks.DIRT)								.setRegistryName("building_block_d"),
	        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"building_light"), new ItemStack(BuildingLight,64), MagicStone,Blocks.TORCH)								.setRegistryName("building_light"),
	        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"waterlily_yutori"), new ItemStack(Blocks.WATERLILY,65), MagicStone,Blocks.WATERLILY)					.setRegistryName("waterlily_yutori"),
	        		new ShapelessOreRecipe(new ResourceLocation(MOD_ID,"saddle_yutori"), new ItemStack(Items.SADDLE), MagicStone,Items.LEATHER,Items.LEATHER,Items.LEATHER)		.setRegistryName("saddle_yutori"),

	        		
					new ShapedOreRecipe(new ResourceLocation(MOD_ID,"auto_lumber"),new ItemStack(AutoLumber),
					                                "MMM",
					                                "MOM",
					                                "MMM",
					                            'M',MStoneAxe,
					                            'O',MachineBase).setRegistryName("auto_lumber"),
					
					new ShapedOreRecipe(new ResourceLocation(MOD_ID,"unrefined_quartz"),new ItemStack(UnrefinedQuartz,4),
                            "SSS",
                            "SMS",
                            "SSS",
                        'S',new ItemStack(Blocks.STONE,1,1),
                        'M',MagicStone).setRegistryName("unrefined_quartz_g"),
					new ShapedOreRecipe(new ResourceLocation(MOD_ID,"unrefined_quartz"),new ItemStack(UnrefinedQuartz,4),
                            "SSS",
                            "SMS",
                            "SSS",
                        'S',new ItemStack(Blocks.STONE,1,2),
                        'M',MagicStone).setRegistryName("unrefined_quartz_gp"),
					new ShapedOreRecipe(new ResourceLocation(MOD_ID,"unrefined_quartz"),new ItemStack(UnrefinedQuartz,2),
                            "SSS",
                            "SMS",
                            "SSS",
                        'S',new ItemStack(Blocks.STONE,1,3),
                        'M',MagicStone).setRegistryName("unrefined_quartz_d"),
					new ShapedOreRecipe(new ResourceLocation(MOD_ID,"unrefined_quartz"),new ItemStack(UnrefinedQuartz,2),
                            "SSS",
                            "SMS",
                            "SSS",
                        'S',new ItemStack(Blocks.STONE,1,4),
                        'M',MagicStone).setRegistryName("unrefined_quartz_dp"),
					new ShapedOreRecipe(new ResourceLocation(MOD_ID,"unrefined_quartz"),new ItemStack(UnrefinedQuartz,1),
                            "SSS",
                            "SMS",
                            "SSS",
                        'S',new ItemStack(Blocks.STONE,1,5),
                        'M',MagicStone).setRegistryName("unrefined_quartz_a"),
					new ShapedOreRecipe(new ResourceLocation(MOD_ID,"unrefined_quartz"),new ItemStack(UnrefinedQuartz,1),
                            "SSS",
                            "SMS",
                            "SSS",
                        'S',new ItemStack(Blocks.STONE,1,6),
                        'M',MagicStone).setRegistryName("unrefined_quartz_ap"),
	        		
					new ShapedOreRecipe(new ResourceLocation(MOD_ID,"mstone_pickaxe_plus"),new ItemStack(MStonePickaxePlus),
					                                "MMM",
					                                "MOM",
					                                "MMM",
					                            'M',MStonePickaxe,
					                            'O',Blocks.OBSIDIAN).setRegistryName("mstone_pickaxe_plus"),
					
					new ShapedOreRecipe(new ResourceLocation(MOD_ID,"mstone_pickaxe"),new ItemStack(MStonePickaxe),
					                                "MMM",
					                                " S ",
					                                " S ",
					                            'M',MagicStoneBlock,
					                            'S',Items.STICK).setRegistryName("mstone_pickaxe"),
					
					new ShapedOreRecipe(new ResourceLocation(MOD_ID,"mstone_shovel"),new ItemStack(MStoneShovel),
					                                "M",
					                                "S",
					                                "S",
					                            'M',MagicStoneBlock,
					                            'S',Items.STICK).setRegistryName("mstone_shovel"),
					
					new ShapedOreRecipe(new ResourceLocation(MOD_ID,"mstone_axe"),new ItemStack(MStoneAxe),
					                                "MM",
					                                "SM",
					                                "S ",
					                            'M',MagicStoneBlock,
					                            'S',Items.STICK).setRegistryName("mstone_axe_r"),
					
					new ShapedOreRecipe(new ResourceLocation(MOD_ID,"mstone_axe"),new ItemStack(MStoneAxe),
					                                "MM",
					                                "MS",
					                                " S",
					                            'M',MagicStoneBlock,
					                            'S',Items.STICK).setRegistryName("mstone_axe_l"),
					
					new ShapedOreRecipe(new ResourceLocation(MOD_ID,"mstone_sword"),new ItemStack(MStoneSword),
					                                "M",
					                                "M",
					                                "S",
					                            'M',MagicStoneBlock,
					                            'S',Items.STICK).setRegistryName("mstone_sword"),

					new ShapedOreRecipe(new ResourceLocation(MOD_ID,"mstone_hoe"),new ItemStack(MStoneHoe),
					                                "MM",
					                                "S ",
					                                "S ",
					                            'M',MagicStoneBlock,
					                            'S',Items.STICK).setRegistryName("mstone_hoe_r"),

					new ShapedOreRecipe(new ResourceLocation(MOD_ID,"mstone_hoe"),new ItemStack(MStoneHoe),
					                                "MM",
					                                " S",
					                                " S",
					                            'M',MagicStoneBlock,
					                            'S',Items.STICK).setRegistryName("mstone_hoe_l")
					
	        );
	        GameRegistry.addSmelting(UnrefinedQuartz,new ItemStack(Items.QUARTZ),0.7f);
        }
		GameRegistry.registerTileEntity(TileEntityOr.class, new ResourceLocation(MOD_ID,"TileEntityOr"));
		GameRegistry.registerTileEntity(TileEntityAnd.class, new ResourceLocation(MOD_ID,"TileEntityAnd"));
		GameRegistry.registerTileEntity(TileEntityLogic.class, new ResourceLocation(MOD_ID,"TileEntityLogic"));
		GameRegistry.registerTileEntity(TileEntityMinecartDetector.class, new ResourceLocation(MOD_ID,"TileEntityMinecartDetector"));
		GameRegistry.registerTileEntity(TileEntityHopperPlus.class, new ResourceLocation(MOD_ID,"TileEntityHopperPlus"));
		GameRegistry.registerTileEntity(TileEntityUnloader.class, new ResourceLocation(MOD_ID,"TileEntityUnloader"));
		GameRegistry.registerTileEntity(TileEntityLoader.class, new ResourceLocation(MOD_ID,"TileEntityLoader"));
		GameRegistry.registerTileEntity(TileEntityMinecartConverter.class, new ResourceLocation(MOD_ID,"TileEntityMinecartConverter"));
		GameRegistry.registerTileEntity(TileEntityMinecartKiller.class, new ResourceLocation(MOD_ID,"TileEntityMinecartKiller"));
		GameRegistry.registerTileEntity(TileEntityAutoLumber.class, new ResourceLocation(MOD_ID,"TileEntityAutoLumber"));
		EntityRegistry.registerModEntity(new ResourceLocation(MOD_ID,"newminecart"),EntityNewMinecartRidable.class, "EntityNewMinecart", 255, this, 80, 3,true);
		EntityRegistry.registerModEntity(new ResourceLocation(MOD_ID,"newminecart_chest"),EntityNewMinecartChest.class, "EntityNewMinecartChest", 253, this, 80,3,true);
		
		//MinecraftForge.EVENT_BUS.register(new WorldEventHandler());
		ForgeChunkManager.setForcedChunkLoadingCallback(this,null);
		if (event.getSide().isClient()) {
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnd.class,new TESRAnd());
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOr.class,new TESROr());
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLogic.class,new TESRLogic());
			//ModelBakery.addVariantName(Item.getItemFromBlock(RemoteBlock), MOD_ID + ":" + "RemoteBlock0", MOD_ID + ":" + "RemoteBlock1", MOD_ID + ":" + "RemoteBlock2", MOD_ID + ":" + "RemoteBlock3", MOD_ID + ":" + "RemoteBlock4", MOD_ID + ":" + "RemoteBlock5", MOD_ID + ":" + "RemoteBlock6", MOD_ID + ":" + "RemoteBlock7", MOD_ID + ":" + "RemoteBlock8", MOD_ID + ":" + "RemoteBlock9", MOD_ID + ":" + "RemoteBlock10", MOD_ID + ":" + "RemoteBlock11", MOD_ID + ":" + "RemoteBlock12", MOD_ID + ":" + "RemoteBlock13", MOD_ID + ":" + "RemoteBlock14", MOD_ID + ":" + "RemoteBlock15");
			//1IDで複数モデルを登録するなら、上のメソッドで登録した登録名を指定する。
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(NewDetector), 0, new ModelResourceLocation(MOD_ID + ":" + "new_detector", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SlowdownRail), 0, new ModelResourceLocation(MOD_ID + ":" + "slowdown_rail", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(HSRail), 0, new ModelResourceLocation(MOD_ID + ":" + "hs_rail", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(MSRail), 0, new ModelResourceLocation(MOD_ID + ":" + "ms_rail", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SwitchRailR), 0, new ModelResourceLocation(MOD_ID + ":" + "switch_rail_r", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SwitchRailL), 0, new ModelResourceLocation(MOD_ID + ":" + "switch_rail_l", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(CrossRail), 0, new ModelResourceLocation(MOD_ID + ":" + "cross_rail", "inventory"));
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ConverterRail), 0, new ModelResourceLocation(MOD_ID + ":" + "converter_rail", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(LilyRail), 0, new ModelResourceLocation(MOD_ID + ":" + "lily_rail", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(LogicBlock), 0, new ModelResourceLocation(MOD_ID + ":" + "logic_block", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(AndBlock), 0, new ModelResourceLocation(MOD_ID + ":" + "and_block", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(OrBlock), 0, new ModelResourceLocation(MOD_ID + ":" + "or_block", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(CharcoalBlock), 0, new ModelResourceLocation(MOD_ID + ":" + "charcoal_block", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BuildingBlock), 0, new ModelResourceLocation(MOD_ID + ":" + "building_block", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BuildingLight), 0, new ModelResourceLocation(MOD_ID + ":" + "building_light", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(MinecartDetector), 0, new ModelResourceLocation(MOD_ID + ":" + "minecart_detector", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(MinecartDetectorInsulated), 0, new ModelResourceLocation(MOD_ID + ":" + "minecart_detector_insulated", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(DepartureRail), 0, new ModelResourceLocation(MOD_ID + ":" + "departure_rail", "inventory"));
			ModelLoader.setCustomModelResourceLocation(InController, 0, new ModelResourceLocation(MOD_ID + ":" + "in_controller", "inventory"));
			ModelLoader.setCustomModelResourceLocation(OutController, 0, new ModelResourceLocation(MOD_ID + ":" + "out_controller", "inventory"));
			ModelLoader.setCustomModelResourceLocation(IOController, 0, new ModelResourceLocation(MOD_ID + ":" + "io_controller", "inventory"));
			//ModelLoader.setCustomModelResourceLocation(TNT2, 0, new ModelResourceLocation(MOD_ID + ":" + "TNT2", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Tntmine2, 0, new ModelResourceLocation(MOD_ID + ":" + "tnt_minecart2", "inventory"));
			ModelLoader.setCustomModelResourceLocation(RailWand, 0, new ModelResourceLocation(MOD_ID + ":" + "rail_wand", "inventory"));
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(HopperPlus), 0, new ModelResourceLocation(MOD_ID + ":" + "hopper_plus", "inventory"));
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Unloader), 0, new ModelResourceLocation(MOD_ID + ":" + "unloader", "inventory"));
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(MinecartConverter), 0, new ModelResourceLocation(MOD_ID + ":" + "minecart_converter", "inventory"));
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(Loader), 0, new ModelResourceLocation(MOD_ID + ":" + "loader", "inventory"));
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(MinecartKiller), 0, new ModelResourceLocation(MOD_ID + ":" + "minecart_killer", "inventory"));
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(AutoLumber), 0, new ModelResourceLocation(MOD_ID + ":" + "auto_lumber", "inventory"));
			for(int i=0;i<16;i++)ModelLoader.setCustomModelResourceLocation(ItemNewMinecart, i, new ModelResourceLocation(MOD_ID + ":" + "item_new_minecart", "inventory"));
			for(int i=0;i<16;i++)ModelLoader.setCustomModelResourceLocation(ItemNewMinecartChest, i, new ModelResourceLocation(MOD_ID + ":" + "item_new_minecart_chest", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(MachineBase), 0, new ModelResourceLocation(MOD_ID + ":" + "machine_base", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(MagicStoneBlock), 0, new ModelResourceLocation(MOD_ID + ":" + "magic_stone_block", "inventory"));
			ModelLoader.setCustomModelResourceLocation(MagicStone, 0, new ModelResourceLocation(MOD_ID + ":" + "magic_stone", "inventory"));
			ModelLoader.setCustomModelResourceLocation(UnrefinedQuartz, 0, new ModelResourceLocation(MOD_ID + ":" + "unrefined_quartz", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Redstone3, 0, new ModelResourceLocation(MOD_ID + ":" + "redstone3", "inventory"));
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(UnpoweredLongRepeater), 0, new ModelResourceLocation(MOD_ID + ":" + "long_repeater", "inventory"));
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(UnpoweredStoper), 0, new ModelResourceLocation(MOD_ID + ":" + "redstone_stoper", "inventory"));
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(UnpoweredExtender), 0, new ModelResourceLocation(MOD_ID + ":" + "redstone_extender", "inventory"));

			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(DarkGlass) , 0, new ModelResourceLocation(MOD_ID + ":" + "dark_glass" , "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(HyperGlass), 0, new ModelResourceLocation(MOD_ID + ":" + "hyper_glass", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(SolidGlass), 0, new ModelResourceLocation(MOD_ID + ":" + "solid_glass", "inventory"));
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(XrayGlass) , 0, new ModelResourceLocation(MOD_ID + ":" + "xray_glass" , "inventory"));
			
			
			ModelLoader.setCustomModelResourceLocation(MStonePickaxe, 0, new ModelResourceLocation(MOD_ID + ":" + "mstone_pickaxe", "inventory"));
			ModelLoader.setCustomModelResourceLocation(MStonePickaxePlus, 0, new ModelResourceLocation(MOD_ID + ":" + "mstone_pickaxe_plus", "inventory"));
			ModelLoader.setCustomModelResourceLocation(MStoneShovel, 0, new ModelResourceLocation(MOD_ID + ":" + "mstone_shovel", "inventory"));
			ModelLoader.setCustomModelResourceLocation(MStoneAxe, 0, new ModelResourceLocation(MOD_ID + ":" + "mstone_axe", "inventory"));
			ModelLoader.setCustomModelResourceLocation(MStoneSword, 0, new ModelResourceLocation(MOD_ID + ":" + "mstone_sword", "inventory"));
			ModelLoader.setCustomModelResourceLocation(MStoneHoe, 0, new ModelResourceLocation(MOD_ID + ":" + "mstone_hoe", "inventory"));
			ModelLoader.setCustomModelResourceLocation(BoardingWand, 0, new ModelResourceLocation(MOD_ID + ":" + "boarding_wand", "inventory"));
			
            ClientRegistry.registerKeyBinding(keyPipeOpposite);
        }

        FMLCommonHandler.instance().bus().register(new KeyHandler());

	}

    @EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        if (event.getSide().isClient()) {
        	/*RenderingRegistry.registerEntityRenderingHandler(EntityTNTPrimed2.class,
        			new IRenderFactory<EntityTNTPrimed2>(){
				@Override
				public Render<? super EntityTNTPrimed2> createRenderFor(RenderManager manager) {
		        	System.out.println("reg");
		        	return new RenderTNTPrimed2(manager);
				}
        	});*/
        	RenderingRegistry.registerEntityRenderingHandler(EntityNewMinecartRidable.class, new RenderNewMinecart<EntityNewMinecartRidable>(Minecraft.getMinecraft().getRenderManager()));
        	RenderingRegistry.registerEntityRenderingHandler(EntityNewMinecartChest.class, new RenderNewMinecart<EntityNewMinecartChest>(Minecraft.getMinecraft().getRenderManager()));
        }
        //rw_block=EasyRails.BuildingBlock.getDefaultState();
        //rw_lblock=EasyRails.BuildingLight.getDefaultState();
        lumberingBlocks = toBlocks(lumberingBlocksStr);
        plantingBlocks  = toBlocks(plantingBlocksStr);
    }

    @EventHandler
    public void start(FMLServerStartingEvent event){
        event.registerServerCommand(new CommandRailWand());
        event.registerServerCommand(new CommandSearchMinecart());
    }
    

}
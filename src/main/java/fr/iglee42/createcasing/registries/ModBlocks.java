package fr.iglee42.createcasing.registries;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.AllTags;
import com.simibubi.create.Create;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.content.decoration.encasing.EncasingRegistry;
import com.simibubi.create.content.fluids.PipeAttachmentModel;
import com.simibubi.create.content.fluids.pipes.EncasedPipeBlock;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.content.kinetics.belt.BeltBlockEntity;
import com.simibubi.create.content.kinetics.chainDrive.ChainDriveGenerator;
import com.simibubi.create.content.kinetics.chainDrive.ChainGearshiftBlock;
import com.simibubi.create.content.kinetics.gearbox.GearboxBlock;
import com.simibubi.create.content.kinetics.motor.CreativeMotorGenerator;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockModel;
import com.simibubi.create.content.kinetics.simpleRelays.CogWheelBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogCTBehaviour;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogwheelBlock;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedShaftBlock;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import com.simibubi.create.content.redstone.displayLink.source.ItemNameDisplaySource;
import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.data.*;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.utility.Couple;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import fr.iglee42.createcasing.CreateCasing;
import fr.iglee42.createcasing.blocks.CreativeCogwheelBlock;
import fr.iglee42.createcasing.blocks.customs.*;
import fr.iglee42.createcasing.blocks.publics.PublicEncasedCogwheelBlock;
import fr.iglee42.createcasing.blocks.publics.PublicEncasedPipeBlock;
import fr.iglee42.createcasing.blocks.publics.PublicEncasedShaftBlock;
import fr.iglee42.createcasing.blocks.shafts.*;
import fr.iglee42.createcasing.items.CustomVerticalGearboxItem;
import fr.iglee42.createcasing.items.WoodenCogwheelBlockItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.simibubi.create.content.redstone.displayLink.AllDisplayBehaviours.assignDataBehaviour;
import static com.simibubi.create.foundation.data.BlockStateGen.axisBlock;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.*;
import static fr.iglee42.createcasing.CreateCasing.REGISTRATE;
import static net.minecraft.world.level.block.Blocks.GLASS;

public class ModBlocks {

    static {
        REGISTRATE.setCreativeTab(ModCreativeModeTabs.MAIN_TAB);
    }

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CreateCasing.MODID);

    public static final BlockEntry<CasingBlock> CREATIVE_CASING = createCasing("creative",AllSpriteShifts.CREATIVE_CASING);


    //BELT CASINGS
    public static BeltBlockEntity.CasingType COPPER_BELT_CASING;
    public static BeltBlockEntity.CasingType RAILWAY_BELT_CASING;
    public static BeltBlockEntity.CasingType INDUSTRIAL_IRON_BELT_CASING;
    public static BeltBlockEntity.CasingType CREATIVE_BELT_CASING;

    //SHAFTS
    public static final BlockEntry<PublicEncasedShaftBlock> RAILWAY_ENCASED_SHAFT = createShaft("railway",AllBlocks.RAILWAY_CASING::get,AllSpriteShifts.RAILWAY_CASING);
    public static final BlockEntry<PublicEncasedShaftBlock> COPPER_ENCASED_SHAFT = createShaft("copper",AllBlocks.COPPER_CASING::get, AllSpriteShifts.COPPER_CASING);
    public static final BlockEntry<PublicEncasedShaftBlock> SHADOW_ENCASED_SHAFT = createShaft("shadow_steel",AllBlocks.SHADOW_STEEL_CASING::get,AllSpriteShifts.SHADOW_STEEL_CASING);
    public static final BlockEntry<PublicEncasedShaftBlock> REFINED_RADIANCE_ENCASED_SHAFT = createShaft("refined_radiance",AllBlocks.REFINED_RADIANCE_CASING::get,AllSpriteShifts.REFINED_RADIANCE_CASING);
    public static final BlockEntry<PublicEncasedShaftBlock> CREATIVE_ENCASED_SHAFT = createShaft("creative",ModBlocks.CREATIVE_CASING::get,AllSpriteShifts.CREATIVE_CASING);
    public static final BlockEntry<PublicEncasedShaftBlock> INDUSTRIAL_IRON_ENCASED_SHAFT = REGISTRATE.block("industrial_iron_encased_shaft", p -> new PublicEncasedShaftBlock(p, AllBlocks.INDUSTRIAL_IRON_BLOCK))
            .properties(p -> p.mapColor(MapColor.PODZOL))
            .transform(encasedNoSpriteShaft("industrial_iron"))
            .transform(EncasingRegistry.addVariantTo(AllBlocks.SHAFT))
            .transform(pickaxeOnly())
            .onRegisterAfter(Registries.ITEM, CreateCasing::hideItem)
            .register();

    //COGWHEELS
    public static final BlockEntry<PublicEncasedCogwheelBlock> RAILWAY_ENCASED_COGWHEEL = createCogwheel("railway",AllBlocks.RAILWAY_CASING::get,AllSpriteShifts.RAILWAY_CASING,ModSprites.RAILWAY_ENCASED_COGWHEEL_SIDE,ModSprites.RAILWAY_ENCASED_COGWHEEL_OTHERSIDE);
    public static final BlockEntry<PublicEncasedCogwheelBlock> COPPER_ENCASED_COGWHEEL = createCogwheel("copper",AllBlocks.COPPER_CASING::get,AllSpriteShifts.COPPER_CASING,ModSprites.COPPER_ENCASED_COGWHEEL_SIDE,ModSprites.COPPER_ENCASED_COGWHEEL_OTHERSIDE);
    public static final BlockEntry<PublicEncasedCogwheelBlock> SHADOW_ENCASED_COGWHEEL = createCogwheel("shadow_steel",AllBlocks.SHADOW_STEEL_CASING::get,AllSpriteShifts.SHADOW_STEEL_CASING,ModSprites.SHADOW_ENCASED_COGWHEEL_SIDE,ModSprites.SHADOW_ENCASED_COGWHEEL_OTHERSIDE);
    public static final BlockEntry<PublicEncasedCogwheelBlock> RADIANCE_ENCASED_COGWHEEL = createCogwheel("refined_radiance",AllBlocks.REFINED_RADIANCE_CASING::get,AllSpriteShifts.REFINED_RADIANCE_CASING,ModSprites.RADIANCE_ENCASED_COGWHEEL_SIDE,ModSprites.RADIANCE_ENCASED_COGWHEEL_OTHERSIDE);
    public static final BlockEntry<PublicEncasedCogwheelBlock> CREATIVE_ENCASED_COGWHEEL = createCogwheel("creative",ModBlocks.CREATIVE_CASING::get,AllSpriteShifts.REFINED_RADIANCE_CASING,ModSprites.CREATIVE_ENCASED_COGWHEEL_SIDE,ModSprites.CREATIVE_ENCASED_COGWHEEL_OTHERSIDE);
    public static final BlockEntry<PublicEncasedCogwheelBlock> INDUSTRIAL_IRON_ENCASED_COGWHEEL =REGISTRATE.block("industrial_iron_encased_cogwheel", p -> new PublicEncasedCogwheelBlock(p, false, AllBlocks.INDUSTRIAL_IRON_BLOCK))
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.mapColor(MapColor.PODZOL).noOcclusion())
            .addLayer(() -> RenderType::cutoutMipped)
            .transform(EncasingRegistry.addVariantTo(AllBlocks.COGWHEEL))
            .transform(axeOrPickaxe())
            .item()
            .transform(customItemModel())
            .onRegisterAfter(Registries.ITEM, CreateCasing::hideItem)
            .register();;

    //LARGE COGWHEELS

    public static final BlockEntry<PublicEncasedCogwheelBlock> RAILWAY_ENCASED_COGWHEEL_LARGE = createLargeCogwheel("railway",AllBlocks.RAILWAY_CASING::get,AllSpriteShifts.RAILWAY_CASING);
    public static final BlockEntry<PublicEncasedCogwheelBlock> COPPER_ENCASED_COGWHEEL_LARGE = createLargeCogwheel("copper",AllBlocks.COPPER_CASING::get,AllSpriteShifts.COPPER_CASING);
    public static final BlockEntry<PublicEncasedCogwheelBlock> SHADOW_ENCASED_COGWHEEL_LARGE = createLargeCogwheel("shadow_steel",AllBlocks.SHADOW_STEEL_CASING::get,AllSpriteShifts.SHADOW_STEEL_CASING);
    public static final BlockEntry<PublicEncasedCogwheelBlock> RADIANCE_ENCASED_COGWHEEL_LARGE = createLargeCogwheel("refined_radiance",AllBlocks.REFINED_RADIANCE_CASING::get,AllSpriteShifts.REFINED_RADIANCE_CASING);
    public static final BlockEntry<PublicEncasedCogwheelBlock> CREATIVE_ENCASED_COGWHEEL_LARGE = createLargeCogwheel("creative",ModBlocks.CREATIVE_CASING::get,AllSpriteShifts.CREATIVE_CASING);
    public static final BlockEntry<PublicEncasedCogwheelBlock> INDUSTRIAL_IRON_ENCASED_COGWHEEL_LARGE = REGISTRATE.block("industrial_iron_encased_large_cogwheel", p -> new PublicEncasedCogwheelBlock(p, true, AllBlocks.INDUSTRIAL_IRON_BLOCK))
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.mapColor(MapColor.PODZOL).noOcclusion())
            .addLayer(() -> RenderType::cutoutMipped)
            .transform(EncasingRegistry.addVariantTo(AllBlocks.LARGE_COGWHEEL))
            .transform(axeOrPickaxe())
            .item()
            .transform(customItemModel())
            .onRegisterAfter(Registries.ITEM, CreateCasing::hideItem)
            .register();

    //PIPES

    public static final BlockEntry<PublicEncasedPipeBlock> ENCASED_ANDESITE_FLUID_PIPE = createPipe("andesite",AllBlocks.ANDESITE_CASING::get,AllSpriteShifts.ANDESITE_CASING);
    public static final BlockEntry<PublicEncasedPipeBlock> ENCASED_BRASS_FLUID_PIPE = createPipe("brass",AllBlocks.BRASS_CASING::get,AllSpriteShifts.BRASS_CASING);
    public static final BlockEntry<PublicEncasedPipeBlock> ENCASED_RAILWAY_FLUID_PIPE = createPipe("railway",AllBlocks.RAILWAY_CASING::get,AllSpriteShifts.RAILWAY_CASING);
    public static final BlockEntry<PublicEncasedPipeBlock> ENCASED_SHADOW_FLUID_PIPE = createPipe("shadow_steel",AllBlocks.SHADOW_STEEL_CASING::get,AllSpriteShifts.SHADOW_STEEL_CASING);
    public static final BlockEntry<PublicEncasedPipeBlock> ENCASED_RADIANCE_FLUID_PIPE = createPipe("refined_radiance",AllBlocks.REFINED_RADIANCE_CASING::get,AllSpriteShifts.REFINED_RADIANCE_CASING);
    public static final BlockEntry<PublicEncasedPipeBlock> ENCASED_CREATIVE_FLUID_PIPE = createPipe("creative",ModBlocks.CREATIVE_CASING::get,AllSpriteShifts.CREATIVE_CASING);
    public static final BlockEntry<PublicEncasedPipeBlock> ENCASED_INDUSTRIAL_IRON_FLUID_PIPE = REGISTRATE.block("industrial_iron_encased_fluid_pipe", p -> new PublicEncasedPipeBlock(p, AllBlocks.INDUSTRIAL_IRON_BLOCK))
            .initialProperties(SharedProperties::copperMetal)
            .properties(p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_GRAY))
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(pickaxeOnly())
            .onRegister(CreateRegistrate.blockModel(() -> PipeAttachmentModel::new))
            .loot((p, b) -> p.dropOther(b, AllBlocks.FLUID_PIPE.get()))
            .transform(EncasingRegistry.addVariantTo(AllBlocks.FLUID_PIPE))
            .onRegisterAfter(Registries.ITEM, CreateCasing::hideItem)
            .register();

    public static final BlockEntry<CustomGearboxBlock> BRASS_GEARBOX = createGearbox("brass",AllSpriteShifts.BRASS_CASING,ModItems.VERTICAL_BRASS_GEARBOX);
    public static final BlockEntry<CustomGearboxBlock> COPPER_GEARBOX = createGearbox("copper",AllSpriteShifts.COPPER_CASING,ModItems.VERTICAL_COPPER_GEARBOX);
    public static final BlockEntry<CustomGearboxBlock> RAILWAY_GEARBOX = createGearbox("railway",AllSpriteShifts.RAILWAY_CASING,ModItems.VERTICAL_RAILWAY_GEARBOX);
    public static final BlockEntry<CustomGearboxBlock> CREATIVE_GEARBOX = createGearbox("creative",AllSpriteShifts.CREATIVE_CASING,ModItems.VERTICAL_CREATIVE_GEARBOX);
    public static final BlockEntry<CustomGearboxBlock> INDUSTRIAL_IRON_GEARBOX = REGISTRATE.block("industrial_iron_gearbox", (p)->new CustomGearboxBlock(p,ModItems.VERTICAL_INDUSTRIAL_IRON_GEARBOX))
            .initialProperties(SharedProperties::stone)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.mapColor(MapColor.PODZOL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(pickaxeOnly())
            .blockstate((c, p) -> axisBlock(c, p, $ -> AssetLookup.partialBaseModel(c, p), true))
            .item()
            .transform(customItemModel())
            .register();

    public static final BlockEntry<CustomMixerBlock> BRASS_MIXER = createMixer("brass");
    public static final BlockEntry<CustomMixerBlock> COPPER_MIXER = createMixer("copper");
    public static final BlockEntry<CustomMixerBlock> RAILWAY_MIXER = createMixer("railway");
    public static final BlockEntry<CustomMixerBlock> CREATIVE_MIXER = createMixer("creative");
    public static final BlockEntry<CustomMixerBlock> INDUSTRIAL_IRON_MIXER = createMixer("industrial_iron");

    public static final BlockEntry<CustomPressBlock> BRASS_PRESS = createPress("brass");
    public static final BlockEntry<CustomPressBlock> COPPER_PRESS = createPress("copper");
    public static final BlockEntry<CustomPressBlock> RAILWAY_PRESS = createPress("railway");
    public static final BlockEntry<CustomPressBlock> CREATIVE_PRESS = createPress("creative");
    public static final BlockEntry<CustomPressBlock> INDUSTRIAL_IRON_PRESS = createPress("industrial_iron");

    public static final BlockEntry<CustomDepotBlock> BRASS_DEPOT = createDepot("brass");
    public static final BlockEntry<CustomDepotBlock> COPPER_DEPOT = createDepot("copper");
    public static final BlockEntry<CustomDepotBlock> RAILWAY_DEPOT = createDepot("railway");
    public static final BlockEntry<CustomDepotBlock> CREATIVE_DEPOT = createDepot("creative");
    public static final BlockEntry<CustomDepotBlock> INDUSTRIAL_IRON_DEPOT = createDepot("industrial_iron");

    public static final BlockEntry<CustomChainDriveBlock> BRASS_CHAIN_DRIVE = createDrive("brass");
    public static final BlockEntry<CustomChainDriveBlock> COPPER_CHAIN_DRIVE = createDrive("copper");
    public static final BlockEntry<CustomChainDriveBlock> RAILWAY_CHAIN_DRIVE = createDrive("railway");
    public static final BlockEntry<CustomChainDriveBlock> CREATIVE_CHAIN_DRIVE = createDrive("creative");
    public static final BlockEntry<CustomChainDriveBlock> INDUSTRIAL_IRON_CHAIN_DRIVE = createDrive("industrial_iron");

    public static final BlockEntry<CustomChainGearshiftBlock> BRASS_CHAIN_GEARSHIFT = createChainGearshift("brass");
    public static final BlockEntry<CustomChainGearshiftBlock> COPPER_CHAIN_GEARSHIFT = createChainGearshift("copper");
    public static final BlockEntry<CustomChainGearshiftBlock> RAILWAY_CHAIN_GEARSHIFT = createChainGearshift("railway");
    public static final BlockEntry<CustomChainGearshiftBlock> CREATIVE_CHAIN_GEARSHIFT = createChainGearshift("creative");
    public static final BlockEntry<CustomChainGearshiftBlock> INDUSTRIAL_IRON_CHAIN_GEARSHIFT = createChainGearshift("industrial_iron");

    public static final BlockEntry<WoodenShaftBlock> OAK_SHAFT = createWoodenShaft("oak");
    public static final BlockEntry<WoodenShaftBlock> SPRUCE_SHAFT = createWoodenShaft("spruce");
    public static final BlockEntry<WoodenShaftBlock> BIRCH_SHAFT = createWoodenShaft("birch");
    public static final BlockEntry<WoodenShaftBlock> JUNGLE_SHAFT = createWoodenShaft("jungle");
    public static final BlockEntry<WoodenShaftBlock> ACACIA_SHAFT = createWoodenShaft("acacia");
    public static final BlockEntry<WoodenShaftBlock> DARK_OAK_SHAFT = createWoodenShaft("dark_oak");
    public static final BlockEntry<WoodenShaftBlock> MANGROVE_SHAFT = createWoodenShaft("mangrove");
    public static final BlockEntry<WoodenShaftBlock> CHERRY_SHAFT = createWoodenShaft("cherry");
    public static final BlockEntry<WoodenShaftBlock> BAMBOO_SHAFT = createWoodenShaft("bamboo");
    public static final BlockEntry<WoodenShaftBlock> CRIMSON_SHAFT = createWoodenShaft("crimson");
    public static final BlockEntry<WoodenShaftBlock> WARPED_SHAFT = createWoodenShaft("warped");


    public static final BlockEntry<WoodenCogwheelBlock> OAK_COGWHEEL = createWoodenCogwheel("oak");
    public static final BlockEntry<WoodenCogwheelBlock> BIRCH_COGWHEEL = createWoodenCogwheel("birch");
    public static final BlockEntry<WoodenCogwheelBlock> JUNGLE_COGWHEEL = createWoodenCogwheel("jungle");
    public static final BlockEntry<WoodenCogwheelBlock> ACACIA_COGWHEEL = createWoodenCogwheel("acacia");
    public static final BlockEntry<WoodenCogwheelBlock> DARK_OAK_COGWHEEL = createWoodenCogwheel("dark_oak");
    public static final BlockEntry<WoodenCogwheelBlock> MANGROVE_COGWHEEL = createWoodenCogwheel("mangrove");
    public static final BlockEntry<WoodenCogwheelBlock> CHERRY_COGWHEEL = createWoodenCogwheel("cherry");
    public static final BlockEntry<WoodenCogwheelBlock> BAMBOO_COGWHEEL = createWoodenCogwheel("bamboo");
    public static final BlockEntry<WoodenCogwheelBlock> CRIMSON_COGWHEEL = createWoodenCogwheel("crimson");
    public static final BlockEntry<WoodenCogwheelBlock> WARPED_COGWHEEL = createWoodenCogwheel("warped");

    public static final BlockEntry<WoodenCogwheelBlock> OAK_LARGE_COGWHEEL = createLargeWoodenCogwheel("oak");
    public static final BlockEntry<WoodenCogwheelBlock> BIRCH_LARGE_COGWHEEL = createLargeWoodenCogwheel("birch");
    public static final BlockEntry<WoodenCogwheelBlock> JUNGLE_LARGE_COGWHEEL = createLargeWoodenCogwheel("jungle");
    public static final BlockEntry<WoodenCogwheelBlock> ACACIA_LARGE_COGWHEEL = createLargeWoodenCogwheel("acacia");
    public static final BlockEntry<WoodenCogwheelBlock> DARK_OAK_LARGE_COGWHEEL = createLargeWoodenCogwheel("dark_oak");
    public static final BlockEntry<WoodenCogwheelBlock> MANGROVE_LARGE_COGWHEEL = createLargeWoodenCogwheel("mangrove");
    public static final BlockEntry<WoodenCogwheelBlock> CHERRY_LARGE_COGWHEEL = createLargeWoodenCogwheel("cherry");
    public static final BlockEntry<WoodenCogwheelBlock> BAMBOO_LARGE_COGWHEEL = createLargeWoodenCogwheel("bamboo");
    public static final BlockEntry<WoodenCogwheelBlock> CRIMSON_LARGE_COGWHEEL = createLargeWoodenCogwheel("crimson");
    public static final BlockEntry<WoodenCogwheelBlock> WARPED_LARGE_COGWHEEL = createLargeWoodenCogwheel("warped");

    public static final BlockEntry<GlassShaftBlock> GLASS_SHAFT = REGISTRATE.block("glass_shaft", GlassShaftBlock::new)
            .initialProperties(()-> GLASS)
            .properties(p -> p.mapColor(MapColor.NONE)
                    .sound(SoundType.GLASS)
                    .noOcclusion())
            .transform(BlockStressDefaults.setNoImpact())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
            .register();

    public static final BlockEntry<BrassShaftBlock> BRASS_SHAFT = REGISTRATE.block("brass_shaft", BrassShaftBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .properties(p -> p.mapColor(MapColor.METAL))
            .transform(BlockStressDefaults.setNoImpact())
            .transform(axeOnly())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
                .register();

    public static final BlockEntry<MetalShaftBlock> MLDEG_SHAFT = REGISTRATE.block("mldeg_shaft", MetalShaftBlock::new)
            .initialProperties(()-> Blocks.BLACKSTONE)
            .properties(p -> p.mapColor(MapColor.NONE)
                    .sound(SoundType.STONE)
                    .noOcclusion())
            .transform(BlockStressDefaults.setNoImpact())
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .onRegisterAfter(Registries.ITEM, CreateCasing::hideItem)
            .simpleItem()
            .register();

    public static final BlockEntry<CreativeCogwheelBlock> CREATIVE_COGWHEEL =
            REGISTRATE.block("creative_cogwheel", CreativeCogwheelBlock::new)
                    .initialProperties(SharedProperties::stone)
                    .properties(p -> p.mapColor(MapColor.COLOR_PURPLE))
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .tag(AllTags.AllBlockTags.SAFE_NBT.tag)
                    .transform(pickaxeOnly())
                    .blockstate(new CreativeMotorGenerator()::generate)
                    .transform(BlockStressDefaults.setCapacity(16384.0))
                    .transform(BlockStressDefaults.setGeneratorSpeed(() -> Couple.create(0, 256)))
                    .addLayer(()-> RenderType::cutoutMipped)
                    .item()
                    .properties(p -> p.rarity(Rarity.EPIC))
                    .transform(customItemModel())
                    .register();


    public static <B extends EncasedShaftBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> encasedNoSpriteShaft(String casing) {
        return builder -> encasedBase(builder, AllBlocks.SHAFT::get)
                .blockstate((c, p) -> axisBlock(c, p, blockState -> p.models()
                        .getExistingFile(p.modLoc("block/encased_shaft/block_" + casing)), true))
                .item()
                .model(AssetLookup.customBlockItemModel("encased_shaft", "item_" + casing))
                .build();
    }

    private static <B extends RotatedPillarKineticBlock, P> BlockBuilder<B, P> encasedBase(BlockBuilder<B, P> b, Supplier<ItemLike> drop) {
        return b.initialProperties(SharedProperties::stone)
                .properties(BlockBehaviour.Properties::noOcclusion)
                .transform(BlockStressDefaults.setNoImpact())
                .loot((p, lb) -> p.dropOther(lb, drop.get()));
    }

    public static BlockEntry<CasingBlock> createCasing(String name, CTSpriteShiftEntry connectedTexturesSprite){
        return REGISTRATE.block(name+"_casing", CasingBlock::new)
                .properties(p -> p.mapColor(MapColor.PODZOL))
                .transform(BuilderTransformers.casing(() -> connectedTexturesSprite))
                .simpleItem()
                .register();
    }




    //METHODS
    private static BlockEntry<PublicEncasedShaftBlock> createShaft(String name, Supplier<Block> casing, CTSpriteShiftEntry sprite){
        return REGISTRATE.block(name+"_encased_shaft", p -> new PublicEncasedShaftBlock(p, casing))
                .properties(p -> p.mapColor(MapColor.PODZOL))
                .transform(BuilderTransformers.encasedShaft(name, () -> sprite))
                .transform(EncasingRegistry.addVariantTo(AllBlocks.SHAFT))
                .transform(axeOrPickaxe())
                .onRegisterAfter(Registries.ITEM, CreateCasing::hideItem)
                .register();
    }

    private static BlockEntry<PublicEncasedCogwheelBlock> createCogwheel(String name, Supplier<Block> casing, CTSpriteShiftEntry sprite, CTSpriteShiftEntry sideSprite, CTSpriteShiftEntry otherSideSprite){
        return REGISTRATE.block(name+"_encased_cogwheel", p -> new PublicEncasedCogwheelBlock(p, false, casing))
                .properties(p -> p.mapColor(MapColor.PODZOL))
                .transform(BuilderTransformers.encasedCogwheel(name, () -> sprite))
                .transform(EncasingRegistry.addVariantTo(AllBlocks.COGWHEEL))
                .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCogCTBehaviour(sprite,
                        Couple.create(sideSprite,
                                otherSideSprite))))
                .transform(axeOrPickaxe())
                .onRegisterAfter(Registries.ITEM, CreateCasing::hideItem)
                .register();
    }

    private static BlockEntry<PublicEncasedCogwheelBlock> createLargeCogwheel(String name, Supplier<Block> casing, CTSpriteShiftEntry sprite){
        return REGISTRATE.block(name+"_encased_large_cogwheel", p -> new PublicEncasedCogwheelBlock(p, true, casing))
                .properties(p -> p.mapColor(MapColor.PODZOL))
                .transform(BuilderTransformers.encasedLargeCogwheel(name, () -> sprite))
                .transform(EncasingRegistry.addVariantTo(AllBlocks.LARGE_COGWHEEL))
                .transform(axeOrPickaxe())
                .onRegisterAfter(Registries.ITEM, CreateCasing::hideItem)
                .register();
    }

    private static BlockEntry<PublicEncasedPipeBlock> createPipe(String name, Supplier<Block> casing, CTSpriteShiftEntry sprite){
        return REGISTRATE.block(name+"_encased_fluid_pipe", p -> new PublicEncasedPipeBlock(p, casing))
                .initialProperties(SharedProperties::copperMetal)
                .properties(p -> p.mapColor(MapColor.TERRACOTTA_LIGHT_GRAY))
                .properties(BlockBehaviour.Properties::noOcclusion)
                .transform(axeOrPickaxe())
                .blockstate(BlockStateGen.encasedPipe())
                .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(sprite)))
                .onRegister(CreateRegistrate.casingConnectivity((block, cc) -> cc.make(block, sprite,
                        (s, f) -> !s.getValue(EncasedPipeBlock.FACING_TO_PROPERTY_MAP.get(f)))))
                .onRegister(CreateRegistrate.blockModel(() -> PipeAttachmentModel::new))
                .loot((p, b) -> p.dropOther(b, AllBlocks.FLUID_PIPE.get()))
                .transform(EncasingRegistry.addVariantTo(AllBlocks.FLUID_PIPE))
                .onRegisterAfter(Registries.ITEM, CreateCasing::hideItem)
                .register();
    }

    private static BlockEntry<CustomGearboxBlock> createGearbox(String name, CTSpriteShiftEntry sprite, ItemEntry<CustomVerticalGearboxItem> item){
        return REGISTRATE.block(name+"_gearbox", (p)->new CustomGearboxBlock(p,item))
                .initialProperties(SharedProperties::stone)
                .properties(BlockBehaviour.Properties::noOcclusion)
                .properties(p -> p.mapColor(MapColor.PODZOL))
                .transform(BlockStressDefaults.setNoImpact())
                .transform(axeOrPickaxe())
                .onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(sprite)))
                .onRegister(CreateRegistrate.casingConnectivity((block, cc) -> cc.make(block, sprite,
                        (s, f) -> f.getAxis() == s.getValue(GearboxBlock.AXIS))))
                .blockstate((c, p) -> axisBlock(c, p, $ -> AssetLookup.partialBaseModel(c, p), true))
                .item()
                .transform(customItemModel())
                .register();
    }

    private static BlockEntry<CustomMixerBlock> createMixer(String name){
        return Objects.equals(name, "brass") || Objects.equals(name, "copper") || Objects.equals(name, "railway") ? REGISTRATE.block(name+"_mixer", CustomMixerBlock::new)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.mapColor(MapColor.STONE))
                .properties(BlockBehaviour.Properties::noOcclusion)
                .transform(axeOrPickaxe())
                .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
                .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.createcasing."+name+"_mixer"))
                .addLayer(() -> RenderType::cutoutMipped)
                .transform(BlockStressDefaults.setImpact(4.0))
                .item(AssemblyOperatorBlockItem::new)
                .transform(customItemModel())
                .register()
        :
                REGISTRATE.block(name+"_mixer", CustomMixerBlock::new)
                        .initialProperties(SharedProperties::stone)
                        .properties(p -> p.mapColor(MapColor.STONE))
                        .properties(BlockBehaviour.Properties::noOcclusion)
                        .transform(axeOrPickaxe())
                        .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
                        .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.createcasing.custom_mixer"))
                        .addLayer(() -> RenderType::cutoutMipped)
                        .transform(BlockStressDefaults.setImpact(4.0))
                        .item(AssemblyOperatorBlockItem::new)
                        .transform(customItemModel())
                        .register();
    }

    private static BlockEntry<CustomPressBlock> createPress(String name){
        return REGISTRATE.block(name+"_press", CustomPressBlock::new)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.mapColor(MapColor.PODZOL))
                .properties(BlockBehaviour.Properties::noOcclusion)
                .transform(axeOrPickaxe())
                .blockstate(BlockStateGen.horizontalBlockProvider(true))
                .transform(BlockStressDefaults.setImpact(8.0))
                .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.createcasing.custom_press"))
                .item(AssemblyOperatorBlockItem::new)
                .transform(customItemModel())
                .register();
    }

    private static BlockEntry<WoodenShaftBlock> createWoodenShaft(String name){
        return REGISTRATE.block(name+"_shaft", WoodenShaftBlock::new)
                .initialProperties(SharedProperties::wooden)
                .properties(p -> p.mapColor(MapColor.METAL))
                .transform(BlockStressDefaults.setNoImpact())
                .transform(axeOnly())
                .blockstate(BlockStateGen.axisBlockProvider(false))
                .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
                .simpleItem()
                .register();
    }

    private static BlockEntry<WoodenCogwheelBlock> createWoodenCogwheel(String name) {
        return REGISTRATE.block(name+"_cogwheel", WoodenCogwheelBlock::small)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.sound(SoundType.WOOD).mapColor(MapColor.DIRT))
                .transform(BlockStressDefaults.setNoImpact())
                .transform(axeOrPickaxe())
                .blockstate(BlockStateGen.axisBlockProvider(false))
                .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
                .item(WoodenCogwheelBlockItem::new)
                .build()
                .register();
    }

    private static BlockEntry<WoodenCogwheelBlock> createLargeWoodenCogwheel(String name) {
        return REGISTRATE.block(name+"_large_cogwheel", WoodenCogwheelBlock::large)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.sound(SoundType.WOOD).mapColor(MapColor.DIRT))
                .transform(axeOrPickaxe())
                .transform(BlockStressDefaults.setNoImpact())
                .blockstate(BlockStateGen.axisBlockProvider(false))
                .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
                .item(WoodenCogwheelBlockItem::new)
                .build()
                .register();

    }


    public static BlockEntry<CustomDepotBlock> createDepot(String name){
        return REGISTRATE.block(name+"_depot", CustomDepotBlock::new)
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.mapColor(MapColor.COLOR_GRAY))
                .transform(axeOrPickaxe())
                .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
                .onRegister(assignDataBehaviour(new ItemNameDisplaySource(), "combine_item_names"))
                .item()
                .transform(customItemModel("_", "block"))
                .register();
    }

    public static BlockEntry<CustomChainDriveBlock> createDrive(String name){
        return REGISTRATE.block(name+"_encased_chain_drive", p-> new CustomChainDriveBlock(p,name))
                        .initialProperties(SharedProperties::stone)
                        .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
                        .transform(BlockStressDefaults.setNoImpact())
                        .transform(axeOrPickaxe())
                        .blockstate((c, p) -> new ChainDriveGenerator((state, suffix) -> p.models()
                                .getExistingFile(p.modLoc("block/" + c.getName() + "/" + suffix))).generate(c, p))
                        .item()
                        .transform(customItemModel())
                        .register();

    }

    public static BlockEntry<CustomChainGearshiftBlock> createChainGearshift(String name){
        return REGISTRATE.block(name+ "_adjustable_chain_gearshift", p->new CustomChainGearshiftBlock(p,name))
                .initialProperties(SharedProperties::stone)
                .properties(p -> p.noOcclusion().mapColor(MapColor.NETHER))
                .transform(BlockStressDefaults.setNoImpact())
                .transform(axeOrPickaxe())
                .blockstate((c, p) -> new ChainDriveGenerator((state, suffix) -> {
                    String powered = state.getValue(ChainGearshiftBlock.POWERED) ? "_powered" : "";
                    return p.models()
                            .withExistingParent(c.getName() + "_" + suffix + powered,
                                    p.modLoc("block/encased_chain_drive/"+ name +"/"+ suffix))
                            .texture("side", p.modLoc("block/" + c.getName() + powered));
                }).generate(c, p))
                .item()
                .model((c, p) -> p.withExistingParent(c.getName(), p.modLoc("block/encased_chain_drive/"+name+"/item"))
                        .texture("side", p.modLoc("block/" + c.getName())))
                .build()
                .register();
    }



    public static void registerEncasedShafts() {
        List<BlockEntry<?>> casings = Arrays.asList(AllBlocks.ANDESITE_CASING,AllBlocks.BRASS_CASING,AllBlocks.COPPER_CASING,AllBlocks.RAILWAY_CASING,AllBlocks.REFINED_RADIANCE_CASING,AllBlocks.SHADOW_STEEL_CASING);
        forEachShaft(shaft-> {
            casings.forEach(c-> {
                String casing = c.getId().getPath().replace("_casing", "");
                try {
                    CTSpriteShiftEntry sprite = (CTSpriteShiftEntry) AllSpriteShifts.class.getField(c.getId().getPath().toUpperCase()).get(new CTSpriteShiftEntry(AllCTTypes.OMNIDIRECTIONAL));
                    REGISTRATE.block(casing + "_encased_" + shaft.getId().getPath(), p -> new EncasedCustomShaftBlock(p, (Supplier<Block>) c, shaft))
                            .properties(p -> p.mapColor(MapColor.PODZOL))
                            .transform(BuilderTransformers.encasedShaft(casing, () -> sprite))
                            .transform(EncasingRegistry.addVariantTo(shaft))
                            .transform(axeOrPickaxe())
                            .loot((l,s)->l.dropOther(s,s.getShaft().get().asItem()))
                            .onRegisterAfter(Registries.ITEM, CreateCasing::hideItem)
                            .register();
                } catch (NoSuchFieldException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            });

            REGISTRATE.block("creative_encased_"+shaft.getId().getPath(), p -> new EncasedCustomShaftBlock(p, ModBlocks.CREATIVE_CASING::get,shaft))
                    .properties(p -> p.mapColor(MapColor.PODZOL))
                    .transform(BuilderTransformers.encasedShaft("creative", () -> AllSpriteShifts.CREATIVE_CASING))
                    .transform(EncasingRegistry.addVariantTo(shaft))
                    .transform(axeOrPickaxe())
                    .loot((l,s)->l.dropOther(s,s.getShaft().get().asItem()))
                    .onRegisterAfter(Registries.ITEM, CreateCasing::hideItem)
                    .register();

            REGISTRATE.block("industrial_iron_encased_"+shaft.getId().getPath(), p -> new EncasedCustomShaftBlock(p, AllBlocks.INDUSTRIAL_IRON_BLOCK,shaft))
                    .properties(p -> p.mapColor(MapColor.PODZOL))
                    .transform(encasedNoSpriteShaft("industrial_iron"))
                    .transform(EncasingRegistry.addVariantTo(shaft))
                    .transform(axeOrPickaxe())
                    .loot((l,s)->l.dropOther(s,s.getShaft().get().asItem()))
                    .onRegisterAfter(Registries.ITEM, CreateCasing::hideItem)
                    .register();

        });
        forEachCogwheel(cogwheel-> {
            casings.forEach(c-> {
                String casing = c.getId().getPath().replace("_casing", "");
                try {
                    CTSpriteShiftEntry sprite = (CTSpriteShiftEntry) AllSpriteShifts.class.getField(c.getId().getPath().toUpperCase()).get(new CTSpriteShiftEntry(AllCTTypes.OMNIDIRECTIONAL));
                    REGISTRATE.block(casing + "_encased_" + cogwheel.getId().getPath(), p -> new EncasedCustomCogwheelBlock(p,cogwheel.get().isLarge, (Supplier<Block>) c, cogwheel))
                            .properties(p -> p.mapColor(MapColor.PODZOL))
                            .transform(BuilderTransformers.encasedCogwheel(casing, () -> sprite))
                            .transform(EncasingRegistry.addVariantTo(cogwheel))
                            .transform(axeOrPickaxe())
                            .loot((l,s)->l.dropOther(s,s.getCogwheel().get().asItem()))
                            .onRegisterAfter(Registries.ITEM, CreateCasing::hideItem)
                            .register();
                } catch (NoSuchFieldException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            });

            REGISTRATE.block("creative_encased_"+cogwheel.getId().getPath(), p -> new EncasedCustomCogwheelBlock(p,cogwheel.get().isLarge, ModBlocks.CREATIVE_CASING::get,cogwheel))
                    .properties(p -> p.mapColor(MapColor.PODZOL))
                    .transform(BuilderTransformers.encasedCogwheel("creative", () -> AllSpriteShifts.CREATIVE_CASING))
                    .transform(EncasingRegistry.addVariantTo(cogwheel))
                    .transform(axeOrPickaxe())
                    .loot((l,s)->l.dropOther(s,s.getCogwheel().get().asItem()))
                    .onRegisterAfter(Registries.ITEM, CreateCasing::hideItem)
                    .register();

            REGISTRATE.block("industrial_iron_encased_"+cogwheel.getId().getPath(), p -> new EncasedCustomCogwheelBlock(p,cogwheel.get().isLarge, AllBlocks.INDUSTRIAL_IRON_BLOCK,cogwheel))
                    .properties(p -> p.mapColor(MapColor.PODZOL))
                    .transform(encasedCogwheel("industrial_iron"))
                    .transform(EncasingRegistry.addVariantTo(cogwheel))
                    .transform(axeOrPickaxe())
                    .loot((l,s)->l.dropOther(s,s.getCogwheel().get().asItem()))
                    .onRegisterAfter(Registries.ITEM, CreateCasing::hideItem)
                    .register();

        });
        forEachLargeCogwheel(cogwheel-> {
            casings.forEach(c-> {
                String casing = c.getId().getPath().replace("_casing", "");
                try {
                    CTSpriteShiftEntry sprite = (CTSpriteShiftEntry) AllSpriteShifts.class.getField(c.getId().getPath().toUpperCase()).get(new CTSpriteShiftEntry(AllCTTypes.OMNIDIRECTIONAL));
                    REGISTRATE.block(casing + "_encased_" + cogwheel.getId().getPath(), p -> new EncasedCustomCogwheelBlock(p,cogwheel.get().isLarge, (Supplier<Block>) c, cogwheel))
                            .properties(p -> p.mapColor(MapColor.PODZOL))
                            .transform(BuilderTransformers.encasedCogwheel(casing, () -> sprite))
                            .transform(EncasingRegistry.addVariantTo(cogwheel))
                            .transform(axeOrPickaxe())
                            .loot((l,s)->l.dropOther(s,s.getCogwheel().get().asItem()))
                            .onRegisterAfter(Registries.ITEM, CreateCasing::hideItem)
                            .register();
                } catch (NoSuchFieldException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            });

            REGISTRATE.block("creative_encased_"+cogwheel.getId().getPath(), p -> new EncasedCustomCogwheelBlock(p,cogwheel.get().isLarge, ModBlocks.CREATIVE_CASING::get,cogwheel))
                    .properties(p -> p.mapColor(MapColor.PODZOL))
                    .transform(BuilderTransformers.encasedCogwheel("creative", () -> AllSpriteShifts.CREATIVE_CASING))
                    .transform(EncasingRegistry.addVariantTo(cogwheel))
                    .transform(axeOrPickaxe())
                    .loot((l,s)->l.dropOther(s,s.getCogwheel().get().asItem()))
                    .onRegisterAfter(Registries.ITEM, CreateCasing::hideItem)
                    .register();

            REGISTRATE.block("industrial_iron_encased_"+cogwheel.getId().getPath(), p -> new EncasedCustomCogwheelBlock(p,cogwheel.get().isLarge, AllBlocks.INDUSTRIAL_IRON_BLOCK,cogwheel))
                    .properties(p -> p.mapColor(MapColor.PODZOL))
                    .transform(encasedLargeCogwheel("industrial_iron"))
                    .transform(EncasingRegistry.addVariantTo(cogwheel))
                    .transform(axeOrPickaxe())
                    .loot((l,s)->l.dropOther(s,s.getCogwheel().get().asItem()))
                    .onRegisterAfter(Registries.ITEM, CreateCasing::hideItem)
                    .register();

        });
    }

    public static <B extends EncasedCogwheelBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> encasedCogwheel(
            String casing) {
        return b -> encasedCogwheelBaseNoShift(b, casing, AllBlocks.COGWHEEL::get, false);
    }

    public static <B extends EncasedCogwheelBlock, P> NonNullUnaryOperator<BlockBuilder<B, P>> encasedLargeCogwheel(
            String casing) {
        return b -> encasedCogwheelBaseNoShift(b, casing, AllBlocks.LARGE_COGWHEEL::get, true);
    }

    private static <B extends EncasedCogwheelBlock, P> BlockBuilder<B, P> encasedCogwheelBaseNoShift(BlockBuilder<B, P> b,
                                                                                              String casing, Supplier<ItemLike> drop, boolean large) {
        String encasedSuffix = "_encased_cogwheel_side" + (large ? "_connected" : "");
        String blockFolder = large ? "encased_large_cogwheel" : "encased_cogwheel";
        String wood = casing.equals("brass") ? "dark_oak" : "spruce";
        String gearbox = casing.equals("brass") ? "brass_gearbox" : "gearbox";
        return encasedBase(b, drop).addLayer(() -> RenderType::cutoutMipped)
                .blockstate((c, p) -> axisBlock(c, p, blockState -> {
                    String suffix = (blockState.getValue(EncasedCogwheelBlock.TOP_SHAFT) ? "_top" : "")
                            + (blockState.getValue(EncasedCogwheelBlock.BOTTOM_SHAFT) ? "_bottom" : "");
                    String modelName = c.getName() + suffix;
                    return p.models()
                            .withExistingParent(modelName, p.modLoc("block/" + blockFolder + "/block" + suffix))
                            .texture("casing", Create.asResource("block/" + casing + "_casing"))
                            .texture("particle", Create.asResource("block/" + casing + "_casing"))
                            .texture("4", Create.asResource("block/" + gearbox))
                            .texture("1", new ResourceLocation("block/stripped_" + wood + "_log_top"))
                            .texture("side", Create.asResource("block/" + casing + encasedSuffix));
                }, false))
                .item()
                .model((c, p) -> p.withExistingParent(c.getName(), p.modLoc("block/" + blockFolder + "/item"))
                        .texture("casing", Create.asResource("block/" + casing + "_casing"))
                        .texture("particle", Create.asResource("block/" + casing + "_casing"))
                        .texture("1", new ResourceLocation("block/stripped_" + wood + "_log_top"))
                        .texture("side", Create.asResource("block/" + casing + encasedSuffix)))
                .build();
    }

    public static void register() {

    }

    public static boolean isWoodenShaftHasState(BlockState state) {

        return OAK_SHAFT.has(state) ||
                SPRUCE_SHAFT.has(state) ||
                BIRCH_SHAFT.has(state) ||
                JUNGLE_SHAFT.has(state) ||
                ACACIA_SHAFT.has(state) ||
                DARK_OAK_SHAFT.has(state) ||
                MANGROVE_SHAFT.has(state) ||
                BAMBOO_SHAFT.has(state) ||
                CHERRY_SHAFT.has(state) ||
                CRIMSON_SHAFT.has(state) ||
                WARPED_SHAFT.has(state);

    }

    public static void forEachShaft(Consumer<BlockEntry<? extends ShaftBlock>> action){
        action.accept(OAK_SHAFT);
        action.accept(BIRCH_SHAFT);
        action.accept(SPRUCE_SHAFT);
        action.accept(JUNGLE_SHAFT);
        action.accept(ACACIA_SHAFT);
        action.accept(DARK_OAK_SHAFT);
        action.accept(MANGROVE_SHAFT);
        action.accept(BAMBOO_SHAFT);
        action.accept(CHERRY_SHAFT);
        action.accept(WARPED_SHAFT);
        action.accept(CRIMSON_SHAFT);
        action.accept(GLASS_SHAFT);
        action.accept(MLDEG_SHAFT);
    }

    public static void forEachCogwheel(Consumer<BlockEntry<? extends WoodenCogwheelBlock>> action){
        action.accept(OAK_COGWHEEL);
        action.accept(BIRCH_COGWHEEL);
        action.accept(JUNGLE_COGWHEEL);
        action.accept(ACACIA_COGWHEEL);
        action.accept(DARK_OAK_COGWHEEL);
        action.accept(MANGROVE_COGWHEEL);
        action.accept(BAMBOO_COGWHEEL);
        action.accept(CHERRY_COGWHEEL);
        action.accept(WARPED_COGWHEEL);
        action.accept(CRIMSON_COGWHEEL);
    }

    public static void forEachLargeCogwheel(Consumer<BlockEntry<? extends WoodenCogwheelBlock>> action){
        action.accept(OAK_LARGE_COGWHEEL);
        action.accept(BIRCH_LARGE_COGWHEEL);
        action.accept(JUNGLE_LARGE_COGWHEEL);
        action.accept(ACACIA_LARGE_COGWHEEL);
        action.accept(DARK_OAK_LARGE_COGWHEEL);
        action.accept(MANGROVE_LARGE_COGWHEEL);
        action.accept(BAMBOO_LARGE_COGWHEEL);
        action.accept(CHERRY_LARGE_COGWHEEL);
        action.accept(WARPED_LARGE_COGWHEEL);
        action.accept(CRIMSON_LARGE_COGWHEEL);
    }
}

package com.lex_eden.fantasy_tavern.data;

import com.lex_eden.fantasy_tavern.FANTASY_TAVERN;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class fumoblocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, FANTASY_TAVERN.MOD_ID);

    public static final RegistryObject<Block> SARA_EDEN_FUMO =
            registerBlocks("sara_eden_fumo",()-> new Block(BlockBehaviour.Properties.of().strength(3.0F, 2.0F).noOcclusion()));

    public static final RegistryObject<Block> LEX_EDEN_FUMO =
            registerBlocks("lex_eden_fumo",()-> new Block(BlockBehaviour.Properties.of().strength(3.0F, 2.0F).noOcclusion()));

    public static final RegistryObject<Block> TIREZ_EDEN_FUMO =
            registerBlocks("tirez_eden_fumo",()-> new Block(BlockBehaviour.Properties.of().strength(3.0F, 2.0F).noOcclusion()));

    public static final RegistryObject<Block> HYSLER_FUMO =
            registerBlocks("hysler_fumo",()-> new Block(BlockBehaviour.Properties.of().strength(3.0F, 2.0F).noOcclusion()));

    public static final RegistryObject<Block> HANA_FUMO =
            registerBlocks("hana_fumo",()-> new Block(BlockBehaviour.Properties.of().strength(3.0F, 2.0F).noOcclusion()));

    public static final RegistryObject<Block> WATER_FUMO =
            registerBlocks("water_fumo",()-> new Block(BlockBehaviour.Properties.of().strength(3.0F, 2.0F).noOcclusion()));

    private static <T extends Block> void registerBlockItems(String name,RegistryObject<T> block){
        Drinkitems.ITEMS.register(name,()->new BlockItem(block.get(),new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> registerBlocks(String name, Supplier<T> block){
        RegistryObject<T> blocks = BLOCKS.register(name,block);
        registerBlockItems(name, blocks);
        return blocks;
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}

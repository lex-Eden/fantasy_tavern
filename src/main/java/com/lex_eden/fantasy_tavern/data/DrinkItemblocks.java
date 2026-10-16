package com.lex_eden.fantasy_tavern.data;

import com.github.ysbbbbbb.kaleidoscopetavern.block.mixology.CocktailBlock;
import com.lex_eden.fantasy_tavern.FANTASY_TAVERN;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DrinkItemblocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, FANTASY_TAVERN.MOD_ID);

    public static final RegistryObject<Block> SATSUKI_RIN =
            BLOCKS.register("satsuki_rin", CocktailBlock::new);

    public static final RegistryObject<Block> ENCHANTED_BERRY_JUICE =
            BLOCKS.register("enchant_berry_juice", CocktailBlock::new);

    public static final RegistryObject<Block> NORDIC_MIST_TALK =
            BLOCKS.register("nordic_mist_talk", CocktailBlock::new);

    public static final RegistryObject<Block> NORDIC_LEGENDS =
            BLOCKS.register("nordic_legends", CocktailBlock::new);

    public static final RegistryObject<Block> HEIMDALL_BREW =
            BLOCKS.register("heimdall_brew", CocktailBlock::new);

    public static final RegistryObject<Block> KOMEIJI_KOISHI =
            BLOCKS.register("komeiji_koishi", CocktailBlock::new);

    public static final RegistryObject<Block> SAKUYA =
            BLOCKS.register("sakuya", CocktailBlock::new);

    public static final RegistryObject<Block> ILLUSIONARY_NIGHT =
            BLOCKS.register("illusionary_night", CocktailBlock::new);

    public static final RegistryObject<Block> DREAM_OF_HUAXU =
            BLOCKS.register("shanghai_tea", CocktailBlock::new);

    public static final RegistryObject<Block> THE_PUREST_SKY_AND_SEA =
            BLOCKS.register("the_purest_sky_and_sea", CocktailBlock::new);

    public static final RegistryObject<Block> CELESTIAL_WIZARDR =
            BLOCKS.register("celestial_wizardr", CocktailBlock::new);

    public static final RegistryObject<Block> BELL_OF_AVICI =
            BLOCKS.register("bell_of_avici", CocktailBlock::new);

    public static final RegistryObject<Block> EMOTIONAL_SKYSCRAPER =
            BLOCKS.register("emotional_skyscraper", CocktailBlock::new);

    public static final RegistryObject<Block> WIND_OF_AGARTHA =
            BLOCKS.register("wind_of_agartha", CocktailBlock::new);

    public static final RegistryObject<Block> THE_SEA_THAT_REFLECTS_ONE_S_HOME_PLANET =
            BLOCKS.register("the_sea_that_reflects_one_s_home_planet", CocktailBlock::new);

    public static final RegistryObject<Block> THE_LOST_EMOTION =
            BLOCKS.register("the_lost_emotion", CocktailBlock::new);

    public static final RegistryObject<Block> ILLUSIONARY_WHITE_TRAVELER =
            BLOCKS.register("illusionary_white_traveler", CocktailBlock::new);

    public static final RegistryObject<Block> MUGEN_NOH =
            BLOCKS.register("mugen_noh", CocktailBlock::new);

    public static final RegistryObject<Block> ENTRUST_THE_WORLD_TO_IDOLS =
            BLOCKS.register("entrust_the_world_to_idols", CocktailBlock::new);


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
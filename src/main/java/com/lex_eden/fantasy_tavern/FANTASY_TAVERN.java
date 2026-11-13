package com.lex_eden.fantasy_tavern;

import com.lex_eden.fantasy_tavern.blocks.DrinkItemblocks;
import com.lex_eden.fantasy_tavern.blocks.Coreblocks;
import com.lex_eden.fantasy_tavern.data.*;
import com.lex_eden.fantasy_tavern.effect.ModEffects;
import com.lex_eden.fantasy_tavern.items.Coreitems;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

@Mod(FANTASY_TAVERN.MOD_ID)
public class FANTASY_TAVERN
{
    public static final String MOD_ID = "fantasy_tavern";
    private static final Logger LOGGER = LogUtils.getLogger();

    public FANTASY_TAVERN(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);
        Coreitems.register(modEventBus);
        CreateModeTabs.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        DrinkItemblocks.register(modEventBus);
        Coreblocks.register(modEventBus);
        SoundEvents.register(modEventBus);
        ModEffects.register(modEventBus);
        modEventBus.addListener(this::addCreative);
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreateModeTabs.FANTASY_TAVERN_TAB.getKey()){
            // 物品
            event.accept(Coreitems.ENCHANTED_BERRY);
            event.accept(Coreitems.LUCKY_BERRY);
            // 饮品
            event.accept(Coreitems.KOMEIJI_KOISHI);
            event.accept(Coreitems.THE_PUREST_SKY_AND_SEA);
            event.accept(Coreitems.ILLUSIONARY_NIGHT);
            event.accept(Coreitems.DREAM_OF_HUAXU);
            event.accept(Coreitems.THE_PUREST_SKY_AND_SEA);
            event.accept(Coreitems.CELESTIAL_WIZARDR);
            event.accept(Coreitems.BELL_OF_AVICI);
            event.accept(Coreitems.EMOTIONAL_SKYSCRAPER);
            event.accept(Coreitems.WIND_OF_AGARTHA);
            event.accept(Coreitems.THE_SEA_THAT_REFLECTS_ONE_S_HOME_PLANET);
            event.accept(Coreitems.THE_LOST_EMOTION);
            event.accept(Coreitems.ILLUSIONARY_WHITE_TRAVELER);
            event.accept(Coreitems.MUGEN_NOH);
            event.accept(Coreitems.ENTRUST_THE_WORLD_TO_IDOLS);
            event.accept(Coreitems.SATSUKI_RIN);
            event.accept(Coreitems.SAKUYA);
            event.accept(Coreitems.YUYUKO);
            event.accept(Coreitems.KAGUYA);
            event.accept(Coreitems.FOURTEENTH_NIGHT);
            event.accept(Coreitems.REMILIA);
            event.accept(Coreitems.TENSHI);
        }
        {
            if (event.getTabKey() == CreateModeTabs.FANTASY_TAVERN_TAB_OTHER.getKey()){
                // 唱片
                event.accept(Coreitems.MUSIC_KID_FESTIVAL);
                event.accept(Coreitems.MUSIC_BOYS_AND_GIRLS_OF_THE_AGE_OF_SCIENCE);
                // fumo
                event.accept(Coreblocks.SARA_EDEN_FUMO);
                event.accept(Coreblocks.LEX_EDEN_FUMO);
                event.accept(Coreblocks.TIREZ_EDEN_FUMO);
                event.accept(Coreblocks.HYSLER_FUMO);
                event.accept(Coreblocks.HANA_FUMO);
                event.accept(Coreblocks.WATER_FUMO);
                event.accept(Coreitems.EDEN_BOX);
            }
        }
    }



    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}

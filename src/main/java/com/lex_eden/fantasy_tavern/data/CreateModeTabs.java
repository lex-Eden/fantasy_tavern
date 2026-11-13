package com.lex_eden.fantasy_tavern.data;


import com.lex_eden.fantasy_tavern.blocks.Coreblocks;
import com.lex_eden.fantasy_tavern.FANTASY_TAVERN;
import com.lex_eden.fantasy_tavern.items.Coreitems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreateModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB,  FANTASY_TAVERN.MOD_ID);

    public static final RegistryObject<CreativeModeTab> FANTASY_TAVERN_TAB =
            CREATIVE_MODE_TABS.register("fantasy_tavern_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Coreitems.ENCHANTED_BERRY.get()))
                    .title(Component.translatable("itemGroup.fantasy_tavern_tab"))
                    .displayItems((parameters, output) -> {
                        // 物品

                        output.accept(Coreitems.ENCHANTED_BERRY.get());
                        output.accept(Coreitems.LUCKY_BERRY.get());
                        // 鸡尾酒饮品

                        output.accept(Coreitems.SATSUKI_RIN.get());
                        output.accept(Coreitems.SAKUYA.get());
                        output.accept(Coreitems.YUYUKO.get());
                        output.accept(Coreitems.KAGUYA.get());
                        output.accept(Coreitems.FOURTEENTH_NIGHT.get());
                        output.accept(Coreitems.KOMEIJI_KOISHI.get());
                        output.accept(Coreitems.REMILIA.get());
                        output.accept(Coreitems.TENSHI.get());

                        output.accept(Coreitems.ILLUSIONARY_NIGHT.get());
                        output.accept(Coreitems.DREAM_OF_HUAXU.get());
                        output.accept(Coreitems.THE_PUREST_SKY_AND_SEA.get());
                        output.accept(Coreitems.CELESTIAL_WIZARDR.get());
                        output.accept(Coreitems.BELL_OF_AVICI.get());
                        output.accept(Coreitems.EMOTIONAL_SKYSCRAPER.get());
                        output.accept(Coreitems.WIND_OF_AGARTHA.get());
                        output.accept(Coreitems.THE_SEA_THAT_REFLECTS_ONE_S_HOME_PLANET.get());
                        output.accept(Coreitems.THE_LOST_EMOTION.get());
                        output.accept(Coreitems.ILLUSIONARY_WHITE_TRAVELER.get());
                        output.accept(Coreitems.MUGEN_NOH.get());
                        output.accept(Coreitems.ENTRUST_THE_WORLD_TO_IDOLS.get());
                    })
                    .build());

    public static final RegistryObject<CreativeModeTab> FANTASY_TAVERN_TAB_OTHER =
            CREATIVE_MODE_TABS.register("fantasy_tavern_tab_other", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Coreitems.EDEN_BOX.get()))
                    .title(Component.translatable("itemGroup.fantasy_tavern_tab_other"))
                    .displayItems((parameters, output) -> {
                        //唱片
                        output.accept(Coreitems.MUSIC_KID_FESTIVAL.get());
                        output.accept(Coreitems.MUSIC_BOYS_AND_GIRLS_OF_THE_AGE_OF_SCIENCE.get());
                        //玩偶
                        output.accept(Coreblocks.HYSLER_FUMO.get());
                        output.accept(Coreblocks.HANA_FUMO.get());
                        output.accept(Coreblocks.WATER_FUMO.get());
                        output.accept(Coreblocks.LEX_EDEN_FUMO.get());
                        output.accept(Coreblocks.TIREZ_EDEN_FUMO.get());
                        output.accept(Coreblocks.SARA_EDEN_FUMO.get());
                        output.accept(Coreitems.EDEN_BOX.get());

                    })
                    .build());
    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}

package com.lex_eden.fantasy_tavern.data;


import com.lex_eden.fantasy_tavern.FANTASY_TAVERN;
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
                    .icon(() -> new ItemStack(Drinkitems.ENCHANTED_BERRY.get()))
                    .title(Component.translatable("itemGroup.fantasy_tavern_tab"))
                    .displayItems((parameters, output) -> {
                        // 物品

                        output.accept(Drinkitems.ENCHANTED_BERRY.get());
                        output.accept(Drinkitems.LUCKY_BERRY.get());
                        // 鸡尾酒饮品
                        output.accept(Drinkitems.ENCHANTED_BERRY_JUICE.get());

                        output.accept(Drinkitems.SATSUKI_RIN.get());
                        output.accept(Drinkitems.SAKUYA.get());
                        output.accept(Drinkitems.YUYUKO.get());
                        output.accept(Drinkitems.KAGUYA.get());
                        output.accept(Drinkitems.FOURTEENTH_NIGHT.get());
                        output.accept(Drinkitems.KOMEIJI_KOISHI.get());

                        output.accept(Drinkitems.ILLUSIONARY_NIGHT.get());
                        output.accept(Drinkitems.DREAM_OF_HUAXU.get());
                        output.accept(Drinkitems.THE_PUREST_SKY_AND_SEA.get());
                        output.accept(Drinkitems.CELESTIAL_WIZARDR.get());
                        output.accept(Drinkitems.BELL_OF_AVICI.get());
                        output.accept(Drinkitems.EMOTIONAL_SKYSCRAPER.get());
                        output.accept(Drinkitems.WIND_OF_AGARTHA.get());
                        output.accept(Drinkitems.THE_SEA_THAT_REFLECTS_ONE_S_HOME_PLANET.get());
                        output.accept(Drinkitems.THE_LOST_EMOTION.get());
                        output.accept(Drinkitems.ILLUSIONARY_WHITE_TRAVELER.get());
                        output.accept(Drinkitems.MUGEN_NOH.get());
                        output.accept(Drinkitems.ENTRUST_THE_WORLD_TO_IDOLS.get());
                    })
                    .build());

    public static final RegistryObject<CreativeModeTab> FANTASY_TAVERN_TAB_OTHER =
            CREATIVE_MODE_TABS.register("fantasy_tavern_tab_other", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(Drinkitems.EDEN_BOX.get()))
                    .title(Component.translatable("itemGroup.fantasy_tavern_tab_other"))
                    .displayItems((parameters, output) -> {
                        //唱片
                        output.accept(Drinkitems.MUSIC_KID_FESTIVAL.get());
                        output.accept(Drinkitems.MUSIC_BOYS_AND_GIRLS_OF_THE_AGE_OF_SCIENCE.get());
                        //玩偶
                        output.accept(fumoblocks.HYSLER_FUMO.get());
                        output.accept(fumoblocks.HANA_FUMO.get());
                        output.accept(fumoblocks.WATER_FUMO.get());
                        output.accept(fumoblocks.LEX_EDEN_FUMO.get());
                        output.accept(fumoblocks.TIREZ_EDEN_FUMO.get());
                        output.accept(fumoblocks.SARA_EDEN_FUMO.get());
                        output.accept(Drinkitems.EDEN_BOX.get());

                    })
                    .build());
    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}

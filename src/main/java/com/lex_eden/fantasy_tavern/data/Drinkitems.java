package com.lex_eden.fantasy_tavern.data;

import com.github.ysbbbbbb.kaleidoscopetavern.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopetavern.item.CocktailBlockItem;
import com.github.ysbbbbbb.kaleidoscopetavern.item.DrinkBlockItem;
import com.lex_eden.fantasy_tavern.FANTASY_TAVERN;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class Drinkitems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, FANTASY_TAVERN.MOD_ID);
    // 唱片注册
    public static final RegistryObject<Item> MUSIC_KID_FESTIVAL =
            ITEMS.register("music_kid_festival",()-> new RecordItem(15, SoundEvents.SOUND_KID_FESTIVAL, (new Item.Properties()).stacksTo(1).rarity(Rarity.RARE), 309 * 20));
    public static final RegistryObject<Item> MUSIC_BOYS_AND_GIRLS_OF_THE_AGE_OF_SCIENCE =
            ITEMS.register("music_boys_and_girls_of_the_age_of_science",()-> new RecordItem(15, SoundEvents.SOUND_BOYS_AND_GIRLS_OF_THE_AGE_OF_SCIENCE, (new Item.Properties()).stacksTo(1).rarity(Rarity.RARE), 204 * 20));
    // 物品注册
    public static final RegistryObject<Item> WHEET_FLOUR=
            ITEMS.register("wheet_flour",()-> new Item((new Item.Properties())));
    public static final RegistryObject<Item> YEAST =
            ITEMS.register("yeast",()-> new Item((new Item.Properties())));
    public static final RegistryObject<Item> WINE_GLASS=
            ITEMS.register("wine_glass",()-> new Item((new Item.Properties())));
    public static final RegistryObject<Item> EDEN_BOX=
            ITEMS.register("eden_box",()-> new BlindBox((new Item.Properties())));
    // 物品注册-浆果

    // 基础食物注册
    public static final RegistryObject<Item> ENCHANTED_BERRY=
            ITEMS.register("enchanted_berry",()-> (new EnchantedGoldenAppleItem(new Item.Properties().food(foods.ENCHANTED_BERRY).rarity(Rarity.EPIC))));
    public static final RegistryObject<Item> LUCKY_BERRY=
            ITEMS.register("lucky_berry",()-> (new Item(new Item.Properties().food(foods.LUCKY_BERRY).rarity(Rarity.RARE))));
    // 饮品注册-基础底子
    // 基础底子饱食度，统一3，eden_box.json.2f
    public static final RegistryObject<Item> TEQUILA =
            ITEMS.register("tequila",()-> new DrinkablePotionItem(
                    (new Item.Properties()).stacksTo(1),
                    List.of(), 3, 1.2f));

    public static final RegistryObject<Item> WHISKEY =
            ITEMS.register("whiskey",()-> new DrinkablePotionItem(
                    (new Item.Properties()).stacksTo(1),
                    List.of(), 3, 1.2f));

    public static final RegistryObject<Item> BRANDY =
            ITEMS.register("brandy",()-> new DrinkablePotionItem(
                    (new Item.Properties()).stacksTo(1),
                    List.of(), 3, 1.2f));
    // 饮品-鸡尾酒特调
    // 效果应该遵循以下物品给予
    // 发光浆果：夜视
    // 浆果：生命恢复，幸运
    // 附魔浆果：生命恢复，幸运，生命提升，伤害吸收
    // 现实饮品
    // 普通的饱食度 5 6.0F
    // 少见的饱食度 5 7.2F
    // 稀有的饱食度 6 9.6F
    // 史诗的饱食度 可自定义
    // 玛格丽特
    public static final RegistryObject<Item> MARGARET =
            ITEMS.register("margaret",()-> new DrinkablePotionItem(
                    (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON),
                    List.of(), 5, 7.2f));
    // 曼哈顿
    public static final RegistryObject<Item> MANHATTAN =
            ITEMS.register("manhattan",()-> new DrinkablePotionItem(
                    (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON),
                    List.of(), 5, 7.2f));
    // 鸡尾酒

    //附魔浆果汁
    public static final RegistryObject<Item> ENCHANTED_BERRY_JUICE =
            ITEMS.register("enchant_berry_juice",()-> new CocktailBlockItem(DrinkItemblocks.ENCHANTED_BERRY_JUICE.get()));

    // 其他饮品

    //古明地恋
    public static final RegistryObject<Item> KOMEIJI_KOISHI =
            ITEMS.register("komeiji_koishi",()-> new CocktailBlockItem(DrinkItemblocks.KOMEIJI_KOISHI.get()));
    //冴月
    public static final RegistryObject<Item> SATSUKI_RIN =
            ITEMS.register("satsuki_rin",()-> new CocktailBlockItem(DrinkItemblocks.SATSUKI_RIN.get()));
    //十六夜
    public static final RegistryObject<Item> SAKUYA =
            ITEMS.register("sakuya",()-> new CocktailBlockItem(DrinkItemblocks.SAKUYA.get()));

    //十四夜
    public static final RegistryObject<Item> FOURTEENTH_NIGHT =
            ITEMS.register("fourteenth_night",()-> new DrinkBlockItem(DrinkItemblocks.FOURTEENTH_NIGHT.get()));
    //樱葬
    public static final RegistryObject<Item> YUYUKO =
            ITEMS.register("yuyuko", () -> new DrinkBlockItem(DrinkItemblocks.YUYUKO.get()));
    //竹林月光
    public static final RegistryObject<Item> KAGUYA =
            ITEMS.register("kaguya",()-> new DrinkBlockItem(DrinkItemblocks.KAGUYA.get()));
    //虚幻之樱


    //幻视之夜
    public static final RegistryObject<Item> ILLUSIONARY_NIGHT =
            ITEMS.register("illusionary_night",()-> new CocktailBlockItem(DrinkItemblocks.ILLUSIONARY_NIGHT.get()));
    //上海红茶
    public static final RegistryObject<Item> DREAM_OF_HUAXU =
            ITEMS.register("dream_of_huaxu",()-> new CocktailBlockItem(DrinkItemblocks.DREAM_OF_HUAXU.get()));
    //最澄澈的空与海
    public static final RegistryObject<Item> THE_PUREST_SKY_AND_SEA =
            ITEMS.register("the_purest_sky_and_sea",()-> new CocktailBlockItem(DrinkItemblocks.THE_PUREST_SKY_AND_SEA.get()));
    //太空魔术
    public static final RegistryObject<Item> CELESTIAL_WIZARDR =
            ITEMS.register("celestial_wizardr",()-> new CocktailBlockItem(DrinkItemblocks.CELESTIAL_WIZARDR.get()));
    //无间之钟
    public static final RegistryObject<Item> BELL_OF_AVICI =
            ITEMS.register("bell_of_avici",()-> new CocktailBlockItem(DrinkItemblocks.BELL_OF_AVICI.get()));
    //感情的摩天轮
    public static final RegistryObject<Item> EMOTIONAL_SKYSCRAPER =
            ITEMS.register("emotional_skyscraper",()-> new CocktailBlockItem(DrinkItemblocks.EMOTIONAL_SKYSCRAPER.get()));
    //阿加尔塔之风
    public static final RegistryObject<Item> WIND_OF_AGARTHA =
            ITEMS.register("wind_of_agartha",()-> new CocktailBlockItem(DrinkItemblocks.WIND_OF_AGARTHA.get()));
    //故乡之星倒映之海
    public static final RegistryObject<Item> THE_SEA_THAT_REFLECTS_ONE_S_HOME_PLANET =
            ITEMS.register("the_sea_that_reflects_one_s_home_planet",()-> new CocktailBlockItem(DrinkItemblocks.THE_SEA_THAT_REFLECTS_ONE_S_HOME_PLANET.get()));
    //亡失感情
    public static final RegistryObject<Item> THE_LOST_EMOTION =
            ITEMS.register("the_lost_emotion",()-> new CocktailBlockItem(DrinkItemblocks.THE_LOST_EMOTION.get()));
    //幻想的白色旅人
    public static final RegistryObject<Item> ILLUSIONARY_WHITE_TRAVELER =
            ITEMS.register("illusionary_white_traveler",()-> new CocktailBlockItem(DrinkItemblocks.ILLUSIONARY_WHITE_TRAVELER.get()));
    //梦幻能
    public static final RegistryObject<Item> MUGEN_NOH =
            ITEMS.register("mugen_noh",()-> new CocktailBlockItem(DrinkItemblocks.MUGEN_NOH.get()));
    //寄世界于偶像
    public static final RegistryObject<Item> ENTRUST_THE_WORLD_TO_IDOLS =
            ITEMS.register("entrust_the_world_to_idols",()-> new CocktailBlockItem(DrinkItemblocks.ENTRUST_THE_WORLD_TO_IDOLS.get()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}



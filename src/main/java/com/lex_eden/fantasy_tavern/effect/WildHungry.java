package com.lex_eden.fantasy_tavern.effect;

import com.lex_eden.fantasy_tavern.FANTASY_TAVERN;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FANTASY_TAVERN.MOD_ID)
public class WildHungry extends MobEffect {
    public WildHungry(MobEffectCategory category, int color) {
        super(category, color);
    }
@SubscribeEvent
public static void onStartUsingItem(LivingEntityUseItemEvent.Start event) {
    if (!(event.getEntity() instanceof Player player)) {
        return;
    }

    if (!player.hasEffect(ModEffects.WILD_HUNGRY.get())) {
        return;
    }

    ItemStack itemStack = event.getItem();
    FoodProperties foodProps = itemStack.getFoodProperties(player);
    if (foodProps == null) {
        return;
    }

    event.setDuration(1);
    }
}
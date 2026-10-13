package com.lex_eden.fantasy_tavern.data;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodConstants;
import net.minecraft.world.food.FoodProperties;


public class foods {
    public static final FoodProperties ENCHANTED_BERRY = (new FoodProperties.Builder()).nutrition(6).saturationMod(9.6F).effect(new MobEffectInstance(MobEffects.LUCK,600 * 20,0),1.0F).effect(new MobEffectInstance(MobEffects.REGENERATION,120 * 20,2),1.0F).effect(new MobEffectInstance(MobEffects.HEALTH_BOOST,300 * 20,4),1.0F).effect(new MobEffectInstance(MobEffects.REGENERATION,1 * 20,5),1.0F).effect(new MobEffectInstance(MobEffects.ABSORPTION, 60 * 20, 2), 1.0F).alwaysEat().build();
    public static final FoodProperties LUCKY_BERRY = (new FoodProperties.Builder()).nutrition(4).saturationMod(4.8F).effect(new MobEffectInstance(MobEffects.LUCK,60 * 20,4),1.0F).alwaysEat().build();
}

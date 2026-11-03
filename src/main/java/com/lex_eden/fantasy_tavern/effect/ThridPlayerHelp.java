package com.lex_eden.fantasy_tavern.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;


public class ThridPlayerHelp extends MobEffect {
    public ThridPlayerHelp(MobEffectCategory category, int color) {
        super(category, color);
    }

    public void playerEffectGet(LivingEntity LivingEntity) {
        if (this == ModEffects.THIRD_PLAYER_HELP.get()) {
            List<MobEffectInstance> effects = List.copyOf(LivingEntity.getActiveEffects());
            for (MobEffectInstance current : effects) {
                MobEffect effect = current.getEffect();
                int amplifier = current.getAmplifier();
                int nextAmplifier = Math.min(amplifier + 1,32767);

                LivingEntity.addEffect(new MobEffectInstance(
                        effect,
                        current.getDuration(),
                        nextAmplifier,
                        current.isAmbient(),
                        current.isVisible(),
                        current.showIcon()
                ));
            }
        }
    }
}

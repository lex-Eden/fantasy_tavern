package com.lex_eden.fantasy_tavern.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

    public class ThridPlayerHelp extends MobEffect {
    public ThridPlayerHelp(MobEffectCategory category, int color) {
    super(category, color);
}

    @Override
    public boolean isDurationEffectTick(int duration, int level) {

        return duration % 20 == 0;
    }
    @Override
    public void applyEffectTick(LivingEntity LivingEntity, int level) {
        if (this == ModEffects.THIRD_PLAYER_HELP.get()) {
            if (LivingEntity.getHealth() < LivingEntity.getMaxHealth()) {
            LivingEntity.heal(1.5F);
            }
        }
    }
}

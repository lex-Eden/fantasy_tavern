package com.lex_eden.fantasy_tavern.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import java.util.List;
import java.util.function.Predicate;

public class ThridPlayer extends MobEffect {
    public ThridPlayer(MobEffectCategory category, int color) {
        super(category, color);
    }
    @Override
    public boolean isDurationEffectTick(int duration, int level) {
        return duration % 20 == 0;
    }
    @Override
    public void applyEffectTick(LivingEntity LivingEntity, int amplifier) {
        if (this == ModEffects.THIRD_PLAYER.get()) {
            if (LivingEntity.getHealth() < LivingEntity.getMaxHealth()) {
                LivingEntity.heal(2.0F);
            }
        }
        Level level = LivingEntity.level();
        double radius = Math.min(amplifier + 1, 1) * 6;
        AABB range = LivingEntity.getBoundingBox().inflate(radius);
        Predicate<LivingEntity> filter = entity -> entity != LivingEntity && entity.isAlive();
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, range, filter);
        if (entities.isEmpty()) {
            return;
        }
        boolean apply = false;
        for (LivingEntity entity : entities) {
            if (!entity.hasEffect(ModEffects.THIRD_PLAYER_HELP.get())) {
                apply = true;
            }
            entity.addEffect(new MobEffectInstance(ModEffects.THIRD_PLAYER_HELP.get(), 40));
        }
    }
}


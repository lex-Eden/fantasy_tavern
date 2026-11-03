package com.lex_eden.fantasy_tavern.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
    public void applyEffectTick(LivingEntity OwnerPlayer, int amplifier) {
        if (this == ModEffects.THIRD_PLAYER.get()) {
            if (OwnerPlayer.getHealth() < OwnerPlayer.getMaxHealth()) {
                OwnerPlayer.heal(2.0F);
            }
        }
        Level level = OwnerPlayer.level();
        double radius = Math.min(amplifier + 1, 1) * 6;
        AABB range = OwnerPlayer.getBoundingBox().inflate(radius);
        Predicate<LivingEntity> filter = entity -> entity != OwnerPlayer && entity.isAlive() && entity instanceof Player;;
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, range, filter);
        if (entities.isEmpty()) {
            return;
        }for (LivingEntity entity : entities) {
        List<MobEffectInstance> effects = List.copyOf(OwnerPlayer.getActiveEffects());
            for (MobEffectInstance current : effects) {
                MobEffect effect = current.getEffect();
                if (effect == ModEffects.THIRD_PLAYER.get()
                        || effect.getCategory() != MobEffectCategory.BENEFICIAL) {
                    continue;
                }
                entity.addEffect(new MobEffectInstance(
                        current.getEffect(),
                        60,
                        current.getAmplifier(),
                        current.isAmbient(),
                        current.isVisible(),
                        current.showIcon()));
            }
        }
    }
}



package com.lex_eden.fantasy_tavern.effect;

import com.lex_eden.fantasy_tavern.FANTASY_TAVERN;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FANTASY_TAVERN.MOD_ID)
public class Scarlett extends MobEffect {
    public Scarlett(MobEffectCategory category, int color) {
        super(category, color);
    }
}
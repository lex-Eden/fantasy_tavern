package com.lex_eden.fantasy_tavern.effect;

import com.lex_eden.fantasy_tavern.FANTASY_TAVERN;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, FANTASY_TAVERN.MOD_ID);


    public static final RegistryObject<MobEffect> WILD_HUNGRY =
            MOB_EFFECTS.register("wild_hungry",
                    () -> new WildHungry(MobEffectCategory.BENEFICIAL, 0xFCA8B8));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
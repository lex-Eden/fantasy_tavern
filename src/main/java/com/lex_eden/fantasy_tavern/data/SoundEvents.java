package com.lex_eden.fantasy_tavern.data;

import com.lex_eden.fantasy_tavern.FANTASY_TAVERN;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, FANTASY_TAVERN.MOD_ID);

    public static final RegistryObject<SoundEvent> SOUND_KID_FESTIVAL = registerSoundEvents("kid_festival");

    public static final RegistryObject<SoundEvent> SOUND_BOYS_AND_GIRLS_OF_THE_AGE_OF_SCIENCE = registerSoundEvents("boys_and_girls_of_the_age_of_science");


    private static RegistryObject<SoundEvent> registerSoundEvents(String name){
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(FANTASY_TAVERN.MOD_ID,name);
        return SOUND_EVENTS.register(name, ()-> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }
}


package com.lex_eden.fantasy_tavern.data;

import com.lex_eden.fantasy_tavern.FANTASY_TAVERN;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class Tags {
    public static final TagKey<Item> BERRY_TAG = bind("berry_tag");

    private static TagKey<Item> bind(String pName) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(FANTASY_TAVERN.MOD_ID, pName));
    }
}

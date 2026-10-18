package com.lex_eden.fantasy_tavern.data;

import com.lex_eden.fantasy_tavern.FANTASY_TAVERN;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;


public class BlindBox extends Item {

    public BlindBox(Properties pProperties) {
        super(pProperties);
    }
    @SuppressWarnings("removal")
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand ){
             ItemStack itemstack = player.getItemInHand(hand);
                if (!level.isClientSide) { //如果不是客户端
                    ServerLevel sl = (ServerLevel) level;             //定义ServerLevel 的属性为sl
                    LootTable table = sl.getServer().getLootData()      //获取战利品表然后创建
                            .getLootTable(new ResourceLocation(FANTASY_TAVERN.MOD_ID, "items/eden_box"));
                    LootParams params = new LootParams.Builder(sl)
                            .withParameter(LootContextParams.ORIGIN, player.position()).create(LootContextParamSets.CHEST);
                    for (ItemStack drop : table.getRandomItems(params)) { //开启之后物品判断逻辑
                        if (!player.getInventory().add(drop)) {
                            player.drop(drop, false);
                        }
                    }
                    player.awardStat(Stats.ITEM_USED.get(this));
                    if (!player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }
                }
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());

    }
}

package com.lex_eden.fantasy_tavern.data;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 饮品专用 Item：
 * - 拿着时 tooltip 显示蓝色效果列表（手动 appendHoverText，最可靠）
 * - 喝完给药水效果 + 补饱食度/饱和度
 */
public class DrinkablePotionItem extends PotionItem {

    private final List<MobEffectInstance> effects;
    private final int nutrition;
    private final float saturation;

    public DrinkablePotionItem(Properties props, List<MobEffectInstance> effects,
                               int nutrition, float saturation) {
        super(props);
        this.effects = effects;
        this.nutrition = nutrition;
        this.saturation = saturation;
    }

    // Forge 1.20.1 签名：(ItemStack, @Nullable Level, List<Component>, TooltipFlag)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level,
                                 List<Component> tooltip, TooltipFlag flag) {
        PotionUtils.addPotionTooltip(effects, tooltip, 1.0f);
    }



    // 可选：喝完返还的空酒杯，在主类初始化时赋值
    // 默认 null 表示不返还；赋值后所有饮品生效
    // 这里 Item 是 Minecraft 原版的物品类型，不是你自己的 items 注册类
    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        Player player = livingEntity instanceof Player ? (Player) livingEntity : null;
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, itemStack);
            player.getFoodData().eat(nutrition, saturation);
            for (MobEffectInstance effect : effects) {
                player.addEffect(new MobEffectInstance(effect));
            }
        }

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
        }

        if (player == null || !player.getAbilities().instabuild) {
            if (itemStack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }

            if (player != null) {
                player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        livingEntity.gameEvent(GameEvent.DRINK);
        return itemStack;
    }
}
package com.lex_eden.fantasy_tavern.mixin;

import com.lex_eden.fantasy_tavern.effect.ModEffects;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Inject(method = "canEat", at = @At("HEAD"), cancellable = true)
    private void onCanEat(boolean canAlwaysEat, CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player) (Object) this;
        if (player.hasEffect(ModEffects.WILD_HUNGRY.get())) {
            cir.setReturnValue(true);
        }
    }
}
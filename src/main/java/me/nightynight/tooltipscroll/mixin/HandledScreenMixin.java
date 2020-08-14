package me.nightynight.tooltipscroll.mixin;

import me.nightynight.tooltipscroll.TooltipScroll;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack; 
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author NightyNight
 */
@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin extends Screen {
    @Shadow
    protected Slot focusedSlot;

    protected HandledScreenMixin(Text title) {
        super(title);
    }
    @Inject(method = "drawMouseoverTooltip", at = @At("TAIL"))
    protected void drawMouseoverTooltip(MatrixStack matrices, int x, int y, CallbackInfo ci) {
        if (!(this.client.player.inventory.getCursorStack().isEmpty() && this.focusedSlot != null && this.focusedSlot.hasStack())) {
            TooltipScroll.scroll = 0.0D;
        }
    }
}

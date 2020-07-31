package me.nightynight.tooltipscroll.mixin;

import me.nightynight.tooltipscroll.TooltipScroll;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author NightyNight
 */
@Mixin(Screen.class)
public abstract class ScreenMixin extends AbstractParentElement {
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (amount == 0D) return false;
        if(Screen.hasShiftDown()) amount *= 3;
        if(Screen.hasShiftDown() && Screen.hasControlDown()) amount *= 3;
        TooltipScroll.scroll += amount;
        return true;
    }

    @Inject(method = "render", at = @At("HEAD"))
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
    }

    @ModifyVariable(method = "renderTooltip(Lnet/minecraft/client/util/math/MatrixStack;Ljava/util/List;II)V", ordinal = 4, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;push()V", ordinal = 0))
    public int moveY(int y) {
        return (int) (y + TooltipScroll.scroll);
    }

    @Inject(method = "onClose", at = @At("TAIL"))
    public void onClose(CallbackInfo ci) {
        TooltipScroll.scroll = 0.0D;
    }
}

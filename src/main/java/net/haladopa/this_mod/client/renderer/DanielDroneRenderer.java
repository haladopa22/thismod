package net.haladopa.this_mod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.haladopa.this_mod.entity.DanielDrone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DanielDroneRenderer extends EntityRenderer<DanielDrone> {

    public DanielDroneRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(DanielDrone entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(entity.tickCount * 3f + partialTick * 3f));
        Minecraft.getInstance().getItemRenderer().renderStatic(
                new ItemStack(Items.ENDER_EYE),
                ItemDisplayContext.FIXED,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack, buffer, null, entity.getId()
        );
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(DanielDrone entity) {
        return new ResourceLocation("minecraft", "textures/item/ender_eye.png");
    }
}

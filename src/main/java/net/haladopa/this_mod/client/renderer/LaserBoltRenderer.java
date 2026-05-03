package net.haladopa.this_mod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.haladopa.this_mod.entity.LaserBoltEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class LaserBoltRenderer extends EntityRenderer<LaserBoltEntity> {

    public LaserBoltRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(LaserBoltEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(0.5f, 0.5f, 0.5f);
        poseStack.mulPose(Axis.YP.rotationDegrees(entity.tickCount * 10f + partialTick * 10f));
        Minecraft.getInstance().getItemRenderer().renderStatic(
                new ItemStack(Items.FIRE_CHARGE),
                ItemDisplayContext.FIXED,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack, buffer, null, entity.getId()
        );
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(LaserBoltEntity entity) {
        return new ResourceLocation("minecraft", "textures/item/fire_charge.png");
    }
}

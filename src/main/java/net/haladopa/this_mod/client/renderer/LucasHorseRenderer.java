package net.haladopa.this_mod.client.renderer;

import net.haladopa.this_mod.entity.LucasHorse;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class LucasHorseRenderer extends AbstractHorseRenderer<LucasHorse, HorseModel<LucasHorse>> {
    @SuppressWarnings("deprecation")
    private static final ResourceLocation TEXTURE =
            new ResourceLocation("minecraft:textures/entity/horse/horse_skeleton.png");

    public LucasHorseRenderer(EntityRendererProvider.Context context) {
        super(context, new HorseModel<>(context.bakeLayer(ModelLayers.SKELETON_HORSE)), 1.1f);
    }

    @Override
    public ResourceLocation getTextureLocation(LucasHorse entity) {
        return TEXTURE;
    }
}

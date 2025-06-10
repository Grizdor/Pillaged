package net.grizdor.pillaged.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.grizdor.pillaged.Pillaged;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IllagerCaptainRenderer extends IllagerRenderer<IllagerCaptainEntity> {
    public IllagerCaptainRenderer(EntityRendererProvider.Context context) {
        super(context, new IllagerModel<>(context.bakeLayer(IllagerCaptainModel.LAYER_LOCATION)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()){
            @Override
            public void render(PoseStack poseStack, MultiBufferSource buffer,
                    int packedLight, IllagerCaptainEntity livingEntity,
                    float limbSwing, float limbSwingAmount,
                    float partialTicks, float ageInTicks,
                    float netHeadYaw, float headPitch) {
                if (livingEntity.isAggressive()) {
                    super.render(poseStack, buffer, packedLight, livingEntity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
                }
//                else {
//                    poseStack.rotateAround(Axis.XP.rotationDegrees(140), 0.0F, 0.0F, 0.0F);
//                    poseStack.translate(0.1F, -1.25F, 0.0F);
//                    super.render(poseStack, buffer, packedLight, livingEntity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
//                }
            }
        });
    }

    @Override
    public ResourceLocation getTextureLocation(IllagerCaptainEntity illagerCaptainEntity) {
        return ResourceLocation.fromNamespaceAndPath(Pillaged.MOD_ID, "textures/entity/illager/illager_captain.png");
    }
}

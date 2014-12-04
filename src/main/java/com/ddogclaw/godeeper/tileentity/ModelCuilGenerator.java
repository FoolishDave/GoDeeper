// Date: 10/10/2014 10:44:29 PM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX






package com.ddogclaw.godeeper.tileentity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCuilGenerator extends ModelBase
{
  //fields
    ModelRenderer sideBar1;
    ModelRenderer sideBar2;
    ModelRenderer sideBar3;
    ModelRenderer sideBar4;
    ModelRenderer bottomBar1;
    ModelRenderer bottomBar2;
    ModelRenderer bottomBar3;
    ModelRenderer topBar1;
    ModelRenderer topBar2;
    ModelRenderer topBar3;
    ModelRenderer topBar4;
    ModelRenderer bottomBar4;
    ModelRenderer midBottom1;
    ModelRenderer midRight1;
    ModelRenderer midLeft1;
    ModelRenderer midTop1;
    ModelRenderer midLeft2;
    ModelRenderer midRight2;
    ModelRenderer midTop2;
    ModelRenderer midBottom2;
    ModelRenderer midRight3;
    ModelRenderer midLeft3;
    ModelRenderer midTop3;
    ModelRenderer midBottom4;
    ModelRenderer midLeft4;
    ModelRenderer midRight4;
    ModelRenderer midTop4;
    ModelRenderer core;
    ModelRenderer midBottom3;
    ModelRenderer glass1;
    ModelRenderer glass2;
    ModelRenderer glass3;
    ModelRenderer glass4;
    ModelRenderer glassTop;
    ModelRenderer glassBottom;
  
  public ModelCuilGenerator()
  {
    textureWidth = 64;
    textureHeight = 128;
    
      sideBar1 = new ModelRenderer(this, 60, 0);
      sideBar1.addBox(15F, -14F, 15F, 1, 14, 1);
      sideBar1.setRotationPoint(-8F, 23F, -8F);
      sideBar1.setTextureSize(64, 128);
      sideBar1.mirror = true;
      setRotation(sideBar1, 0F, 0F, 0F);
      sideBar2 = new ModelRenderer(this, 60, 0);
      sideBar2.addBox(15F, -14F, 0F, 1, 14, 1);
      sideBar2.setRotationPoint(-8F, 23F, -8F);
      sideBar2.setTextureSize(64, 128);
      sideBar2.mirror = true;
      setRotation(sideBar2, 0F, 0F, 0F);
      sideBar3 = new ModelRenderer(this, 60, 0);
      sideBar3.addBox(0F, -14F, 15F, 1, 14, 1);
      sideBar3.setRotationPoint(-8F, 23F, -8F);
      sideBar3.setTextureSize(64, 128);
      sideBar3.mirror = true;
      setRotation(sideBar3, 0F, 0F, 0F);
      sideBar4 = new ModelRenderer(this, 60, 0);
      sideBar4.addBox(0F, -13F, 0F, 1, 14, 1);
      sideBar4.setRotationPoint(-8F, 22F, -8F);
      sideBar4.setTextureSize(64, 128);
      sideBar4.mirror = true;
      setRotation(sideBar4, 0F, 0F, 0F);
      bottomBar1 = new ModelRenderer(this, 30, 15);
      bottomBar1.addBox(14F, 0F, 0F, 1, 1, 16);
      bottomBar1.setRotationPoint(-7F, 23F, -8F);
      bottomBar1.setTextureSize(64, 128);
      bottomBar1.mirror = true;
      setRotation(bottomBar1, 0F, 0F, 0F);
      bottomBar2 = new ModelRenderer(this, 0, 19);
      bottomBar2.addBox(1F, 0F, 15F, 14, 1, 1);
      bottomBar2.setRotationPoint(-8F, 23F, -8F);
      bottomBar2.setTextureSize(64, 128);
      bottomBar2.mirror = true;
      setRotation(bottomBar2, 0F, 0F, 0F);
      bottomBar3 = new ModelRenderer(this, 30, 15);
      bottomBar3.addBox(0F, 0F, 0F, 1, 1, 16);
      bottomBar3.setRotationPoint(-8F, 23F, -8F);
      bottomBar3.setTextureSize(64, 128);
      bottomBar3.mirror = true;
      setRotation(bottomBar3, 0F, 0F, 0F);
      topBar1 = new ModelRenderer(this, 0, 19);
      topBar1.addBox(1F, 0F, 0F, 14, 1, 1);
      topBar1.setRotationPoint(-8F, 8F, -8F);
      topBar1.setTextureSize(64, 128);
      topBar1.mirror = true;
      setRotation(topBar1, 0F, 0F, 0F);
      topBar2 = new ModelRenderer(this, 30, 15);
      topBar2.addBox(15F, 0F, 0F, 1, 1, 16);
      topBar2.setRotationPoint(-8F, 8F, -8F);
      topBar2.setTextureSize(64, 128);
      topBar2.mirror = true;
      setRotation(topBar2, 0F, 0F, 0F);
      topBar3 = new ModelRenderer(this, 0, 19);
      topBar3.addBox(1F, 0F, 15F, 14, 1, 1);
      topBar3.setRotationPoint(-8F, 8F, -8F);
      topBar3.setTextureSize(64, 128);
      topBar3.mirror = true;
      setRotation(topBar3, 0F, 0F, 0F);
      topBar4 = new ModelRenderer(this, 30, 15);
      topBar4.addBox(0F, 0F, 0F, 1, 1, 16);
      topBar4.setRotationPoint(-8F, 8F, -8F);
      topBar4.setTextureSize(64, 128);
      topBar4.mirror = true;
      setRotation(topBar4, 0F, 0F, 0F);
      bottomBar4 = new ModelRenderer(this, 0, 19);
      bottomBar4.addBox(1F, 0F, 0F, 14, 1, 1);
      bottomBar4.setRotationPoint(-8F, 23F, -8F);
      bottomBar4.setTextureSize(64, 128);
      bottomBar4.mirror = true;
      setRotation(bottomBar4, 0F, 0F, 0F);
      midBottom1 = new ModelRenderer(this, 0, 22);
      midBottom1.addBox(-2F, 21F, -4F, 4, 1, 1);
      midBottom1.setRotationPoint(0F, 0F, 0F);
      midBottom1.setTextureSize(64, 128);
      midBottom1.mirror = true;
      setRotation(midBottom1, 0F, 0F, 0F);
      midRight1 = new ModelRenderer(this, 54, 0);
      midRight1.addBox(2F, 11F, -4F, 1, 11, 1);
      midRight1.setRotationPoint(0F, 0F, 0F);
      midRight1.setTextureSize(64, 128);
      midRight1.mirror = true;
      setRotation(midRight1, 0F, 0F, 0F);
      midLeft1 = new ModelRenderer(this, 54, 0);
      midLeft1.addBox(-3F, 11F, -4F, 1, 11, 1);
      midLeft1.setRotationPoint(0F, 0F, 0F);
      midLeft1.setTextureSize(64, 128);
      midLeft1.mirror = true;
      setRotation(midLeft1, 0F, 0F, 0F);
      midTop1 = new ModelRenderer(this, 13, 22);
      midTop1.addBox(-3F, 10F, -4F, 6, 1, 1);
      midTop1.setRotationPoint(0F, 0F, 0F);
      midTop1.setTextureSize(64, 128);
      midTop1.mirror = true;
      setRotation(midTop1, 0F, 0F, 0F);
      midLeft2 = new ModelRenderer(this, 54, 0);
      midLeft2.addBox(3F, 11F, -3F, 1, 11, 1);
      midLeft2.setRotationPoint(0F, 0F, 0F);
      midLeft2.setTextureSize(64, 128);
      midLeft2.mirror = true;
      setRotation(midLeft2, 0F, 0F, 0F);
      midRight2 = new ModelRenderer(this, 54, 0);
      midRight2.addBox(3F, 11F, 2F, 1, 11, 1);
      midRight2.setRotationPoint(0F, 0F, 0F);
      midRight2.setTextureSize(64, 128);
      midRight2.mirror = true;
      setRotation(midRight2, 0F, 0F, 0F);
      midTop2 = new ModelRenderer(this, 13, 22);
      midTop2.addBox(3F, 10F, -3F, 1, 1, 6);
      midTop2.setRotationPoint(0F, 0F, 0F);
      midTop2.setTextureSize(64, 128);
      midTop2.mirror = true;
      setRotation(midTop2, 0F, 0F, 0F);
      midBottom2 = new ModelRenderer(this, 0, 22);
      midBottom2.addBox(4F, 21F, -2F, 1, 1, 4);
      midBottom2.setRotationPoint(-1F, 0F, 0F);
      midBottom2.setTextureSize(64, 128);
      midBottom2.mirror = true;
      setRotation(midBottom2, 0F, 0F, 0F);
      midRight3 = new ModelRenderer(this, 54, 0);
      midRight3.addBox(6F, 2F, 0F, 1, 11, 1);
      midRight3.setRotationPoint(-10F, 9F, -3F);
      midRight3.setTextureSize(64, 128);
      midRight3.mirror = true;
      setRotation(midRight3, 0F, 0F, 0F);
      midLeft3 = new ModelRenderer(this, 54, 0);
      midLeft3.addBox(3F, 11F, 2F, 1, 11, 1);
      midLeft3.setRotationPoint(-7F, 0F, 0F);
      midLeft3.setTextureSize(64, 128);
      midLeft3.mirror = true;
      setRotation(midLeft3, 0F, 0F, 0F);
      midTop3 = new ModelRenderer(this, 13, 22);
      midTop3.addBox(3F, 10F, -3F, 1, 1, 6);
      midTop3.setRotationPoint(-7F, 0F, 0F);
      midTop3.setTextureSize(64, 128);
      midTop3.mirror = true;
      setRotation(midTop3, 0F, 0F, 0F);
      midBottom4 = new ModelRenderer(this, 0, 22);
      midBottom4.addBox(-2F, 21F, -4F, 4, 1, 1);
      midBottom4.setRotationPoint(0F, 0F, 7F);
      midBottom4.setTextureSize(64, 128);
      midBottom4.mirror = true;
      setRotation(midBottom4, 0F, 0F, 0F);
      midLeft4 = new ModelRenderer(this, 54, 0);
      midLeft4.addBox(2F, 11F, -4F, 1, 11, 1);
      midLeft4.setRotationPoint(0F, 0F, 7F);
      midLeft4.setTextureSize(64, 128);
      midLeft4.mirror = true;
      setRotation(midLeft4, 0F, 0F, 0F);
      midRight4 = new ModelRenderer(this, 54, 0);
      midRight4.addBox(-3F, 11F, -4F, 1, 11, 1);
      midRight4.setRotationPoint(0F, 0F, 7F);
      midRight4.setTextureSize(64, 128);
      midRight4.mirror = true;
      setRotation(midRight4, 0F, 0F, 0F);
      midTop4 = new ModelRenderer(this, 13, 22);
      midTop4.addBox(-3F, 10F, -4F, 6, 1, 1);
      midTop4.setRotationPoint(0F, 0F, 7F);
      midTop4.setTextureSize(64, 128);
      midTop4.mirror = true;
      setRotation(midTop4, 0F, 0F, 0F);
      core = new ModelRenderer(this, 0, 0);
      core.addBox(0F, 2F, 0F, 6, 12, 6);
      core.setRotationPoint(-3F, 8F, -3F);
      core.setTextureSize(64, 128);
      core.mirror = true;
      setRotation(core, 0F, 0F, 0F);
      midBottom3 = new ModelRenderer(this, 0, 22);
      midBottom3.addBox(0F, 0F, 0F, 1, 1, 4);
      midBottom3.setRotationPoint(-4F, 21F, -2F);
      midBottom3.setTextureSize(64, 128);
      midBottom3.mirror = true;
      setRotation(midBottom3, 0F, 0F, 0F);
      glass1 = new ModelRenderer(this, 34, 60);
      glass1.addBox(-7F, 9F, -7F, 1, 14, 14);
      glass1.setRotationPoint(0F, 0F, 0F);
      glass1.setTextureSize(64, 128);
      glass1.mirror = true;
      setRotation(glass1, 0F, 0F, 0F);
      glass2 = new ModelRenderer(this, 34, 60);
      glass2.addBox(0F, 0F, 0F, 1, 14, 14);
      glass2.setRotationPoint(6F, 9F, -7F);
      glass2.setTextureSize(64, 128);
      glass2.mirror = true;
      setRotation(glass2, 0F, 0F, 0F);
      glass3 = new ModelRenderer(this, 0, 60);
      glass3.addBox(0F, 0F, 0F, 12, 14, 1);
      glass3.setRotationPoint(-6F, 9F, 6F);
      glass3.setTextureSize(64, 128);
      glass3.mirror = true;
      setRotation(glass3, 0F, 0F, 0F);
      glass4 = new ModelRenderer(this, 0, 60);
      glass4.addBox(-6F, 9F, -7F, 12, 14, 1);
      glass4.setRotationPoint(0F, 0F, 0F);
      glass4.setTextureSize(64, 128);
      glass4.mirror = true;
      setRotation(glass4, 0F, 0F, 0F);
      glassTop = new ModelRenderer(this, 0, 40);
      glassTop.addBox(0F, 0F, 0F, 12, 1, 12);
      glassTop.setRotationPoint(-6F, 9F, -6F);
      glassTop.setTextureSize(64, 128);
      glassTop.mirror = true;
      setRotation(glassTop, 0F, 0F, 0F);
      glassBottom = new ModelRenderer(this, 0, 40);
      glassBottom.mirror = true;
      glassBottom.addBox(0F, 0F, 0F, 12, 1, 12);
      glassBottom.setRotationPoint(-3F, 13F, -3F);
      glassBottom.setTextureSize(64, 128);
      glassBottom.mirror = true;
      setRotation(glassBottom, 0F, 0F, 0F);
      glassBottom.mirror = false;
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    sideBar1.render(f5);
    sideBar2.render(f5);
    sideBar3.render(f5);
    sideBar4.render(f5);
    bottomBar1.render(f5);
    bottomBar2.render(f5);
    bottomBar3.render(f5);
    topBar1.render(f5);
    topBar2.render(f5);
    topBar3.render(f5);
    topBar4.render(f5);
    bottomBar4.render(f5);
    midBottom1.render(f5);
    midRight1.render(f5);
    midLeft1.render(f5);
    midTop1.render(f5);
    midLeft2.render(f5);
    midRight2.render(f5);
    midTop2.render(f5);
    midBottom2.render(f5);
    midRight3.render(f5);
    midLeft3.render(f5);
    midTop3.render(f5);
    midBottom4.render(f5);
    midLeft4.render(f5);
    midRight4.render(f5);
    midTop4.render(f5);
    core.render(f5);
    midBottom3.render(f5);
    glass1.render(f5);
    glass2.render(f5);
    glass3.render(f5);
    glass4.render(f5);
    glassTop.render(f5);
    glassBottom.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
  }

}

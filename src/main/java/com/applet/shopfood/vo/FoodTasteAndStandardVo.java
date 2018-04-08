package com.applet.shopfood.vo;

import com.tcsb.tcsbfoodtastefunction.vo.FoodTastePageVo;
import java.util.List;

public class FoodTasteAndStandardVo
{
  private List<FoodTastePageVo> foodTaste;
  private FoodStandardPageVo foodStandard;
  
  public FoodStandardPageVo getFoodStandard()
  {
    return this.foodStandard;
  }
  
  public void setFoodStandard(FoodStandardPageVo foodStandard)
  {
    this.foodStandard = foodStandard;
  }
  
  public List<FoodTastePageVo> getFoodTaste()
  {
    return this.foodTaste;
  }
  
  public void setFoodTaste(List<FoodTastePageVo> foodTaste)
  {
    this.foodTaste = foodTaste;
  }
}

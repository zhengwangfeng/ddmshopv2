package com.applet.shopfood.vo;

import java.util.List;

public class FoodStandardPageVo
{
  private String standaraFrist;
  private List<FoodStandardVo> foodStandard;
  
  public String getStandaraFrist()
  {
    return this.standaraFrist;
  }
  
  public void setStandaraFrist(String standaraFrist)
  {
    this.standaraFrist = standaraFrist;
  }
  
  public List<FoodStandardVo> getFoodStandard()
  {
    return this.foodStandard;
  }
  
  public void setFoodStandard(List<FoodStandardVo> foodStandard)
  {
    this.foodStandard = foodStandard;
  }
}

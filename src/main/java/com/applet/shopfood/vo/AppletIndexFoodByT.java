package com.applet.shopfood.vo;

import com.tcsb.desk.entity.TcsbDeskEntity;
import com.tcsb.food.vo.FoodTypeVO;
import com.tcsb.shop.entity.TcsbShopEntity;
import java.util.List;

@Deprecated
public class AppletIndexFoodByT
{
  private TcsbShopEntity tcsbShop;
  private TcsbDeskEntity tcsbDesk;
  private List<FoodTypeVO> foodType;
  private String promotion;
  
  public TcsbShopEntity getTcsbShop()
  {
    return this.tcsbShop;
  }
  
  public void setTcsbShop(TcsbShopEntity tcsbShop)
  {
    this.tcsbShop = tcsbShop;
  }
  
  public TcsbDeskEntity getTcsbDesk()
  {
    return this.tcsbDesk;
  }
  
  public void setTcsbDesk(TcsbDeskEntity tcsbDesk)
  {
    this.tcsbDesk = tcsbDesk;
  }
  
  public List<FoodTypeVO> getFoodType()
  {
    return this.foodType;
  }
  
  public void setFoodType(List<FoodTypeVO> foodType)
  {
    this.foodType = foodType;
  }
  
  public String getPromotion()
  {
    return this.promotion;
  }
  
  public void setPromotion(String promotion)
  {
    this.promotion = promotion;
  }
}

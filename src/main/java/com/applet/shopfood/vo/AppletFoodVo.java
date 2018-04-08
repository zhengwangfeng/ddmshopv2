package com.applet.shopfood.vo;

public class AppletFoodVo
{
  private String id;
  private String name;
  private double price;
  private String img;
  private int num;
  private String unitName;
  private boolean foodTaste;
  private boolean currentPrice;
  private boolean foodstandard;
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public double getPrice()
  {
    return this.price;
  }
  
  public void setPrice(double price)
  {
    this.price = price;
  }
  
  public String getImg()
  {
    return this.img;
  }
  
  public void setImg(String img)
  {
    this.img = img;
  }
  
  public int getNum()
  {
    return this.num;
  }
  
  public void setNum(int num)
  {
    this.num = num;
  }
  
  public String getUnitName()
  {
    return this.unitName;
  }
  
  public void setUnitName(String unitName)
  {
    this.unitName = unitName;
  }
  
  public boolean isFoodTaste()
  {
    return this.foodTaste;
  }
  
  public void setFoodTaste(boolean foodTaste)
  {
    this.foodTaste = foodTaste;
  }
  
  public boolean isCurrentPrice()
  {
    return this.currentPrice;
  }
  
  public void setCurrentPrice(boolean currentPrice)
  {
    this.currentPrice = currentPrice;
  }
  
  public boolean isFoodstandard()
  {
    return this.foodstandard;
  }
  
  public void setFoodstandard(boolean foodstandard)
  {
    this.foodstandard = foodstandard;
  }
}

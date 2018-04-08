package com;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * 金额计算工具类
 *
 *
 * @author Mar_x
 * create on 2017年7月6日 上午11:18:41
 */
public class BigDecimalUtil {
	
	/**
	 * 除法（四舍五入）
	 * @param dividend 被除数
	 * @param divisor 除数
	 * @param rdp 保留的小数位
	 * @return String
	 */
	public static String divide(String dividend,String divisor,int rdp){
		BigDecimal b = new BigDecimal(dividend);   
		BigDecimal one = new BigDecimal(divisor); 
		if(rdp>0){ 
			try {
				return b.divide(one,rdp,BigDecimal.ROUND_HALF_UP).doubleValue() + "";  
			} catch (Exception e) {
				return "";
			}
		}else{
			if(rdp>-1){
				double d = b.divide(one,rdp,BigDecimal.ROUND_HALF_UP).doubleValue();   
				return (new Double(d)).intValue() + "";
			}else{
				System.out.println("保留的小数位"+rdp+" Exception：必需为正整数");
				return "";
			}
		}
	}
	
	
	/**
	 * 
	 * 除法
	 * @param dividend 被除数
	 * @param divisor 除数
	 * @param rdp 保留的小数位
	 * @param rdm 转换类型
	 * @return String
	 */
	public static String divide(String dividend,String divisor,int rdp,RoundingMode rdm){
		BigDecimal b = new BigDecimal(dividend);   
		BigDecimal one = new BigDecimal(divisor); 
		if(rdp>0){ 
			try {
				return b.divide(one,rdp,rdm).doubleValue() + "";  
			} catch (Exception e) {
				return "";
			}
		}else{
			if(rdp>-1){
				double d = b.divide(one,rdp,rdm).doubleValue();   
				return (new Double(d)).intValue() + "";
			}else{
				System.out.println("保留的小数位"+rdp+" Exception：必需为正整数");
				return "";
			}
		}
	}
	
	
	/**
	 * 数值保留小数位转换(四舍五入)
	 * @param dble 转换的数
	 * @param rdp 保留的小数位
	 * @return String
	 */
	public static String numericRetentionDecimal(String dble, int rdp) {
		if(rdp>0){
			BigDecimal bg = new BigDecimal(dble).setScale(rdp, RoundingMode.HALF_UP);
			return bg.doubleValue() + "";
		}else{
			if(rdp>-1){
				BigDecimal bg = new BigDecimal(dble).setScale(rdp, RoundingMode.HALF_UP);
				double d = bg.doubleValue();  
				return (new Double(d)).intValue() + "";
			}else{
				
				System.out.println("保留的小数位"+rdp+" Exception：必需为正整数");
				return "";
			}
		}
	}
	
	
	/**
	 * 数值保留小数位转换
	 * @param dble 转换的数
	 * @param rdp 保留的小数位
	 * @param rdm 转换类型
	 * @return String
	 */
	public static String numericRetentionDecimal(String dble, int rdp, RoundingMode rdm) {
		if(rdp>0){
			BigDecimal bg = new BigDecimal(dble).setScale(rdp, rdm);
			return bg.doubleValue() + "";
		}else{
			if(rdp>-1){
				BigDecimal bg = new BigDecimal(dble).setScale(rdp, rdm);
				double d = bg.doubleValue();  
				return (new Double(d)).intValue() + "";
			}else{
				
				System.out.println("保留的小数位"+rdp+" Exception：必需为正整数");
				return "";
			}
		}
	}
	
	
	/**
	 * 
	 * 数值保留小数位转换(四舍五入)
	 * @param dble 转换的数
	 * @param rdp 保留的小数位
	 * @return 返回值格式（***，***，***.****） String
	 */
	public static String numericRetentionDecimal(double dble,int rdp) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(rdp); 
        nf.setRoundingMode(RoundingMode.HALF_UP);
        return nf.format(dble);
    }
	
	/**
	 * 
	 * 数值保留小数位转换
	 * @param dble 转换的数
	 * @param rdp 保留的小数位
	 * @param rdm 转换类型
	 * @return 返回值格式（***，***，***.****）
	 */
	public static String numericRetentionDecimal(double dble,int rdp,RoundingMode rdm) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(rdp); 
        nf.setRoundingMode(rdm);
        return nf.format(dble);
    }

	
	/**  
	* 提供精确的加法运算。  
	* @param augend 被加数  
	* @param addend 加数  
	* @return 两个参数的和  
	*/  
	public static double add(double augend,double addend){   
		BigDecimal b1 = new BigDecimal(Double.toString(augend));   
		BigDecimal b2 = new BigDecimal(Double.toString(addend));   
		return b1.add(b2).doubleValue();
	}   
	
	
	 public static void main(String[] args) { 
		 double a = BigDecimalUtil.add(0.222, 0.9999);
		 System.out.println(a);
	 } 
	

}

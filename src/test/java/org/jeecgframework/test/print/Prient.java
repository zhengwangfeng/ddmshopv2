/**
 * jeecgos
 * @author Mar_x
 * create on 2017 2017年6月12日 下午3:20:47
 */
package org.jeecgframework.test.print;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;  
import java.awt.Graphics;  
import java.awt.Graphics2D;
import java.awt.Image;

import java.awt.print.Book;  
import java.awt.print.PageFormat;  
import java.awt.print.Paper;  
import java.awt.print.Printable;  
import java.awt.print.PrinterException;  
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.tcsb.order.VO.TcsbOrderPrintVO;
import com.tcsb.order.entity.TcsbOrderEntity;  

/**
 * jeecgos
 *
 *
 * @author Mar_x
 * create on 2017年6月12日 下午3:20:47
 */
public class Prient implements Printable {  
	private Font font; 
	private List<TcsbOrderPrintVO> list;  
  
    public List<TcsbOrderPrintVO> getList() {
		return list;
	}

	public void setList(List<TcsbOrderPrintVO> list) {
		this.list = list;
	}

	@Override  
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {  
  
    	 Component c = null;  
         // 转换成Graphics2D 拿到画笔  
         Graphics2D g2 = (Graphics2D) graphics;  
         // 设置打印颜色为黑色  
         g2.setColor(Color.black);  
   
         // 打印起点坐标  
         double x = 0;  
         double y = 10; 
         System.out.println("=====================");
         System.out.println(list);
         // 虚线  
         float[] dash1 = { 4.0f };  
         // width - 此 BasicStroke 的宽度。此宽度必须大于或等于 0.0f。如果将宽度设置为  
         // 0.0f，则将笔划呈现为可用于目标设备和抗锯齿提示设置的最细线条。  
         // cap - BasicStroke 端点的装饰  
         // join - 应用在路径线段交汇处的装饰  
         // miterlimit - 斜接处的剪裁限制。miterlimit 必须大于或等于 1.0f。  
         // dash - 表示虚线模式的数组  
         // dash_phase - 开始虚线模式的偏移量  
   
         // 设置画虚线  
         g2.setStroke(new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 4.0f, dash1, 0.0f));  
   
         // 设置打印字体（字体名称、样式和点大小）（字体名称可以是物理或者逻辑名称）  
         font = new Font("宋体", Font.PLAIN, 12);  
         g2.setFont(font);// 设置字体  
         float heigth = font.getSize2D();// 字体高度  
         // 标题  
         g2.drawString("广西机电工程学校智能超市", (float) x, (float) y + heigth);  
         float line = 2 * heigth;  
   
         font = new Font("宋体", Font.PLAIN, 10);  
         g2.setFont(font);// 设置字体  
         heigth = font.getSize2D();// 字体高度  
   
         // 显示收银员  
         //g2.drawString("收银员:" + cashier, (float) x, (float) y + line);  
         // 显示订单号  
         g2.drawString("订单号:" + "201705273520164686", (float) x, (float) y + line);  
   
         line += heigth;  
         // 显示标题  
         g2.drawString("名称", (float) x, (float) y + line);  
         g2.drawString("数量", (float) x + 60, (float) y + line);  
         g2.drawString("金额", (float) x + 100, (float) y + line);  
         line += heigth;  
         g2.drawLine((int) x, (int) (y + line), (int) x + 158, (int) (y + line));  
   
         // 第4行  
         line += heigth;  
   
         // 显示内容  
         for (TcsbOrderPrintVO f : list) {
        	 g2.drawString(f.getTcsbOrderItemPrintVOs().get(0).getFoodName(), (float) x, (float) y + line);  
             g2.drawString("2", (float) x + 70, (float) y + line);  
             g2.drawString("0.02", (float) x + 110, (float) y + line);  
             line += heigth; 
		}
         line += heigth;  
   
         g2.drawLine((int) x, (int) (y + line), (int) x + 158, (int) (y + line));  
         line += heigth;  
   
         //g2.drawString("售出商品数:" + "1" + "件", (float) x, (float) y + line);  
        // g2.drawString("合计:" + "0.02" + "元", (float) x + 70, (float) y + line);  
        // line += heigth;  
         g2.drawString("总金额:" + "0.02" + "元", (float) x, (float) y + line); 
         line += heigth;  
         g2.drawString("店铺优惠:" + "0.00" + "元", (float) x, (float) y + line);  
         line += heigth;  
         g2.drawString("平台优惠:" + "0.00" + "元", (float) x, (float) y + line);  
         line += heigth; 
         g2.drawString("实收款:" + "0.02" + "元", (float) x, (float) y + line);  
         //line += heigth;  
        // g2.drawString("天天平价,日日新鲜", (float) x + 20, (float) y + line);  
   
         line += heigth;  
         g2.drawString("钱票请当面点清，离开柜台恕不负责", (float) x, (float) y + line);  
         line += heigth; 
        
	/*	try {
			Image img = ImageIO.read(new File("D:\\workspace\\jeecgos\\src\\main\\webapp\\images\\12.png"));
			
			
			g2.drawImage(img, 0, 200, null);
			//line += 100; 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
         
         switch (pageIndex) {  
         case 0:  
   
             return PAGE_EXISTS;  
         default:  
             return NO_SUCH_PAGE;  
   
         }  
    
    }  

    public static void main(String[] args) {  
  
        int height = 105 + 2 * 15 + 20;  
  
        // 通俗理解就是书、文档  
        Book book = new Book();  
  
        // 打印格式  
        PageFormat pf = new PageFormat();  
        pf.setOrientation(PageFormat.PORTRAIT);  
  
        // 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。  
        Paper p = new Paper();  
        p.setSize(230, height);  
        p.setImageableArea(-20, -20, 230, height + 20);  
        pf.setPaper(p);  
  
        // 把 PageFormat 和 Printable 添加到书中，组成一个页面  
        Prient pr = new Prient();
       // pr.setList("asdjk855");
        book.append(pr, pf);  

        // 获取打印服务对象  
        PrinterJob job = PrinterJob.getPrinterJob();  
        job.setPageable(book);  
        try {  
            job.print();  
        } catch (PrinterException e) {  
            System.out.println("================打印出现异常");  
        }  
  
    }


  
}  
package org.jeecgframework.test.demo;

import java.io.*;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import gui.ava.html.image.generator.HtmlImageGenerator;  
  
public class PrintDemo {  

    private final static int[] li_SecPosValue = { 1601, 1637, 1833, 2078, 2274,
            2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858,
            4027, 4086, 4390, 4558, 4684, 4925, 5249, 5590 };
    private final static String[] lc_FirstLetter = { "a", "b", "c", "d", "e",
            "f", "g", "h", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "w", "x", "y", "z" };

    /**
     * 取得给定汉字串的首字母串,即声母串
     * @param str 给定汉字串
     * @return 声母串
     */
    public String getAllFirstLetter(String str) {
        if (str == null || str.trim().length() == 0) {
            return "";
        }

        String _str = "";
        for (int i = 0; i < str.length(); i++) {
            _str = _str + this.getFirstLetter(str.substring(i, i + 1));
        }

        return _str;
    }

    /**
     * 取得给定汉字的首字母,即声母
     * @param chinese 给定的汉字
     * @return 给定汉字的声母
     */
    public String getFirstLetter(String chinese) {
        if (chinese == null || chinese.trim().length() == 0) {
            return "";
        }
        chinese = this.conversionStr(chinese, "GB2312", "ISO8859-1");

        if (chinese.length() > 1) // 判断是不是汉字
        {
            int li_SectorCode = (int) chinese.charAt(0); // 汉字区码
            int li_PositionCode = (int) chinese.charAt(1); // 汉字位码
            li_SectorCode = li_SectorCode - 160;
            li_PositionCode = li_PositionCode - 160;
            int li_SecPosCode = li_SectorCode * 100 + li_PositionCode; // 汉字区位码
            if (li_SecPosCode > 1600 && li_SecPosCode < 5590) {
                for (int i = 0; i < 23; i++) {
                    if (li_SecPosCode >= li_SecPosValue[i]
                            && li_SecPosCode < li_SecPosValue[i + 1]) {
                        chinese = lc_FirstLetter[i];
                        break;
                    }
                }
            } else // 非汉字字符,如图形符号或ASCII码
            {
                chinese = this.conversionStr(chinese, "ISO8859-1", "GB2312");
                chinese = chinese.substring(0, 1);
            }
        }

        return chinese;
    }

    /**
     * 字符串编码转换
     * @param str 要转换编码的字符串
     * @param charsetName 原来的编码
     * @param toCharsetName 转换后的编码
     * @return 经过编码转换后的字符串
     */
    private String conversionStr(String str, String charsetName,String toCharsetName) {
        try {
            str = new String(str.getBytes(charsetName), toCharsetName);
        } catch (UnsupportedEncodingException ex) {
            System.out.println("字符串编码转换异常：" + ex.getMessage());
        }
        return str;
    }

    public static void main(String[] args) {
        PrintDemo demo = new PrintDemo();
        System.out.println("获取拼音首字母："+ demo.getAllFirstLetter("北京联席办"));
    }


    public void print() throws PrintException {  
//    	PrintDemo dp = new PrintDemo();
//    	String fileName = "D:/print_test.png";
//    	dp.drawImage(fileName);
//    	dp.drawImage("D:\\workspace\\jeecgos\\src\\main\\webapp\\images\\1496996216.png");
    	
    }
    
    

    
    
    /** 
     * 画图片的方法 
     *  
     * @param fileName 
     *            [图片的路径] 
     * @throws PrintException 
     */  
    public void drawImage(String fileName) throws PrintException {  
        try {  
           
	 		DocFlavor dof = null;  
            // 根据用户选择不同的图片格式获得不同的打印设备  
            if (fileName.endsWith(".gif")) {  
                // gif  
                dof = DocFlavor.INPUT_STREAM.GIF;  
            } else if (fileName.endsWith(".jpg")) {  
                // jpg  
                dof = DocFlavor.INPUT_STREAM.JPEG;  
            } else if (fileName.endsWith(".png")) {  
                // png  
                dof = DocFlavor.INPUT_STREAM.PNG;  
            }  
            // 字节流获取图片信息  
            FileInputStream fin = new FileInputStream(fileName);  
	 		
	 		
            // 获得打印属性  
            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();  
            // 每一次默认打印一页  
            pras.add(new Copies(1));  
            // 获得打印设备 ，字节流方式，图片格式  
            PrintService pss[] = PrintServiceLookup.lookupPrintServices(dof,  
                    pras);  
            // 如果没有获取打印机  
            if (pss.length == 0) {  
                // 终止程序  
                return;  
            }  
            // 获取第一个打印机  
            PrintService ps = pss[0];  
            System.out.println("Printing image..........." + ps);  
            // 获得打印工作  
            DocPrintJob job = ps.createPrintJob();  
            // 设置打印内容  
            Doc doc = new SimpleDoc(fin, dof, null);  
            // 出现设置对话框  
            MediaPrintableArea area = new MediaPrintableArea(0, 0, 50, 50, MediaPrintableArea.MM);
            pras.add(area);
            job.print(doc, pras);  
            fin.close();  

            
        } catch (IOException ie) {  
            // 捕获io异常  
            ie.printStackTrace();  
        }  
    }  
    
    
    
    
    
    public static void printTxt(String fileName){
    	
    	//JFileChooser fileChooser = new JFileChooser(); // 创建打印作业  
        // File file = new File("D:\\workspace\\jeecgos\\src\\main\\webapp\\webpage\\com\\tcsb\\order\\print.html");// 获取选择的文件  
        // File file2 = new File("D:/111.txt");
         // 构建打印请求属性集  
         HashPrintRequestAttributeSet pras2 = new HashPrintRequestAttributeSet();  

         // 设置打印格式，因为未确定类型，所以选择autosense  
         DocFlavor flavor2 = DocFlavor.INPUT_STREAM.AUTOSENSE;  
         
         // 定位默认的打印服务  
         PrintService defaultService2 = PrintServiceLookup.lookupDefaultPrintService();  

         
         try {  
             DocPrintJob jobs = defaultService2.createPrintJob(); // 创建打印作业  
            
             //创建打印数据
             String str = "\t 标题  \n" +
					 "*******************************\n" +
						 "订单号:134564654\n" +
					 "桌号:001  开始时间:2017-06-09\n" +
					 "*******************************\n" + 
					 "菜单名称 \t数量\t金额\n" +
					 "海西\t1\t0.01\n" +
					 "pijiu\t1\t0.01\n" +
					 "我不知道是什么 \t1\t0.01\n" +
					 "\n\n\n";
             
             
             InputStream inputStream = new ByteArrayInputStream(str.getBytes("GBK")); 

             //file2.createNewFile();
             
            // fis2 = new FileInputStream(file2); // 构造待打印的文件流  
             DocAttributeSet das = new HashDocAttributeSet();  
             Doc doc2 = new SimpleDoc(inputStream, flavor2, das);  
           
             jobs.print(doc2, pras2);  
            // fis2.close(); 
         } catch (Exception e) {  
             e.printStackTrace();  
         } 
    }
    
}
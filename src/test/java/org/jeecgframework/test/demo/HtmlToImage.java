/**
 * jeecgos
 * @author Mar_x
 * create on 2017 2017年6月9日 下午3:43:16
 */
package org.jeecgframework.test.demo;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import gui.ava.html.image.generator.HtmlImageGenerator;

/**
 * jeecgos
 *
 *
 * @author Mar_x
 * create on 2017年6月9日 下午3:43:16
 */
public class HtmlToImage {
	
	
	  public static void generateOutput(String html){  
          
		//java Html2Image 实现html转图片功能 

		// html2image
		  HtmlImageGenerator imageGenerator = new HtmlImageGenerator();

		 
		  imageGenerator.loadHtml(html);

		  imageGenerator.getBufferedImage();
		  
		  System.out.println(imageGenerator.getBufferedImage().toString());

		  imageGenerator.saveAsImage("D:/hello-world.png");

		  imageGenerator.saveAsHtmlWithMap("hello-world.html", "hello-world.png"); 
		  
		  
		  ImageHandleHelper.overlapImage("D:/hello-world.png", "D:/1496996216.png", "d:/");
		  
	    }  
	    public static void main(String[] args) {  
	        try {  
	        	
	        	 String html= "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>"
	                     +" <html xmlns='http://www.w3.org/1999/xhtml'>"
	                     +"<style>.p1 {font-size:36px;font-weight:200;color:#0000FF;}</style>"
	                     +"<style>.p2 { font-size:24px;font-weight:300;color: #FF0000;}</style>"
	                     +"<head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /><title>CSS外联样式表</title></head>"
	                     +"<body><p class='p1'>CSS外联样式表</p><p class='p2'>CSS外联样式表</p><p class='p3'>CSS外联样式表</p><p class='p4'>CSS外联样式表 </p>"
	                     + "</body></html>";
	        	
	        	generateOutput(html);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
}

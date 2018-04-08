package com.apiservice.handler;


import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apiservice.common.utils.OutputResult;
import com.apiservice.common.utils.ReturnFormat;
import com.apiservice.common.utils.ReturnMessageEnum;

/**
 * 异常增强，以JSON的形式返回给客服端
 * 异常增强类型：NullPointerException,RunTimeException,ClassCastException,
　　　　　　　　 NoSuchMethodException,IOException,IndexOutOfBoundsException
　　　　　　　　 以及springmvc自定义异常等，如下：
SpringMVC自定义异常对应的status code  
           Exception                       HTTP Status Code  
ConversionNotSupportedException         500 (Internal Server Error)
HttpMessageNotWritableException         500 (Internal Server Error)
HttpMediaTypeNotSupportedException      415 (Unsupported Media Type)
HttpMediaTypeNotAcceptableException     406 (Not Acceptable)
HttpRequestMethodNotSupportedException  405 (Method Not Allowed)
NoSuchRequestHandlingMethodException    404 (Not Found) 
TypeMismatchException                   400 (Bad Request)
HttpMessageNotReadableException         400 (Bad Request)
MissingServletRequestParameterException 400 (Bad Request)
 *
 */
@ControllerAdvice
public class RestExceptionHandler{
    //运行时异常
    @ExceptionHandler(RuntimeException.class)  
    @ResponseBody  
    public OutputResult runtimeExceptionHandler(RuntimeException runtimeException,HttpServletResponse response) {  
    	response.setStatus(400);
        return new OutputResult(ReturnMessageEnum.RUNTIMEEXCEPTION.getStatus(), ReturnMessageEnum.RUNTIMEEXCEPTION.getMsg(), null);
    }  

    //空指针异常
    @ExceptionHandler(NullPointerException.class)  
    @ResponseBody  
    public OutputResult nullPointerExceptionHandler(NullPointerException ex,HttpServletResponse response) {  
    	response.setStatus(400);
        ex.printStackTrace();
        return new OutputResult(ReturnMessageEnum.NULLPOINTEXCEPTION.getStatus(), ReturnMessageEnum.NULLPOINTEXCEPTION.getMsg(), null);
    }   
    //类型转换异常
    @ExceptionHandler(ClassCastException.class)  
    @ResponseBody  
    public OutputResult classCastExceptionHandler(ClassCastException ex,HttpServletResponse response) {  
    	response.setStatus(400);
        ex.printStackTrace();
        return new OutputResult(ReturnMessageEnum.CLASSCATEXCEPTION.getStatus(), ReturnMessageEnum.CLASSCATEXCEPTION.getMsg(), null);  
    }  

    //IO异常
    @ExceptionHandler(IOException.class)  
    @ResponseBody  
    public OutputResult iOExceptionHandler(IOException ex,HttpServletResponse response) {  
    	response.setStatus(400);
        ex.printStackTrace();
        return new OutputResult(ReturnMessageEnum.IOEXCEPTION.getStatus(), ReturnMessageEnum.IOEXCEPTION.getMsg(), null);
    }  
    //未知方法异常
    @ExceptionHandler(NoSuchMethodException.class)  
    @ResponseBody  
    public OutputResult noSuchMethodExceptionHandler(NoSuchMethodException ex,HttpServletResponse response) {  
    	response.setStatus(400);
        ex.printStackTrace();
        return new OutputResult(ReturnMessageEnum.NOSUCHMETHODEXCEPTION.getStatus(), ReturnMessageEnum.NOSUCHMETHODEXCEPTION.getMsg(), null);
    }  

    //数组越界异常
    @ExceptionHandler(IndexOutOfBoundsException.class)  
    @ResponseBody  
    public OutputResult indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex,HttpServletResponse response) { 
    	response.setStatus(400);
        ex.printStackTrace();
        return new OutputResult(ReturnMessageEnum.INDEXOUTOFBOUNDSEXCEPTION.getStatus(), ReturnMessageEnum.INDEXOUTOFBOUNDSEXCEPTION.getMsg(), null);
    }
    //400错误
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public OutputResult requestNotReadable(HttpMessageNotReadableException ex,HttpServletResponse response){
    	response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        System.out.println("400..requestNotReadable");
        ex.printStackTrace();
        return new OutputResult(ReturnMessageEnum.BADREQUEST.getStatus(), ReturnMessageEnum.BADREQUEST.getMsg(), null);
    }
    //400错误
    @ExceptionHandler({TypeMismatchException.class})
    @ResponseBody
    public OutputResult requestTypeMismatch(TypeMismatchException ex,HttpServletResponse response){
    	response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        System.out.println("400..TypeMismatchException");
        ex.printStackTrace();
        return new OutputResult(ReturnMessageEnum.BADREQUEST.getStatus(), ReturnMessageEnum.BADREQUEST.getMsg(), null);
    }
    //400错误
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public OutputResult requestMissingServletRequest(MissingServletRequestParameterException ex,HttpServletResponse response){
    	response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        System.out.println("400..MissingServletRequest");
        ex.printStackTrace();
        return new OutputResult(ReturnMessageEnum.BADREQUEST.getStatus(), ReturnMessageEnum.BADREQUEST.getMsg(), null);
    }
    //405错误
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public OutputResult request405(HttpServletResponse response){
    	response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        System.out.println("405...");
        return new OutputResult(ReturnMessageEnum.METHODNOTALLOWED.getStatus(), ReturnMessageEnum.METHODNOTALLOWED.getMsg(), null);
    }
    //406错误
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    @ResponseBody
    public OutputResult request406(HttpServletResponse response){
    	response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        System.out.println("406...");
        return new OutputResult(ReturnMessageEnum.NOTACCEPTABLE.getStatus(), ReturnMessageEnum.NOTACCEPTABLE.getMsg(), null);
    }
    //500错误
    @ExceptionHandler({ConversionNotSupportedException.class,HttpMessageNotWritableException.class})
    @ResponseBody
    public OutputResult server500(RuntimeException runtimeException,HttpServletResponse response){
    	response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        System.out.println("500...");
        return new OutputResult(ReturnMessageEnum.INTERNALSERVERERROR.getStatus(), ReturnMessageEnum.INTERNALSERVERERROR.getMsg(), null);
    }
}

/**
 * 
 */
package com.www.utils.cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 执行windows下的cmd指令
 * @author Shinelon
 *
 */
public class ExecuteCMDUtil {
	
	private static final String PREFIX_CMD="cmd.exe /c ";
	
	/**
	 * 执行cmd指令
	 * @param name cmd指令
	 */
	public static void executeCmd(String cmdCode){
		try {
			String cmd=PREFIX_CMD+cmdCode;
			Runtime rt = Runtime.getRuntime();
			Process p = rt.exec(cmd);
			InputStream in = p.getInputStream();
			InputStreamReader isr = new InputStreamReader(in);
	        BufferedReader br = new BufferedReader(isr);
	        String str = null;
	        while( (str = br.readLine()) != null){
	            System.out.println(str);
	        }
	        br.close();
	        isr.close();
	        in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 使用jdk自带的wsimport生成webservice客户端文件，有多个文件
	 * 执行语句是：wsimport -s src目录 -p 生成类所在包名 -keep wsdl发布地址
	 * @param catalog src目录
	 * @param packageName 生成类所在包名
	 * @param wsdlUrl wsdl发布地址
	 */
	public static void produceJDKWebServiceClientFiles(String catalog,String packageName,String wsdlUrl){
		if (catalog!=null&&"".equals(catalog)&&packageName!=null&&"".equals(packageName)&&wsdlUrl!=null&&"".equals(wsdlUrl)) {
			return;
		}
		File file=new File(catalog);
		if (!file.exists()||file.isFile()) {
			System.out.println("src目录错误");
			return;
		}
		String cmd="wsimport -s "+catalog+" -p "+packageName+" -keep "+wsdlUrl;
		executeCmd(cmd);
	}
	/**
	 * 使用axis2的wsdl2java生成webservice客户端文件，只有三个文件，*Stub和*CallbackHandler、build.xml
	 * 执行语句是：%AXIS2_HOME%\bin\wsdl2java -uri wsdl发布地址 -p 生成文件包名 -o 项目src的路径
	 * @param catalog src目录，不包含src这一级
	 * @param packageName 生成类所在包名
	 * @param wsdlUrl wsdl发布地址
	 */
	public static void produceAxis2WebServiceClientFiles(String catalog,String packageName,String wsdlUrl){
		if (catalog!=null&&"".equals(catalog)&&packageName!=null&&"".equals(packageName)&&wsdlUrl!=null&&"".equals(wsdlUrl)) {
			return;
		}
		File file=new File(catalog);
		if (!file.exists()||file.isFile()) {
			System.out.println("src目录错误");
			return;
		}
		String cmd="%AXIS2_HOME%\\bin\\wsdl2java -uri "+wsdlUrl+" -p "+packageName+" -o "+catalog;
		executeCmd(cmd);
	}
}

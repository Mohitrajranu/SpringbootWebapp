package com.restfulservice.util;

import java.net.*;
import java.util.*;

import org.json.JSONObject;

import java.io.*;

// TODO: Auto-generated Javadoc
/**
 * The Class DNS.
 */
public class DNS

 {
    
    /**
     * The main method.
     *
     * @param args the arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception

    {
    	try {
			String uri = "https://doctiger.com/doctiger-for-hr?EMAIL=vivek@bizlem.com&utm_source=HRMailTemplate";
			StringBuilder email = new StringBuilder();
			email.append(((uri.split("\\?", -1)[1]).split("\\&", -1)[0]).split("\\=", -1)[1]);
			 StringBuilder templatename = new StringBuilder();
			 templatename.append(((uri.split("\\?", -1)[1]).split("\\&", -1)[1]).split("\\=", -1)[1]);
			 StringBuilder urlClick = new StringBuilder();
			 urlClick.append(uri.split("\\?", -1)[0]);
			
			 System.out.println(urlClick.toString());
			 System.out.println(templatename.toString());
			 System.out.println(email.toString());
			 JSONObject dependencyjson= new JSONObject();
				dependencyjson.put("email", "salesautoconvertuser1@gmail.com");
				dependencyjson.put("group", "G1");
				dependencyjson.put("MailTempName", "15nov");
				
				 String response=BizUtil.callPostAPIJSON("https://bizlem.io:8083/portal/LeadAutoConvert_To_Key", dependencyjson);
				 if(!BizUtil.isNullString(response)){
					 StringBuilder responseApi=new StringBuilder(response);
					 System.out.println("Response returned is "+response);
				 JSONObject outresponse = new JSONObject(responseApi.toString());
				 System.out.println("Response returned is "+outresponse.getString("To"));
				 }else{
					 System.out.println("Response returned is "+response);
				 }
				 /*Scanner s = new Scanner(System.in);
Response returned is {"MailTempName":"Sales Template","email":"jobs@bizlem.com","group":"DoctigerMailTemplate","To":"Email"}
			System.out.println("Enter the Domain`enter code here` Name");
			String domainName = s.nextLine();
			System.out.println("Domain into IP");
			System.out.println(InetAddress.getByName(domainName).getHostAddress());

			System.out.println("Enter the IP Name");
			String ipName = s.nextLine();
			System.out.println("IP into Domain");
			System.out.println(InetAddress.getByName(ipName).getHostName());     */
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   

    }
 }
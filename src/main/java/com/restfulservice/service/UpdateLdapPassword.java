package com.restfulservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.restfulservice.util.BizUtil;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
@Service
public class UpdateLdapPassword {
	private static final Logger log = LoggerFactory.getLogger(UpdateLdapPassword.class);
	@Value("${app.ldapUrl}")
	 private String ldapUrl;
	private DirContext dirContext = null;
	ModificationItem[] modItemsOne = new ModificationItem[1];
	
	public void updateLdap(String userId,String userpassword){
		try
		{
     //  String url = "ldap://development.bluealgo.com:389";
	  // String conntype = "simple";
  	    String AdminDn  = "cn=admin,dc=portal,dc=com";
	    String password = "portal@99";
        //Hashtable<String, String> environment = new Hashtable<String, String>();
 
		Hashtable<String, String> environmentVar = new Hashtable<String, String>();
 
		environmentVar.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		environmentVar.put(Context.PROVIDER_URL, ldapUrl);
		//environmentVar.put(Context.SECURITY_AUTHENTICATION, conntype);
		environmentVar.put(Context.SECURITY_PRINCIPAL,AdminDn);
		environmentVar.put(Context.SECURITY_CREDENTIALS, password);
 
		dirContext = new InitialDirContext(environmentVar);
		log.info(" updateLdap context created");
        
      //  String userIdForDN = "leadautoconvert@gmail.com";
		String passwdToUpdate = BizUtil.encryptLdapPassword("SHA", userpassword);
		//entryUpdateInLdap entryUpdateLdap = new entryUpdateInLdap();
		if(updateStatus(userId,passwdToUpdate))
		{
			log.info(" updateLdap entry update completed");
		}
		else
		{
			log.info(" updateLdap entry update failed");	
		}
		}
		catch(Exception ee)
		{
			log.error("updateLdap "+ee.getMessage());
			//ee.printStackTrace();
		}
		finally {
	        try {
	            if (dirContext != null) {
	            	
	            	dirContext.close();
	            	dirContext = null;
	            }
	        }
	        catch (Exception ignored) {
	            // Ignore.
	        }
	    }
	}
	public boolean updateStatus(String userId,String status )
	{
	boolean flag = false;
	String entryDN = "uid="+userId.trim()+",ou=People,dc=portal,dc=com";
                        //uid=142,ou=alzebra,dc=mathsdep,dc=college,dc=org,dc=in  
	try
	{
		modItemsOne[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                                                      new BasicAttribute("userPassword",status));
		dirContext.modifyAttributes(entryDN, modItemsOne);	
	    flag = true;
	}
	catch(Exception exception)
	{
		exception.printStackTrace();	
	}
	return flag;
}
}

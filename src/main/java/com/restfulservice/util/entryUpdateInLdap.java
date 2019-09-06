package com.restfulservice.util;


/*ldap.host=localhost
ldap.rootdn=cn=admin,dc=portal,dc=com
ldap.password=portal@99
ldap.basedn=ou=People,dc=portal,dc=com

ldapsearch -x -b "dc=portal,dc=com" -s sub "objectclass=*"


ldapmodify -h localhost -x -W -D “cn=leadconvert,dc=portal,dc=com”

dn: uid=leadautoconvert@gmail.com,ou=People,dc=portal,dc=com*/

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
public class entryUpdateInLdap {
private DirContext dirContext = null;
ModificationItem[] modItemsOne = new ModificationItem[1];
	public entryUpdateInLdap()
	{
    System.out.println("constructer called");
		try
		{
       String url = "ldap://development.bizlem.io:389";
	  // String conntype = "simple";
  	 String AdminDn  = "cn=admin,dc=portal,dc=com";
	   String password = "portal@99";
        Hashtable<String, String> environment = new Hashtable<String, String>();
 
		Hashtable<String, String> environmentVar = new Hashtable<String, String>();
 
		environmentVar.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		environmentVar.put(Context.PROVIDER_URL, url);
		//environmentVar.put(Context.SECURITY_AUTHENTICATION, conntype);
		environmentVar.put(Context.SECURITY_PRINCIPAL,AdminDn);
		environmentVar.put(Context.SECURITY_CREDENTIALS, password);
 
		dirContext = new InitialDirContext(environmentVar);
        System.out.println("context created");
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
		}
	}
 
	
	
	public static void main(String[] args) {
		//dn: uid=leadautoconvert@gmail.com,ou=People,dc=portal,dc=com
		String userIdForDN = "leadautoconvert@gmail.com";
		String StatusToUpdate = BizUtil.encryptLdapPassword("SHA", "bizlem12!@");
		entryUpdateInLdap entryUpdateLdap = new entryUpdateInLdap();
		if(entryUpdateLdap.updateStatus(userIdForDN,StatusToUpdate))
		{
		System.out.println("entry update completed");
		}
		else
		{
		System.out.println("entry update failed");	
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
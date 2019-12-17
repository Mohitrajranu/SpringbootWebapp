package com.restfulservice.util;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.naming.*;
import javax.naming.directory.*;

// TODO: Auto-generated Javadoc
/**
 * The Class SMTPMXLookup.
 */
public class SMTPMXLookup {
  
     /**
      * Gets the mx.
      *
      * @param hostName the host name
      * @return the mx
      * @throws NamingException the naming exception
      */
     private static String getMX( String hostName )
         throws NamingException {
     // Perform a DNS lookup for MX records in the domain
    	 StringBuilder mailhost= null;
     Hashtable env = new Hashtable();
     env.put("java.naming.factory.initial",
             "com.sun.jndi.dns.DnsContextFactory");
     DirContext ictx = new InitialDirContext( env );
     Attributes attrs = ictx.getAttributes
                           ( hostName, new String[] { "MX" });
     Attribute attr = attrs.get( "MX" );

     // if we don't have an MX record, try the machine itself
     if (( attr == null ) || ( attr.size() == 0 )) {
       attrs = ictx.getAttributes( hostName, new String[] { "A" });
       attr = attrs.get( "A" );
       if( attr == null )
            throw new NamingException
                     ( "No match for name '" + hostName + "'" );
     }
         // Huzzah! we have machines to try. Return them as an array list
     // NOTE: We SHOULD take the preference into account to be absolutely
     //   correct. This is left as an exercise for anyone who cares.
     //ArrayList res = new ArrayList();
     NamingEnumeration en = attr.getAll();
     mailhost = new StringBuilder();
     while ( en.hasMore() ) {
        
        String x = (String) en.next();
        String f[] = x.split( " " );
        //  THE fix *************
        
        if (f.length == 1){
        	if(f[0].contains("google")){
        		mailhost.append("smtp.gmail.com");
        	}else if(f[0].contains("outlook")){
        		mailhost.append("smtp-mail.outlook.com");
        	}
        	
        	
        	else{
            mailhost.append(f[0]);
        	}
      //  break;
        }
        else if ( f[1].endsWith( "." ) ){
        	if(f[1].contains("google")){
        		mailhost.append("smtp.gmail.com");
        	}else if(f[1].contains("outlook")){
        		mailhost.append("smtp-mail.outlook.com");
        	}else
        	{
            mailhost.append(f[1].substring( 0, (f[1].length() - 1)));
        	}
        //    break;
        	
        }
        else{
        	if(f[1].contains("google")){
        		mailhost.append("smtp.gmail.com");
        	}else if(f[1].contains("outlook")){
        		mailhost.append("smtp-mail.outlook.com");
        	}else{
            mailhost.append(f[1]);
            
        	}
        	//break;
        }
        //  THE fix *************            
     //   res.add( mailhost );
     }
     return mailhost.toString();
     }

   /**
    * Checks if is address valid.
    *
    * @param address the address
    * @return the string
    */
   public static String isAddressValid( String address ) {
     // Find the separator for the domain name
     int pos = address.indexOf( '@' );

     // If the address does not contain an '@', it's not valid
     if ( pos == -1 ) return "";

     // Isolate the domain/machine name and get a list of mail exchangers
     String domain = address.substring( ++pos );
     
     
     String mxList = null;
     
   
     try {
        mxList = getMX( domain );
     }
     catch (NamingException ex) {
        return "";
     }
     
     
     return mxList;
     }

   /**
    * The main method.
    *
    * @param args the arguments
    */
   public static void main( String args[] ) {
     String testData[] = {
         "welcome@leadaconvert.com"
         };

     for ( int ctr = 0 ; ctr < testData.length ; ctr++ ) {
      
            String mailhost =  isAddressValid( testData[ ctr ] );
            System.out.println("mailhost returned is "+mailhost);
     }
     }
} 

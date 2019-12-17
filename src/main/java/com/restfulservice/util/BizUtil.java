package com.restfulservice.util;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONObject;
// TODO: Auto-generated Javadoc

/**
 * The Class BizUtil.
 */
public class BizUtil {

	/** The iv. */
	private String iv = "fedcba9876543210";
	
	/** The key. */
	private String key = "";
	
	/** The cipher. */
	private Cipher cipher = null;
	
	/** The key spec. */
	private SecretKeySpec keySpec = null;
	
	/** The iv spec. */
	private IvParameterSpec ivSpec = null;

	/**
	 * Instantiates a new biz util.
	 */
	public BizUtil(){
		try {
			this.key = "D4:6E:AC:3F:F0:BE";

			// Make sure the key length should be 16
			int len = this.key.length();
			if (len < 16) {
				int addSpaces = 16 - len;
				for (int i = 0; i < addSpaces; i++) {
					this.key = this.key + " ";
				}
			} else {
				this.key = this.key.substring(0, 16);
			}
			this.keySpec = new SecretKeySpec(this.key.getBytes(), "AES");
			this.ivSpec = new IvParameterSpec(iv.getBytes());
			this.cipher = Cipher.getInstance("AES/CBC/NoPadding");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Bytes to Hexa conversion.
	 *
	 * @param data the data
	 * @return the string
	 */
	public String bytesToHex(byte[] data) {
		if (data == null) {
			return null;
		} else {
			int len = data.length;
			String str = "";
			for (int i = 0; i < len; i++) {
				if ((data[i] & 0xFF) < 16)
					str = str + "0"
							+ java.lang.Integer.toHexString(data[i] & 0xFF);
				else
					str = str + java.lang.Integer.toHexString(data[i] & 0xFF);
			}
			return str;
		}
	}

	/**
	 * Encrpt the goven string.
	 *
	 * @param plainData the plain data
	 * @return the string
	 */
	public  String encrypt(String plainData){

		byte[] encrypted = null;
		try {
			// Make sure the plainData length should be multiple with 16
			int len = plainData.length();
			int q = len / 16;
			int addSpaces = ((q + 1) * 16) - len;
			for (int i = 0; i < addSpaces; i++) {
				plainData = plainData + " ";
			}

			this.cipher.init(Cipher.ENCRYPT_MODE, this.keySpec, this.ivSpec);
			encrypted = cipher.doFinal(plainData.getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bytesToHex(encrypted);
	}

	/**
	 * Hex to bytes.
	 *
	 * @param str the str
	 * @return the byte[]
	 */
	public byte[] hexToBytes(String str) {
		if (str == null) {
			return null;
		} else if (str.length() < 2) {
			return null;
		} else {
			int len = str.length() / 2;
			byte[] buffer = new byte[len];
			for (int i = 0; i < len; i++) {
				buffer[i] = (byte) Integer.parseInt(
						str.substring(i * 2, i * 2 + 2), 16);
			}
			return buffer;
		}
	}

	/**
	 * Decrypt the given excrypted string.
	 *
	 * @param encrData the encr data
	 * @return the string
	 * @throws Exception the exception
	 */
	public String decrypt(String encrData) throws Exception {
		this.cipher.init(Cipher.DECRYPT_MODE, this.keySpec, this.ivSpec);
		byte[] outText = this.cipher.doFinal(hexToBytes(encrData));

		String decrData = new String(outText).trim();
		return decrData;
	}

	
	
	/**
	 * Encrypt ldap password.
	 *
	 * @param algorithm the algorithm
	 * @param _password the password
	 * @return the string
	 */
	public static String encryptLdapPassword(String algorithm, String _password) {
		String sEncrypted = _password;
		if ((_password != null) && (_password.length() > 0)) {
			boolean bMD5 = algorithm.equalsIgnoreCase("MD5");
			boolean bSHA = algorithm.equalsIgnoreCase("SHA")
					|| algorithm.equalsIgnoreCase("SHA1")
					|| algorithm.equalsIgnoreCase("SHA-1");
			if (bSHA || bMD5) {
				String sAlgorithm = "MD5";
				if (bSHA) {
					sAlgorithm = "SHA";
				}
				try {
					MessageDigest md = MessageDigest.getInstance(sAlgorithm);
					md.update(_password.getBytes("UTF-8"));
					sEncrypted = "{" + sAlgorithm + "}"
							+ (new BASE64Encoder()).encode(md.digest());
				} catch (Exception e) {
					sEncrypted = null;
					//logger.error(e, e);
				}
			}
		}
		return sEncrypted;
	}
	
	/**
	 * Find last index.
	 *
	 * @param str the str
	 * @param x the x
	 * @return the int
	 */
	public static int findLastIndex(String str, Character x) 
	{ 
	 int index = -1; 
	 try {
		for (int i = 0; i < str.length(); i++) 
		     if (str.charAt(i) == x) 
		         index = i;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	 return index; 
	} 

	/**
	 * Replace last char.
	 *
	 * @param str the str
	 * @param x the x
	 * @return the string
	 */
	public static String replaceLastChar(String str, Character x) {

		try {
			int index = findLastIndex(str, x); 
			 if (index == -1) {
			     System.out.println("Character not found"); 
			 }else {
			    
			     str = str.substring(0, index) + "@"
			    		 + str.substring(index+1,str.length());
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return str;
	}
	
	/** The Constant alphaNumeric. */
	private static final String alphaNumeric= "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
	
	/**
	 * Generate random.
	 *
	 * @return the string
	 */
	public static String generateRandom() {
		String randomStr = null;
		
		StringBuilder strBuild= null;
		//int count=6;
		
		/*while(count!=0) {*/
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

				String dateString = format.format( new Date()   );
				
				strBuild= new StringBuilder();
				int character= (int) (Math.random()*alphaNumeric.length());
				strBuild.append(dateString);
				strBuild.append(character);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		/*	count--;
		}
		*/
			randomStr= strBuild.toString();
		return randomStr;
	}
	
	/**
	 * Checks if is null string.
	 *
	 * @param p_text the p text
	 * @return true, if is null string
	 */
	public static boolean isNullString (String p_text){
		if(p_text != null && p_text.trim().length() > 0 && !"null".equalsIgnoreCase(p_text.trim())){
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * Checks if is blank.
	 *
	 * @param cs the cs
	 * @return true, if is blank
	 */
	public static boolean isBlank(final CharSequence cs) {
		       int strLen;
		      if (cs == null || (strLen = cs.length()) == 0) {
		           return true;
		       }
		       for (int i = 0; i < strLen; i++) {
	            if (!Character.isWhitespace(cs.charAt(i))) {
		              return false;
		           }
	       }
	        return true;
	    }
	
	/**
	 * Call post APIJSON.
	 *
	 * @param urlstr the urlstr
	 * @param Obj the obj
	 * @return the string
	 */
	public static String callPostAPIJSON(String urlstr, JSONObject Obj)  {



		StringBuilder response =null;
		int responseCode = 0;

		try {

		HttpURLConnection con = null;


		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		return null;
		}

		public void checkClientTrusted(X509Certificate[] certs, String authType) {
		}

		public void checkServerTrusted(X509Certificate[] certs, String authType) {
		}

		} };

		try {
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		}

		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) {
		return true;
		}
		};
		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);


		URL url = new URL(urlstr);
		con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");

		con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
		con.setRequestProperty("Accept-Charset", "UTF-8");

		con.setDoOutput(true);

		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
		writer.write(Obj.toString());
		writer.close();
		wr.close();

		responseCode = con.getResponseCode();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		response = new StringBuilder();

		while ((inputLine = in.readLine()) != null) {
		response.append(inputLine);
		}
		in.close();



		}
		catch (Exception e) {
		e.printStackTrace();
		//e.getMessage();
		}
		return response.toString();

		}
	
	
}

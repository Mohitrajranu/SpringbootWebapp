package com.restfulservice.util;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;



// TODO: Auto-generated Javadoc
/**
 * The Class CallPythonApi.
 */
public class CallPythonApi {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		//Thu Dec 12 15:42:06 IST 2019
		
			try {
				DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
				SimpleDateFormat f = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
				 String dateStr = f.format(new Date());
			 
				Date pastDate = (Date)formatter.parse(dateStr);
				System.out.println(pastDate.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		String randomNum= BizUtil.generateRandom();
		System.out.println(randomNum);
		String refreshToken="1\\0g-Ho67DRVLmUCgYIARAAGBASNwF-L9IrkJIUj170kKrDHhe2bf5ucKr1TFPx1fLS8c4Kk7zXdIXmUdtZZAy39DYHe-1KTa0Zw2g";
		String receivedUrlString = "http://somewebsite.com/somepage.asp";
		JSONObject outresponse = new JSONObject();
		outresponse.put("refreshToken",receivedUrlString);
		System.out.println(outresponse.get("refreshToken"));
		
		String cleanedUrlString  = outresponse.get("refreshToken").toString();
		System.out.println(cleanedUrlString);
		
		/*String serverUrl = "http://35.221.160.146:5012/?file=/home/ubuntu/pyscript/ESSAR_FILES/Pricing Approval Sheet 2.xlsx";
	        RestTemplate restTemplate = new RestTemplate();
	       
	        ResponseEntity<String> responseG = restTemplate.getForEntity(serverUrl, String.class);
	        JsonParser parser = new JsonParser();
	        
	        JsonObject obj = (JsonObject) parser.parse(responseG.getBody());
	        System.out.println(responseG.getBody());

		 HttpHeaders headers = null;
		JSONObject requestPassword = null;
		
		 HttpEntity<String> passwordentity = null;
		 RestTemplate restPasswordTemplate = null;
		 ResponseEntity<String> passwordResponse = null;
		try {
			String serverUrl = "http://bluealgo.com:8087/InvoiceAutoProcessUI/Userpassword";
			requestPassword = new JSONObject();
			requestPassword.put("username", "rahul@bizlem.com");
        	headers = new HttpHeaders();
        	headers.setContentType(MediaType.APPLICATION_JSON);
        	passwordentity = new HttpEntity<String>(requestPassword.toString(), headers);
        	restPasswordTemplate = new RestTemplate();
        	passwordResponse = restPasswordTemplate
		        	  .exchange(serverUrl, HttpMethod.POST, passwordentity, String.class);
        	
        	System.out.println(passwordResponse.getBody().toString());
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		*/	
	
	}
}
/*
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
 
import com.ducat.springboot.ehcache.dao.ProductRepo;
import com.ducat.springboot.ehcache.model.Product;
 
@Service
public class ProductService {
    @Autowired
    private ProductRepo prepo;
 
   //  @Cacheable annotation adds the caching behaviour. 
    // If multiple requests are received, then the method won't be repeatedly executed, instead, the results are shared from cached storage.
    @Cacheable(value="productsCache", key="#p0")
    public Optional<Product> getProductById(int productId) {
        return prepo.findById(productId);
    }
 
    
    // @CachePut annotation updates the cached value.
    @CachePut(value="productsCache")
    public Product updateProductById(Product product, String productName) {
        product.setName(productName);
        prepo.save(product);
 
        return product;
    }
 
   
    // @CacheEvict annotation removes one or all entries from cached storage.
    // <code>allEntries=true</code> attribute allows developers to purge all entries from the cache.
    @CacheEvict(value="productsCache", key="#p0")
    public void deleteProductById(int productId) {
        prepo.deleteById(productId);
    }
}
*/
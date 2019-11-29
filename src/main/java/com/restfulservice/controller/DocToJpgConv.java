package com.restfulservice.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/doctojpeg")
public class DocToJpgConv {

	public static final Logger logger = LoggerFactory.getLogger(DocToJpgConv.class);
	
	
	
	
	
	@RequestMapping(value = "/decodeString", method = RequestMethod.GET)
	public String decodeString(@RequestParam String encodeStr){
		String s = null;
		try{
		byte[] bytes = encodeStr.getBytes("UTF-8");
		String encoded = Base64.getEncoder().encodeToString(bytes);
		byte[] decoded = Base64.getDecoder().decode(encoded);
		s = new String(decoded, "UTF-8");
		logger.info("Source content: " + s);
		
		//System.out.println(decoded.toString());
	//	getDecodeData("هتاف للترحيب");
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return s;
		/*
		String decoded = null;
       
		try{
			
	        byte[] authBytes = encodeStr.getBytes(StandardCharsets.UTF_8);
	        String encoded = Base64.getEncoder().encodeToString(authBytes);
			
			
			byte[] contentInBytes = Base64.getDecoder().decode(encoded);
			decoded = new String(contentInBytes, "UTF-8");
			System.out.println("Source content: " + new String(contentInBytes, "UTF-8"));//Hello Việt Nam			
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return decoded;
	*/}
	
	
	@RequestMapping(value = "/convertApi", method = RequestMethod.GET)
	public String convertedFileName(@RequestParam String filename){
		
		ProcessBuilder processBuilder = new ProcessBuilder();

		StringBuilder output = null;
		// -- Linux --

		// Run a shell command
		//processBuilder.command("bash", "-c", "ls /home/mkyong/");

		// Run a shell script
		processBuilder.command("/home/ubuntu/generationTomcat/apache-tomcat-8.5.41/DocToJpegConverter.sh",filename);

		
		// -- Windows --

		// Run a command
		//processBuilder.command("cmd.exe", "/c", "dir C:\\Users\\mkyong");

		// Run a bat file
		//processBuilder.command("C:\\Users\\mkyong\\hello.bat");

		try {

			Process process = processBuilder.start();

			output = new StringBuilder();

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

			int exitVal = process.waitFor();
			if (exitVal == 0) {
				logger.info("convertedFileName "+output.toString());
				//System.exit(0);
			} else {
				//abnormal...
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return output.toString();
	}
	
	@RequestMapping(value="/postpdfTrackApi" ,method = RequestMethod.POST)
	public Map<String, Object> pdfTrack(@RequestBody String json){
		 Map<String, Object> jsonOut = new HashMap<>();
		 JSONObject inputJson = null;
		 
		 
		ProcessBuilder processBuilder = new ProcessBuilder();

		StringBuilder output = null;
		

		try {
			inputJson = new JSONObject(json);
			
            processBuilder.command("/home/ubuntu/generationTomcat/apache-tomcat-8.5.41/MailTracking.sh",inputJson.get("filename").toString());
			Process process = processBuilder.start();

			output = new StringBuilder();

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

			int exitVal = process.waitFor();
			if (exitVal == 0) {
				logger.info("pdfTrack MailTracking  "+output.toString());
				//System.exit(0);
			} else {
				//abnormal...
			}
			JSONObject outputJson = new JSONObject(output.toString());
           jsonOut.put("outputdata", outputJson.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonOut;
	}
	
	@RequestMapping(value="/postpdfprevTrackApi" ,method = RequestMethod.POST)
	public Map<String, Object> pdfprevTrack(@RequestBody String json){
		 Map<String, Object> jsonOut = new HashMap<>();
		 JSONObject inputJson = null;
		 
		 
		ProcessBuilder processBuilder = new ProcessBuilder();

		StringBuilder output = null;
	
		try {
			inputJson = new JSONObject(json);
			if(inputJson.has("projectName")){
			    processBuilder.command("/home/ubuntu/generationTomcat/apache-tomcat-8.5.41/PreviousSlingMailTracking.sh",inputJson.get("filename").toString(),inputJson.get("logfilename").toString());
			}else{
            processBuilder.command("/home/ubuntu/generationTomcat/apache-tomcat-8.5.41/PreviousMailTracking.sh",inputJson.get("filename").toString(),inputJson.get("logfilename").toString());
			
			}Process process = processBuilder.start();

			output = new StringBuilder();

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

			int exitVal = process.waitFor();
			if (exitVal == 0) {
				logger.info("pdfprevTrack "+output.toString());
				//System.exit(0);
			} else {
				//abnormal...
			}
			JSONObject outputJson = new JSONObject(output.toString());
           jsonOut.put("outputdata", outputJson.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonOut;
	}
	@RequestMapping(value="/pretimepdfTrackApi" ,method = RequestMethod.POST)
	public Map<String, Object> prevTimeTrack(@RequestBody String json){
		 Map<String, Object> jsonOut = new HashMap<>();
		 JSONObject inputJson = null;
		 
		 
		ProcessBuilder processBuilder = new ProcessBuilder();

		StringBuilder output = null;
	
		try {
			inputJson = new JSONObject(json);
            if(inputJson.has("projectName")){
            	processBuilder.command("/home/ubuntu/generationTomcat/apache-tomcat-8.5.41/prevSlingLogFile.sh",inputJson.get("filename").toString(),inputJson.get("logfilename").toString(),inputJson.get("timestamp").toString());
			}else{
            processBuilder.command("/home/ubuntu/generationTomcat/apache-tomcat-8.5.41/prevLogFile.sh",inputJson.get("filename").toString(),inputJson.get("logfilename").toString(),inputJson.get("timestamp").toString());
			}Process process = processBuilder.start();

			output = new StringBuilder();

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

			int exitVal = process.waitFor();
			if (exitVal == 0) {
				logger.info("prevTimeTrack "+output.toString());
				//System.exit(0);
			} else {
				//abnormal...
			}
			JSONObject outputJson = new JSONObject(output.toString());
           jsonOut.put("outputdata", outputJson.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonOut;
	}
	@RequestMapping(value="/postmailTrackApi" ,method = RequestMethod.POST)
	public Map<String, Object> save(@RequestBody String json){
		 Map<String, Object> jsonOut = new HashMap<>();
		 JSONObject inputJson = null;
		 
		 
		ProcessBuilder processBuilder = new ProcessBuilder();

		StringBuilder output = null;
		
		try {
			inputJson = new JSONObject(json);
			
			
            processBuilder.command("/usr/local/tomcat9/MailTracking.sh",inputJson.get("filename").toString());
			Process process = processBuilder.start();

			output = new StringBuilder();

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

			int exitVal = process.waitFor();
			if (exitVal == 0) {
				logger.info("prevTimeTrack "+output.toString());
				//System.exit(0);
			} else {
				//abnormal...
			}
			JSONObject outputJson = new JSONObject(output.toString());
           jsonOut.put("outputdata", outputJson.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonOut;
	}
	
	@RequestMapping(value="/postprevmailTrackApi" ,method = RequestMethod.POST)
	public Map<String, Object> saveprev(@RequestBody String json){
		 Map<String, Object> jsonOut = new HashMap<>();
		 JSONObject inputJson = null;
		 
		 
		ProcessBuilder processBuilder = new ProcessBuilder();

		StringBuilder output = null;
		
		try {
			inputJson = new JSONObject(json);
			
            processBuilder.command("/usr/local/tomcat9/PreviousMailTracking.sh",inputJson.get("filename").toString(),inputJson.get("logfilename").toString());
			Process process = processBuilder.start();

			output = new StringBuilder();

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

			int exitVal = process.waitFor();
			if (exitVal == 0) {
				logger.info("postprevmailTrackApi "+output.toString());
				//System.exit(0);
			} else {
				//abnormal...
			}
			JSONObject outputJson = new JSONObject(output.toString());
           jsonOut.put("outputdata", outputJson.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonOut;
	}
	
	@RequestMapping(value="/pretimevmailTrackApi" ,method = RequestMethod.POST)
	public Map<String, Object> fetchprev(@RequestBody String json){
		 Map<String, Object> jsonOut = new HashMap<>();
		 JSONObject inputJson = null;
		 
		 
		ProcessBuilder processBuilder = new ProcessBuilder();

		StringBuilder output = null;
			try {
			inputJson = new JSONObject(json);
			
            processBuilder.command("/usr/local/tomcat9/prevLogFile.sh",inputJson.get("filename").toString(),inputJson.get("logfilename").toString(),inputJson.get("timestamp").toString());
			Process process = processBuilder.start();

			output = new StringBuilder();

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

			int exitVal = process.waitFor();
			if (exitVal == 0) {
				logger.info("pretimevmailTrackApi "+output.toString());
				//System.exit(0);
			} else {
				//abnormal...
			}
			JSONObject outputJson = new JSONObject(output.toString());
           jsonOut.put("outputdata", outputJson.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonOut;
	}

	
	@RequestMapping(value = "/mailTrackpi", method = RequestMethod.GET)
	public String trackedFileName(@RequestParam String filename){
		
		ProcessBuilder processBuilder = new ProcessBuilder();

		StringBuilder output = null;
		// -- Linux --

		// Run a shell command
		//processBuilder.command("bash", "-c", "ls /home/mkyong/");

		// Run a shell script
		processBuilder.command("/usr/local/tomcat9/MailTracking.sh",filename);

		
		// -- Windows --

		// Run a command
		//processBuilder.command("cmd.exe", "/c", "dir C:\\Users\\mkyong");

		// Run a bat file
		//processBuilder.command("C:\\Users\\mkyong\\hello.bat");

		try {

			Process process = processBuilder.start();

			output = new StringBuilder();

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

			int exitVal = process.waitFor();
			if (exitVal == 0) {
				logger.info("mailTrackpi "+output.toString());
				//System.exit(0);
			} else {
				//abnormal...
			}
			
           
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return output.toString();
	}

}
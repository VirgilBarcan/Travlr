package controllers;

import play.*;
import play.mvc.*;
import views.html.*;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.text.WordUtils;

public class Application extends Controller {
	//Application is run only once so the values remain the same
	//private static String controller = "home";
	//private static String method = "index";
	private static String defaultController;
	private static Method defaultMethod;
	
    private static String sanitizeUrl(String url){
        //$-_.+!*'(),{}|\\^~[]`"><#%;/?:@&=
    	StringBuilder sanitizedUrl = new StringBuilder();
    	
    	for (int i=0; i<url.length(); ++i){    		
    		char c = url.charAt(i);
    		
    		if (Character.isLetterOrDigit(c)){
    			sanitizedUrl.append(c);
    			continue;
    		}
    		if ("$-_.+!*'(),{}|\\^~[]`\"><#%;/?:@&=".indexOf(c)!=-1){
    			sanitizedUrl.append(c);
    			continue;
    		}
    	}
        return sanitizedUrl.toString();
    }
    
    private static ArrayList<String> parseUrl(String url){
    	String tokens[];
    	
    	url = sanitizeUrl(url);
    	tokens = url.split("/");
    	return new ArrayList<String>(Arrays.asList(tokens));
    }
    
    public static Result index(String url) {
    	String controllerName = "controllers.Home";
    	String methodName = "index";
    	ArrayList<String> urlTokens = parseUrl(url);
        
        
        String cwd = System.getProperty("user.dir");
        File appDir = new File(cwd, "app");
        File controllersDir = new File(appDir, "controllers");
        
        if (new File(controllersDir.getAbsolutePath(), urlTokens.get(0) + ".java").exists()){
        	//TODO: check if controller was compiled (get name from there - check case)
        	controllerName = "controllers." + WordUtils.capitalize(urlTokens.get(0));
        	urlTokens.remove(0);
        }
        
        ClassLoader classLoader = Application.class.getClassLoader();
        Class controllerClass = null;
        Method method = null;
        
        try{
        	//solve case problem
        	controllerClass = classLoader.loadClass(controllerName);
        }
        catch (ClassNotFoundException e){
        	return ok(index.render("Method not found"));
        	//render 404 not found
        }
        
        try{
        	method = controllerClass.getMethod(methodName);
        	
        	return (Result)method.invoke(null);
        }
        catch (Exception e){ //NoSuchMethodException or SecurityException
            return ok(index.render("Method not found"));
            //render 404 not found
        }

        //return ok(index.render(controllerName));
        //return ok(index.render("Your new application is ready."));
    }

}

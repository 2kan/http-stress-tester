package com.b2kan.stresstest;
import java.io.*;
import java.net.*;
import java.util.*;

public class URLs {
	/**
	 * Sends request to <code>url</code> with <code>params</code> as POST data.
	 * 
	 * @param	url		address to send request to
	 * @param	params	parameters to send in POST data
	 * 
	 * @return	data from web page
	 */
	public String post(String url, Map<String, String> params) {

        //Check if Valid URL
        if(!url.toLowerCase().startsWith("http://"))
        	url	= "http://" + url;
        
        StringBuilder bldr = new StringBuilder();

        try {
            //Build the post data
            StringBuilder post_data = new StringBuilder();

            //Build the posting variables from the map given
            for(Iterator<?> iter = params.entrySet().iterator(); iter.hasNext();) {
                Map.Entry<?, ?> entry = (Map.Entry<?, ?>) iter.next();
                String key = (String) entry.getKey();
                String value = (String)entry.getValue();

                if(key.length() > 0 && value.length() > 0) {

                    if(post_data.length() > 0) post_data.append("&");

                    post_data.append(URLEncoder.encode(key, "UTF-8"));
                    post_data.append("=");
                    post_data.append(URLEncoder.encode(value, "UTF-8"));
                }
            }

            // Send data
            URL remote_url = new URL(url);
            URLConnection conn = remote_url.openConnection();
            
            // IMPORTANT TO DEFLECT BOT DETECTION
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(post_data.toString());
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = rd.readLine()) != null) {
                bldr.append(inputLine);
            }
            wr.close();
            rd.close();
        } catch (ConnectException e) {
        	print("Could not connect, is target available?");
    		Main.endProgram(false);
		} catch (UnknownHostException e) {
			print("Invalid target.");
    		Main.endProgram(false);
		} catch (IllegalArgumentException e) {
			print("Invalid target.");
    		Main.endProgram(false);
		} catch (IOException e) {
			if(e.getMessage().contains("Server returned HTTP response code: 403")) {
				print("Server responded with 403: Forbidden.");
        		Main.endProgram(false);
			} else if(e.getMessage().contains("Server returned HTTP response code: 405")) {
				print("Server responded with 405: Method not allowed.");
        		Main.endProgram(false);
			}
        } catch (Exception e) {
        	// Shit hit the fan wrong, tell the user
        	print(e.getMessage());
    		Main.endProgram(false);
        }

        return bldr.length() > 0 ? bldr.toString() : null;
    }

	/**
	 * Sends request to <code>url</code> with <code>params</code> as GET data.
	 * @param	url		address to send request to
	 * @param	params	parameters to send in GET data
	 * @return	data from web page
	 */
	public String get(String url, Map<String, String> params) {
	
	    //Check if Valid URL
        if(!url.toLowerCase().startsWith("http://"))
        	url	= "http://" + url;
        if(!url.endsWith("/"))
        	url	= url + "/";
	
	    StringBuilder bldr = new StringBuilder();
	
	    try {
	        //Build the post data
	        String get_data	= "?";

            //Build the posting variables from the map given
            for(Iterator<?> iter = params.entrySet().iterator(); iter.hasNext();) {
                Map.Entry<?, ?> entry = (Map.Entry<?, ?>) iter.next();
                String key = (String) entry.getKey();
                String value = (String)entry.getValue();

                get_data	+= key.replace(" ", "+") + "=" + value.replace(" ", "+") + "&";
            }
	        
	        // Send data
	        URL remote_url = new URL(url + get_data);
	        URLConnection conn = remote_url.openConnection();
	        conn.setDoOutput(true);
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	        wr.write(get_data.toString());
	        wr.flush();
	
	        // Get the response
	        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String inputLine;
	        while ((inputLine = rd.readLine()) != null) {
	            bldr.append(inputLine);
	        }
	        wr.close();
	        rd.close();
	    } catch (ConnectException e) {
	    	print("Could not connect, is target available?");
			Main.endProgram(false);
		} catch (UnknownHostException e) {
			print("Invalid target.");
    		Main.endProgram(false);
		} catch (IllegalArgumentException e) {
			print("Invalid target.");
    		Main.endProgram(false);
		} catch (IOException e) {
			if(e.getMessage().contains("Server returned HTTP response code: 403")) {
				print("Server responded with 403: Forbidden.");
        		Main.endProgram(false);
			} else if(e.getMessage().contains("Server returned HTTP response code: 405")) {
				print("Server responded with 405: Method not allowed.");
        		Main.endProgram(false);
			}
        } catch (Exception e) {
        	// Shit hit the fan wrong, tell the user
			print(e.getMessage());
    		Main.endProgram(false);
        }
	
	    return bldr.length() > 0 ? bldr.toString() : null;
	}
	
	private static void print(String text) {
		if(!Main.terminate) {
			System.out.println(text);
		}
	}
}

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainClass {
	private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
	private static final String CONTENT_LANGUAGE = "en-US";
	private static final String USER_ARENT = "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11";
	private static final String COOKIE = ""; //__cfduid=df83fae0487b4e39f73f8837ca250e35e1369247829
	
	private static final String SEARCH_URL = "http://www.cpasbien.me/recherche/";
	private static final String DL_TORRENT_URL = "http://www.cpasbien.me/dl-torrent";
	private static final String TORRENT_URL = "http://www.cpasbien.me/_torrents/";
	private static final String PARAMS = "champ_recherche=";
	
	public static void main(String args[]) throws Exception {
		Map<String, String> res = MainClass.getTorrents("bones");
		for(String s : res.keySet()){
			System.out.println(s + " > " + res.get(s));
		}
	}
	
	public static Map<String, String> getTorrents(String search) throws Exception {
		Map<String, String> res = new HashMap<String, String>();
		URL url = null;
	    HttpURLConnection connection = null;        
	    try{
	        //Create connection
	        url = new URL(SEARCH_URL);
	        connection = (HttpURLConnection)url.openConnection();
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-Type", CONTENT_TYPE);
	        connection.setRequestProperty("Content-Language", CONTENT_LANGUAGE); 
	        connection.setRequestProperty("User-Agent", USER_ARENT);
	        connection.setRequestProperty("Cookie", COOKIE); 
	        connection.setUseCaches(false);
	        connection.setDoInput(true);
	        connection.setDoOutput(true);

	        //send Request 
	        DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
	        dataout.writeBytes(PARAMS+search);
	        dataout.flush();
	        dataout.close();

	        //get response
	        InputStream is = connection.getInputStream();
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));
	        String line;
	        StringBuffer response = new StringBuffer();
	
	        while((line = br.readLine()) != null){
	            if(line.contains(DL_TORRENT_URL)){
					res.put(parseUrl(line), parseLabel(line));
				}	
	        }
			
	        //System.out.println(response.toString());
	        br.close();
	        //System.out.println(response.toString());
	    }catch(Exception e){
	        System.out.println("Unable to full create connection");
	        e.printStackTrace();
	    }finally {

	          if(connection != null) {
	            connection.disconnect(); 
	          }
	    }
		return res;
	}
	
	private static String parseUrl(String s2){
		if(!s2.contains(DL_TORRENT_URL)){
			return null;
		}
		String url = s2;
		url = url.substring(url.indexOf(DL_TORRENT_URL));
		url = url.substring(0,url.indexOf(".html"));
		url = url.substring(url.lastIndexOf("/")+1);
		url = TORRENT_URL + url + ".torrent";       
		return url;
	}
	
	private static String parseLabel(String s2){
		if(!s2.contains(DL_TORRENT_URL)){
			return null;
		}
		String label = s2;
		label = label.substring(label.lastIndexOf("/>")+2, label.lastIndexOf("</a>"));
		label = label.trim();
		return label;
	}
}
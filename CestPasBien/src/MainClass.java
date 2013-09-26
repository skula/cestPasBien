import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainClass {

	public static void main(String args[]) throws Exception {
		String urlParameters = "champ_recherche=bones";
		String urlstr = "http://www.cpasbien.me/recherche/";
		

		URL url;
	    HttpURLConnection connection = null;        
	    try{
	        //Create connection

	        url = new URL(urlstr);
	        connection = (HttpURLConnection)url.openConnection();

	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-Type", 
	                   "application/x-www-form-urlencoded");
	        connection.setRequestProperty("Content-Language", "en-US"); 
	        connection.setRequestProperty("User-Agent",
	                "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11");
	        connection.setRequestProperty("Cookie", "");//__cfduid=df83fae0487b4e39f73f8837ca250e35e1369247829

	        connection.setUseCaches(false);
	        connection.setDoInput(true);
	        connection.setDoOutput(true);

	        //send Request 
	        DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
	        dataout.writeBytes(urlParameters);
	        dataout.flush();
	        dataout.close();

	        //get response
	        InputStream is = connection.getInputStream();
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));
	        String line;
	        StringBuffer response = new StringBuffer();

	        while((line = br.readLine()) != null){
	            response.append(line);
	            response.append('\n');

	        }
	        System.out.println(response.toString());
	        br.close();
	        System.out.println(response.toString());
	    }catch(Exception e){
	        System.out.println("Unable to full create connection");
	        e.printStackTrace();
	    }finally {

	          if(connection != null) {
	            connection.disconnect(); 
	          }
	    }
	}
	
	public static void doThat(){
		/*
		 *  s2= s2.substring(s2.indexOf("http://www.cpasbien.me/dl-torrent"));
             s2= s2.substring(0,s2.indexOf("\"><img src"));
             s2=s2.substring(tmp.length(), s2.length()-4);
             s2="http://www.cpasbien.me/_torrents/"+s2+"torrent";       
             System.out.println(s2);
		 */
	}
}
/*
 * NAME: Anandamayee Modak
 * 
 * DETAILS: Class reads the csv file for Nifty100 stocks according to user's input sector. 
 *          Generates the necessary uri and sends API calls.
 *          Prints "quote" API response
 */


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class demo {
	
//=============Method to read CSV file=============
 static List<String> readNiftyCSV(String user_sector) throws FileNotFoundException{
	List<String> symbolListRet = new ArrayList<>();
	
	Scanner sc = new Scanner(new File("C:\\Users\\Anandamayee Modak\\Documents\\CitiBridge\\Nifty100.xlsx.csv"));  
	sc.useDelimiter(",");   //sets the delimiter pattern  
	
	while (sc.hasNext())  //returns a boolean value  
	{  
	
	String symbol = sc.next();
	String sector = sc.next();
	
	if(sector.equalsIgnoreCase(user_sector)) {
		symbolListRet.add(symbol);
	}
	}
	//returns list of ticker symbols corresponding to the user selected sector
	return symbolListRet;
}
	
//=============Method for generating Uri=============
public static String generateUri(String uri, int i, List<String>symbolList) {
	String str = String.join(".NS%2C", symbolList.subList(10*i, (10*i)+9));
	str = str.replace("\n", "").replace("\r", "");
	str = str.concat(".NS");
	uri = uri.concat(str);
	
	return uri;
}
 
//=============Method for generating Uri (overloaded)=============
public static String generateUri(String uri, int i, int rem, List<String>symbolList) {
	String str = String.join(".NS%2C", symbolList.subList(10*i, (10*i)+rem));
	str = str.replace("\n", "").replace("\r", "");
	str = str.concat(".NS");
	uri = uri.concat(str);
	
	return uri;
}

//=============Method for API calls=============
public static void apiCall(String uri) throws IOException, InterruptedException {
			HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(uri))
			.header("x-api-key", "1EL8IbiY129ImJDpu2et07BSlYQseRXe8FNPjGpS")
			.method("GET", HttpRequest.BodyPublishers.noBody())
			.build();
			HttpResponse<String> response = HttpClient.newHttpClient()
					.send(request, HttpResponse.BodyHandlers.ofString());
	System.out.println(response.body());
}

//=============Driver code=============
public static void main(String[] args) throws InterruptedException, IOException {

//user's sector selection	
	String user_sector = "Healthcare";
	String uri = "https://yfapi.net/v8/finance/spark?interval=1d&range=14d&symbols=";

	List<String> symbolList = readNiftyCSV(user_sector);
	
	int i=0;
	int rem = symbolList.size()%10;
	
	while(i<symbolList.size()/10) {
		uri = "https://yfapi.net/v8/finance/spark?interval=1d&range=14d&symbols=";
		apiCall(generateUri(uri, i, symbolList));
		i++;
	}
	
	if(rem!=0) {
		apiCall(generateUri(uri, i, rem, symbolList));
	}
	
	}  

}

//https://yfapi.net/v6/finance/quote?region=IN&lang=en&symbols=
//https://yfapi.net/v8/finance/spark?interval=1d&range=14d&symbols=TATASTEEL.NS
//https://yfapi.net/v6/finance/recommendationsbysymbol/YESBANK.NS OR TATASTEEL.NS

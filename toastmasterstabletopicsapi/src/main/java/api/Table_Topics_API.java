package api;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Table_Topics_API {

	@RequestMapping(value = "/test/", method = RequestMethod.GET,
	        produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String testFunction() {
		return "test";
	}
	
	@RequestMapping(value = "/test2", method = RequestMethod.GET,
	        produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String testFunction2(@RequestParam("count") String count) {
		return "test/ "+ count;
	}
	
	@RequestMapping(value = "/randomTopic", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject TableTopicValues(@RequestParam("count") int count, @RequestParam(value = "category1", required=false) String[] category1, @RequestParam(value = "category2", required=false) String[] category2, @RequestParam(value = "category3", required=false) String[] category3) {
		ArrayList<String> Cat01Array = new ArrayList<String>();
		ArrayList<String> Cat02Array = new ArrayList<String>();
		ArrayList<String> Cat03Array = new ArrayList<String>();
		
		if(category1 != null) {
			for(String tempCat : category1)
			Cat01Array.add(tempCat);	
			}
		if(category2 != null) {
			for(String tempCat : category2)
			Cat02Array.add(tempCat);	
			}
		if(category3 != null) {
			for(String tempCat : category3)
			Cat03Array.add(tempCat);	
			}
		return getTopicsFromDB(count, Cat01Array, Cat02Array, Cat03Array);

	}
	
	
	@SuppressWarnings("unchecked")
	protected JSONObject getTopicsFromDB(int count, ArrayList<String> Cat01Array, ArrayList<String> Cat02Array, ArrayList<String> Cat03Array) {
		JSONObject tableTopicFullObject = new JSONObject();
		String message = "We were able to produce your requested table topics";
		String confirmation = "OK";
		
		 Path path = null;
			try {
				path = Paths.get(Table_Topics_API.class.getResource(".").toURI());
			} catch (URISyntaxException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			 System.out.println("GOT MY PATH"+path.getParent());  
			 
		ArrayList<Integer> TT_id_Count = new ArrayList<Integer>();
		 ResourceBundle reader = null; 
		 StringBuilder query = new StringBuilder();
		 Properties props = new Properties();  
		  
		 try {
			props.load(new FileInputStream(".dbconfig.properties"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		 
		    //reader = ResourceBundle.getBundle(".dbconfig.properties");
			ResultSet rs;
			//int playerCount = 0;
			//ArrayList <String> players = new ArrayList <String>();

		Connection conn;
		try {
			conn = DriverManager.getConnection((props.getProperty("db.url")+props.getProperty("db.hostName")+":"+props.getProperty("db.portName")+"/"+props.getProperty("db.databaseName")),props.getProperty("db.username"),props.getProperty("db.password"));
		query.append("SELECT TT_id from table_topics_list");
		if(Cat01Array != null || Cat02Array != null || Cat03Array != null)
		query.append(" WHERE ((");
		
		if(Cat01Array !=null) {
		for(int x=0;x<Cat01Array.size();x++) {
		for(int y=0;y<10;y++)
			query.append("category_0"+(y+1)+"="+"'"+Cat01Array.get(x)+"'"+" or ");
		
		query.append("category_10='"+Cat01Array.get(x)+"')");
		if(Cat01Array.size() > x+1)
			query.append(" and (");
		else 
			query.append(")");
		}
		}
		
		if(Cat02Array !=null) {
			query.append(" or ((");			
			for(int x=0;x<Cat02Array.size();x++) {
			for(int y=0;y<10;y++)
				query.append("category_0"+(y+1)+"="+"'"+Cat02Array.get(x)+"'"+" or ");
			
			query.append("category_10='"+Cat02Array.get(x)+"')");
			if(Cat02Array.size() > x+1)
				query.append(" and (");
			else 
				query.append(")");
			}
			}
		
		if(Cat03Array !=null) {
			query.append(" or ((");	
			for(int x=0;x<Cat03Array.size();x++) {
			for(int y=0;y<10;y++)
				query.append("category_0"+(y+1)+"="+"'"+Cat03Array.get(x)+"'"+" or ");
			
			query.append("category_10='"+Cat03Array.get(x)+"')");
			if(Cat03Array.size() > x+1)
				query.append(" and (");
			else 
				query.append(")");
			}
			}
		query.append(";");	
		
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		rs = stmt.executeQuery(query.toString());
		//rs.next();
		while(rs.next()) {
			TT_id_Count.add(rs.getInt("tt_id"));
		}
		if(TT_id_Count.size() < count) {
			confirmation = "Not Enough Topics";
			message = "Your query did not produce enough table topics. You requested "+ Integer.toString(count)+" Table Topics, but your requested categories only could produce "+ Integer.toString(TT_id_Count.size())+" Table Topics.";
			count = TT_id_Count.size();
		}
		Random randNum = new Random();
	      Set<Integer>set = new LinkedHashSet<Integer>();
	      while (set.size() < count) {
	         set.add(randNum.nextInt(TT_id_Count.size())+1);
	      }
	      StringBuilder finalQuery = new StringBuilder();
	      finalQuery.append("SELECT tt_id, tt_questions FROM table_topics_list WHERE tt_id IN (");
	      String comma = "";
	      for(int tempCount : set) {
		      finalQuery.append(comma);
		      finalQuery.append(tempCount);
		      comma = ", ";   	  
	      }
	      finalQuery.append(");");
	      Statement finalStmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet finalRS = finalStmt.executeQuery(finalQuery.toString());
			TT_id_Count.clear();
			ArrayList<String> tableTopicList = new ArrayList<String>();
			//finalRS.next();
			while(finalRS.next()) {
				TT_id_Count.add(finalRS.getInt("tt_id"));
				tableTopicList.add(finalRS.getString("tt_questions"));
			}
			
			JSONArray tableTopicArray = new JSONArray();
			JSONObject tableTopicObject = new JSONObject();
			for(int x=0; x < TT_id_Count.size();x++) {
			tableTopicObject.put("Topic_id", TT_id_Count.get(x));
			tableTopicObject.put("Table_Topic", tableTopicList.get(x));
			tableTopicArray.add(tableTopicObject);
			tableTopicObject.clear();
			}
			tableTopicFullObject.put("Topics", tableTopicObject);
			tableTopicFullObject.put("Message", message);
			tableTopicFullObject.put("Confirmation", confirmation);
		} catch (SQLException e) {
			confirmation = "Connection Error!";
			message = "There was an issue with the Database Connection";
			tableTopicFullObject.put("Message", message);
			tableTopicFullObject.put("Confirmation", confirmation);
			e.printStackTrace();
		}
				
			return tableTopicFullObject;
	      
	   }

}

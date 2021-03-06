package api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
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
		
			 
		ArrayList<Integer> TT_id_Count = new ArrayList<Integer>();
		 StringBuilder query = new StringBuilder();

			ResultSet rs;
		Connection conn = null;
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(System.getenv().get("DB_CONNECTION"),System.getenv().get("USER"),System.getenv().get("PASSWORD"));
			//conn = DriverManager.getConnection((props.getProperty("db.url")+props.getProperty("db.hostName")+":"+props.getProperty("db.portName")+"/"+props.getProperty("db.databaseName")+"?sslmode=require"),props.getProperty("db.username"),props.getProperty("db.password"));
			query.append("SELECT TT_id from table_topics_list");
		if(!Cat01Array.isEmpty() || !Cat02Array.isEmpty() || !Cat03Array.isEmpty())
		query.append(" WHERE ('");
		
		if(!Cat01Array.isEmpty()) {
		for(int x=0;x<Cat01Array.size();x++) {
			query.append(Cat01Array.get(x));
			query.append("' in (");
		for(int y=1;y<10;y++) 
			query.append("category_0"+(y)+", ");
		
		query.append("category_10)");
		if(Cat01Array.size() > x+1)
			query.append(" and '");
		else 
			query.append(")");
		}
		if(!Cat02Array.isEmpty())
			query.append(" or ('");	
			
		}
		
		if(!Cat02Array.isEmpty()) {
			for(int x=0;x<Cat02Array.size();x++) {
				query.append(Cat02Array.get(x));
				query.append("' in (");
			for(int y=1;y<10;y++) 
				query.append("category_0"+(y)+", ");
			
			query.append("category_10)");
			if(Cat02Array.size() > x+1)
				query.append(" and '");
			else 
				query.append(")");
			}
			if(!Cat03Array.isEmpty())
				query.append(" or ('");	
				
			}
		
		if(!Cat03Array.isEmpty()) {
			for(int x=0;x<Cat03Array.size();x++) {
				query.append(Cat03Array.get(x));
				query.append("' in (");
			for(int y=1;y<10;y++) 
				query.append("category_0"+(y)+", ");
			
			query.append("category_10)");
			if(Cat03Array.size() > x+1)
				query.append(" and '");
			else 
				query.append(")");
			}
		}
		query.append(";");	
		
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		rs = stmt.executeQuery(query.toString());
		while(rs.next()) {
			TT_id_Count.add(rs.getInt("tt_id"));
		}
		if(TT_id_Count.size() < count) {
			confirmation = "Not Enough Topics";
			message = "Your query did not produce enough table topics. You requested "+ Integer.toString(count)+" Table Topics, but your requested categories only could produce "+ Integer.toString(TT_id_Count.size())+" Table Topics.";
			count = TT_id_Count.size();
		}
		
		 Collections.shuffle(TT_id_Count); 

	      StringBuilder finalQuery = new StringBuilder();
	      finalQuery.append("SELECT tt_id, tt_questions FROM table_topics_list WHERE tt_id IN (");
	      String comma = "";
	      for(int x=0;x<count;x++) {
		      finalQuery.append(comma);
		      finalQuery.append(TT_id_Count.get(x));

		      comma = ", ";   	  
	      }

	      finalQuery.append(");");
	      Statement finalStmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet finalRS = finalStmt.executeQuery(finalQuery.toString());
		    ArrayList<Integer> final_TT_id_Count = new ArrayList<Integer>();
			ArrayList<String> tableTopicList = new ArrayList<String>();
			while(finalRS.next()) {
				final_TT_id_Count.add(finalRS.getInt("tt_id"));
				tableTopicList.add(finalRS.getString("tt_questions"));
			}
			JSONArray tableTopicArray = new JSONArray();
			for(int x=0; x < count;x++) {
			JSONObject tableTopicObject = new JSONObject();
			tableTopicObject.put("Topic_id", TT_id_Count.get(x));
			tableTopicObject.put("Table_Topic", tableTopicList.get(findIndex(final_TT_id_Count, TT_id_Count.get(x))));
			tableTopicArray.add(tableTopicObject);
			}
			tableTopicFullObject.put("Topics", tableTopicArray);
			tableTopicFullObject.put("Message", message);
			tableTopicFullObject.put("Confirmation", confirmation);
		} catch (SQLException | ClassNotFoundException e) {
			if(count <=0) {
				confirmation = "Request Number Error!";
				message = "You need to request at least one table topic.";
				tableTopicFullObject.put("Message", message);
				tableTopicFullObject.put("Confirmation", confirmation);
			}else {
			confirmation = "Connection Error!";
			message = "There was an issue with the Database Connection";
			tableTopicFullObject.put("Message", message);
			tableTopicFullObject.put("Confirmation", confirmation);
			}
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				confirmation = "Connection Error!";
				message = "There was an issue with the Database Connection";
				tableTopicFullObject.put("Message", message);
				tableTopicFullObject.put("Confirmation", confirmation);
			}
		}
				
			return tableTopicFullObject;
	      
	   }
	
	public static int findIndex(ArrayList<Integer> tableTopicList, int t) 
    { 
        int index = Arrays.binarySearch(tableTopicList.toArray(), t); 
        return (index < 0) ? -1 : index; 
    } 
}

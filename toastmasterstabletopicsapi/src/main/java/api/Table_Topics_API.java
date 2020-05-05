package api;

import java.util.ArrayList;

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
		System.out.println("Test2");
		return "test";
	}
	
	@RequestMapping(value = "/randomTopic", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	protected void TableTopicValues(@RequestParam("count") int count, @RequestParam(value = "category1", required=false) String[] category1, @RequestParam(value = "category2", required=false) String[] category2, @RequestParam(value = "category3", required=false) String[] category3) {
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
		getTopicsFromDB(count, Cat01Array, Cat02Array, Cat03Array);

	}
	
	
	protected void getTopicsFromDB(int count, ArrayList<String> Cat01Array, ArrayList<String> Cat02Array, ArrayList<String> Cat03Array) {
		
	}

}

package api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

public class Table_Topics_API {
	
	
	@RequestMapping(value = "/{p1}/{p2}/getsession", method = RequestMethod.GET,
	        produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String get(@PathVariable String p1, @PathVariable String p2) {
		return "test";
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET,
	        produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String testFunction() {
		return "test";
	}

}

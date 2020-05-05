package api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

}

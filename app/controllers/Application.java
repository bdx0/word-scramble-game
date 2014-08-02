package controllers;

import java.util.ArrayList;
import java.util.List;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

	// public static Result index() {
	// return ok(index.render("Your new application is ready."));
	// }

	public static Result index() {
		return ok(index.render("Your new application is ready."));
	}

	public static Result ajax() {
		return ok("Here's my server-side data");
	}
	
	public static Result suggest() {
		return ok("LLUPS");
	}

	private static List<String> dict = new ArrayList<String>() {
		{
			add("pus");
			add("sup");
			add("ups");
			add("plus");
			add("pull");
			add("pulls");
		}
	};

	public static Result check(String match_string) {
		// if true, save the match_string with pair session
		return ok(Boolean.toString(dict.contains(match_string)));
	}
}
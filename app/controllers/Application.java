package controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

	public static Result index() {
		return ok(index.render("Your new application is ready."));
	}

	public static Result ajax() {
		return ok("This is a scramble word game.");
	}

	private static String KEY_WORD_COLUMN = "word";
	private static String KEY_DICT_TABLE = "dict";

	public static Result suggest() {
		try {
			// if true, save the match_string with pair session
			Connection conn = getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(String.format(
					"SELECT %s FROM %s ORDER BY random() limit 1",
					KEY_WORD_COLUMN, KEY_DICT_TABLE));
			if (rs.next()) {
				return ok(String.format("%s", rs.getString(KEY_WORD_COLUMN)
						.trim().toUpperCase()));
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		return ok("LLUPS");
	}

	public static Result check(String match_string) {
		try {
			// if true, save the match_string with pair session
			Connection conn = getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(String.format(
					"SELECT %s FROM %s WHERE word = '%s'", KEY_WORD_COLUMN,
					KEY_DICT_TABLE, match_string.toLowerCase()));
			if (rs.next()) {
				return ok(String.format("{ \"check\" : %s }", true));
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		return ok(String.format("{ \"check\" : %s }", false));
	}

	private static Connection getConnection() throws URISyntaxException,
			SQLException {
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://"
				+ dbUri.getHost()
				+ dbUri.getPath()
				+ "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
		// String dbUrl =
		// "jdbc:postgres://ec2-107-20-234-127.compute-1.amazonaws.com:5432/dfa6hc8mahc1u";
		// return DriverManager.getConnection(dbUrl, "zfdlckheicvnni",
		// "85ASUzJllQcoUSQC43qq0uFygg");
		return DriverManager.getConnection(dbUrl, username, password);
	}
}
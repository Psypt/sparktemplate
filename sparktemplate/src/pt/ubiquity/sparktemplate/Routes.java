package pt.ubiquity.sparktemplate;

import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.options;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.HashMap;

import com.google.gson.Gson;

import pt.ubiquity.sparktemplate.dao.UserDAO;
import pt.ubiquity.sparktemplate.exception.NotFoundException;
import pt.ubiquity.sparktemplate.model.SysUser;
import spark.Spark;

public class Routes {

	public Routes() {

	}

	public static Gson gson = new Gson();
	private UserDAO userDAO;
	private HashMap<String,SysUser> sessions = null;
	
	
	public void initRoutes() {
		Spark.externalStaticFileLocation(ServerStart.appProperties.getFrontEndPath());
		userDAO = ServerStart.userDAO;
		sessions = new HashMap<String,SysUser>();
		enableCORS();
		userServices();
		authServices();
	}
	
	public void authServices(){
		before((req,res)-> {
			if(!req.pathInfo().equalsIgnoreCase("/login")){
				if(sessions.get(req.headers("x-auth-token")) == null){
					halt(401, "Failed to authenticate.");
				}
			}
		});
		post("/login", (req, res) -> {
			SysUser user = gson.fromJson(req.body(), SysUser.class);
			SysUser authed = userDAO.login(user);
			sessions.put(authed.getToken(),authed);
			return authed.getToken();
		});
		post("/logout", (req,res) -> {
			if(sessions.get(req.headers("x-auth-token")) != null){
				return gson.toJson(sessions.remove(req.headers("x-auth-token")));
			}
			res.status(401);
			return "Invalid auth token";
		});
	}

	public void userServices() {
		get("/user", (req, res) -> userDAO.getAll(), gson::toJson);
		get("/user/:id", (req, res) -> {
			String id = req.params(":id");
			if (id == null || id.isEmpty()) {
				res.status(400);
				return "Invalid id parameter.";
			}
			SysUser user = userDAO.getById(id);
			if (user == null) {
				res.status(404);
				return "User not found.";
			}
			return gson.toJson(user);
		});
		post("/user", (req, res) -> {
			SysUser user = gson.fromJson(req.body(), SysUser.class);
			userDAO.add(user);
			return "User " + user.getUsername() + " inserted with sucess.";
		});
		put("/user", (req, res) -> {
			SysUser user = gson.fromJson(req.body(), SysUser.class);
			try {
				userDAO.update(user);
			} catch (NotFoundException e) {
				return e.getMessage();
			}
			return "User " + user.getUsername() + " updated with sucess.";
		});
		delete("/user/:id", (req, res) -> {
			String id = req.params(":id");
			if (id == null || id.isEmpty()) {
				res.status(400);
				return "Invalid id parameter.";
			}
			if (userDAO.getById(id) == null) {
				res.status(404);
				return "User not found.";
			}
			userDAO.delete(id);
			return "User with id " + id + " deleted with sucess.";
		});
	}
	
	public void stop(){
		spark.Spark.stop();
	}

	public void enableCORS() {
		options("/*", (request, response) -> {
			String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
			if (accessControlRequestHeaders != null) {
				response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
			}

			String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
			if (accessControlRequestMethod != null) {
				response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
			}
			return "OK";
		});

		before((request, response) -> {
			response.header("Access-Control-Allow-Origin", "*");
		});
	}

}

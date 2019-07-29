package com.example.mavenDemoRest;

import com.example.mavenDemoRest.DaoServices.DaoImplementations.LocationDaoImpl;
import com.example.mavenDemoRest.DaoServices.DaoImplementations.PostDaoImpl;
import com.example.mavenDemoRest.DaoServices.DaoImplementations.UserDaoImpl;
import com.example.mavenDemoRest.DaoServices.LocationDaoService;
import com.example.mavenDemoRest.DaoServices.PostDaoService;
import com.example.mavenDemoRest.DaoServices.UserDaoService;
import com.example.mavenDemoRest.constants.UserType;
import com.example.mavenDemoRest.model.Location;
import com.example.mavenDemoRest.model.Post;
import com.example.mavenDemoRest.model.User;
import com.example.mavenDemoRest.repositories.LocationRepository;
//import com.example.mavenDemoRest.services.PostService;
import com.example.mavenDemoRest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class MavenDemoRestApplication {






	//####################################
	//THIS CODE POPULATES THE DATA STRUCTURE WOTH MOCK DATA
	//#######################################################
	/*private static List<User> users;
	private static List<Post> posts;
	//private static List<Location> locations;

	private static void loadData(){
		System.out.println("Data is LOADED");
		DataStore.loadData();
		users = UserService.getInstance().getUsers();
		posts = PostService.getInstance().getPosts();
	}

	private static void startApp(){
		for(Post post : posts){
			View.displayPosts(post);
			System.out.println();
			System.out.println();
		}
	}
	//######################################################*/








	public static void main(String[] args) {





		//loadData();
		//startApp();

		SpringApplication.run(MavenDemoRestApplication.class, args);
	}

}

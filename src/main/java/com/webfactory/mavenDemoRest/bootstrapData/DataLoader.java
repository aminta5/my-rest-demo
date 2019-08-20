package com.webfactory.mavenDemoRest.bootstrapData;

import com.webfactory.mavenDemoRest.constants.UserType;
import com.webfactory.mavenDemoRest.model.Location;
import com.webfactory.mavenDemoRest.model.Post;
import com.webfactory.mavenDemoRest.model.RegClientDetails;
import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.repositories.ClientDetailsRepository;
import com.webfactory.mavenDemoRest.repositories.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    //private final ClientDetailsRepository clientDetailsRepository;

    public DataLoader(UserRepository userRepository/*, ClientDetailsRepository clientDetailsRepository*/) {
        this.userRepository = userRepository;
        //this.clientDetailsRepository = clientDetailsRepository;
    }

    private RegClientDetails regClientDetails = new RegClientDetails("filip-client", "filip-secret", "read,write", "password");



    @Override

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        userRepository.saveAll(getUsers());
        //clientDetailsRepository.save(regClientDetails);
    }


    private List<User> getUsers() {
        List<User> users = new ArrayList<>();

        // first user
        User user1 = new User("John", "Smith", "Jonny", "user@semanticsquate.com","password", UserType.ADMIN);
        Post post1 = new Post("Weather");
        post1.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galle");
        Location pLoc1 = new Location("Belgrade", "Serbia", 28.3f, 45.6f);
        Location uLoc1 = new Location("La Valetta", "Malta", 45.9f, 90.1f);
        post1.setLocation(pLoc1);
        post1.setUser(user1);
        user1.setLocation(uLoc1);
        user1.getPosts().add(post1);
        users.add(user1);


        //second user
        User user2 = new User("Mickey", "Mouse", "Miki", "miki@myemail.com","password", UserType.USER);
        Post post2 = new Post("Disney");
        post2.setDescription("Cu mucius alienum qui. Vim ne doming contentiones, sea eu aeque noster pertinax. Ei atomorum expetendis eam.");
        Location pLoc2 = new Location("Zadar", "Croatia", 78.9f, 67.8f);
        Location uLoc2 = new Location("Skopje", "Makedonija", 65.3f, 34.2f);
        post2.setLocation(pLoc2);
        post2.setUser(user2);
        user2.setLocation(uLoc2);
        user2.getPosts().add(post2);
        users.add(user2);

        //third user
        User user3 = new User("Petko", "Petkoski", "Pepi", "pepi@globalmail.com","password", UserType.USER);
        Post post3 = new Post("Vacation");
        post3.setDescription("Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old");
        Location pLoc3 = new Location("Madrid", "Spain", 99.0f, 34.5f);
        Location uLoc3 = new Location("Athens", "Greece", 78.4f, 21.7f);
        post3.setLocation(pLoc3);
        post3.setUser(user3);
        user3.setLocation(uLoc3);
        user3.getPosts().add(post3);
        users.add(user3);

        //fourth user
        User user4 = new User("Sheldon", "Cooper", "Shelly", "atom@yellow.com","password", UserType.USER);
        Post post4 = new Post("Camping");
        post4.setDescription("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout");
        Location pLoc4 = new Location("Goa", "India", 66.3f, 88.4f);
        Location uLoc4 = new Location("Ohrid", "Makedonija", 61.9f, 78.3f);
        post4.setLocation(pLoc4);
        post4.setUser(user4);
        user4.setLocation(uLoc4);
        user4.getPosts().add(post4);
        users.add(user4);

        //fifth user
        User user5 = new User("Denise", "Menice", "Budd", "buddy@space.com","password", UserType.USER);
        Post post5 = new Post("Vitamin Sea");
        post5.setDescription("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi");
        Location pLoc5 = new Location("Moscow", "Russia", 99.1f, 41.8f);
        Location uLoc5 = new Location("Acra", "India", 65.8f, 79.3f);
        post5.setLocation(pLoc5);
        post5.setUser(user5);
        user5.setLocation(uLoc5);
        user5.getPosts().add(post5);
        users.add(user5);

        return users;
    }


}

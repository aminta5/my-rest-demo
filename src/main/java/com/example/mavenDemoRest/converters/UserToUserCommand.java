/*
package com.example.mavenDemoRest.converters;

import com.example.mavenDemoRest.commands.UserCommand;
import com.example.mavenDemoRest.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserCommand implements Converter<User, UserCommand> {
    private final PostToPostCommand postToPostCommand;
    private final LocationToLocationCommand locationToLocationCommand;
    //constructor


    public UserToUserCommand(PostToPostCommand postToPostCommand, LocationCommandToLocation locationCommandToLocation, LocationToLocationCommand locationToLocationCommand) {
        this.postToPostCommand = postToPostCommand;
        this.locationToLocationCommand = locationToLocationCommand;
    }

    @Override
    public UserCommand convert(User source) {

        if (source == null) {
            return null;
        }
        final UserCommand userCommand = new UserCommand();

        userCommand.setId(source.getId());
        userCommand.setFirstName(source.getFirstName());
        userCommand.setLastName(source.getLastName());
        userCommand.setNickname(source.getNickname());
        userCommand.setEmail(source.getEmail());
        userCommand.setPassword(source.getPassword());

        if(source.getPosts() != null && source.getPosts().size() > 0){
            source.getPosts().forEach(post -> userCommand.getPost().add(postToPostCommand.convert(post)));
        }

        userCommand.setLocation(locationToLocationCommand.convert(source.getLocation()));
        userCommand.setUserType(source.getUserType());

        return userCommand;

    }
}
*/

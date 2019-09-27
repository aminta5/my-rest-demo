package com.webfactory.mavenDemoRest.managers;

import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.services.PostService;
import com.webfactory.mavenDemoRest.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class AccessManagerTest {

    @Mock
    private PostService postService;
    @Mock
    private UserService userService;

    private AccessManager accessManager;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        accessManager = new AccessManager(userService, postService);
        mockMvc = MockMvcBuilders.standaloneSetup(accessManager).build();
    }

    @Test
    public void authorizedUser() {
        User user = User.builder().id(1L).nickname("terra").build();
        Authentication mockAuth = mock(Authentication.class);
        mockAuth.setAuthenticated(true);
        when(mockAuth.getName()).thenReturn(user.getNickname());
        when(userService.getUserByNickname(anyString())).thenReturn(user);
        

    }

    @Test
    public void userCanBeUpdated() {
    }

    @Test
    public void postCanBeUpdatedOrSeen() {
    }
}
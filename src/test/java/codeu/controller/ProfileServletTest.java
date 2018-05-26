package codeu.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import codeu.model.data.User;
import codeu.model.data.Profile;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.ProfileStore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ProfileServletTest {

  private ProfileServlet profileServlet;
  private HttpServletRequest mockRequest;
  private HttpSession mockSession;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private UserStore mockUserStore;

  @Before
  public void setup() {
    profileServlet = new ProfileServlet();

    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/profile.jsp")).thenReturn(mockRequestDispatcher);

    mockUserStore = Mockito.mock(UserStore.class);
    profileServlet.setUserStore(mockUserStore);
  }

  @Test
  public void testDoGet() throws IOException, ServletException{
    Mockito.when(mockRequest.getRequestURI()).thenReturn("/user/test_user");
    Profile mockProfile = Mockito.mock(Profile.class);
    Mockito.when(mockProfile.getName()).thenReturn("test_username");
    Mockito.when(mockProfile.getBio()).thenReturn("test bio");

    Mockito.when(mockRequest.getParameter("profileUrl")).thenReturn("/user/test_user");
    Mockito.when(mockRequest.getParameter("userUrl")).thenReturn("test_user");
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test user");

    ProfileStore mockProfileStore = Mockito.mock(ProfileStore.class);
    Mockito.when(mockProfileStore.getProfile("test user")).thenReturn(mockProfile);

    profileServlet.setProfileStore(mockProfileStore);
    HttpSession mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    profileServlet.doGet(mockRequest,mockResponse);

    Mockito.verify(mockRequestDispatcher).forward(mockRequest,mockResponse);
  }

    @Test
    public void toDoPost_null() throws IOException, ServletException {
      Mockito.when(mockRequest.getParameter("user")).thenReturn("test_user");
      Mockito.when(mockRequest.getParameter("username")).thenReturn("test_username");
      Mockito.when(mockRequest.getParameter("editBio")).thenReturn("test bio");

      UserStore mockUserStore = Mockito.mock(UserStore.class);
      ProfileStore mockProfileStore = Mockito.mock(ProfileStore.class);
      Mockito.when(mockProfileStore.doesProfileExist("test_username")).thenReturn(false);
      profileServlet.setProfileStore(mockProfileStore);
      profileServlet.doPost(mockRequest, mockResponse);
      Mockito.verify(mockResponse).sendRedirect("/user/test_username");
    }
}

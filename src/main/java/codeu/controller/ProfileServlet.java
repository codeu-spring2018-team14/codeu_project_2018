// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.controller;

import codeu.model.data.User;
import codeu.model.data.Message;
import codeu.model.data.Profile;
import codeu.model.data.Conversation;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.ProfileStore;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* Servlet class responsible for use profile page
*/
public class ProfileServlet extends HttpServlet{

  /* Store class that gives access to Users. */
  private UserStore userStore;
  private MessageStore messageStore;
  private ProfileStore profileStore;

  /** Set up state for handling profile requests */
  @Override
  public void init() throws ServletException{
    super.init();
    setUserStore(UserStore.getInstance());
    setMessagesStore(MessageStore.getInstance());
    setProfileStore(ProfileStore.getInstance());
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  void setMessagesStore(MessageStore messageStore){
    this.messageStore = messageStore;
  }

  void setProfileStore(ProfileStore profileStore){
    this.profileStore = profileStore;
  }

  /**
   * This function fires when a user navigates to the profile page. It forwards
   * the request to conversations.jsp.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
        String profileUrl = request.getRequestURI();
        String userUrl = profileUrl.substring("/user/".length());
        String username = (String)request.getSession().getAttribute("user");
        User user = userStore.getUser(username);
        List<Message> messages = messageStore.getUserMessages(user.getId());
        Profile profile = profileStore.getProfile(username);
        //if user is not logged in, redirect to log in page
        if (username == null) {
          request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
          return;
        }
        request.setAttribute("profileUser", user);
        request.setAttribute("username", username);
        request.setAttribute("profile", profile);
        request.setAttribute("bio", profile.getBio());
        request.setAttribute("messages", messages);
        request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);
      }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException{
      String bio = request.getParameter("bio");
      String username = (String)request.getSession().getAttribute("user");
      User user = userStore.getUser(username);
      Profile profile = profileStore.getProfile(username);
      if (bio != null){
        profile.setBio(bio);
      }
      userStore.putUser(user);
      response.sendRedirect("/user/"+username);
    }


}

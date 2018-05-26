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
import codeu.model.data.Conversation;
import codeu.model.data.UserProfile;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.UserProfileStore;
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
  private ConversationStore conversationStore;
  private UserProfileStore userProfileStore;
  private List<Conversation> conversations;
  private List<Message> displayMessages;
  private UUID profileId;

  /** Set up state for handling profile requests */
  @Override
  public void init() throws ServletException{
    super.init();
    setUserStore(UserStore.getInstance());
    setMessagesStore(MessageStore.getInstance());
    setUserProfileStore(UserProfileStore.getInstance());
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

  void setUserProfileStore(UserProfileStore userProfileStore){
    this.userProfileStore = userProfileStore;
  }

  /**
   * This function fires when a user navigates to the profile page. It forwards
   * the request to conversations.jsp.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
        String requestUrl = request.getRequestURI();
        String username = requestUrl.substring("/user/".;length()).replaceAll("%20","");
        User user = userStore.getUser(username);
        displayMessages = new ArrayList<>();
        UserProfile userProfile = userProfileStore.getUserProfile(username);
        //if user is not logged in, redirect to log in page
        if (username == null) || (user == null) || (userProfile == null){
          request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
          return;
        }
        conversations = conversationStore.getAllConversations();
        for (Conversation convo : conversations){
          messages = messageStore.getMessageInConversation(convo.getId());
          for (Message message : messages){
            if (message.getAuthorId().equals(user.getId())){
              displayMessages.add(message);
            }
          }
        }
        request.setAttribute("user", user);
        request.setAttribute("username", username);
        request.setAttribute("profile", userProfile);
        request.setAttribute("about me", profile.getAboutMe());
        request.setAttribute("messages", displayMessages);
        request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);
      }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException{
      String requestUrl = request.getRequestURI();
      String username = requestUrl.substring("/user/".length()).replaceAll("%20","");
      User user = userStore.getUser(username);
      UserProfile userProfile = userProfileStore.getUserProfile(username);
      if (userProfile == null){
        response.sendRedirect("/index.jsp");
        return;
      }
      if (request.getParameter("update") != null){
        String about = request.getParameter("editAbout");
        userProfile.setAboutMe(about);
        userProfileStore.addUserProfile(userProfile);
      }
      response.sendRedirect("/user/"+username);
    }


}

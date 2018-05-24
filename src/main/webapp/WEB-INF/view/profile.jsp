<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<%@ page import="java.util.List"%>
<%@ page import="codeu.model.data.User"%>
<%@ page import="codeu.model.data.Profile"%>
<%@ page import="codeu.model.data.Message"%>
<%@ page import="codeu.model.store.basic.ProfileStore"%>
<%@ page import="codeu.model.store.basic.ConversationStore"%>
<%@ page import="java.util.UUID"%>

<%
  User user = (User) request.getAttribute("profileUser");
  Profile profile = (Profile) request.getAttribute("profile");
%>
<!DOCTYPE html>
<html>
<head>
  <title>Profile</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <nav>
    <a id="navTitle" href="/">CodeU Team 14 Chat App</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
      <a href="/profile">Profile</a>
    <% } else{ %>
      <a href="/login">Login</a>
      <a href="/register">Register</a>
    <% } %>
    <a href="/about.jsp">About</a>
    <a href="/testdata">Load Test Data</a>
  </nav>


  <script language="javascript">
      function showEdit() {
          editProfile.style.display="block";
      }
  </script>

  <div id="container">
    <div
    <h1><% request.getAttribute("username")%></h1>
    <h3>About</h3>
    <% if (request.getAttribute("username").equals(request.getSession().getAttribute("user"))){%>
        <form action="/user/<%= request.getAttribute("user")%>" method="POST">
        About:<textarea rows="7" cols="70" name= "newAbout" id="newAbout"></textarea>
        <input type="submit" name = "update" value = "Update"</>
      </form>
      <% }else{ %>
            <p>
              <%= (String) request.getAttribute("bio")%>
            </p>
        <%}%>
    <h3>Messages</h3>

    <%
    List<Message> messages = (List<Message>) request.getAttribute("messages");
    if (messages == null || messages.isEmpty()){
    %>
      <h2 style="color:red">No messages.</h2>
      <%
    }else{
    %>
      <ul class="mdl=list">
    <%
      for(Message message:messages){
        %>
          <li><a href="/chat/<%= ConversationStore.getInstance().getConversation(message.getConversationId()).getTitle()%>">
            <%= ConversationStore.getInstance().getConversation(message.getConversationId()).getTitle() %></a>:
            <%= message.getContent() %></a></li>
        <%
          }
        %>
          </ul>
        <%
        }
        %>
    </div>
  </div>
</body>
</html>

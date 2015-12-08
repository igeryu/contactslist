<%-- 
    Document   : search.jsp
    Created on : Oct 7, 2015, 8:43 AM
    Author     : Alan Johnson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="domain.*"%>
<jsp:useBean id="friendDao" class="domain.FriendDAO" scope="session" />
<jsp:setProperty name="friendDao" property="*" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alan Johnson - CMIS 440 - Final Project - Search</title>
    </head>

    <body>

        <h1>CMIS 440</h1>
        <h2>Alan Johnson</h2>

        <%-- Get search string and process: --%>
        <% String searchString = request.getParameter("searchString");%> 
        <h3>Search: "<%= searchString%>"</h3><br/><br/>
        <%
            ArrayList<Friend> results = friendDao.findFriends(searchString);

            if (results.size() < 1) {

        %> No results.<br/> <%} else { %>

        <%-- Print Results: --%>
        Results: <%= results.size() %><br/><br/>
        <table border="1" cellspacing="0" cellpadding="0">
            <tr><th>First Name</th><th>Last Name</th><th>Number</th><th>Edit</th><th>Delete</th></tr>
                    <%

                        for (Friend f : results) {%>
            
                <tr>
                    <td><%= f.getFirstName()%></td>
                    <td><%= f.getLastName()%></td>
                    <td><%= f.getPhoneFormatted()%></td>
                <form action="edit.jsp" method="POST">
                    <input name="contactID" type="hidden" value="<%= f.getObjectID()%>" />
                    <input name="submitted" type="hidden" value="false" />
                    <td><input type="submit" value="Edit" /></td></form>
                <form action="edit.jsp" method="POST">
                    <input name="contactID" type="hidden" value="<%= f.getObjectID()%>" />
                    <input name="submitted" type="hidden" value="delete" />
                    <td><input type="submit" value="Delete" /></td></form>
        </tr>
        </tr>
        <%
                } %> </table> 

        <%
            }
        %>

        <%-- Back to Index, button: --%>
        <br/><br/>
        <form action="index.jsp" method="POST">
            <br/><br/>
            <input type="submit" value="Return to Index" />
        </form>

    </body>

</html>
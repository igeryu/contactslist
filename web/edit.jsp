<%-- 
    Document   : edit.jsp
    Created on : Oct 7, 2015, 8:43 AM
    Author     : Alan Johnson
--%>


<%@page import="domain.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="friendDao" class="domain.FriendDAO" scope="session" />
<jsp:setProperty name="friendDao" property="*" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alan Johnson - CMIS 440 - Final Project - Edit</title>
    </head>
    <body>
        <h1>CMIS 440</h1>
        <h2>Alan Johnson</h2><br/><br/>

        <%-- Determine if this is an initial loading, or an edit follow-up.  If
             it is an edit follow-up, it will be determined whether new data was
             entered. --%>
        <%
            int contactID = Integer.valueOf(request.getParameter("contactID"));
            Friend friend = friendDao.getFriend(contactID);

            String submitted = request.getParameter("submitted");

            if (submitted.equals("delete")) {
                friendDao.delete(friend);%>
        <b>Deleted Contact</b>: <%= friend.getFirstName()%> <%= friend.getLastName()%><br/><br/>
        <% } else {
            if (submitted.equals("true")) {

                String newFirstName = request.getParameter("firstName");
                friend.setFirstName(newFirstName);

                String newLastName = request.getParameter("lastName");
                friend.setLastName(newLastName);

                String newPhone = request.getParameter("phone");
                friend.setPhone(newPhone);
                    friendDao.update(friend);%>
        <%-- Print info on edit profile: --%>
        <b>Edited Contact</b>: <%= friend.getFirstName()%> <%= friend.getLastName()%><br/><br/>

        <% }%>

        <%-- Regardless of initial edit or follow-up, display the contact's
             current information, with fields for new data --%>
        <form action="edit.jsp" method="POST" >
            <table border="1" cellspacing="0" cellpadding="0">
                <tr><th>Field</th><th>Current</th><th>New</th></tr>
                <tr>
                    <td><b>First Name</b></td><td><%= friend.getFirstName()%></td>
                    <td><input type="text" name="firstName" /></td>
                </tr>
                <tr>
                    <td><b>Last Name</b></td><td><%= friend.getLastName()%></td>
                    <td><input type="text" name="lastName" /></td>
                </tr>
                <tr>
                    <td><b>Phone</b></td><td><%= friend.getPhoneFormatted()%></td>
                    <td><input type="text" name="phone" /></td>
                </tr>
            </table>
            <input type="hidden" name="submitted"  value="true" />
            <input name="contactID" type="hidden" value="<%= contactID%>" />
            <input type="submit" value="Submit" />
        </form>
        <br/><br/>
        <% }%>

        <%-- Return to Index, button --%>
        <form action="index.jsp" method="POST" >
            <input type="submit" value="Return to Index" />
        </form>
    </body>
</html>

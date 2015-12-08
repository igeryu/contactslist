<%-- 
    Document   : add.jsp
    Created on : Oct 7, 2015, 8:43 AM
    Author     : Alan Johnson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="friendDao" class="domain.FriendDAO" scope="session" />
<jsp:setProperty name="friendDao" property="*" />
<%-- <jsp:useBean id="friend" class="domain.Friend" scope="session" />
<jsp:setProperty name="friend" property="*" /> --%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alan Johnson - CMIS 440 - Final Project - Add</title>
    </head>
    <body>
        <h1>CMIS 440</h1>
        <h2>Alan Johnson</h2><br/><br/>
        <%-- Check if a new contact is being added, or this is an initial
             load. --%>
        <%
            String accepted = request.getParameter("submitted");

            if (accepted.equals("true")) {  //  Add New Contact
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String number = request.getParameter("number");
                if (firstName != "" && lastName != "" && number!= ""
                    && friendDao.newFriend(firstName, lastName, number)) { %>
                    <h3>Success!</h3><br/>
                    Added Contact: <%= firstName%> <%= lastName%>, <%= number%><br/> <%
                } else { %>
                    <h3>Failure.</h3><br/>
                    Could not add Contact: <%= firstName%> <%= lastName%>, <%= number%><br/> <%
                } %>
                <br/><br/>
                
                <%-- Add Contact button: --%>
                <form action="add.jsp" method="POST">
                    <br/>
                    <input name="submitted" type="hidden" value="false" />
                    <input type="submit" value="Add New Contact" />
                </form>
                
                <form action="index.jsp" method="POST">
                    <br/>
                    <input type="submit" value="Return to Index" />
                </form>
                <%
            } else { %>
            <br/><br/>
            
                <%-- Get new contact info --%>
                <h3>Add Contact</h3>
                <h4>Please enter a 10-digit phone number.</h4><br/><br/>

                <form  action="add.jsp" method="POST">
                    <input name="submitted" type="hidden" value="true" />
                    <table border="0" cellspacing="0" cellpadding="0">
                        <tr><th>First Name</th><th>Last Name</th><th>Number</th></tr>
                        <tr>
                            <td><input name="firstName" type="text" /></td>
                            <td><input name="lastName" type="text" /></td>
                            <td><input name="number" type="number" /></td>
                        </tr>
                    </table>
            <br/><br/>
            
            <%-- Return to Index, button --%>
            <input name="submit" type="submit" value="Add" />
            </form>

            <% } %>
            
    </body>
</html>

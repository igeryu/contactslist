<%-- 
    Document   : index.jsp
    Created on : Oct 7, 2015, 8:43 AM
    Author     : Alan Johnson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="friendDao" class="domain.FriendDAO" scope="session" />
<jsp:setProperty name="friendDao" property="*" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alan Johnson - CMIS 440 - Final Project - Index</title>
    </head>
    <body>
        <h1>CMIS 440</h1>
        <h2>Alan Johnson</h2><br/><br/>
        <%
            //  Get number of contacts, and print appropriate message:
            int count = friendDao.countFriends();
            if (count < 1) {
                %> There are no contacts saved. <%
                } else { %>
                    Contacts: <%= count %>
                <% } %>
                <br/><br/>
            
             <%-- Search Form: --%>
            <form name="searchForm" action="search.jsp" method="POST">
                You can search for a contact by name<br/><br/>
                Name: <input type="text" name="searchString" id="searchString" /><br><br/>
                <input type="submit" value="Search" />
            </form>
            <br/><br/>
            
            <%-- Add Contact button: --%>
            <form action="add.jsp" method="POST">
                You can add a new contact by clicking the button below:
                <br/>
                <input name="submitted" type="hidden" value="false" />
                <input type="submit" value="Add New Contact" />
            </form>
        
    </body>
</html>

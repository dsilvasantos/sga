<%@ page import="java.io.*,java.util.*" %>
<html>
<head>
<title>Autenticação sistema monitor</title>
<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/images/favicon.ico"/>
</head>
<body>
<center>
<h1>Redirecionando ...</h1>
</center>
<%
   // New location to be redirected
   String site = new String(request.getContextPath()+"/login.xhtml");
   response.setStatus(response.SC_MOVED_TEMPORARILY);
   response.setHeader("Location", site); 
%>
</body>
</html>

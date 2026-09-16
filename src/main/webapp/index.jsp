<%--
  Redireciona a raiz da aplicacao para a home.
--%>
<%
    response.sendRedirect(request.getContextPath() + "/home");
%>

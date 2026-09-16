<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Trocas - MVC Aula</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css">
</head>
<body>
<header class="topbar">
    <div class="container">
        <strong>MVC Aula</strong>
        <nav>
            <a href="${pageContext.request.contextPath}/home">Home</a>
            <a href="${pageContext.request.contextPath}/usuarios">Usuarios</a>
            <a href="${pageContext.request.contextPath}/habilidades">Habilidades</a>
            <a href="${pageContext.request.contextPath}/trocas">Trocas</a>
        </nav>
    </div>
</header>

<main class="container">
    <div class="page-header">
        <h1>Trocas</h1>
        <a class="btn" href="${pageContext.request.contextPath}/trocas?acao=novo">Nova troca</a>
    </div>

    <c:if test="${not empty erro}">
        <div class="alert alert-erro">${erro}</div>
    </c:if>

    <div class="table-wrap">
        <c:choose>
            <c:when test="${empty trocas}">
                <p class="empty">Nenhuma troca cadastrada.</p>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Oferece</th>
                        <th>Interessado</th>
                        <th>Habilidade</th>
                        <th>Acoes</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="troca" items="${trocas}">
                        <tr>
                            <td>${troca.id}</td>
                            <td>${troca.usuarioOferecendo.nome}</td>
                            <td>${troca.usuarioInteressado.nome}</td>
                            <td>${troca.habilidade.nome}</td>
                            <td class="links">
                                <a href="${pageContext.request.contextPath}/trocas?acao=editar&id=${troca.id}">Editar</a>
                                <a href="${pageContext.request.contextPath}/trocas?acao=excluir&id=${troca.id}"
                                   onclick="return confirm('Excluir esta troca?');">Excluir</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</main>
</body>
</html>

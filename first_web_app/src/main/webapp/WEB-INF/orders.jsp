<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">

<%@ include file="head.jsp"%>

<body>

<%@ include file="navbar.jsp"%>

<div class="container">
    <div class="row py-2">
        <div class="col-12">
            <c:url value="/order/new_order" var="orderNewUrl"/>
            <a class="btn btn-primary" href="${orderNewUrl}">new order</a>
        </div>

        <div class="col-12">
            <table class="table table-bordered my-2">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Description</th>
                    <th scope="col">Price</th>
                    <th scope="col">Actions</th>
                </tr>
                </thead>
                <tbody>


                <c:forEach var="order" items="${requestScope.order}">

                    <tr>
                        <th scope="row">
                            <c:out value="${order.id}"/>
                        </th>

                        <td>
                            <c:out value="${order.description}"/>
                        </td>
                        <td>
                            <c:out value="${order.price}"/>
                        </td>
                        <td>
                            <c:url value="/order/edit_order" var="orderEditUrl">
                                <c:param name="id" value="${order.id}"/>
                            </c:url>
                            <a class="btn btn-success" href="${orderEditUrl}"><i class="fas fa-edit"></i></a>
                            <c:url value="/order/delete_order" var="orderDeleteUrl">
                                <c:param name="id" value="${order.id}"/>
                            </c:url>
                            <a class="btn btn-danger" href="${orderDeleteUrl}"><i class="far fa-trash-alt"></i></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="footer_scripts.jsp" %>

</html>

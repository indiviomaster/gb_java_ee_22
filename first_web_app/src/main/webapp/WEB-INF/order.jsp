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
            <c:url value="/order/order_post" var = "orderPostUrl"/>
            <form action="${orderPostUrl}" method="post">

                <input type="hidden" id="id" name="id" value="${order.id}">

                <div class="form-group">
                    <label>Description</label>
                    <input type="text" class="form-control" id="description" name="description" placeholder="Enter description" value="${order.description}">
                </div>
                <div class="form-group">
                    <label>Price</label>
                    <input type="text" class="form-control" id="price" name="price" placeholder="Enter price" value="${order.price}">
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </div>
</div>


<%@ include file="footer_scripts.jsp" %>

</html>





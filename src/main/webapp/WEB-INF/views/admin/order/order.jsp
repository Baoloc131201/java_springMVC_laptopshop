<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Dashboard - SB Admin</title>
        <link href="/css/style.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    </head>
    <body class="sb-nav-fixed">
        <jsp:include page="../layout/header/header.jsp" />
        <div id="layoutSidenav">
                    <jsp:include page="../layout/navbar/navbar.jsp" />
            <div id="layoutSidenav_content">
                <main class="px-4">
                    <div class="container-fluid">
                        <h1 class="mt-4">Dashboard</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                            <li class="breadcrumb-item active">Order</li>
                        </ol>
                        <div class="d-flex justify-content-between mb-3"><h3>Table Order</h3></div>
                    </div>
                    <hr />
                     <table class="mb-3 table table-bordered table-hover">
                           <thead>
                                   <tr>
                                        <th>ID</th>
                                        <th>Total Price</th>
                                        <th>User</th>
                                        <th>Status</th>
                                       <th>Action</th>
                                   </tr>
                               </thead>
                               <tbody class="table-group-divider">
                                   <c:forEach var="order" items="${orders}">
                                       <tr>
                                           <th>${order.id}</th>
                                           <td>
                                               <fmt:formatNumber type="number"
                                                   value="${order.totalPrice}" /> đ
                                           </td>
                                           <td>${order.receiverName}</td>
                                           <td>${order.status}</td>
                                           <td>
                                               <a href="/admin/order/${order.id}"
                                                   class="btn btn-success">View</a>
                                               <a href="/admin/order/update/${order.id}"
                                                   class="btn btn-warning  mx-2">Update</a>
                                               <a href="/admin/order/delete/${order.id}"
                                                   class="btn btn-danger">Delete</a>
                                           </td>
                                       </tr>
                                   </c:forEach>
                               </tbody>
                     </table>
                </main>
                <jsp:include page="../layout/footer/footer.jsp" />
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="js/scripts.js"></script>
    </body>
</html>
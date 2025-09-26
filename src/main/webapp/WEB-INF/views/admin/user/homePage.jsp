<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Dashboard</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                            <li class="breadcrumb-item active">User</li>
                        </ol>
                        <div class="container mt-5">
                            <div class="row">
                               <div class="col-12 mx-auto">
                                 <div class="d-flex justify-content-between mb-3">
                                  <h3>Table User</h3>
                                  <a href="/admin/user/create-user" class="btn btn-primary">Create a User</a>
                                  </div>
                                 <table class="table table-bordered table-hover mb-3">
                                   <thead>
                                       <tr>
                                         <th scope="col">ID</th>
                                         <th scope="col">Email</th>
                                         <th scope="col">FullName</th>
                                         <th scope="col">Role</th>
                                         <th scope="col mx-3">Action</th>
                                       </tr>
                                     </thead>
                                     <tbody class="table-group-divider">
                                     <c:forEach items="${listUsers}" var="user">
                                         <tr>
                                           <th scope="row">${user.id}</th>
                                           <td>${user.email}</td>
                                           <td>${user.fullName}</td>
                                           <td>${user.role.name}</td>
                                           <td><div class"group-btn">
                                           <button type="button" class="btn btn-success mx-2"><a href="/admin/user/detail-user/${user.id}" class="btn btn-success">View</a></button>
                                           <button type="button" class="btn btn-warning mx-2"><a href="/admin/user/update/${user.id}" class="btn btn-warning">Update</a></button>
                                           <button type="button" class="btn btn-danger mx-2"><a href="/admin/user/delete/${user.id}" class="btn btn-danger">Delete</a></button>
                                           </div></td>
                                         </tr>
                                     </c:forEach>
                                     </tbody>
                                 </table>
                                 <nav aria-label="Page navigation example">
                                                                                                                                          <ul class="pagination justify-content-center">
                                                                                                                                              <li class="page-item">
                                                                                                                                                  <a class="${1 eq currentPage ? 'disabled page-link' : 'page-link'}"
                                                                                                                                                      href="/admin/user/list?page=${currentPage - 1}"
                                                                                                                                                      aria-label="Previous">
                                                                                                                                                      <span aria-hidden="true">&laquo;</span>
                                                                                                                                                  </a>
                                                                                                                                              </li>
                                                                                                                                              <c:forEach begin="0" end="${totalPages - 1}" varStatus="loop">
                                                                                                                                                  <li class="page-item">
                                                                                                                                                      <a class="${(loop.index + 1) eq currentPage ? 'active page-link' : 'page-link'}"
                                                                                                                                                          href="/admin/user/list?page=${loop.index + 1}">
                                                                                                                                                          ${loop.index + 1}
                                                                                                                                                      </a>
                                                                                                                                                  </li>
                                                                                                                                              </c:forEach>
                                                                                                                                              <li class="page-item">
                                                                                                                                                  <a class="${totalPages eq currentPage ? 'disabled page-link' : 'page-link'}"
                                                                                                                                                      href="/admin/user/list?page=${currentPage + 1}"
                                                                                                                                                      aria-label="Next">
                                                                                                                                                      <span aria-hidden="true">&raquo;</span>
                                                                                                                                                  </a>
                                                                                                                                              </li>
                                                                                                                                          </ul>
                                                                                                                                      </nav>
                                 </div>
                            </div>
                    </div>
                </main>
                <jsp:include page="../layout/footer/footer.jsp" />
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="/js/script.js"></script>
    </body>
</html>
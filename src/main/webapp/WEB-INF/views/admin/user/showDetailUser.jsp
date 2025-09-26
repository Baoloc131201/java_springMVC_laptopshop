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
                            <li class="breadcrumb-item active">Detail User</li>
                        </ol>
                        <div class="container mt-5">
                            <div class="row">
                               <div class="col-12 mx-auto">
                                 <div class="d-flex justify-content-between">
                                  <h3>Detail User With ${userDetail.id}</h3>
                                  </div>
                                 <table class="table table-bordered table-hover mb-3">
                                     <tbody class="table-group-divider">
                                         <ul class="list-group list-group-flush">
                                           <li class="list-group-item">ID: ${userDetail.id}</li>
                                           <li class="list-group-item">Email: ${userDetail.email}</li>
                                           <li class="list-group-item">FullName: ${userDetail.fullName}</li>
                                           <li class="list-group-item">Address: ${userDetail.address}</li>
                                           <li class="list-group-item">Phone: ${userDetail.phone}</li>

                                           <li class="list-group-item" style="width: 60%">Avatar: <img src="/images/avatar/${userDetail.avatar}"/></li>
                                         </ul>
                                     </tbody>
                                 </table>
                                 </div>
                                 <a href="/admin/user/list" class="btn btn-success mt-3">Back</a>
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
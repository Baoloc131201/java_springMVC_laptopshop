<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
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
                            <li class="breadcrumb-item active">Update User</li>
                        </ol>
                        <div class="container mt-5">
                            <div class="row">
                                <div class="col-md-6 col-12 mx-auto">
                                 <h3>Update a User</h3>
                                 </hr>
                                 <form:form action="/admin/user/update" modelAttribute="userDetail" method="post">
                                  <div class="mb-3" style="display:none">
                                      <label for="exampleFormControlInput1" class="form-label">Id:</label>
                                      <form:input class="form-control" path="id" placeholder="id"/>
                                  </div>
                                 <div class="mb-3">
                                      <label for="exampleFormControlInput1" class="form-label">Name:</label>
                                      <form:input class="form-control" path="name" placeholder="Lộc"/>
                                 </div>
                                 <div class="mb-3">
                                      <label for="exampleFormControlInput1" class="form-label">FullName:</label>
                                      <form:input class="form-control" path="fullName" placeholder="Huỳnh Bảo Lộc"/>
                                 </div>
                                 <div class="mb-3">
                                      <label for="exampleFormControlInput1" class="form-label">Email:</label>
                                      <form:input class="form-control" path="email" placeholder="name@example.com" disabled="true"/>
                                 </div>

                                 <div class="mb-3">
                                       <label for="exampleFormControlInput1" class="form-label">Phone Number:</label>
                                        <form:input class="form-control" path="phone" placeholder="0337041207"/>
                                 </div>

                                  <div class="mb-3">
                                        <label for="exampleFormControlInput1" class="form-label">Address:</label>
                                        <form:input class="form-control" path="address" placeholder="107/26A quang trung....."/>
                                  </div>
                                 <button type="submit" class="btn btn-primary">update</button>
                                  <a href="/admin/user" class="btn btn-success">Back</a>
                                 </form:form>
                                </div>
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
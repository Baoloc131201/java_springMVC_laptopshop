<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<%
    org.springframework.validation.BindingResult bindingResult = (org.springframework.validation.BindingResult) request.getAttribute("org.springframework.validation.BindingResult.userCreate");
    java.util.List<org.springframework.validation.FieldError> fieldNameErrors = bindingResult.getFieldErrors("name");
    java.util.List<org.springframework.validation.FieldError> fieldEmailErrors = bindingResult.getFieldErrors("email");
    java.util.List<org.springframework.validation.FieldError> fieldPasswordErrors = bindingResult.getFieldErrors("password");
    pageContext.setAttribute("fieldNameErrors", fieldNameErrors);
    pageContext.setAttribute("fieldEmailErrors", fieldEmailErrors);
    pageContext.setAttribute("fieldPasswordErrors", fieldPasswordErrors);
%>
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
                            <li class="breadcrumb-item active">User</li>
                        </ol>
                        <div class="container mt-5">
                            <div class="row">
                                <div class="col-md-6 col-12 mx-auto">
                                 <h3>Create a User</h3>
                                 </hr>
                                 <form:form action="/admin/user" modelAttribute="userCreate" enctype="multipart/form-data" method="post">
                                 <div class="mb-3 col-12">

                                <c:set var="hasNotBlankError" value="false" />
                                <c:forEach var="error" items="${fieldNameErrors}">
                                    <c:if test="${error.code == 'NotBlank'}">
                                        <c:set var="hasNotBlankError" value="true" />
                                    </c:if>
                                </c:forEach>

                                <label for="exampleFormControlInput1" class="form-label">Name:</label>
                                <form:input class="form-control ${not empty fieldErrors ? 'is-invalid' : ''}" path="name" placeholder="Lộc"/>

                                <div class="invalid-feedback" style="display:block;">
                                    <c:choose>
                                        <c:when test="${hasNotBlankError}">
                                            <c:forEach var="error" items="${fieldNameErrors}">
                                                <c:if test="${error.code == 'NotBlank'}">
                                                    ${error.defaultMessage}<br/>
                                                </c:if>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="error" items="${fieldNameErrors}">
                                                <c:if test="${error.code != 'NotBlank'}">
                                                    ${error.defaultMessage}<br/>
                                                </c:if>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </div>


                                 <div class="mb-3 col-12">
                                      <label for="exampleFormControlInput1" class="form-label">FullName:</label>
                                      <form:input class="form-control" path="fullName" placeholder="Huỳnh Bảo Lộc"/>
                                 </div>

                                 <c:set var="hasNotBlankError" value="false" />
                                                                 <c:forEach var="error" items="${fieldEmailErrors}">
                                                                     <c:if test="${error.code == 'NotBlank'}">
                                                                         <c:set var="hasNotBlankError" value="true" />
                                                                     </c:if>
                                                                 </c:forEach>

                                                                 <label for="exampleFormControlInput1" class="form-label">Email:</label>
                                                                 <form:input class="form-control ${not empty fieldErrors ? 'is-invalid' : ''}" path="email" placeholder="baoloc131201@gmail.com"/>

                                                                 <div class="invalid-feedback" style="display:block;">
                                                                     <c:choose>
                                                                         <c:when test="${hasNotBlankError}">
                                                                             <c:forEach var="error" items="${fieldNameErrors}">
                                                                                 <c:if test="${error.code == 'NotBlank'}">
                                                                                     ${error.defaultMessage}<br/>
                                                                                 </c:if>
                                                                             </c:forEach>
                                                                         </c:when>
                                                                         <c:otherwise>
                                                                             <c:forEach var="error" items="${fieldNameErrors}">
                                                                                 <c:if test="${error.code != 'NotBlank'}">
                                                                                     ${error.defaultMessage}<br/>
                                                                                 </c:if>
                                                                             </c:forEach>
                                                                         </c:otherwise>
                                                                     </c:choose>
                                                                 </div>

                                 <c:set var="hasNotBlankError" value="false" />
                                                                 <c:forEach var="error" items="${fieldPasswordErrors}">
                                                                     <c:if test="${error.code == 'NotBlank'}">
                                                                         <c:set var="hasNotBlankError" value="true" />
                                                                     </c:if>
                                                                 </c:forEach>

                                                                 <label for="exampleFormControlInput1" class="form-label">Password:</label>
                                                                 <form:input class="form-control ${not empty fieldErrors ? 'is-invalid' : ''}" path="password" placeholder="Lộc" type="password"/>

                                                                 <div class="invalid-feedback" style="display:block;">
                                                                     <c:choose>
                                                                         <c:when test="${hasNotBlankError}">
                                                                             <c:forEach var="error" items="${fieldPasswordErrors}">
                                                                                 <c:if test="${error.code == 'NotBlank'}">
                                                                                     ${error.defaultMessage}<br/>
                                                                                 </c:if>
                                                                             </c:forEach>
                                                                         </c:when>
                                                                         <c:otherwise>
                                                                             <c:forEach var="error" items="${fieldPasswordErrors}">
                                                                                 <c:if test="${error.code != 'NotBlank'}">
                                                                                     ${error.defaultMessage}<br/>
                                                                                 </c:if>
                                                                             </c:forEach>
                                                                         </c:otherwise>
                                                                     </c:choose>
                                                                 </div>

                                 <div class="mb-3">
                                       <label for="exampleFormControlInput1" class="form-label">Phone Number:</label>
                                        <form:input class="form-control" path="phone" placeholder="0337041207"/>
                                 </div>

                                  <div class="mb-3">
                                        <label for="exampleFormControlInput1" class="form-label">Address:</label>
                                        <form:input class="form-control" path="address" placeholder="107/26A quang trung....."/>
                                  </div>

                                  <div class="mb-3 col-12 col-md-6">
                                  <label for="example" class="form-label">Role</label>
                                  <form:select class="form-select" path="role.name">
                                    <form:option value="ADMIN">Admin</form:option>
                                    <form:option value="USER">User</form:option>
                                  </form:select>
                                  </div>

                                  <div class="mb-3 col-12 col-md-6">
                                      <label for="avatarFile" class="form-label">Avatar:</label>
                                      <input class="form-control" type="file" id="avatarFile"
                                      accept=".png, .jpg, .jpeg" name="avatarFile" />
                                  </div>

                                 <div class="col-12 mb-3">
                                       <img style="max-height: 250px; display: none;" alt="avatar preview" id="avatarPreview" />
                                 </div>

                                 <div class="col-12 mb-5">
                                  <button type="submit" class="btn btn-primary">create</button>
                                  <a href="/admin/user/list" class="btn btn-success">Back</a>
                                 </div>
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
        <script src="/js/script.js">
        </script>
    </body>
</html>
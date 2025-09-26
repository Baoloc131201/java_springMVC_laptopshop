<%@page contentType="text/html" pageEncoding="UTF-8" %>
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
                <title>Register - Laptopshop</title>
                <link href="/css/style.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
            </head>

            <body class="bg-primary">
                   <div id="layoutAuthentication">
                                       <div id="layoutAuthentication_content">
                                           <main>
                                               <div class="container">
                                                 <div class="row justify-content-center">


                                                  <div class="card-header">
                                                      <h3 class="text-center font-weight-light my-4">Forgot Password</h3>
                                                  </div>
                                                    <c:if test="${error != null}">
                                                        <p class="text-danger">[[${error}]]</p>
                                                    </c:if>
                                                    <c:if test="${message != null}">
                                                        <p class="text-warning">[[${message}]]</p>
                                                    </c:if>

                                                    <form action="/forgot_password" method="post" class="card shadow-lg" style="max-width: 420px; margin: 0 auto;">
                                                    <div class="border-secondary rounded p-3">
                                                        <div>
                                                            <p>We will be sending a reset password link to your email.</p>
                                                        </div>
                                                        <div>
                                                            <p>
                                                                <input type="email" name="email" class="form-control" placeholder="Enter your e-mail" required autofocus/>
                                                            </p>
                                                            <div>
                                                            <div>
                                                                           <input type="hidden" name="${_csrf.parameterName}"
                                                               value="${_csrf.token}" />

                                                            </div>
                                                           </div>
                                                            <p class="text-center">
                                                                <input type="submit" value="Send" class="btn btn-primary" />
                                                            </p>
                                                        </div>
                                                    </div>
                                                    </form>

                                               </div>
                                           </main>
                                       </div>
                   </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="/js/script.js"></script>
            </body>

 </html>
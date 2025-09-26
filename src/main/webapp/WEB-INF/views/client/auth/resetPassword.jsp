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
                                                      <h3 class="text-center font-weight-light my-4">Reset Password</h3>
                                                  </div>

                                                    <form action="/reset_password" method="post" class="card shadow-lg" style="max-width: 420px; margin: 0 auto;">
                                                    <div class="border-secondary rounded p-3">

                                                        <div>
                                                             <input type="hidden" name="token" value="${token}" />
                                                            <p>
                                                               <input type="password" name="password" id="password" class="form-control"
                                                               placeholder="Enter your new password" required autofocus />
                                                            </p>

                                                             <input type="hidden" name="${_csrf.parameterName}" id="token" value="${_csrf.token}" />

                                                            <p>
                                                               <input type="password" class="form-control" placeholder="Confirm your new password"
                                                               required oninput="checkPasswordMatch(this);" />
                                                            </p>

                                                            <p class="text-center">
                                                               <input type="submit" value="Change Password" class="btn btn-primary" />
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
                <script>
                function checkPasswordMatch(fieldConfirmPassword) {
                    if (fieldConfirmPassword.value != $("#password").val()) {
                        fieldConfirmPassword.setCustomValidity("Passwords do not match!");
                    } else {
                        fieldConfirmPassword.setCustomValidity("");
                    }
                }
                </script>
            </body>

 </html>
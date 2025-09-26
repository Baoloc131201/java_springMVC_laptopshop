<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
    org.springframework.validation.BindingResult bindingResult = (org.springframework.validation.BindingResult) request.getAttribute("org.springframework.validation.BindingResult.productCreate");
    java.util.List<org.springframework.validation.FieldError> fieldNameErrors = bindingResult.getFieldErrors("name");
    java.util.List<org.springframework.validation.FieldError> fieldPriceErrors = bindingResult.getFieldErrors("price");
    java.util.List<org.springframework.validation.FieldError> fieldDetailDescErrors = bindingResult.getFieldErrors("detailDesc");
    java.util.List<org.springframework.validation.FieldError> fieldShortDescErrors = bindingResult.getFieldErrors("shortDesc");
    java.util.List<org.springframework.validation.FieldError> fieldQuantityErrors = bindingResult.getFieldErrors("quantity");

    pageContext.setAttribute("fieldNameErrors", fieldNameErrors);
    pageContext.setAttribute("fieldPriceErrors", fieldPriceErrors);
    pageContext.setAttribute("fieldShortErrors", fieldShortDescErrors);
    pageContext.setAttribute("fieldQuantityErrors", fieldQuantityErrors);
    pageContext.setAttribute("fieldDetailErrors", fieldDetailDescErrors);

%>
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
                            <li class="breadcrumb-item active">Product</li>
                        </ol>
                        <div class="container mt-5">
                            <div class="row">
                                <div class="col-md-6 col-12 mx-auto">
                                 <h3>Create a Product</h3>
                                 </hr>

                                  <c:set var="hasNotBlankError" value="false" />
                                  <c:forEach var="error" items="${fieldNameErrors}">
                                    <c:if test="${error.codes == 'NotBlank' || error.codes == 'NotNull'}">
                                    <c:set var="hasNotBlankError" value="true" />
                                    </c:if>
                                  </c:forEach>

                                 <form:form action="/admin/product" modelAttribute="productCreate" enctype="multipart/form-data" method="post">
                                  <label for="exampleFormControlInput1" class="form-label">Name:</label>
                                   <form:input class="form-control ${not empty fieldErrors ? 'is-invalid' : ''}" path="name" placeholder="Lộc"/>

                                                                 <div class="invalid-feedback" style="display:block;">
                                                                     <c:choose>
                                                                         <c:when test="${hasNotBlankError}">
                                                                             <c:forEach var="error" items="${fieldNameErrors}">
                                                                                 <c:if test="${error.codes == 'NotBlank' || error.codes == 'NotNull'}">
                                                                                     ${error.defaultMessage}<br/>
                                                                                 </c:if>
                                                                             </c:forEach>
                                                                         </c:when>
                                                                         <c:otherwise>
                                                                             <c:forEach var="error" items="${fieldNameErrors}">
                                                                                 <c:if test="${error.codes != 'NotBlank'}">
                                                                                     ${error.defaultMessage}<br/>
                                                                                 </c:if>
                                                                             </c:forEach>
                                                                         </c:otherwise>
                                                                     </c:choose>
                                                                 </div>

                                  <c:set var="hasNotBlankError" value="false" />
                                  <c:forEach var="error" items="${fieldPriceErrors}">
                                    <c:if test="${error.codes == 'NotBlank' || error.codes == 'NotNull'}">
                                    <c:set var="hasNotBlankError" value="true" />
                                    </c:if>
                                  </c:forEach>

                                  <label for="exampleFormControlInput1" class="form-label">Price:</label>
                                     <form:input class="form-control ${not empty fieldErrors ? 'is-invalid' : ''}" path="price" placeholder="Lộc"/>

                                                                                                  <div class="invalid-feedback" style="display:block;">
                                                                                                      <c:choose>
                                                                                                          <c:when test="${hasNotBlankError}">
                                                                                                              <c:forEach var="error" items="${fieldPriceErrors}">
                                                                                                                  <c:if test="${error.codes == 'NotBlank' || error.codes == 'NotNull'}">
                                                                                                                      ${error.defaultMessage}<br/>
                                                                                                                  </c:if>
                                                                                                              </c:forEach>
                                                                                                          </c:when>
                                                                                                          <c:otherwise>
                                                                                                              <c:forEach var="error" items="${fieldPriceErrors}">
                                                                                                                  <c:if test="${error.codes != 'NotBlank'}">
                                                                                                                      ${error.defaultMessage}<br/>
                                                                                                                  </c:if>
                                                                                                              </c:forEach>
                                                                                                          </c:otherwise>
                                                                                                      </c:choose>
                                                                                                  </div>

                                  <c:set var="hasNotBlankError" value="false" />
                                        <c:forEach var="error" items="${fieldDetailErrors}">
                                        <c:if test="${error.codes == 'NotBlank' || error.codes == 'NotNull'}">
                                        <c:set var="hasNotBlankError" value="true" />
                                        </c:if>
                                  </c:forEach>

                                  <label for="exampleFormControlInput1" class="form-label">Detail Description:</label>
                                  <form:input class="form-control ${not empty fieldErrors ? 'is-invalid' : ''}" path="detailDesc" placeholder="Lộc"/>

                                                                                                                                   <div class="invalid-feedback" style="display:block;">
                                                                                                                                       <c:choose>
                                                                                                                                           <c:when test="${hasNotBlankError}">
                                                                                                                                               <c:forEach var="error" items="${fieldDetailErrors}">
                                                                                                                                                   <c:if test="${error.codes == 'NotBlank' || error.codes == 'NotNull'}">
                                                                                                                                                       ${error.defaultMessage}<br/>
                                                                                                                                                   </c:if>
                                                                                                                                               </c:forEach>
                                                                                                                                           </c:when>
                                                                                                                                           <c:otherwise>
                                                                                                                                               <c:forEach var="error" items="${fieldDetailErrors}">
                                                                                                                                                   <c:if test="${error.codes != 'NotBlank'}">
                                                                                                                                                       ${error.defaultMessage}<br/>
                                                                                                                                                   </c:if>
                                                                                                                                               </c:forEach>
                                                                                                                                           </c:otherwise>
                                                                                                                                       </c:choose>
                              </div>

                                  <c:set var="hasNotBlankError" value="false" />
                                      <c:forEach var="error" items="${fieldShortErrors}">
                                      <c:if test="${error.codes == 'NotBlank' || error.codes == 'NotNull'}">
                                      <c:set var="hasNotBlankError" value="true" />
                                      </c:if>
                                  </c:forEach>

                                  <label for="exampleFormControlInput1" class="form-label">Short Description:</label>
                                  <form:input class="form-control ${not empty fieldErrors ? 'is-invalid' : ''}" path="shortDesc" placeholder="Lộc"/>

                                 <div class="invalid-feedback" style="display:block;">
                                   <c:choose>
                                     <c:when test="${hasNotBlankError}">
                                         <c:forEach var="error" items="${fieldShortErrors}">
                                             <c:if test="${error.codes == 'NotBlank' || error.codes == 'NotNull'}">
                                                 ${error.defaultMessage}<br/>
                                             </c:if>
                                         </c:forEach>
                                     </c:when>
                                     <c:otherwise>
                                         <c:forEach var="error" items="${fieldShortErrors}">
                                             <c:if test="${error.codes != 'NotBlank'}">
                                                 ${error.defaultMessage}<br/>
                                             </c:if>
                                         </c:forEach>
                                     </c:otherwise>
                                   </c:choose>
                                  </div>

                                   <c:set var="hasNotBlankError" value="false" />
                                      <c:forEach var="error" items="${fieldShortErrors}">
                                      <c:if test="${error.codes == 'NotBlank' || error.codes == 'NotNull'}">
                                      <c:set var="hasNotBlankError" value="true" />
                                      </c:if>
                                  </c:forEach>

                                  <label for="exampleFormControlInput1" class="form-label">Quantity:</label>
                                  <form:input class="form-control ${not empty fieldErrors ? 'is-invalid' : ''}" path="quantity" placeholder="2"/>

                                   <div class="invalid-feedback" style="display:block;">
                                                                  <c:choose>
                                                                      <c:when test="${hasNotBlankError}">
                                                                          <c:forEach var="error" items="${fieldQuantityErrors}">
                                                                              <c:if test="${error.codes == 'NotBlank' || error.codes == 'NotNull'}">
                                                                                  ${error.defaultMessage}<br/>
                                                                              </c:if>
                                                                          </c:forEach>
                                                                      </c:when>
                                                                      <c:otherwise>
                                                                          <c:forEach var="error" items="${fieldQuantityErrors}">
                                                                              <c:if test="${error.codes != 'NotBlank'}">
                                                                                  ${error.defaultMessage}<br/>
                                                                              </c:if>
                                                                          </c:forEach>
                                                                      </c:otherwise>
                                                                   </c:choose>
                                   </div>

                                  <div class="mb-3 col-12 col-md-6">
                                  <label for="example" class="form-label">Factory</label>
                                  <form:select class="form-select" path="factory">
                                    <form:option value="APPLE">Apple (Macbook)</form:option>
                                    <form:option value="ASUS">Asus</form:option>
                                    <form:option value="LENOVO">Lenovo</form:option>
                                    <form:option value="DELL">Dell</form:option>
                                    <form:option value="LG">LG</form:option>
                                    <form:option value="ACER">Acer</form:option>
                                  </form:select>
                                  </div>

                                  <div class="mb-3 col-12 col-md-6">
                                          <label class="form-label">Target:</label>
                                      <form:select class="form-select" path="target">
                                            <form:option value="GAMING">Gaming</form:option>
                                            <form:option value="SINHVIEN-VANPHONG">Sinh viên - Văn phòng
                                            </form:option>
                                                 <form:option value="THIET-KE-DO-HOA">Thiết kế đồ họa
                                                 </form:option>
                                                  <form:option value="MONG-NHE">Mỏng nhẹ</form:option>
                                                   <form:option value="DOANH-NHAN">Doanh nhân</form:option>
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
                                  <a href="/admin/product/list" class="btn btn-success">Back</a>
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
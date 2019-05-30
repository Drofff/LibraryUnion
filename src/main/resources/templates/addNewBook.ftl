<!DOCTYPE html>
<html lang="en">
<head>
    <title>Library - Add Book</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.0.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</head>
<body>
<script>
    $(function() {

    <#if oldDescription??>
        $("#desc").val("${oldDescription}");
        </#if>

        $("#photoInput").change(function() {
            $("#forImage").html("<img class='mx-5' width='70px' height='70px' alt='Wrong url' src='" + $("#photoInput").val() + "' />");
        });
    });
</script>
<nav class="navbar navbar-light bg-primary" >
    <a class="navbar-brand" href="/"><font color="white">Library</font></a>
    <a class="nav-link" href="#"><font color="white">Adding new book</font></a>
</nav>
<#if message??>
<div class="alert alert-danger mx-auto text-center" style="width : 280px;" role="alert">
    ${message}
</div>
</#if>
<form action="/library/addBook" method="post">
    <div class="form-group row mt-4 mx-4">
        <label for="staticEmail" class="col-sm-2 col-form-label">Name of book</label>
        <input type="text" id="staticEmail" style="width : 400px;" name="name" <#if oldName??>value = "${oldName}"</#if>   <#if name??>class="form-control is-invalid" <#else> class="form-control" </#if> placeholder="Book name">
<#if name??>
<div class="invalid-feedback" style="margin-left : 220px">
    ${name}
</div>
</#if>
</div>

<div class="form-group row mt-4 mx-4">
    <label class="col-sm-2 col-form-label">Author name</label>
    <input type="text" style="width : 400px;" name="author" <#if oldAuthor??>value = "${oldAuthor}"</#if>   <#if author??>class="form-control is-invalid" <#else> class="form-control" </#if> placeholder="author..">
<#if author??>
<div class="invalid-feedback" style="margin-left : 220px">
    ${author}
</div>
</#if>
</div>

<div class="form-group row mt-4 mx-4">
    <label  class="col-sm-2 col-form-label">Description</label>
    <textarea rows="3" id="desc" style="width : 400px;" name="description"  <#if description??>class="form-control is-invalid" <#else> class="form-control" </#if> ></textarea>
<#if description??>
<div class="invalid-feedback" style="margin-left : 220px">
    ${description}
</div>
</#if>
</div>

<div class="form-group row mt-4 mx-4">
    <label  class="col-sm-2 col-form-label">Year of publishing</label>
    <input type="date" style="width : 400px;" name="year" <#if oldYear??>value="${oldYear}" </#if> <#if year??>class="form-control is-invalid" <#else> class="form-control" </#if> >
<#if year??>
<div class="invalid-feedback" style="margin-left : 220px">
    ${year}
</div>
</#if>
</div>

<select name="subscriptionType" class="mx-5 my-3">
    <#list subscriptionTypes as t>
    <option value="${t}">${t}</option>
    </#list>
</select>

<select name="type" class="mx-5 my-3">
    <#list types as t>
    <option value="${t}">${t}</option>
</#list>
</select>

<div class="form-group row mt-4 mx-4">
    <label class="col-sm-2 col-form-label">Photo</label>
    <input type="text" id="photoInput" style="width : 400px;" name="photoUrl" <#if oldPhotoUrl??>value = "${oldPhotoUrl}"</#if>   <#if photoUrl??>class="form-control is-invalid" <#else> class="form-control" </#if> placeholder="Photo url">
<#if photoUrl??>
<div class="invalid-feedback" style="margin-left : 220px">
    ${photoUrl}
</div>
</#if>
<div id="forImage"></div>
</div>

<br/>
<br/>
<input type="submit" value="Add Book" class="btn btn-primary mx-5 my-2"/>
</form>
</body>
</html>
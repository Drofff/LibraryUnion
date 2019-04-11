<!DOCTYPE html>
<html lang="en">
<head>
    <title>Library - Registration</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</head>
<body>
<nav class="navbar navbar-light bg-primary" >
    <a class="navbar-brand" href="/"><font color="white">Library</font></a>
</nav>
<#if message??>
<div class="alert alert-danger mx-auto text-center" style="width : 280px;" role="alert">
    ${message}
</div>
</#if>
<form action="/registration" method="post">
    <div class="form-group row mt-4 mx-4">
        <label for="staticEmail" class="col-sm-2 col-form-label">Email</label>
            <input type="text" id="staticEmail" style="width : 400px;" name="username" <#if oldEmail??>value = "${oldEmail}"</#if>   <#if username??>class="form-control is-invalid" <#else> class="form-control" </#if> placeholder="email@example.com">
    <#if username??>
    <div class="invalid-feedback" style="margin-left : 220px">
        ${username}
    </div>
</#if>
    </div>
    <small class="form-text text-muted" style="margin-left : 220px">Will be used for activation</small>
    <div class="form-group row  mx-4 my-4">
        <label for="inputPassword" class="col-sm-2 col-form-label">Password</label>
            <input type="password" name="password" style="width : 400px;" <#if password??>class="form-control is-invalid" <#else> class="form-control" </#if> id="inputPassword" placeholder="Password">
            <#if password??>
            <div class="invalid-feedback" style="margin-left : 220px">
                ${password}
            </div>
        </#if>
    </div>
    <div class="form-group row  mx-4 my-4">
        <label for="inputPassword" class="col-sm-2 col-form-label">Confirm password</label>
            <input type="password" name="confirm" style="width : 400px;" <#if confirmationError??> class="form-control is-invalid" <#else> class="form-control" </#if> id="inputConf" placeholder="Repeat password">
            <#if confirmationError??>
                <div class="invalid-feedback" style="margin-left : 220px;">
                    Passwords are not equal
                </div>
            </#if>
    </div>
<br/>
<br/>
    <input type="submit" value="Register" class="btn btn-primary mx-5 my-2"/>
    <a href="/" class="mx-1">Up to the main page</a>
</form>
</body>
</html>
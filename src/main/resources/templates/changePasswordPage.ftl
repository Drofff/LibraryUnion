<!DOCTYPE html>
<html lang="en">
<head>
    <title>Library - Change Password</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</head>
<body background="https://i.pinimg.com/originals/4a/d3/1e/4ad31e581fea06529ca8997f583b87be.jpg">
<nav class="navbar navbar-light bg-primary" >
    <a class="navbar-brand" href="/"><font color="white">Library</font></a>
</nav>
<#if message??>
<div class="alert alert-danger mx-auto text-center" style="width : 50%;" role="alert">
    ${message}
</div>
</#if>
<div class="card text-center mx-auto my-5" style="width : 50%;">
    <div class="card-body">
        <div class="text-left">
        <p>Change password of user ${email}</p>
        </div>
        <form action="/library/passwd" method="post" class="form-inline">
            <div class="form-group my-2">
                <label for="inputPassword6">Old password</label>
                <div class="ml-3">
                <input type="password" name="old-passwd" class="form-control ml-4 mr-3" aria-describedby="passwordHelpInline3">
                </div>
                </div>
            <div class="form-group my-2">
                <label for="inputPassword6">Password</label>
                <div class="ml-3">
                <input type="password" name="passwd" id="inputPassword6" class="form-control ml-5 mr-3" aria-describedby="passwordHelpInline">
                </div>
                    <small id="passwordHelpInline" class="text-muted">
                    Must be longer than 6 characters
                </small>
            </div>
            <div class="form-group my-2">
                <label for="inputPassword6">Repeat password</label>
                <input type="password" name="repeat-passwd" class="form-control ml-2 mr-3" aria-describedby="passwordHelpInline2">
                <small id="passwordHelpInline2" class="text-muted">
                    Must be longer than 6 characters
                </small>
            </div>
            <button type="submit" class="btn btn-primary">Change Password</button>
        </form>
    </div>
    <div class="card-footer text-muted">
        Library Union - We are always near by
    </div>
</div>
</body>
</html>
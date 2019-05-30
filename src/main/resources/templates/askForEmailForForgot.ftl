<!DOCTYPE html>
<html lang="en">
<head>
    <title>Library - Password recovery</title>
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
    <div class="card-header">
        Password Recovery
    </div>
    <div class="card-body">
        <form action="/recover" method="post">
            <h5 class="card-title" style="font-size:22px;">Email:</h5>
        <input class="mx-auto my-1 form-control" style="width:300px;" type="text" name="email" />
            <input class="mx-4 my-2 btn btn-primary" type="submit" value="Recover"/>
        </form>
    </div>
    <div class="card-footer text-muted">
        Library Union - We are always near by
    </div>
</div>
</body>
</html>
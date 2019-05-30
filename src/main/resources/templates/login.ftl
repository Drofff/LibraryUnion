<!DOCTYPE html>
<html lang="en">
<head>
    <title>Library - Login</title>
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
<br/>
<br/>
<div class="container">
    <div class="row">
        <div class="col">
<form action="/login" method="post">
    <div class="form-group row">
        <label for="username" class="mx-5 my-2">Email</label>
        <input type="text" name="username" id = "username" class = "form-control" style="width : 300px;" aria-describedby="emailHelp" placeholder="Enter email" />
    </div>
    <div class="form-group row mt-4">
        <label for="password" class="ml-5 mr-3 my-2">Password</label>
        <input type="password" name="password" id = "password" class = "form-control" style="width : 300px;" placeholder="Password" />
    </div>
    <br/>
    <div class="form-group row" style="margin-left : 100px;">
        <label for="remMe" class="form-check-label mx-1">Remember me</label>
        <input type="checkbox" name="remember_me" id = "remMe" class = "form-check-input" />
    </div>
    <br/>
    <button class="btn btn-primary mx-5 my-2" type="submit">Login</button>
    <#if forgot??>
    <a href="/recover" class="ml-3">Hm.. I forgot it!</a>
</#if>
</form>

        </div>
        <div class="col">

<div class="card mx-auto my-1" style="width: 320px;">
    <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRJx4F6UmhDcqLuYpYPdrkNtZjariNB5t1JEFc2LPXyCFU74ZAlXA" class="card-img-top" alt="Welcome!">
    <div class="card-body">
        <h5 class="card-title">Welcome to our libraries union</h5>
        <p class="card-text">We propose you, dear customer, system where you can find and order book in libraries all around your place. And it's free! To join us you need to register in our system. Then fill your universal library card and receive it instantly. That's it!</p>
    </div>
    <ul class="list-group list-group-flush">
        <li class="list-group-item"><a href="/registration" class="card-link">Registration</a></li>
        <li class="list-group-item"><a href="/" class="card-link">Main page</a></li>
    </ul>
</div>
        </div>
    </div>
</div>
</body>
</html>
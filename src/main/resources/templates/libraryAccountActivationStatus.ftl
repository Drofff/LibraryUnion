<!DOCTYPE html>
<html lang="en">
<head>
    <title>Library - Home</title>
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
<nav class="nav nav-pills flex-column flex-sm-row" style="background-color : white;">
    <a class="flex-sm-fill text-sm-center nav-link disabled" href="#" tabindex="-1" aria-disabled="true" href="#">My books</a>
    <a class="flex-sm-fill text-sm-center nav-link  disabled" href="#" tabindex="-1" aria-disabled="true" href="#">Renting info</a>
    <a class="flex-sm-fill text-sm-center nav-link  disabled" href="#" tabindex="-1" aria-disabled="true" href="#">Give/Receive book</a>
    <a class="flex-sm-fill text-sm-center nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Profile</a>
</nav>
<#if message??>
<div class="alert alert-success mx-auto text-center" style="width : 100%;" role="alert">
    ${message}
</div>
</#if>
<div class="card text-center mx-auto my-5" style="width : 50%;">
    <div class="card-header">
        Please, wait
    </div>
    <div class="card-body">
        <h5 class="card-title">Thank you for using our service!</h5>
        <p class="card-text">We will consider your application as soon as possible! Thank you for understanding!</p>
    </div>
    <div class="card-footer text-muted">
        Library Union - We are always near by
    </div>
</div>
</body>
</html>
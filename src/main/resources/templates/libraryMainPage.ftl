<!DOCTYPE html>
<html lang="en">
<head>
    <title>Library - Home</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</head>
<body>
<nav class="navbar navbar-light bg-primary" >
    <a class="navbar-brand" href="/library"><font color="white">Library</font></a>
    <a class="nav-link" href="/library/addBook"><font color="white">Add book</font></a>
</nav>
<nav class="nav nav-pills flex-column flex-sm-row my-2 mx-2">
    <a class="flex-sm-fill text-sm-center nav-link active" href="/library/">My books</a>
    <a class="flex-sm-fill text-sm-center nav-link" href="/library/analyse">Renting info</a>
    <a class="flex-sm-fill text-sm-center nav-link" href="/library/operations">Give/Receive book</a>
    <a class="flex-sm-fill text-sm-center nav-link" href="/library/profile" >Profile</a>
</nav>
<#if message??>
<div class="alert alert-success mx-auto text-center" style="width : 50%;" role="alert">
    ${message}
</div>
</#if>
<div class="card-columns">
<#list books as b>
<div class="card mx-5 my-5" style="width:300px;">
    <div class="text-center">
    <img src="${b.photoUrl}" style="max-width : 300px;" height="300px" alt="">
    </div>
        <div class="card-body">
            <h5 class="card-title">${b.name}</h5>
            <p class="card-text"><small class="text-muted">Rate: ${b.rate}/10</small></p>
            <p class="card-text">Author: ${b.author}</p>
            <p class="card-text">${b.description}</p> </div>
    <div class="row">
        <div class="col">
            <a href="/library/delete/${b.id}" class="btn btn-primary ml-3 mb-2">Delete</a>
        </div>
        <div class="col">
            <a href="/library/edit/${b.id}" class="btn btn-primary mb-2">Edit</a>
        </div>
    </div>
        <div class="card-footer text-center">
            <small class="text-muted"><#if b.subscriptionType == 'REGULAR'>${b.subscriptionType}<#else><font class="text-primary">${b.subscriptionType}</font></#if></small>
        </div>
</div>
</#list>
</div>
</body>
</html>
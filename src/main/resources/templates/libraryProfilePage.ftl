<!DOCTYPE html>
<html lang="en">
<head>
    <title>Library - Profile</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</head>
<body background="https://i.pinimg.com/originals/4a/d3/1e/4ad31e581fea06529ca8997f583b87be.jpg">
<nav class="navbar navbar-light bg-primary" >
    <a class="navbar-brand" href="/library"><font color="white">Library</font></a>
    <a class="nav-link" href="/library/passwd"><font color="white">Change password</font></a>
</nav>
<nav class="nav nav-pills flex-column flex-sm-row my-2 mx-2">
    <a class="flex-sm-fill text-sm-center nav-link" href="/library/"><font color="white">My books</font></a>
    <a class="flex-sm-fill text-sm-center nav-link" href="/library/analyse"><font color="white">Renting info</font></a>
    <a class="flex-sm-fill text-sm-center nav-link" href="/library/operations"><font color="white">Give/Receive book</font></a>
    <a class="flex-sm-fill text-sm-center nav-link active" href="/library/profile" >Profile</a>
</nav>
<#if message??>
<div class="alert alert-success mx-auto text-center" style="width : 50%;" role="alert">
    ${message}
</div>
</#if>
<div class="jumbotron mx-auto mt-3" style="background-color: white; width : 600px;">
<div class="text-center">
<img src="${lib.photoUrl}" alt="Wrong url" class="img-thumbnail mb-5" height="200px" width="200px">
</div>
    <div class="text-left">
        <h3>${lib.name}</h3>
        <p><i class="fas fa-map-marker-alt"></i> ${lib.address}</p>
        <p><i class="fas fa-book"></i> ${lib.providedBooks?size}</p>
        <p><i class="fas fa-phone"></i> ${lib.phoneNumber}</p>
    </div>
    <div class="text-center"><a href="/library/data">Change information</a></div>
    <div class="text-center mt-3"><a href="/logout">Logout</a></div>
</div>
</body>
</html>
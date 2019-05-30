<!DOCTYPE html>
<html lang="en">
<head>
    <title>Library - Profile</title>
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
<#if card??>
<div class="card text-center mx-auto my-5" style="width : 50%;">
    <div class="card-header text-center">
        <img src="${card.photoUrl}" class="rounded-circle my-2" height="150px" width="150px" />
        <h5 class="card-title mt-2">${card.firstName} ${card.lastName}</h5>
    </div>
    <div class="card-body text-left">
        <p class="card-text">Birthday: ${card.dateOfBirthday}</p>
        <p class="card-text">Address: ${card.address}</p>
        <p class="card-text">Books renting: ${card.booksTaken?size}</p>
        <p class="card-text">Subscription type: <#if subscription == 'REGULAR'> <span class="badge badge-success"> ${subscription} </span> <#else> <span class="badge badge-pill badge-warning"> <font color="white">${subscription}</font> </span> </#if></p>
        <p class="card-text"><a href="/changeProfile">Edit</a></p>
    </div>
    <div class="card-footer text-muted">
        Library Union - We are always near by
    </div>
</div>
</#if>
</body>
</html>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Library - ${book.name}</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://fonts.googleapis.com/css?family=Indie+Flower" rel="stylesheet">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</head>
<body>
<nav class="navbar navbar-light bg-primary" >
    <a class="navbar-brand" href="/"><font color="white">Library</font></a>
    <div class="dropleft">
        <img src="${photoUrl}" width="40px" height="40px" id="dropdownMenuButton" class="rounded-circle my-1 mx-1 dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
            <a class="dropdown-item" href="/profile">Profile</a>
            <a class="dropdown-item" href="/primary">Primary card</a>
            <a class="dropdown-item" href="/passwd">Change password</a>
            <a class="dropdown-item" href="/logout">Logout</a>
        </div>
    </div>
</nav>

<div class="container my-5">
    <div class="row">
        <div class="col">
            <img src="${book.photoUrl}" class="rounded float-left" style="max-width:240px;" alt="">
        </div>
        <div class="col-6">
            <h3>${book.name}</h3>
            <h5>by ${book.author}</h5>
            <small class="text-muted">Rate: ${book.rate}/10</small>
            <hr/>
            <#if reserved??>
            <h5 class="text-info mx-5">RESERVED</h5>
            <#else>
                <form action="/book/${book.id}" method="post">
            <#if book.subscriptionType == 'REGULAR'><button type="submit" class="btn btn-outline-info">Regular subscription<hr/><b>Reserve</b></button><#else><button type="submit" class="btn btn-outline-warning" <#if userPrimary??><#else>disabled</#if> >Primary subscription<hr/><b><font color="black">Reserve</font></b></button></#if>
            <#if userPrimary?? || book.subscriptionType == 'REGULAR'><#else><br/><small class="text-muted mx-1">Primary subscription is required</small> </#if>
                </form>
            </#if>
            <p class="mt-4">Written in ${book.year} <font color="gray"><i class="fas fa-grip-lines-vertical mx-2"></i></font> ${book.type}</p>
            <p>${book.description}</p>
        </div>
        <div class="col">
            <div class="card border-info mb-3 text-dark" style="max-width: 18rem;">
                <div class="card-header">Receive a primary library card</div>
                <div class="card-body text-info">
                    <h5 class="card-title">Use PayPal to buy fast!</h5>
                    <p class="card-text">Primary library card gives you access to an exclusive books! Order it now using PayPal! Also primary card allows you to take as many books at once as you can grab. Enjoy!</p>
                    <a href="/primary" class="card-link">Order primary card now!</a>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
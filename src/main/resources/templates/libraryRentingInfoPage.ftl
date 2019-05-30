<!DOCTYPE html>
<html lang="en">
<head>
    <title>Library - Renting Info</title>
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
</nav>
<nav class="nav nav-pills flex-column flex-sm-row my-2 mx-2">
    <a class="flex-sm-fill text-sm-center nav-link" href="/library/">My books</a>
    <a class="flex-sm-fill text-sm-center nav-link active" href="/library/analyse">Renting info</a>
    <a class="flex-sm-fill text-sm-center nav-link" href="/library/operations">Give/Receive book</a>
    <a class="flex-sm-fill text-sm-center nav-link" href="/library/profile" >Profile</a>
</nav>
<#if message??>
<div class="alert alert-success mx-auto text-center" style="width : 50%;" role="alert">
    ${message}
</div>
</#if>
<#list bookInfo as b, h>
<div class="card mb-3 ml-4" style="max-width: 540px;">
    <div class="row no-gutters">
        <div class="col-md-4">
            <img src="${b.photoUrl}" class="card-img" alt="...">
        </div>
        <div class="col-md-8">
            <div class="card-body">
                <h5 class="card-title">History of ${b.name}</h5>
                <#assign
                    bookSize = h?size
                />
                <#if bookSize == 0>
                    <p class="card-text">This book haven't been in use</p>
                <#else>

                <div class="list-group">
                    <#list h as hist>
                    <a href="/library/card/${hist.holderId.id}" class="list-group-item list-group-item-action">
                        <div class="d-flex w-100 justify-content-between">
                            <h5 class="mb-1">${hist.holderName}</h5>
                            <small>rate : ${hist.rate}</small>
                        </div>
                        <p class="mb-1">take: ${hist.dateOfStart}</p>
                        <p class="mb-1">return: ${hist.dateOfEnd}</p>
                    </a>
                </#list>
                </div>
                </#if>
            </div>
        </div>
    </div>
</div>
</#list>
</body>
</html>
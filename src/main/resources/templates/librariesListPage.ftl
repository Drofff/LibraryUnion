<!DOCTYPE html>
<html lang="en">
<head>
    <title>Library Admin</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://fonts.googleapis.com/css?family=Indie+Flower" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script
            src="https://code.jquery.com/jquery-3.4.0.js"
            integrity="sha256-DYZMCC8HTC+QDr5QNaIcfR7VSPtcISykd+6eSmBW5qo="
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-primary bg-primary">
    <a class="navbar-brand" href="/admin"><b><font color="white">Library</font> <font color="#FFDF00">Admin</font></b></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavDropdown">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="/admin/applications"><font color="white">Applications <#if apps?? && apps gt 0><span class="badge badge-pill badge-light">${apps}</span></#if></font></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/"><font color="white">Back to User Page</font></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/admin/blockIp"><font color="white">Block IP</font></a>
            </li>
        </ul>
    </div>
    <a class="nav-link" href="/logout"><font color="white">Logout</font></a>
</nav>
<div>
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Address</th>
            <th scope="col">Books</th>
            <th scope="col">Activated</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <#list libs as lib, books>
        <tr>
            <td>${lib.name}</td>
            <td>${lib.address}</td>
            <td>${books}</td>
            <td><#if lib.activated><font color="green"><i class="fas fa-check"></i></font><#else><i class="fas fa-hourglass-half"></i></#if></td>
            <td><a href="/admin/block/user/${lib.account.id}">Delete</a></td>
        </tr>
        </#list>
        </tbody>
    </table>

</div>
</body>
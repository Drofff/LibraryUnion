<!DOCTYPE html>
<html lang="en">
<head>
    <title>Library Admin - Applications</title>
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
                <a class="nav-link" href="/admin/applications"><font color="white">Applications <#if apps?? && apps?size gt 0><span class="badge badge-pill badge-light">${apps?size}</span></#if></font></a>
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
<#if message??>
<div class="alert alert-success mx-auto text-center" style="width : 50%;" role="alert">
    ${message}
</div>
</#if>
<p class="my-2 ml-3">${apps?size} applications</p>
<hr class="ml-2 mb-2"/>
<#list apps as app>
<div class="card my-4 mx-4 text-center" style="width:50%">
    <div class="card-body">
        <div class="text-right">
            <a href="/admin/applications/decline/${app.id}" class="mr-2 text-muted">Decline</a>
        </div>
        <h4 class="card-title">${app.name}</h4>
        <div class="container">
            <div class="row">
                <div class="column">
                    <img src="${app.photoUrl}" class="rounded ml-2 mr-4 mt-2 mb-4" style="width:200px; height:200px;">
                </div>
                <div class="column mt-4">
                    <h6 class="card-subtitle mb-2 text-muted">${app.phoneNumber}</h6>
                    <p class="card-text">${app.address}</p>
                    <#assign docPhoto = 'doc' + app.id >
                    <a class="btn btn-success text-white" data-toggle="modal" data-target="#exampleModalLong">View Documents</a>
                    <div class="modal fade" id="exampleModalLong" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLongTitle">Documents of ${app.name}</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <img src="${app.documentUrl}" style="width:400px; height:500px;" class="rounded"/>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <br/>
                    <a href="/admin/applications/accept/${app.id}" class="btn btn-primary mx-2 mt-4">Accept</a>
                </div>
            </div>
        </div>
    </div>
</div>
</#list>
</body>
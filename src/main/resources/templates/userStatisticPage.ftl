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
<#if booksStatistic??>
<script>

$(function () {

    var c = document.getElementById("chart");

    var counter = 1;

    var step = c.offsetWidth / ( ${booksStatistic?size} + 1);
    var limitY = c.offsetHeight - 10;
    var stepY = limitY / ${maxBooksStatistic};

    var prevPointX = 0;
    var prevPointY = limitY;

    var ctx = c.getContext("2d");

    ctx.lineWidth = 3;

    ctx.strokeStyle = "#0000FF";

    ctx.beginPath();

    <#list booksStatistic as bs>

        var bs = ${bs};
        var newY = limitY - ( bs * stepY );

        if (newY < 10) {
            newY = 10;
        }

        ctx.moveTo(prevPointX, prevPointY);
        ctx.lineTo(counter * step, newY);

        prevPointX = counter * step;

        prevPointY = newY;

        counter++;

    </#list>

    if ( ${booksStatistic?size} != 0 ) {
        ctx.moveTo(prevPointX, prevPointY);
        ctx.lineTo(c.offsetWidth, limitY);
    }

     ctx.stroke();

});

</script>
</#if>
<h5 class="mx-5 mb-3 mt-5">User: <b>${username}</b></h5>
<div class="container">
    <div class="row">
        <div class="col mt-5 ml-3">
            <p>Books currently in use: ${currentBooks}</p>
            <#if booksStatistic??>
            <p>Used books: ${booksStatistic?size}</p>
            <p>Late books: ${booksLate}</p>
            </#if>
            <p>Card type: <#if cardType><span class="badge badge-warning text-white">Primary</span><#else><span class="badge badge-success">Regular</span></#if></p>
            <p><a class="btn btn-danger mt-3" href="/admin/block/user/${userId}">Block</a></p>
        </div>
        <div class="col text-center mt-3 mr-5">
            <#if booksStatistic??>
            <canvas id = "chart" class="mx-1" width="400" height="250" style="border:1px solid #d3d3d3;">Browser do not support canvas</canvas>
            <br/>
            Books returned not in time
            </#if>
        </div>
    </div>
</div>
</body>
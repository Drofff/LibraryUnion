<!DOCTYPE html>
<html lang="en">
<head>
    <title>Library - Search</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://fonts.googleapis.com/css?family=Indie+Flower" rel="stylesheet">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script
            src="https://code.jquery.com/jquery-3.4.0.js"
            integrity="sha256-DYZMCC8HTC+QDr5QNaIcfR7VSPtcISykd+6eSmBW5qo="
            crossorigin="anonymous"></script>
    <link href="https://fonts.googleapis.com/css?family=Indie+Flower" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</head>
<body class="bg-light">
<nav class="navbar navbar-light bg-primary">
    <a class="navbar-brand" href="/"><font color="white">Library</font></a>
    <a class="nav-link" href="/"><font color="white">Main Page</font></a>
</nav>
<nav class="navbar navbar-primary bg-light text-primary" >
    <div id="search_dropdown" class="dropdown" style="margin-top:40px"></div>
    <form class="form mt-3" style="width: 70%;" action="/search" method="get">
        <input id="search_field" class="form-control form-control-dark" <#if lastQuery??> value="${lastQuery}" </#if> name="query" style="width: 70%;" type="text" placeholder="Name" aria-label="Search">
        <div class="col-auto my-1 form-inline">
            <label class="mr-sm-2 my-2 text-primary" for="inlineFormCustomSelect">Type</label>
            <select class="custom-select mr-sm-2 my-2" name="type" id="inlineFormCustomSelect" style="width:20%;">
                <option value="" <#if lastType??><#else> selected </#if>>Choose...</option>
                <#if types??>
                <#list types as t>
                <option <#if lastType?? && lastType == t> selected </#if> value="${t}">${t}</option>
            </#list>
        </#if>
        </select>
        <input type="text" name="author" class="form-control ml-3" style="width:30%;" <#if lastAuthor??> value="${lastAuthor}" </#if> placeholder="Author">
        </div>
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" <#if lastSubscriptionType?? && lastSubscriptionType == 'REGULAR'> checked </#if> name="subscriptionType" id="inlineRadio1" value="REGULAR">
            <label class="form-check-label text-primary" for="inlineRadio1">Regular</label>
        </div>
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" <#if lastSubscriptionType?? && lastSubscriptionType == 'PRIMARY'> checked </#if>  name="subscriptionType" id="inlineRadio2" value="PRIMARY">
            <label class="form-check-label text-primary" for="inlineRadio2">Primary</label>
        </div>
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" <#if lastSubscriptionType?? && lastSubscriptionType == ''> checked </#if>  value="" name="subscriptionType" id="inlineRadio3">
            <label class="form-check-label text-primary" for="inlineRadio3">All</label>
        </div>
        <div class="form-inline mt-3">
            <p class="text-primary mx-1 mt-2">Rate:</p>
        <input type="number" class="mx-2" style="width:10%;" <#if lastMinRate??> value="${lastMinRate}" </#if> name="minRate">
        <input type="number" class="mx-2" style="width:10%;" <#if lastMaxRate??> value="${lastMaxRate}" </#if> name="maxRate">
        </div>
        <div class="form-inline my-3">
            <p class="text-primary mr-3 ml-1 mt-2">Year:</p>
        <input type="date" <#if lastMinYear??> value="${lastMinYear}" </#if> name="minYear">
        <input type="date" <#if lastMaxYear??> value="${lastMaxYear}" </#if> name="maxYear">
        </div>
        <button style="margin-right:17%; margin-left:7px; font-size:24px;" class="badge badge-light text-primary" type="submit">Find</button>
    </form>
</nav>
<#if books?size == 0>
<div class="text-center">
    <h1 class="mt-5" style="font-size:60px;"><i class="fas fa-search"></i></h1>
<h3 class="my-3">Nothing for you query</h3>
</div>
<#else>
<div class="card-columns" style="font-family: 'Indie Flower', cursive;">
<#list books as book>
    <#if book.subscriptionType == 'REGULAR'>
    <div class="card text-white bg-primary mx-5 my-5" style="width: 270px;">
        <#else>
        <div class="card text-white bg-warning mx-5 my-5" style="width: 270px;">
        </#if>
        <div class="card-body">
            <h5 class="card-title nav-link"><a href="/book/${book.id}"><font color="white">${book.name}</font></a></h5>
            <#if book.holderId??><h5><span class='badge badge-dark'>RESERVED</span></h5></#if>
        <#if book.subscriptionType == 'REGULAR'>
        <small><font color="white">Rate: ${book.rate}/10</font></small>
        <#else>
        <small class="text-muted">Rate: ${book.rate}/10</small>
    </#if>
    <div class="container">
        <div class="row">
            <div class="col-sm">
                <img src="${book.photoUrl}" class="mt-2" width="90px" height="110px" alt="Wrong url">
            </div>
            <div class="col-sm">
                <p class="card-text">${book.author}</p>
                <p class="card-text">${book.year}</p>
                <p class="card-text">${book.type}</p>
        </div>
    </div>
</div>
</div>
</div>
</#list>
</#if>
</div>
</body>
</html>
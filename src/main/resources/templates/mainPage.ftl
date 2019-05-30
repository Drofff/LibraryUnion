<!DOCTYPE html>
<html lang="en">
<head>
    <title>Library - Home</title>
    <meta charset="UTF-8">
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
<script>

    var prev_text = "";

    $(function() {

            $("#do_search").click(function () {
                if ( $("#search_field").val().length > 0 ) {
                    window.location.replace("/search?query=" + $("#search_field").val());
                }
            });

            $("#search_field").keydown(function() {

                var text = $("#search_field").val();

                if ( text != prev_text ) {

                if (text.length > 1) {

                    $.post( "/search/quick", { query : text }, function( data ) {

                    var tag = "<div class='dropdown-menu'>";

                        $.each(data, function(i, item) {
                             tag += "<a class='dropdown-item' id='book" + item.id + "' href='/book/" + item.id + "'>" + item.name + "</a>";
                        });

                    if (tag == "<div class='dropdown-menu'>") {
                        tag += "<h6 class='dropdown-header'>No result</h6>";
                    }

                    tag += "</div>";

                    $("#search_dropdown").html(tag);
                    $("#search_dropdown").dropdown('hide');
                    $("#search_dropdown").dropdown('dispose');
                    $("#search_dropdown").dropdown('toggle');

                     $.each(data, function(i, item) {
                        $("#book" + item.id).on("click", function() {
                            $("#search_field").val(item.name);
                            $("#search_dropdown").dropdown('hide');
                            $("#search_dropdown").dropdown('dispose');
                        });
                     });

                    });

                } else {
                    $("#search_dropdown").dropdown('hide');
                    $("#search_dropdown").dropdown('dispose');
                }

             }

            });
    });

</script>
<nav class="navbar navbar-light bg-primary" >
    <a class="navbar-brand" href="/"><font color="white">Library</font></a>
    <div id="search_dropdown" class="dropdown" style="margin-top:40px"></div>
    <div class="form-inline" style="width: 70%;">
    <input id="search_field" autocomplete="off" class="form-control form-control-dark" style="width: 70%;" type="text" placeholder="Search" aria-label="Search">
    <a href="#" id="do_search" style="margin-right:17%; margin-left:7px; font-size:24px;" class="badge badge-light text-primary">Find</a>
    </div>
    <div class="dropleft">
        <img src="${photoUrl}" width="40px" height="40px" id="dropdownMenuButton" class="rounded-circle my-1 mx-1 dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
            <#if admin??><a class="dropdown-item" href="/admin">Admin Panel</a></#if>
            <a class="dropdown-item" href="/profile">Profile</a>
            <a class="dropdown-item" href="/primary">Primary card</a>
            <a class="dropdown-item" href="/passwd">Change password</a>
            <a class="dropdown-item" href="/logout">Logout</a>
        </div>
    </div>
</nav>
<div id="carouselExampleSlidesOnly" class="carousel slide" data-ride="carousel">
    <div class="carousel-inner">
        <div class="carousel-item">
            <img src="https://static.careers360.mobi/media/article_images/2015/09/25/CAT-Books-700.jpeg" height="300px" width="100%" class="d-block w-100" alt="">
        </div>
        <div class="carousel-item active">
            <img src="https://bluesyemre.files.wordpress.com/2019/01/getty_598063032_349381.jpg" class="d-block" height="300px" width="100%" alt="">
        </div>
        <div class="carousel-item">
            <img src="https://www.incimages.com/uploaded_files/image/970x450/getty_508400521_2000133320009280263_305526.jpg" height="300px" width="100%" class="d-block w-100" alt="">
        </div>
    </div>
</div>
<div class="card-columns">
        <div class="mx-5 my-3">
            <div class="card" style="width: 18rem;">
                <div class="card-header">
                    My books
                </div>
                <ul class="list-group list-group-flush">
                    <#if myBooks?size == 0>
                            <li class="list-group-item">Have not any books yet</li>
                    <#else>
                        <#list myBooks as myBook>
                            <li class="list-group-item"<#if myBook.missed>style='background=RED'</#if> ><a href="/book/${myBook.id}">${myBook.text}</a></li>
                        </#list>
                    </#if>
                </ul>
            </div>
        </div>
        <div class=" mt-4" style="font-family: 'Indie Flower', cursive;" >
            <#list books as book, lib>
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
                                <#if book.subscriptionType == 'REGULAR'>
                                <p class="card-text"><a href="/owner/${lib.id}"><font color="white">${lib.name}</font></a></p>
                                <#else>
                                <p class="card-text"><a href="/owner/${lib.id}">${lib.name}</a></p>
                                </#if>
                            </div>
                        </div>
                </div>
                </div>
            </div>
            </#list>
            </div>
    </div>
</div>
</body>
</html>
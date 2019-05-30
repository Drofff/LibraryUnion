<!DOCTYPE html>
<html lang="en">
<head>
    <title>Library - Book Control</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</head>
<body>
<script>
    $(function() {
        $("rateF").change(function() {
            var input = $("rateF").val();
            if ( input < 1 || input > 10 ) {
                $("rateF").val(10);
            }
        });
    });
</script>
<nav class="navbar navbar-light bg-primary" >
    <a class="navbar-brand" href="/library"><font color="white">Library</font></a>
</nav>
<nav class="nav nav-pills flex-column flex-sm-row my-2 mx-2">
    <a class="flex-sm-fill text-sm-center nav-link" href="/library/">My books</a>
    <a class="flex-sm-fill text-sm-center nav-link" href="/library/analyse">Renting info</a>
    <a class="flex-sm-fill text-sm-center nav-link active" href="/library/operations">Give/Receive book</a>
    <a class="flex-sm-fill text-sm-center nav-link" href="/library/profile" >Profile</a>
</nav>
<#if message??>
<div class="alert alert-success mx-auto text-center" style="width : 50%;" role="alert">
    ${message}
</div>
</#if>
<table class="table">
    <thead>
    <tr>
        <th scope="col">Start Date</th>
        <th scope="col">Name of Book</th>
        <th scope="col">Customer data</th>
        <th scope="col"></th>
        <th scope="col"></th>
    </tr>
    </thead>
    <tbody class ="text-white">


        <#list books as b>
        <tr <#if b.dateWhenTaken??>class="bg-danger"<#else>class="bg-info"</#if>>
            <td><#if b.dateWhenTaken??>${b.dateWhenTaken}<#else>Not took yet</#if></td>
            <td>${b.name}</td>
            <td>${b.holderId.firstName} ${b.holderId.lastName}</td>
            <td><#if b.dateWhenTaken??><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
                Receive
            </button><#else><a href="/library/give/${b.id}"><font color="white">Give</font></a></#if></td>
            <td><#if b.dateWhenTaken??><#else><a href="/library/clear/${b.id}"><font color="white">Delete</font></a></#if></td>
        </tr>

    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Enter book id to confirm return</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form action="/library/receive" method="post">
                <div class="modal-body">
                    <input type="number" name="id" class="form-control mx-auto my-2" style="width:70px;">
                    <input type="hidden" name="trueId" value="${b.id}" >
                    <hr/>
                    <div class="text-center">
                        <p class="mx-5 my-2">Rate this book, please!</p><div class="text-center"></div>
                    <small class="mx-5 my-2 text-muted">Range 1-10</small>
                </div>
                    <input type="number" name="rate" id="rateF" class="form-control mx-auto my-2" style="width:70px;">
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-secondary" >Rate</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
                </form>
            </div>
        </div>
    </div>
        </#list>
    </tbody>
</table>
</body>
</html>
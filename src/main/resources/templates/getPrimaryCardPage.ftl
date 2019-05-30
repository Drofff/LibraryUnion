<!DOCTYPE html>
<html lang="en">
<head>
    <title>Library - Card option</title>
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
    <a class="navbar-brand" href="/"><font color="white">Library</font></a>
</nav>
<#if errorMessage??>
<div class="alert alert-danger text-center" role="alert">
    ${errorMessage}
</div>
</#if>
<#if successMessage??>
<div class="alert alert-success text-center" role="alert">
    <font color="green"><i class="fas fa-check"></i></font> ${successMessage}
</div>
</#if>
<div class="card text-center mx-auto my-5" style="width : 50%;">
    <div class="card-header">
        Primary Card
    </div>
    <div class="card-body">
        <#if valid??>
        <p style="color:green; font-size:30px;"><b>Valid till ${valid}</b></p>
        <#else>
        <p>Dear User! We propose you to buy Primary Subscription for 6 months</p>
        <p>Price: 15$</p>
        <p>With Primary Subscription you can:</p>
        <ul>
            <li> Take exclusive books in all system libraries</li>
            <li> Reserve unlimited number of books at the same time</li>
        </ul>
        <a href="/primary/pay">Buy subscription <i class="fab fa-paypal"></i></a>
        </#if>
    </div>
    <div class="card-footer text-muted">
        Library Union - We are always near by
    </div>
</div>
</body>
</html>
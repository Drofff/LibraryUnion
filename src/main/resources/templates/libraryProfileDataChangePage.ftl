<!DOCTYPE html>
<html lang="en">
<head>
    <title>Library - Change Info</title>
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
<script>

    $(function() {

        $("#photoInput").change(function() {
            $("#forImage").html("<img class='mx-5 my-4' width='70px' height='70px' alt='Wrong url' src='" + $("#photoInput").val() + "' />");
        });
    });
</script>
<#if message??>
<div class="alert alert-danger mx-auto text-center" style="width : 50%;" role="alert">
    ${message}
</div>
</#if>
<div class="card text-center mx-auto my-5" style="width : 50%;">
    <div class="card-body">
        <form action="/library/data" method="post">
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="validationCustom01">Name</label>
                    <input type="text" name="name" <#if nameError??>class="form-control is-invalid"<#else>class="form-control"</#if> id="validationCustom01" placeholder="Library name" value="${lib.name}" required>
                    <#if nameError??>
                    <div class="invalid-feedback">
                        ${nameError}
                    </div>
                    </#if>
                </div>
                <div class="col-md-4 mb-3">
                    <label for="validationCustom02">Address</label>
                    <input type="text" name="address" <#if addressError??>class="form-control is-invalid"<#else>class="form-control"</#if> id="validationCustom02" placeholder="Address" value="${lib.address}" required>
                <#if addressError??>
                <div class="valid-feedback">
                        ${addressError}
                    </div>
                </#if>
                </div>
                <div class="col-md-4 mb-3">
                    <label for="validationCustomUsername">Phone number</label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="inputGroupPrepend">@</span>
                        </div>
                        <input type="text" name = "phoneNumber" <#if phoneNumberError??>class="form-control is-invalid"<#else>class="form-control"</#if> id="validationCustomUsername" placeholder="Phone number" value="${lib.phoneNumber}" aria-describedby="inputGroupPrepend" required>
                        <#if phoneNumberError??>
                        <div class="invalid-feedback">
                            ${phoneNumberError}
                        </div>
                        </#if>
                    </div>
                </div>
                <div class="form-group row mt-4 mx-4">
                    <label class="col-sm-2 col-form-label">Photo</label>
                    <input type="text" id="photoInput" style="width : 400px;" name="photoUrl" value = "${lib.photoUrl}" <#if photoUrlError??>class="form-control is-invalid" <#else> class="form-control" </#if> placeholder="Photo url">
                <#if photoUrlError??>
                <div class="invalid-feedback" style="margin-left : 220px">
                    ${photoUrlError}
                </div>
                </#if>
                <div id="forImage"></div>
                </div>
            </div>

            <button class="btn btn-primary" type="submit">Save</button>
        </form>
    </div>
    <div class="card-footer text-muted">
        Library Union - We are always near by
    </div>
</div>
</body>
</html>
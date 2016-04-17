<%@include file="templates/header_login.jsp" %>

    <div class="row" style="margin-top:20px">
        <div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
            <form role="form" action="autenticador" method="post">
                <fieldset>
                    <h2>Login</h2>
                    <hr class="colorgraph">
                    <div class="form-group">
                        <input type="text" name="login" id="login" class="form-control input-lg" placeholder="Login" required>
                    </div>
                    <div class="form-group">
                        <input type="password" name="senha" id="senha" class="form-control input-lg" placeholder="Senha" required>
                    </div>
                   
                    <hr class="colorgraph">
                    <div class="row text-center">
                        <div class="col-xs-12 col-sm-12 col-md-12">
                            <input type="submit" class="btn btn-lg btn-primary btn-block" value="Entrar">
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>



<%@include file="templates/footer.jsp" %>


<script type="text/javascript">
    $(document).ready(function(){
            $("#login").focus();	
    });
</script>

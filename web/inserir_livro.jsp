<%@include file="templates/header.jsp" %>
<div class="row text-center">
    <div class="col-md-10 col-md-offset-1">
    <div class="panel panel-default">
        
        <div class="panel-heading">
            <h3 class="panel-title text-center"><strong>Cadastrar Livro</strong></h3>
        </div>
        
        <div class="panel-body">
            
            <%
                Object s = request.getAttribute("sucesso");

                if (s != null) {
                    String sucesso = (String)s;

                    if (sucesso.equals("sim")) {
                        out.println("<div class='row'>");
                        out.println("<div class='col-md-10 col-md-offset-1'>");
                        out.println("<div class='alert alert-success alert-dismissible' role='alert'>");
                        out.println("<button type='button' class='close' data-dismiss='alert' aria-label='Close'>");
                        out.println("<span aria-hidden='true'>&times;</span>");
                        out.println("</button>");
                        out.println("<strong>Inserido com Sucesso!</strong>");
                        out.println("</div>");
                        out.println("</div>");
                        out.println("</div>");
                    }
                    else {
                        out.println("<div class='row'>");
                        out.println("<div class='col-md-10 col-md-offset-1'>");
                        out.println("<div class='alert alert-danger alert-dismissible' role='alert'>");
                        out.println("<button type='button' class='close' data-dismiss='alert' aria-label='Close'>");
                        out.println("<span aria-hidden='true'>&times;</span>");
                        out.println("</button>");
                        out.println("<strong>Ocorreu um problema, tente novamente.</strong>");
                        out.println("</div>");
                        out.println("</div>");
                        out.println("</div>");
                    }
                }
            %>
            
            
            <form class="form-horizontal" method="post" action="LivroControler">
                <div class="form-group">
                    <label for="livroNome" class="col-md-2 control-label">Titulo</label>
                    <div class="input-group col-md-8">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-book"></span></span>
                        <input class="form-control" id="titulo" name="titulo" type="text" maxlength="30" required autofocus>
                    </div>
                </div>

                <div class="form-group">
                    <label for="livroAutor" class="col-md-2 control-label">Autor</label>
                    <div class="input-group col-md-8">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                        <input class="form-control" id="autor" name="autor" type="text" maxlength="30" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="livroEditora" class="col-md-2 control-label">Editora</label>
                    <div class="input-group col-md-8">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-question-sign"></span></span>
                        <input class="form-control" id="editora" name="editora" type="text" maxlength="30" required>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="livroQuantidade" class="col-md-2 control-label">Quantidade</label>
                    <div class="input-group col-md-8">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-question-sign"></span></span>
                        <input class="form-control" id="quantidade" onkeypress="return SomenteNumero(event)" name="quantidade" type="text" required>
                    </div>
                </div>

                <input type="hidden" name="acao" value="inserir">

                <div class="form-group">
                    <div class="col-md-6 col-sm-6 col-xs-6">
                        <button  class='btn btn-warning btn-large' onclick='window.history.go(-1)'><span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>Voltar</button>
                    </div>
                    
                    <div class="col-md-6 col-sm-6 col-xs-6">
                        <button class="btn btn-primary" type="submit"><strong>Cadastrar</strong> <span class="glyphicon glyphicon-save" aria-hidden="true"></span></button>
                    </div>
                </div>
                
            </form>
        </div>
    </div>
    </div>
</div>
<%@include file="templates/footer.jsp" %>

<script type="text/javascript">
    
    function SomenteNumero(e){
        var tecla=(window.event)?event.keyCode:e.which;   
        if((tecla>47 && tecla<58)) return true;
        else{
            if (tecla==8 || tecla==0) return true;
            else  return false;
        }
    }
    
</script>
<%@page import="modelo.Livro"%>
<%@include file="templates/header.jsp" %>
<div class="row text-center">
    <div class="col-md-10 col-md-offset-1">
    <div class="panel panel-default">
        
        <div class="panel-heading">
            <h3 class="panel-title text-center"><strong>Editar Livro</strong></h3>
        </div>
        
        <div class="panel-body">
            
            <%
                Livro l = (Livro)request.getAttribute("livro");
            %>
            
            
            <form class="form-horizontal" method="post" action="LivroControler">
                
                <input class="form-control" id="id" value="<%=l !=null ? l.getId() : ""%>" name="id" type="hidden">
                
                <div class="form-group">
                    <label for="livroNome" class="col-md-2 control-label">Titulo</label>
                    <div class="input-group col-md-8">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-book"></span></span>
                        <input class="form-control" id="titulo" name="titulo" value="<%=l !=null ? l.getTitulo() : ""%>" type="text" maxlength="30" required autofocus>
                    </div>
                </div>

                <div class="form-group">
                    <label for="livroAutor" class="col-md-2 control-label">Autor</label>
                    <div class="input-group col-md-8">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                        <input class="form-control" id="autor" name="autor" value="<%=l !=null ? l.getAutor() : ""%>" type="text" maxlength="30" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="livroEditora" class="col-md-2 control-label">Editora</label>
                    <div class="input-group col-md-8">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-question-sign"></span></span>
                        <input class="form-control" id="editora" name="editora" value="<%=l !=null ? l.getEditora() : ""%>" type="text" maxlength="30" required>
                    </div>
                </div>
                    
                <div class="form-group">
                    <label for="livroQuantidade" class="col-md-2 control-label">Quantidade</label>
                    <div class="input-group col-md-8">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-question-sign"></span></span>
                        <input class="form-control" id="quantidade" name="quantidade" onkeypress="return SomenteNumero(event)" value="<%=l !=null ? l.getQuantidade() : ""%>" type="text" required>
                    </div>
                </div>

                <input type="hidden" name="acao" value="atualizar">

                <div class="form-group">
                    <div class="col-md-6 col-sm-6 col-xs-6">
                        <a href='LivroControler?acao=visualizar'  class='btn btn-warning btn-large' role='button'><span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span><strong>Voltar</strong></a>
                    </div>
                    
                    <div class="col-md-6 col-sm-6 col-xs-6">
                        <button class="btn btn-primary" type="submit"><strong>Salvar</strong> <span class="glyphicon glyphicon-save" aria-hidden="true"></span></button>
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
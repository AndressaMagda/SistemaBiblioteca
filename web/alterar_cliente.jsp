<%@page import="modelo.Cliente"%>
<%@include file="templates/header.jsp" %>
<div class="row text-center">
    <div class="col-md-10 col-md-offset-1">
    <div class="panel panel-default">
        
        <div class="panel-heading">
            <h3 class="panel-title text-center"><strong>Alterar Cliente</strong></h3>
        </div>
        
        <div class="panel-body">
            <%
                Cliente c = (Cliente)request.getAttribute("cliente");
                
                Object s = request.getAttribute("sucesso");
                Object m = request.getAttribute("matricula");
                
                String matricula;
                if (m != null) {
                    matricula = (String)m;
                }
                else{
                    matricula = "";
                }

                if (s != null) {
                    String sucesso = (String)s;

                    if (sucesso.equals("sim")) {
                        out.println("<div class='row'>");
                        out.println("<div class='col-md-10 col-md-offset-1'>");
                        out.println("<div class='alert alert-success alert-dismissible' role='alert'>");
                        out.println("<button type='button' class='close' data-dismiss='alert' aria-label='Close'>");
                        out.println("<span aria-hidden='true'>&times;</span>");
                        out.println("</button>");
                        out.println("<strong>Alterado com Sucesso!</strong>");
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
                        out.println("<strong>Erro ao alterar o cliente. "+matricula+"</strong>");
                        out.println("</div>");
                        out.println("</div>");
                        out.println("</div>");
                    }
                }
                
            %>

            <form class="form-horizontal" action="ClienteControler" method="post">
                
                <input class="form-control" id="id" value="<%=c !=null ? c.getId() : ""%>" name="id" type="hidden">
                
                <div class="form-group">
                    <label for="clienteNome" class="col-md-2 control-label">Nome</label>
                    <div class="input-group col-md-8">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                        <input class="form-control" id="clienteNome" value="<%=c !=null ? c.getNome() : ""%>" onkeypress="return SomenteCaracteres(event)" name="nome" type="text" maxlength="30" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="clienteMatricula" class="col-md-2 control-label">Matrícula</label>
                    <div class="input-group col-md-5">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-question-sign"></span></span>
                        <input class="form-control" id="clienteMatricula" value="<%=c !=null ? c.getMatricula() : ""%>" onkeypress="return SomenteNumero(event)" name="matricula" type="text" maxlength="10" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="clienteTipo" class="col-md-2 control-label">Tipo</label>
                    <div class="input-group col-md-5">
                        <span class="input-group-addon"><span class="glyphicon glyphicon glyphicon-search"></span></span>
                        <select class="form-control" id="tipo" name="tipo" required>
                            <option value="">Selecione uma opção</option>
                            <option value="aluno" <%=c !=null && c.getTipo().equals("aluno") ? "selected" : ""%>>Aluno</option>
                            <option value="professor" <%=c !=null && c.getTipo().equals("professor") ? "selected" : ""%>>Professor</option>
                        </select>
                    </div>
                </div>
                
                <input type="hidden" name ="acao" value="atualizar" />
                        
                <div class="form-group">
                    <div class="col-md-6 col-sm-6 col-xs-6">
                        <a href='ClienteControler?acao=visualizar' class='btn btn-warning btn-large' role='button'><span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span><strong>Voltar</strong></a>
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
    
    function SomenteCaracteres(e)
    {
	var tecla=new Number();
	if(window.event) {
		tecla = e.keyCode;
	}
	else if(e.which) {
		tecla = e.which;
	}
	else {
		return true;
	}
	if((tecla >= "48") && (tecla <= "57")){
		return false;
	}
    }
    
    function SomenteNumero(e){
        var tecla=(window.event)?event.keyCode:e.which;   
        if((tecla>47 && tecla<58)) return true;
        else{
            if (tecla==8 || tecla==0) return true;
            else  return false;
        }
    }
    
</script>
<%@page import="modelo.Livro"%>
<%@page import="modelo.Cliente"%>
<%@page import="java.util.List"%>
<%@include file="templates/header.jsp" %>

<%
//   System.out.println("Chegando na View e tentando settar os selects!!");
   List<Cliente> clientesSemAtraso = null;
   if (request.getAttribute("clientesSemAtraso")!=null){
       clientesSemAtraso = (List<Cliente>) request.getAttribute("clientesSemAtraso");
   }   
   
   List<Livro> livros =null;
   if (request.getAttribute("livros")!=null){
       livros = (List<Livro>) request.getAttribute("livros");
   }
%>


<div class="row">
    <div class="col-md-10 col-md-offset-1">
    <div class="panel panel-default text-center" id="painel-emprestar">
        
        <div class="panel-heading">
            <h3 class="panel-title text-center"><strong>Emprestimo de Livros</strong></h3>
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
                        out.println("<strong>Emprestado com Sucesso!</strong>");
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
            <form class="form-horizontal" action="EmprestimoControler" method="post">

                <div class="form-group">
                    <label for="clientesAptos" class="col-md-3 control-label">Clientes</label>
                    <div class="input-group col-md-1">
                        <!--<span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>-->
                        <select class="form-control" id="clientesAptos" name="clientesAptos" required>
                            <% for(int i=0; i<clientesSemAtraso.size(); i++){ %>
                                <option value="<%=clientesSemAtraso.get(i).getId() %>"><%=clientesSemAtraso.get(i).getNome()%> - <%=clientesSemAtraso.get(i).getTipo()%></option>
                            <% } %>
                        </select>
                    </div>
                </div>
                        
                <div class="form-group">
                    <label for="livrosCadastrados" class="col-md-3 control-label">Livros</label>
                    <div class="input-group col-md-1">
                        <select class="form-control" id="livrosCadastrados" name="livrosCadastrados[]" multiple="multiple" required>
                            <% for(int i=0; i<livros.size(); i++){ %>
                                <option value="<%=livros.get(i).getId() %>"><%=livros.get(i).getTitulo() %></option>
                            <% } %>
                        </select>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="dataEmprestimo" class="col-md-3 control-label">Data de Empréstimo</label>
                    <div class="input-group col-md-3">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                        <input class="form-control" id="dataEmprestimo" name="dataEmprestimo" type="text" maxlength="0" required>
                    </div>
                </div>

                <input type="hidden" name="acao" value="emprestar">
                
                <div class="form-group" id="botoes">
                    <div class="col-md-6 col-sm-6 col-xs-6">
                        <button  class='btn btn-warning btn-large' onclick='window.history.go(-1)'><span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>Voltar</button>
                    </div>
                    
                    <div class="col-md-6 col-sm-6 col-xs-6">
                        <button class="btn btn-primary" type="submit">Emprestar</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    </div>
</div>
            
<%@include file="templates/footer.jsp" %>

<script type="text/javascript">
    $(document).ready(function() {
        
        $('#livrosCadastrados').multiselect({
            maxHeight: 200,
            nonSelectedText: 'Escolha os livros',
            disableIfEmpty: true,
            enableFiltering: true,
            enableCaseInsensitiveFiltering: true,
            onChange: function(option, checked) {
                var tipo_cliente = $("#clientesAptos :selected").text();
                tipo_cliente = tipo_cliente.match(/professor/g);
                var qtd_livros=3; //Se for professor, pode pegar até 3 livros simultaneamente
                if(tipo_cliente==null) qtd_livros=2; //Alunos podem pegar até 2 livros simultaneamente
                
                // Get selected options.
                var selectedOptions = $('#livrosCadastrados option:selected');
                if (selectedOptions.length >= qtd_livros) {
                    // Disable all other checkboxes.
                    var nonSelectedOptions = $('#livrosCadastrados option').filter(function() {
                        return !$(this).is(':selected');
                    });

                    var dropdown = $('#livrosCadastrados').siblings('.multiselect-container');
                    nonSelectedOptions.each(function() {
                        var input = $('input[value="' + $(this).val() + '"]');
                        input.prop('disabled', true);
                        input.parent('li').addClass('disabled');
                    });
                    alert("Este cliente só pode pegar até "+qtd_livros+" simultaneamente.");
                }
                else {
                    // Enable all checkboxes.
                    var dropdown = $('#livrosCadastrados').siblings('.multiselect-container');
                    $('#livrosCadastrados option').each(function() {
                        var input = $('input[value="' + $(this).val() + '"]');
                        input.prop('disabled', false);
                        input.parent('li').addClass('disabled');
                    });
                }
            }
            
        });
        
        $('#clientesAptos').multiselect({
            maxHeight: 200,
            disableIfEmpty: true,
            enableFiltering: true,
            enableCaseInsensitiveFiltering: true,
        });
        
         $('#dataEmprestimo').datepicker({
            format: "dd/mm/yyyy",
            language: "pt-BR",
            todayHighlight: true,
            orientation: "bottom auto",
            autoclose: true
        });
        
    });
    
    $('#clientesAptos').on('change', function() {
        $('option', $('#livrosCadastrados')).each(function(element) {
            $(this).removeAttr('selected').prop('selected', false);
        });
        $('#livrosCadastrados').multiselect('refresh');
    });
    
</script>

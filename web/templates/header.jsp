<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Andressa M. de O. Ferreira">
    <meta name="author" content="Davi Barros">
    <link rel="icon" href="imagens/icone3.ico">

    <title>Sistema Biblioteca</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    
    <link href="css/sticky-footer-navbar.css" rel="stylesheet">
    <link href="css/geral.css" rel="stylesheet"><!-- CSS Projeto -->  
    <link href="css/dataTables.bootstrap.css" rel="stylesheet"><!-- CSS DataTables -->  
    <link rel="stylesheet" type="text/css" href="css/bootstrap-datepicker.css"><!-- CSS Datepicker -->  
    <link rel="stylesheet" type="text/css" href="css/bootstrap-multiselect.css"><!-- CSS Multiselect --> 
    <link rel="stylesheet" type="text/css" href="css/bootstrap-select.css"><!-- CSS Select -->

    
    <script type="text/javascript" src="js/jquery-1.11.1.js"></script>
    <!--<script type="text/javascript" src="js/jquery.maskedinput-1.3.1.js"></script>-->
        
    <script type="text/javascript" src="js/jquery-ui.min.js"></script>
        
    <!-- JavaScript DataTables -->    
        <script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="js/dataTables.bootstrap.js"></script>
    <!-- JavaScript DataTables -->
    
    <script type="text/javascript" src="js/bootstrap-datepicker.js"></script><!-- JS Datepicker --> 
    <script type="text/javascript" src="js/bootstrap-multiselect.js"></script><!-- JS Multiselect --> 
    <script type="text/javascript" src="js/bootstrap-select.js"></script><!-- JS Select -->

    
    <script src="js/ie-emulation-modes-warning.js"></script>

  </head>

  <body>
    <header>
    <% String data = DateFormat.getDateInstance(DateFormat.FULL).format(new Date()); %>
    <nav class="navbar navbar-inverse">
        <!--<div class="navbar-inner">-->
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                  <span class="sr-only">Toggle navigation</span>
                  <span class="icon-bar"></span>
                  <span class="icon-bar"></span>
                  <span class="icon-bar"></span>
                </button>
                <span class="navbar-brand"><%=data%></span>
            </div>
            <div id="navbar" class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li><a href="home.jsp">Home</a></li>
                   
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Livros <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="inserir_livro.jsp">Inserir</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="LivroControler?acao=visualizar">Visualizar</a></li>
                        </ul>
                    </li>
                   
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Clientes <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="inserir_cliente.jsp">Inserir</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="ClienteControler?acao=visualizar">Visualizar</a></li>
                        </ul>
                    </li>
                   
                    <li><a href="EmprestimoControler?acao=emprestimo">Empréstimos</a></li>
                    
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Devoluções <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="EmprestimoControler?acao=devolucao_selecionar_cliente">Por Clientes</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="EmprestimoControler?acao=devolucao_selecionar_livro">Por Livros</a></li>
                        </ul>
                    </li>
                    
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Relatório <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="EmprestimoControler?acao=relatorio_selecionar_cliente">Cliente</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="EmprestimoControler?acao=relatorio_selecionar_livro">Empréstimos por Livro</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="EmprestimoControler?acao=relatorio_livros_emprestados">Livros Emprestados</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="EmprestimoControler?acao=relatorio_livros_mais_emprestados">Mais Emprestados</a></li>
                        </ul>
                    </li>
                    <li role="separator" class="divider"></li>
                    <li><a href="autenticador">Sair</a></li>
                </ul>
            </div><!--/.nav-collapse -->
        </div><!-- End Container -->
        <!--</div> End navbar-inner -->
    </nav>
    </header>
    

    <!-- Begin page content -->
    <div class="container">
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
  <% String data = DateFormat.getDateInstance(DateFormat.FULL).format(new Date()); %>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Andressa M. de O. Ferreira">
    <meta name="author" content="Davi Barros">
    <link rel="icon" href="imagens/icone3.ico">

    <title>Sistema de Biblioteca</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    
    <link href="css/sticky-footer-navbar.css" rel="stylesheet">
    <link href="css/geral.css" rel="stylesheet">

    <script src="js/ie-emulation-modes-warning.js"></script>

  </head>

  <body>
    <header>  
    <nav class="navbar navbar-inverse">
        <div class="container">
            <span class="navbar-brand text-right"><%=data%></span>
        </div>
    </nav>
    </header>

    <!-- Begin page content -->
    <div class="container">
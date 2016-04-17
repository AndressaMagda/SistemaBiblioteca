<%@include file="templates/header_login.jsp" %>

<%
    Object msg = (String)request.getAttribute("msg");
    
%>

<div class="row">
    <div class="col-xs-12 col-sm-8 col-md-10 col-sm-offset-2 col-md-offset-1">
        <div class="panel panel-danger">
            <div class="panel-heading">
                <h3 class="panel-title text-center"><strong>Atenção!!</strong></h3>
            </div>
            <div class="panel-body">
                <h4 class="text-center text-danger"><%= msg!=null?msg:"Erro" %></h4>
                <div class="col-md-6 col-sm-6 col-xs-6">
                    <button  class='btn btn-warning btn-large' onclick="window.location.href='index.jsp'"><span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>Voltar</button>
                </div>
            </div>
        </div>
    </div>
</div>
                
<%@include file="templates/footer.jsp" %>
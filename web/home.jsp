<%@page import="modelo.Usuario"%>
<%@include file="templates/header.jsp" %>

    <%
        Usuario userAutenticado = (Usuario)session.getAttribute("userAutenticado");
    %>

    <div class="panel panel-default text-center">
        <div class="panel-heading">
            <h3 class="panel-title text-center"><strong>Bem vindo(a) <%=userAutenticado.getNome() %>!!</strong></h3>
        </div>
        <div class="panel-body">
            <p class="text-justify text-muted">
                Os sistemas de gerenciamento de bibliotecas concentram-se nas atividades
                de aquisi��o de materiais, cataloga��o, controle de circula��o, controle de
                peri�dicos, informa��o gerencial e empr�stimos entre bibliotecas. A introdu��o de
                sistemas informatizados nas bibliotecas resultou em padroniza��o, aumento de
                efici�ncia, interliga��o por redes e melhores servi�os (ROWLEY, 2002).
                O uso da tecnologia e dos sistemas de gerenciamento de bibliotecas �
                justificado por alguns motivos, conforme descreve Rowley (2002): necessidade de
                lidar com mais informa��es e maior n�vel de atividade; necessidade de maior
                efici�ncia; oportunidades de oferecer servi�os novos ou melhores; oportunidades de
                coopera��o e centraliza��o na cria��o e utiliza��o de dados compartilhados.
                Atualmente a utiliza��o da tecnologia da informa��o nas empresas em geral resulta
                na cria��o de novos produtos, melhores servi�os e na significativa redu��o de
                custos. Por conseguinte, percebe-se a import�ncia da tecnologia para
                armazenamento e dissemina��o da informa��o. 
            </p>
        </div>
    </div>
<%@include file="templates/footer.jsp" %>
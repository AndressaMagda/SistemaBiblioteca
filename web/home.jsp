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
                de aquisição de materiais, catalogação, controle de circulação, controle de
                periódicos, informação gerencial e empréstimos entre bibliotecas. A introdução de
                sistemas informatizados nas bibliotecas resultou em padronização, aumento de
                eficiência, interligação por redes e melhores serviços (ROWLEY, 2002).
                O uso da tecnologia e dos sistemas de gerenciamento de bibliotecas é
                justificado por alguns motivos, conforme descreve Rowley (2002): necessidade de
                lidar com mais informações e maior nível de atividade; necessidade de maior
                eficiência; oportunidades de oferecer serviços novos ou melhores; oportunidades de
                cooperação e centralização na criação e utilização de dados compartilhados.
                Atualmente a utilização da tecnologia da informação nas empresas em geral resulta
                na criação de novos produtos, melhores serviços e na significativa redução de
                custos. Por conseguinte, percebe-se a importância da tecnologia para
                armazenamento e disseminação da informação. 
            </p>
        </div>
    </div>
<%@include file="templates/footer.jsp" %>
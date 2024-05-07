<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.project.list.label.code" path="code" width="20%"/>
	<acme:list-column code="any.project.list.label.title" path="title" width="60%"/>
	<acme:list-column code="any.project.list.label.cost" path="cost" width="20%"/>
	<acme:list-payload path="payload"/>
</acme:list>
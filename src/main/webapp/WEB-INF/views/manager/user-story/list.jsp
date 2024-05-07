<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="manager.user-story.list.label.title" path="title" width="40%"/>
	<acme:list-column code="manager.user-story.list.label.estimatedCost" path="estimatedCost" width="20%"/>
	<acme:list-column code="manager.user-story.list.label.priority" path="priority" width="20%"/>
	<acme:list-column code="manager.user-story.list.label.draftMode" path="draftMode" width="20%"/>
	<acme:list-payload path="payload"/>
</acme:list>

<jstl:if test="${_command == 'list'}">
	<acme:button code="manager.user-story.list.button.create" action="/manager/user-story/create"/>
</jstl:if>
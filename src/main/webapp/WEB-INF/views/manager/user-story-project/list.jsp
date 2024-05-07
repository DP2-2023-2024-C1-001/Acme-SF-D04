<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="manager.user-story-project.list.label.project-title" path="projectTitle" width="50%"/>
	<acme:list-column code="manager.user-story-project.list.label.user-story-title" path="userStoryTitle" width="50%"/>
</acme:list>

<jstl:if test="${_command == 'list'}">
	<acme:button code="manager.user-story-project.list.button.create" action="/manager/user-story-project/create"/>
</jstl:if>
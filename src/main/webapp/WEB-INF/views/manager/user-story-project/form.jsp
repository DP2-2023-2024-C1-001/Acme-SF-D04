<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|delete')}">
			<acme:input-select code="manager.user-story-project.list.label.project-title" path="project" choices="${projectChoices }" readonly="true"/>
			<acme:input-select code="manager.user-story-project.list.label.user-story-title" path="userStory" choices="${userStoryChoices }" readonly="true"/>
			<acme:submit code="manager.user-story-project.form.button.delete" action="/manager/user-story-project/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:input-select code="manager.user-story-project.list.label.project-title" path="project" choices="${projects }"/>
			<acme:input-select code="manager.user-story-project.list.label.user-story-title" path="userStory" choices="${userStories }"/>
			<acme:submit code="manager.user-story-project.list.button.create" action="/manager/user-story-project/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>
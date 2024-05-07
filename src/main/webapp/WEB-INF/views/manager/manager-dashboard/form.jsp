<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="manager.manager-dashboard.form.title.user-story-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-dashboard.form.label.user-story-must"/>
		</th>
		<td>
			<acme:print value="${mustUserStories}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-dashboard.form.label.user-story-should"/>
		</th>
		<td>
			<acme:print value="${shouldUserStories}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-dashboard.form.label.user-story-could"/>
		</th>
		<td>
			<acme:print value="${couldUserStories}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-dashboard.form.label.user-story-wont"/>
		</th>
		<td>
			<acme:print value="${wontUserStories}"/>
		</td>
	</tr>
	
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-dashboard.form.label.user-story-avg-cost"/>
		</th>
		<td>
			<acme:print value="${averageCostOfUserStory}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-dashboard.form.label.user-story-dev-cost"/>
		</th>
		<td>
			<acme:print value="${deviationCostOfUserStory}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-dashboard.form.label.user-story-min-cost"/>
		</th>
		<td>
			<acme:print value="${minimumCostOfUserStory}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="manager.manager-dashboard.form.label.user-story-max-cost"/>
		</th>
		<td>
			<acme:print value="${maximumCostOfUserStory}"/>
		</td>
	</tr>
</table>

<h2>
	<acme:message code="manager.manager-dashboard.form.title.project-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
	<th scope="row">
			<acme:message code="manager.manager-dashboard.form.label.project-avg-cost"/>
		</th>
	    <jstl:forEach var="entry" items="${averageCostOfProject}">
        <tr>
            <th scope="row">${entry.key}</th>
            <td><acme:print value="${entry.value}"/></td>
        </tr>
    </jstl:forEach>
    <tr>
    
    <tr>
    <th scope="row">
			<acme:message code="manager.manager-dashboard.form.label.project-dev-cost"/>
		</th>
    <jstl:forEach var="entry" items="${deviationCostOfProject}">
        <tr>
            <th scope="row">${entry.key}</th>
            <td><acme:print value="${entry.value}"/></td>
        </tr>
    </jstl:forEach>
    </tr>
    
    <tr>
    <th scope="row">
			<acme:message code="manager.manager-dashboard.form.label.project-min-cost"/>
		</th>
    <jstl:forEach var="entry" items="${minimumCostOfProject}">
        <tr>
            <th scope="row">${entry.key}</th>
            <td><acme:print value="${entry.value}"/></td>
        </tr>
    </jstl:forEach>
    </tr>
    
    <tr>
    <th scope="row">
			<acme:message code="manager.manager-dashboard.form.label.project-max-cost"/>
		</th>
    <jstl:forEach var="entry" items="${maximumCostOfProject}">
        <tr>
            <th scope="row">${entry.key}</th>
            <td><acme:print value="${entry.value}"/></td>
        </tr>
    </jstl:forEach>
    </tr>
	
</table>


<acme:return/>
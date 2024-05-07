<%--
- form.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:message code="developer.developer-dashboard.form.title.training-module-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="developer.developer-dashboard.form.label.training-module-numbers-update"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfTrainingModulesWithUpdateMoment}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="developer.developer-dashboard.form.label.training-module-numbers-link"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfTrainingSessionsWithLink}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="developer.developer-dashboard.form.label.training-module-avg"/>
		</th>
		<td>
			<acme:print value="${averageTimeOfTrainingModules}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="developer.developer-dashboard.form.label.training-module-deviation"/>
		</th>
		<td>
			<acme:print value="${deviationTimeOfTrainigModules}"/>
		</td>
	</tr>
	
		<tr>
		<th scope="row">
			<acme:message code="developer.developer-dashboard.form.label.training-module-min"/>
		</th>
		<td>
			<acme:print value="${minimumTimeOfTrainingModules}"/>
		</td>
	</tr>
	
			<tr>
		<th scope="row">
			<acme:message code="developer.developer-dashboard.form.label.training-module-max"/>
		</th>
		<td>
			<acme:print value="${maximumTimeOfTrainingModules}"/>
		</td>
	</tr>
</table>


<acme:return/>
<%-- Copyright (C) 2012-2024 Rafael Corchuelo.
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



<acme:form>
	<acme:input-moment code="administrator.objective.form.label.instantiationMoment" path="instantiationMoment" />	
	<acme:input-textbox code="administrator.objective.form.label.title" path="title"/>	
	<acme:input-textarea code="administrator.objective.form.label.description" path="description"/>
	<acme:input-select code="administrator.objective.form.label.priority" path="priority" choices="${priorities}"/>
	<acme:input-checkbox code="administrator.objective.form.label.status" path="status"/>
	<acme:input-moment code="administrator.objective.form.label.initialPeriod" path="initialPeriod"/>
	<acme:input-moment code="administrator.objective.form.label.finalPeriod" path="finalPeriod"/>
	<acme:input-url code="administrator.objective.form.label.link" path="link"/>
	
	<jstl:if test="${_command == 'create'}">
		<acme:input-checkbox code="administrator.objective.form.label.confirmation" path="confirmation"/>
		<acme:submit code="administrator.objective.form.button.create" action="/administrator/objective/create"/>
	</jstl:if>
	
</acme:form>
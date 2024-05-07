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



<acme:form>
	<acme:input-textbox code="any.code-audit.form.label.code" path="code"/>	
	<acme:input-moment code="any.code-audit.form.label.execution" path="execution" />
	<acme:input-select code="any.code-audit.form.label.type" path="type" choices= "${types}"/>
	<acme:input-textarea code="any.code-audit.form.label.correctiveActions" path="correctiveActions"/>
	<acme:input-url code="any.code-audit.form.label.link" path="link"/>
	<acme:input-select code="any.code-audit.form.label.project" path="project" choices= "${projects}"/>	
	
	
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show')}">
			<acme:button code="any.code-audit.form.button.auditRecords" action="/any/audit-record/list?masterId=${id}"/>
		
		</jstl:when>
		</jstl:choose>
		
	
</acme:form>
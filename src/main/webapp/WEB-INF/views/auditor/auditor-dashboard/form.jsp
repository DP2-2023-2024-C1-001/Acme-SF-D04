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
	<acme:message code="auditor.auditor-dashboard.form.title.code-audit-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.code-audit-numbers-static"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfCodeAuditsWithTypeStatic}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.code-audit-numbers-dynamic"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfCodeAuditsWithTypeDynamic}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.audit-record-avg"/>
		</th>
		<td>
			<acme:print value="${averageNumberOfAuditRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.audit-record-deviation"/>
		</th>
		<td>
			<acme:print value="${deviationNumberOfAuditRecords}"/>
		</td>
	</tr>
	
		<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.audit-record-min"/>
		</th>
		<td>
			<acme:print value="${minimumNumberOfAuditRecords}"/>
		</td>
	</tr>
	
			<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.audit-record-max"/>
		</th>
		<td>
			<acme:print value="${maximumNumberOfAuditRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.audit-record-time-avg"/>
		</th>
		<td>
			<acme:print value="${averageTimeOfPeriodLegthOfAuditRecord}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.audit-record-time-deviation"/>
		</th>
		<td>
			<acme:print value="${deviationTimeOfPeriodLegthOfAuditRecord}"/>
		</td>
	</tr>
	
		<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.audit-record-time-min"/>
		</th>
		<td>
			<acme:print value="${minimumTimeOfPeriodLegthOfAuditRecord}"/>
		</td>
	</tr>
	
			<tr>
		<th scope="row">
			<acme:message code="auditor.auditor-dashboard.form.label.audit-record-time-max"/>
		</th>
		<td>
			<acme:print value="${maximumTimeOfPeriodLegthOfAuditRecord}"/>
		</td>
	</tr>
</table>


<acme:return/>
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
	<acme:message code="sponsor.sponsor-dashboard.form.title.sponsorship-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-dashboard.form.label.sponsorship-with-link"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfSponsorshipWithLink}"/>
		</td>
	</tr>
	
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-dashboard.form.label.sponsorship-average-amount"/>
		</th>
		 <jstl:forEach var="entry" items="${averageAmountOfSponsorship}">
        <tr>
            <th scope="row">${entry.key}</th>
            <td><acme:print value="${entry.value}"/></td>
        </tr>
    </jstl:forEach>
	</tr>
	
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-dashboard.form.label.sponsorship-deviation-amount"/>
		</th>
		<jstl:forEach var="entry" items="${deviationAmountOfSponsorship}">
        <tr>
            <th scope="row">${entry.key}</th>
            <td><acme:print value="${entry.value}"/></td>
        </tr>
    </jstl:forEach>
	</tr>
	
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-dashboard.form.label.sponsorship-minimun-amount"/>
		</th>
		<jstl:forEach var="entry" items="${minimumAmountOfSponsorship}">
        <tr>
            <th scope="row">${entry.key}</th>
            <td><acme:print value="${entry.value}"/></td>
        </tr>
    </jstl:forEach>
	</tr>
	
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-dashboard.form.label.sponsorship-maximum-amount"/>
		</th>
		<jstl:forEach var="entry" items="${maximumAmountOfSponsorship}">
        <tr>
            <th scope="row">${entry.key}</th>
            <td><acme:print value="${entry.value}"/></td>
        </tr>
    </jstl:forEach>
	</tr>
	
</table>


<h2>
	<acme:message code="sponsor.sponsor-dashboard.form.title.invoice-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-dashboard.form.label.invoices-With-Tax-Less-Or-Equals-21Percent"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfInvoicesWithTaxLessOrEquals21Percent}"/>
		</td>
	</tr>
	
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-dashboard.form.label.invoice-average-quantity"/>
		</th>
		<jstl:forEach var="entry" items="${averageQuantityOfInvoice}">
        <tr>
            <th scope="row">${entry.key}</th>
            <td><acme:print value="${entry.value}"/></td>
        </tr>
    </jstl:forEach>
	</tr>
	
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-dashboard.form.label.invoice-deviation-quantity"/>
		</th>
		<jstl:forEach var="entry" items="${deviationQuantityOfInvoice}">
        <tr>
            <th scope="row">${entry.key}</th>
            <td><acme:print value="${entry.value}"/></td>
        </tr>
    </jstl:forEach>
	</tr>
	
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-dashboard.form.label.invoice-minimun-quantity"/>
		</th>
		<jstl:forEach var="entry" items="${minimumQuantityOfInvoice}">
        <tr>
            <th scope="row">${entry.key}</th>
            <td><acme:print value="${entry.value}"/></td>
        </tr>
    </jstl:forEach>
	</tr>
	
	<tr>
		<th scope="row">
			<acme:message code="sponsor.sponsor-dashboard.form.label.invoice-maximum-quantity"/>
		</th>
		<jstl:forEach var="entry" items="${maximumQuantityOfInvoice}">
        <tr>
            <th scope="row">${entry.key}</th>
            <td><acme:print value="${entry.value}"/></td>
        </tr>
    </jstl:forEach>
	</tr>
	
</table>
















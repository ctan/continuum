<%@ taglib uri="/webwork" prefix="ww" %>
<%@ taglib uri="/tld/extremecomponents" prefix="ec" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="continuum" prefix="c1" %>
<html>
  <ww:i18n name="localization.Continuum">
    <head>
        <title><ww:text name="releaseProject.page.title"/></title>
    </head>
    <body>
      <h2><ww:text name="performReleaseProgress.section.title"/></h2>
      <h3><ww:property value="name"/></h3>
      <ww:form action="performRelease!checkProgress.action" method="post">
        <ww:hidden name="projectId"/>
        <ww:hidden name="releaseId"/>
        <div class="axial">
          <table width="100%">
            <tr>
              <th><ww:text name="releaseProject.status"/></th>
              <th width="100%"><ww:text name="releaseProject.phase"/></th>
            </tr>
            <ww:iterator value="listener.phases">
              <tr>
                <td>
                <ww:if test="listener.completedPhases.contains( top )">
                  <img src="<ww:url value='/images/icon_success_sml.gif'/>"
                       alt="Done" title="Done" border="0">
                </ww:if>
                <ww:elseif test="listener.inProgress.equals( top )">
                  <ww:if test="listener.error == null">
                    <img src="<ww:url value='/images/building.gif'/>"
                         alt="In Progress" title="In Progress" border="0">
                  </ww:if>
                  <ww:else>
                    <img src="<ww:url value='/images/icon_error_sml.gif'/>"
                         alt="Error" title="Error" border="0">
                  </ww:else>
                </ww:elseif>
                <ww:else>
                  <img src="<ww:url value='/images/inqueue.gif'/>"
                       alt="Queued" title="Queued" border="0">
                </ww:else>
                </td>
                <td><ww:property/></td>
              </tr>
            </ww:iterator>
          </table>
        </div>
        <ww:submit value="Refresh"/>
      </ww:form>
    </body>
  </ww:i18n>
</html>

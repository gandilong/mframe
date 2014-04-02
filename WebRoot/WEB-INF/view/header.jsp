<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../include/taglibs.jsp" %>

<div class="container-fluid">
   <div class="row-fluid">
     <ul class="inline">
         <li><a href="main" class="btn btn-large <c:if test='${"main" eq values.model}'>btn-primary</c:if>"><label class="icon-home"></label>首页</a></li>
         <li><a href="project" class="btn btn-large <c:if test='${"project" eq values.model}'>btn-primary</c:if>"><label class="icon-briefcase"></label>项目管理</a></li>
         <li><a href="" class="btn btn-large <c:if test='${"bug" eq values.model}'>btn-primary</c:if>"><label class="icon-exclamation-sign"></label>Bug管理</a></li>
         <shiro:hasPermission name="system">
             
             <li><a href="system" class="btn btn-large <c:if test='${"system" eq values.model}'>btn-primary</c:if>" ><label class="icon-cog"></label>系统管理</a></li>
         </shiro:hasPermission>
         <li class="offset4"><label class="icon-user"></label><shiro:principal></shiro:principal></li>
         <li><a href="system/user/logout" class="btn"><label class="icon-off"></label>退出</a></li>
     </ul>
   </div>
</div>

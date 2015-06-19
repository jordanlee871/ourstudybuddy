<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.billjc.essay.student.dao.EssayStudentDao" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>后臺管理系統</title>
<link href="../css/index.css" rel="stylesheet" type="text/css" />
<link href="../css/mains.css" rel="stylesheet" type="text/css" />
<script charset="utf-8" type="text/javascript" src="../js/jquery.min.js"></script>
<script charset="utf-8" type="text/javascript" src="../kindeditor/kindeditor.js"></script>
<script charset="utf-8" type="text/javascript" src="../js/jquery.form.js"></script>
<script charset="utf-8" type="text/javascript" src="../js/DatePicker/WdatePicker.js"></script>
<script charset="utf-8" type="text/javascript" src="../js/global.js"></script>
<script charset="utf-8" type="text/javascript" src="../js/jquery.contextmenu.js"></script>
<link href="../css/mycss.css" rel="stylesheet" type="text/css" />
<link href="../css/jquery.css" rel="stylesheet" type="text/css" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.js" type="text/
javascript"></script>
<link rel="stylesheet" href="../css/validationEngine.jquery.css" type="text/css"/>
<script src="../js/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
<script src="../js/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-36372141-1']);
  _gaq.push(['_setDomainName', 'ourstudybuddy.com']);
  _gaq.push(['_setAllowLinker', true]);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'stats.g.doubleclick.net/dc.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
  
</script>
<style type="text/css">
	.do_appointment_div{
		border: 1px solid #000000;
		height: 400px;
		margin-left: 200px;
		margin-top: 100px;
		overflow: hidden;
		position: absolute;
		width: 600px;
	}
	.do_appointment_a{
		color:#3A66FF;
		cursor:pointer;
	}


</style>
</head>
<body class="index">
	<img src="../images/logo.gif"  class="logo"/>
	<div class="adminBox">歡迎你,${requestScope.UserNameType}<a href="../logout.php"><img src="../images/logoutbtn.gif" border="0"  class="logout"/></a></div>
	<div class="menu">
					<a href="index.php?ac=cms" class='select' id='menu'>
						<div class='tableft'></div>
						<div class='tabcenter'>CMS</div>
						<div class='tabright'></div>
					</a>
			
					<a href="index.php?ac=set" class='' id='menu'>
						<div class='tableft'></div>
						<div class='tabcenter'>Setting</div>
						<div class='tabright'></div>
					</a>
	
	
	</div>
	<div class="menubar"></div>
	<div class="requestTime"></div>
	<div class="wrap1">
		<table width="100%" border="0" class="layout" >
		  <tr>
			<td height="445" valign="top" class="leftlayout">
				  <div class="menuheader">控制面板</div>				  
				  <div class="menu1" id='leftmenu'>	
						

<div class="menu2">
<a href="#">助教操作</a>
<div class="menu3 "><a href="/ourstudybuddy/${UserType}/student_Edit.do ">注册</a></div>
<div class="menu3 selected"><a href="/ourstudybuddy/${UserType}/student_List.do">学生管理</a></div>
<div class="menu3 "><a href="/ourstudybuddy/${UserType}/appointment_List.do">预约记录</a></div>
<div class="menu3 "><a href="/ourstudybuddy/${UserType}/appointment_Edit.do">预约</a></div>
<div class="menu3 menu_search_box_div">
	<input id="fuzzy_search_box"/>
</div>
<div class="menu3 "><a href="/ourstudybuddy/${UserType}/teacher_Edit.do">老师修改数量限制</a></div>
<div class="menu3 "><a 
	href="/ourstudybuddy/${UserType}/cancelappointment_List.do">批量删除预约</a></div>
<div class="menu3 "><a 
	href="/ourstudybuddy/${UserType}/charge_edit.do">批量充值</a></div>
<div class="menu3 "><a href="/ourstudybuddy/${UserType}/export_Edit.do">导出xls文件</a></div>
<div class="menu3 "><a href="/ourstudybuddy/${UserType}/assistant_Edit.do">配置</a></div>
</div>




				  </div>
				  <div class="menufooter"></div>
			</td>
			
			<td valign="top" class="rightlayout">
				<div style="position:relative;">
					<div id="fishbox" style="position:absolute;z-index:1000;display:none;background-color:#ffffff;padding-bottom:10px;width:100%;">
						<div class="workplace_body" >
						<div class="topleft"></div>
						<div class="topright"></div>
						<div class="buttomleft"></div>
						<div class="buttomright"></div>
						<div class="panelHeader" ><span id="fishboxTitle"></span><a href="javascript:closeFishbox();" style="float:right;">Close</a></div>
						<div id="fishboxContent" style="background-color:#ffffff;width:100%;"></div>
						</div>
					</div>
                    <div id="alert_box">
                    	<span class="close_btn">X</span>
                        <div class="text"></div>
                    </div>
					<div id="mask" style="
                    	display: none;  
                        position: absolute;  
                        top: 0%;  
                        left: 0%;  
                        width: 100%;  
                        height: 100%;  
                        background-color: black;  
                        z-index:1001;  
                        -moz-opacity: 0.7;  
                        opacity:.70;  
                        filter: alpha(opacity=70);
                    "><div style="color:#999999;font-size:25px;margin-left:auto;margin-right:auto;
                    	margin-top:100px;text-align:center;">我们正在努力加载^_^，不过貌似网速有点慢#_#，麻烦稍等一下</div></div>
					<div class="workplace" id="Main">
					 
						
<div class="workplace_body" >
<div class="topleft"></div>
<div class="topright"></div>
<div class="buttomleft"></div>
<div class="buttomright"></div>
<div class="panelHeader">学生资料</div>
<div class="panelsubmenu">
<div style="text-align:right;padding-right:20px;">
		<a href="/ourstudybuddy/${UserType}/student_Edit.do ">增加学生</a>
	</div>
</div>
<table width="100%" border="0" cellspacing="0" style='color:#4F5D66;'>
	<br/>
    <tr>
    			<td style="padding-left:5px;" height="25" align="center"><span class="title">旺旺</span></td>
   				<td style="padding-left:5px;" height="25" align="center"><span class="title">QQ</span></td>
   				<td style="padding-left:5px;" height="25" align="center"><span class="title">手机</span></td>
   				<td style="padding-left:5px;" height="25" align="center"><span class="title">邮箱</span></td>
   				<td style="padding-left:5px;" height="25" align="center"><span class="title">注册时间</span></td>
   				<td style="padding-left:5px;" height="25" align="center"><span class="title"></span></td>
   	    </tr>
    <tr height='15'>
    	<form action="/ourstudybuddy/${UserType}/studentessaysearch.do" method="post">
        	<input type="hidden" value="student_list" name="type"/>
			<td style="padding-left:5px;" height="25" align="center"><input style="width:100px" name="wanwan"  
            	value="${wanwan}"/></td>                                                                     
            <td style="padding-left:5px;" height="25" align="center"><input style="width:100px" name="qq"    
            value="${qq}"/></td>                                                                             
            <td style="padding-left:5px;" height="25" align="center"><input style="width:100px" name="phone" 
            value="${phone}"/></td>                                                                          
            <td style="padding-left:5px;" height="25" align="center"><input style="width:100px" name="email" 
            value="${email}"/></td>                                                                          
            <td style="padding-left:5px;" height="25" align="center"><input style="width:100px" name="date"  
             value="${date}"/></td> 
            <td style="padding-left:5px;" height="25" align="center"><input value="搜索" type="submit"/><br/>
            
        </form>
    </tr>
	<!-- <tr height='15'><td colspan='5'></td></tr>-->
    
 		<c:forEach items="${studentlist}" var="node">
		<tr>
		<c:url var="editUrl" value="/system/studentessaydetail.do">
        <c:param name="id" value="${node.stuId}" />
        </c:url>		
		<td style="padding-left:5px;" height="25" align="center">${node.name}</td>
        <td style="padding-left:5px;" height="25" align="center">${node.qq}</td>
        <td style="padding-left:5px;" height="25" align="center">${node.phone}</td>
        <td style="padding-left:5px;" height="25" align="center">${node.email}</td>
        <td style="padding-left:5px;" height="25" align="center">${node.createTime}</td>
        <td style="padding-left:5px;" height="25" align="center"><a href='<c:out value="${editUrl}"/>'>详情</a></td>
		</tr>		
		</c:forEach>  
		
    	<!--<tr>
    	<td style="padding-left:5px;" height="25">Essay3</td>
		<td style="padding-left:5px;" height="25">2014-9-7 23:29PM</td>
		<td style="padding-left:5px;" height="25"></td>
		<td style="padding-left:5px;" height="25"></td>
		<td style="padding-left:5px;" height="25"><a href="">修改</a><a href="">删除</a></td>
    </tr>
	<tr>
    	<td style="padding-left:5px;" height="25">Essay2</td>
		<td style="padding-left:5px;" height="25">2014-9-7 23:29PM</td>
		<td style="padding-left:5px;" height="25">（老师正在批改，请稍等……）</td>
		<td style="padding-left:5px;" height="25"></td>
		<td style="padding-left:5px;" height="25"></td>
    </tr>
	<tr>
    	<td style="padding-left:5px;" height="25">Essay1</td>
		<td style="padding-left:5px;" height="25">2014-9-7 23:29PM</td>
		<td style="padding-left:5px;" height="25"><a href="">点击下载</a></td>
		<td style="padding-left:5px;" height="25">2014-9-7 23:29PM</td>
		<td style="padding-left:5px;" height="25"></td>
    </tr>-->
</table>
<table width="100%" border="0" cellspacing="0">
	<tr colspan="4" height="10"></tr>
    <tr>
      <td width="220" height="30" align='left'></td>
      <td width="" class="title" colspan='3'>
	  <a href='/ourstudybuddy/${UserType}/studentessaysearch2.do?wanwan=${wanwan}&qq=${qq}&phone=${phone}&email=${email}&date=${date}&page=1'>首頁</a>&nbsp;&nbsp;&nbsp;&nbsp; 
	  <a href='/ourstudybuddy/${UserType}/studentessaysearch2.do?wanwan=${wanwan}&qq=${qq}&phone=${phone}&email=${email}&date=${date}&page=${checkNo-1}'>上一頁</a>&nbsp;&nbsp;&nbsp;&nbsp; 
	  <a href='/ourstudybuddy/${UserType}/studentessaysearch2.do?wanwan=${wanwan}&qq=${qq}&phone=${phone}&email=${email}&date=${date}&page=${checkNo+1}'>下一頁</a>&nbsp;&nbsp;&nbsp;&nbsp; 
	  <a href='/ourstudybuddy/${UserType}/studentessaysearch2.do?wanwan=${wanwan}&qq=${qq}&phone=${phone}&email=${email}&date=${date}&page=${pagecount}'>末頁</a>&nbsp;&nbsp;&nbsp;&nbsp; 
	  <select onchange="javascript:submitback('/ourstudybuddy/${UserType}/studentessaysearch2.do?wanwan=${wanwan}&qq=${qq}&phone=${phone}&email=${email}&date=${date}&page='+ this.options[this.selectedIndex].value)">
<c:forEach var="x" begin="1" end="${pagecount}" step="1"> 
 <option value='${x}' ${x==checkNo?'selected':''} > 
 ${x}
 </option> 
</c:forEach>
</select> &nbsp;&nbsp;總共有&nbsp;${pagecount}&nbsp;頁 &nbsp;&nbsp;每頁10&nbsp;條記錄</td>
	</tr>
	<tr colspan="4" height="10"></tr>
</table>
</div>					 </div>
				 </div>
		 	   </td>
			</tr>
  		</table>
 	</div>
</body>

</html>

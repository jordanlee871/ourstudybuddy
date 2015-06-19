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

<!--<script src="../js/jquery.js" type="text/javascript"></script>-->
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
<div class="menu3 "><a href="/ourstudybuddy/${UserType}/student_List.do">学生管理</a></div>
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
<div class="panelHeader">学生详情</div>
<div class="panelsubmenu">
<div style="text-align:right;padding-right:20px;">

</div>
</div>
<br/>
<form id="viewLinkForm" action="do/do_EditCommmon.php" method="POST" enctype="multipart/form-data">
	<input type="hidden" name="table" value="com_member">
    <input type="hidden" name="editId" value="2602">  
	<input type="hidden" name="assistant_id" value="20"/>
    <input type="hidden" name="role" value="student"/>

<table width="100%" border="0" cellspacing="0" style='color:#4F5D66;'>
	<tr><td height="20" colspan=2><hr></td></tr>
	<tr>
         <td width="120" height="35" class="title">旺旺:<font color="#FF0000">&nbsp;</font></td>
		 <td><input type="text" name="wanwan" class='contentinput' value="${StudentInfor.ww}"/>*</td>
    </tr>
    <tr>
         <td width="120" height="35" class="title">QQ:<font color="#FF0000">&nbsp;</font></td>
		 <td><input type="text" name="qq" class='contentinput' value="${StudentInfor.qq}"/></td>
    </tr>
    <tr>
         <td width="120" height="35" class="title">手机:<font color="#FF0000">&nbsp;</font></td>
		 <td><input type="text" name="phone" class='contentinput' value="${StudentInfor.phone}"/>*</td>
    </tr>
    <tr>
         <td width="120" height="35" class="title">邮箱:<font color="#FF0000">&nbsp;</font></td>
		 <td><input type="text" name="email" value="${StudentInfor.email}" class="contentinput validate[required,custom[email]]" id="email"/>*</td>
    </tr>
  	
	<tr>
         <td width="120" height="35" class="title">注册时间:<font color="#FF0000">&nbsp;</font></td>
		 <td><input type="text" name="real_name" class='contentinput' value="${StudentInfor.createTime}" readonly="readonly"/></td>
    </tr>
	<tr>
       <td width="120" height="35" class="title">是否启用:<font color="#FF0000">&nbsp;</font></td>
       		<td><input type="radio" value="1" name="active" checked   />是&nbsp;&nbsp;
        <input type="radio" value="${StudentInfor.active}" name="active" />否</td>
    </tr>
	
	
	<tr>
         <td width="120" height="35" class="title"></td>
		 <td>
		 	<input type="submit" name="publish" class="commmonBtn" value="Save" onClick="submitform('do/do_EditCommmon.php?tag=1','/ourstudybuddy/${UserType}/student_List.do&page=1');">&nbsp;&nbsp;
			<input type="button" name="back" class="commmonBtn" value="Back" onClick="submitback('/ourstudybuddy/${UserType}/student_List.do&page=1');">
                        <input type="button" name="charge" class="commmonBtn" value="充值" onClick="editFunction('index.php?type=charge_edit&backType=student_list&editId=2602');">
                        <input type="button" name="charge" class="commmonBtn" value="作文记录" onClick="editFunction('/ourstudybuddy/${UserType}/student_List.do&student_id=2602');">
                        <input type="button" name="charge" class="commmonBtn" value="退款" onClick="do_function('do_refund','2602',
            '/ourstudybuddy/${UserType}/student_Edit.do &editId=2602','即将为学生白小白qaq退款，请确认');">
            		 </td>
    </tr>
</table>
</form>
</div>
<script language="javascript">

</script>					 </div>
				 </div>
		 	   </td>
			</tr>
  		</table>
 	</div>
</body>

</html>

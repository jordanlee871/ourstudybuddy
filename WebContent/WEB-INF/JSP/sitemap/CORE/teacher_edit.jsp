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
	<div class="adminBox">歡迎你,TA-Wayne(助教)<a href="../logout.php"><img src="../images/logoutbtn.gif" border="0"  class="logout"/></a></div>
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
<div class="menu3 "><a href="index.php?type=student_edit&backType=student_list">注册</a></div>
<div class="menu3 "><a href="index.php?type=student_list">学生管理</a></div>
<div class="menu3 "><a href="index.php?type=appointment_list">预约记录</a></div>
<div class="menu3 "><a href="index.php?type=appointment_edit">预约</a></div>
<div class="menu3 menu_search_box_div">
	<input id="fuzzy_search_box"/>
</div>
<div class="menu3 selected"><a href="index.php?type=teacher_edit">老师修改数量限制</a></div>
<div class="menu3 "><a 
	href="index.php?type=cancel_appointment_list">批量删除预约</a></div>
<div class="menu3 "><a 
	href="index.php?type=charge_s_edit">批量充值</a></div>
<div class="menu3 "><a href="index.php?type=export_edit">导出xls文件</a></div>
<div class="menu3 "><a href="index.php?type=assistant_edit">配置</a></div>
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
<div class="panelHeader"></div>
<div class="panelsubmenu">
<div style="text-align:right;padding-right:20px;">
	</div>
</div>
<form id="viewLinkForm" action="do/do_AddCommmon.php" method="POST" enctype="multipart/form-data">
	<input type="hidden" name="table" value="t_teacher">
	<input type="hidden" name="assistant_id" value="20"/>

<table width="50%" border="1" cellpadding="1" cellspacing="0" class="appointment_table" style='color:#4F5D66;'>
	<br/>
    <tr>
        </tr>
	<!-- <tr height='15'><td colspan=''></td></tr>-->
   <!-- -->
    <tr>
    	<td style="padding-left:5px;" height="25" align="center"></td>
    	    	<td style="padding-left:5px;" height="25" align="center">2014-09-07</td>
            	<td style="padding-left:5px;" height="25" align="center">2014-09-08</td>
            	<td style="padding-left:5px;" height="25" align="center">2014-09-09</td>
            	<td style="padding-left:5px;" height="25" align="center">2014-09-10</td>
            </tr>
        <tr>
    	<td style="padding-left:5px;" height="25" align="center">Aimee        <input name="teacher_id[]" value="1" type="hidden"/>
        </td>
        <td style="padding-left:5px;" height="25" align="center">
                <input name="app_value_1_1" value="7" size="2" 
       		class="change_input" id="appoint_0_1"/>
        <input name="app_value_1_1_hidden" value="7" size="2" 
         	id="hide_appoint_0_1" type="hidden"/>
        </td>
        <td style="padding-left:5px;" height="25" align="center">
                 <input name="app_value_1_2" value="7" size="2" 
        	class="change_input" id="appoint_0_2"/>
        <input name="app_value_1_2_hidden" value="7" size="2" 
        	id="hide_appoint_0_2" type="hidden"/></td>
        <td style="padding-left:5px;" height="25" align="center">
                <input name="app_value_1_3" value="7" size="2" 
        	class="change_input" id="appoint_0_3"/>
        <input name="app_value_1_3_hidden" value="7" size="2" 
        	id="hide_appoint_0_3" type="hidden"/></td>
         <td style="padding-left:5px;" height="25" align="center">
                <input name="app_value_1_4" value="" size="2" 
        	class="change_input" id="appoint_0_4"/>
        <input name="app_value_1_4_hidden" value="" size="2" 
        	id="hide_appoint_0_4" type="hidden"/></td>
    </tr>
	    <tr>
    	<td style="padding-left:5px;" height="25" align="center">Lea        <input name="teacher_id[]" value="2" type="hidden"/>
        </td>
        <td style="padding-left:5px;" height="25" align="center">
                <input name="app_value_2_1" value="3" size="2" 
       		class="change_input" id="appoint_1_1"/>
        <input name="app_value_2_1_hidden" value="3" size="2" 
         	id="hide_appoint_1_1" type="hidden"/>
        </td>
        <td style="padding-left:5px;" height="25" align="center">
                 <input name="app_value_2_2" value="3" size="2" 
        	class="change_input" id="appoint_1_2"/>
        <input name="app_value_2_2_hidden" value="3" size="2" 
        	id="hide_appoint_1_2" type="hidden"/></td>
        <td style="padding-left:5px;" height="25" align="center">
                <input name="app_value_2_3" value="3" size="2" 
        	class="change_input" id="appoint_1_3"/>
        <input name="app_value_2_3_hidden" value="3" size="2" 
        	id="hide_appoint_1_3" type="hidden"/></td>
         <td style="padding-left:5px;" height="25" align="center">
                <input name="app_value_2_4" value="" size="2" 
        	class="change_input" id="appoint_1_4"/>
        <input name="app_value_2_4_hidden" value="" size="2" 
        	id="hide_appoint_1_4" type="hidden"/></td>
    </tr>
	    <tr>
    	<td style="padding-left:5px;" height="25" align="center">Jamie(中教)        <input name="teacher_id[]" value="11" type="hidden"/>
        </td>
        <td style="padding-left:5px;" height="25" align="center">
                <input name="app_value_11_1" value="0" size="2" 
       		class="change_input" id="appoint_2_1"/>
        <input name="app_value_11_1_hidden" value="0" size="2" 
         	id="hide_appoint_2_1" type="hidden"/>
        </td>
        <td style="padding-left:5px;" height="25" align="center">
                 <input name="app_value_11_2" value="7" size="2" 
        	class="change_input" id="appoint_2_2"/>
        <input name="app_value_11_2_hidden" value="7" size="2" 
        	id="hide_appoint_2_2" type="hidden"/></td>
        <td style="padding-left:5px;" height="25" align="center">
                <input name="app_value_11_3" value="0" size="2" 
        	class="change_input" id="appoint_2_3"/>
        <input name="app_value_11_3_hidden" value="0" size="2" 
        	id="hide_appoint_2_3" type="hidden"/></td>
         <td style="padding-left:5px;" height="25" align="center">
                <input name="app_value_11_4" value="" size="2" 
        	class="change_input" id="appoint_2_4"/>
        <input name="app_value_11_4_hidden" value="" size="2" 
        	id="hide_appoint_2_4" type="hidden"/></td>
    </tr>
	</table>
<table width="50%" border="0" cellpadding="1" cellspacing="0">
	<tr>
         <td width="120" height="35" class="title"></td>
		 <td>
		 	<input type="submit" class="commmonBtn" value="保存" onclick="submitform('do/do_AddCommmon.php?tag=1','index.php?type=teacher_edit');">&nbsp;&nbsp;
		 </td>
    </tr>
</table>
</form>
</div>					 </div>
				 </div>
		 	   </td>
			</tr>
  		</table>
 	</div>
</body>

</html>

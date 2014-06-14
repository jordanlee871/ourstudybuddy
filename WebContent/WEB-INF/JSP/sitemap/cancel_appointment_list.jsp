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
<div class="menu3 "><a href="index.php?type=student_list">学生管理</a></div>
<div class="menu3 "><a href="index.php?type=appointment_list">学生预约</a></div>
<div class="menu3 "><a href="index.php?type=appointment_edit">作文预约</a></div>
<div class="menu3 "><a href="index.php?type=teacher_edit">老师修改数量限制</a></div>
<div class="menu3 selected"><a 
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
<div class="panelHeader">批量取消预约</div>
<div class="panelsubmenu">
<div style="text-align:right;padding-right:20px;">
	    <form action="index.php" method="post">
        	<input type="hidden" value="cancel_appointment_list" name="type"/>
            <div class="search_box_div" style="width:90px"><input type="checkbox" name="less" />余额小于0</div>
            <div class="search_box_div">开始时间：<input style="width:120px" name="start_time" value="" 
            	onclick="WdatePicker({lang:'zh-tw',dateFmt:'yyyy-MM-dd HH:mm'});"/></div>
            <div class="search_box_div">结束时间：<input style="width:120px" name="end_time" value="" 
            	onclick="WdatePicker({lang:'zh-tw',dateFmt:'yyyy-MM-dd HH:mm'});"/></div>
           <div class="search_box_div" style="width:90px"><input value="搜索" type="submit"/></div>
           <div class="search_box_div" style="width:90px"><a href="javascript:deleteAppoint();">取消预约</a></div>
        </form>
</div>
</div>
<table width="100%" border="0" cellspacing="0" style='color:#4F5D66;'>
	<br/>
    <tr>
    	
    			<td style="padding-left:5px;" height="25" align="center" width='6%'><span class="title"><input class="choose_all" type="checkbox"/></span></td>
   				<td style="padding-left:5px;" height="25" align="center" width='15%'><span class="title">预约日期</span></td>
   				<td style="padding-left:5px;" height="25" align="center" width='5%'><span class="title">student</span></td>
   				<td style="padding-left:5px;" height="25" align="center" width='5%'><span class="title">task</span></td>
   				<td style="padding-left:5px;" height="25" align="center" ><span class="title">附件名</span></td>
   				<td style="padding-left:5px;" height="25" align="center" width='10%'><span class="title">teacher</span></td>
   				<td style="padding-left:5px;" height="25" align="center" width='15%'><span class="title">创建时间</span></td>
   	    </tr>
	<!-- <tr height='15'><td colspan='创建时间'></td></tr>-->
   <!-- -->
       <tr>
    	<td style="padding-left:5px;" height="25" align="center"><input style="width:70px" name="check" type="checkbox" value="10240"/></td> 
		<td style="padding-left:5px;" height="25" align="center">2014-05-24</td>
        <td style="padding-left:5px;" height="25" align="center">happyfang2008_love</td>
        <td style="padding-left:5px;" height="25" align="center">task1</td>
        <!--<td style="padding-left:5px;" height="25" align="center">10</td>-->
        <td style="padding-left:5px;" height="25" align="center">happyfang2008.love@163.com-task1-cycle10-Jamie(中教)</td>
        <td style="padding-left:5px;" height="25" align="center">Jamie(中教)</td>
        <td style="padding-left:5px;" height="25" align="center">2014-05-22 12:10:49</td>
    </tr>
    </table>
<table width="100%" border="0" cellspacing="0">
	<tr colspan="4" height="10"></tr>
    <tr>
      <td width="220" height="30" align='left'></td>
      <td width="" class="title" colspan='3'><a href='#'>首頁</a>&nbsp;&nbsp;&nbsp;&nbsp; <a href='#'>上一頁</a>&nbsp;&nbsp;&nbsp;&nbsp; <a href='#'>下一頁</a>&nbsp;&nbsp;&nbsp;&nbsp; <a href='#'>末頁</a>&nbsp;&nbsp;&nbsp;&nbsp; <select onchange="javascript:submitback('index.php?type=cancel_appointment_list&page='+ this.options[this.selectedIndex].value)">
	<option value=1 selected>1</option>
</select> &nbsp;&nbsp;總共有&nbsp;1&nbsp;頁 &nbsp;&nbsp;每頁30&nbsp;條記錄</td>
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

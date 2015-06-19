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
<div class="menu3 selected"><a href="index.php?type=appointment_list">预约记录</a></div>
<div class="menu3 "><a href="index.php?type=appointment_edit">预约</a></div>
<div class="menu3 menu_search_box_div">
	<input id="fuzzy_search_box"/>
</div>
<div class="menu3 "><a href="index.php?type=teacher_edit">老师修改数量限制</a></div>
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
<div class="panelHeader">预约记录</div>
<div class="panelsubmenu">
<div style="text-align:right;padding-right:20px;">
		<a href="#" id="sendSmsBtn">通知已经发回</a>
</div>
</div>
<table width="100%" border="0" cellspacing="0" style='color:#4F5D66;'>
	<br/>
    <tr>
    <td style="padding-left:5px;" height="25" align="center" >
    	<span class="title">
    		<input type="checkbox" class="checkbox" name="check_all" id="check_all"/>
		</span>
    </td>
    			<td style="padding-left:5px;" height="25" align="center" width='6%'><span class="title">date</span></td>
   				<td style="padding-left:5px;" height="25" align="center" width='5%'><span class="title">student</span></td>
   				<td style="padding-left:5px;" height="25" align="center" width='5%'><span class="title">task</span></td>
   				<td style="padding-left:5px;" height="25" align="center" width='3%'><span class="title">cycle</span></td>
   				<td style="padding-left:5px;" height="25" align="center" ><span class="title">附件名</span></td>
   				<td style="padding-left:5px;" height="25" align="center" width='10%'><span class="title">teacher</span></td>
   				<td style="padding-left:5px;" height="25" align="center" width='10%'><span class="title"></span></td>
   	    </tr>
	<!-- <tr height='15'><td colspan=''></td></tr>-->
   <!-- -->
    <tr height='15'>
    	<form action="index.php" method="post">
    		<td></td>
            <td style="padding-left:5px;" height="25" align="center"><input type="hidden" value="appointment_list" name="type"/>
            	<input style="width:70px" name="date"
             value="" onclick="WdatePicker({lang:'zh-tw',dateFmt:'yyyy-MM-dd'});"/></td> 
			<td style="padding-left:5px;" height="25" align="center"><input style="width:100px" name="wanwan" 
            	value=""/></td> 
            <td style="padding-left:5px;" height="25" align="center"><input style="width:50px" name="task" 
            value=""/></td> 
            <td style="padding-left:5px;" height="25" align="center"><input style="width:30px" name="cycle" 
            value=""/></td> 
            <td style="padding-left:5px;" height="25" align="center"><input style="width:200px" name="email" 
            value=""/></td>
            <td style="padding-left:5px;" height="25" align="center"><input style="width:100px" name="teacher_name_en" 
            value=""/></td> 
            <td style="padding-left:5px;" height="25" align="center"><input value="搜索" type="submit"/><br />
            	</td>
        </form>
    </tr>
        <tr>
    	<td style="padding-left:5px;" height="25" align="center">
    		<input type="checkbox" class="checkbox" name="check_single" id="check_single" value="12168"/>
    	</td> 
		<td style="padding-left:5px;" height="25" align="center">2014-09-07</td>
        <td style="padding-left:5px;" height="25" align="center">apple_forever92</td>
        <td style="padding-left:5px;" height="25" align="center">task2</td>
        <td style="padding-left:5px;" height="25" align="center">3</td>
        <td style="padding-left:5px;" height="25" align="center">fxyxshxxb@163.com-task2-cycle3-Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">
                   <!--<a href="do/do_function.php?f_name=delete_appointment&editId=12168">取消预约</a>-->
           <a href="javascript:do_function('delete_appointment','12168','index.php?type=appointment_list','即将为您取消2014-09-07的一篇task2作文，请确认');">取消预约</a>
                </td>
    </tr>
        <tr>
    	<td style="padding-left:5px;" height="25" align="center">
    		<input type="checkbox" class="checkbox" name="check_single" id="check_single" value="12163"/>
    	</td> 
		<td style="padding-left:5px;" height="25" align="center">2014-09-07</td>
        <td style="padding-left:5px;" height="25" align="center">liumanman19681001</td>
        <td style="padding-left:5px;" height="25" align="center">task2</td>
        <td style="padding-left:5px;" height="25" align="center">12</td>
        <td style="padding-left:5px;" height="25" align="center">1378141556@qq.com-task2-cycle12-Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">
                   <!--<a href="do/do_function.php?f_name=delete_appointment&editId=12163">取消预约</a>-->
           <a href="javascript:do_function('delete_appointment','12163','index.php?type=appointment_list','即将为您取消2014-09-07的一篇task2作文，请确认');">取消预约</a>
                </td>
    </tr>
        <tr>
    	<td style="padding-left:5px;" height="25" align="center">
    		<input type="checkbox" class="checkbox" name="check_single" id="check_single" value="12121"/>
    	</td> 
		<td style="padding-left:5px;" height="25" align="center">2014-09-07</td>
        <td style="padding-left:5px;" height="25" align="center">nkwwy</td>
        <td style="padding-left:5px;" height="25" align="center">task2</td>
        <td style="padding-left:5px;" height="25" align="center">6</td>
        <td style="padding-left:5px;" height="25" align="center">18901035967@189.cn-task2-cycle6-Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">
                   <!--<a href="do/do_function.php?f_name=delete_appointment&editId=12121">取消预约</a>-->
           <a href="javascript:do_function('delete_appointment','12121','index.php?type=appointment_list','即将为您取消2014-09-07的一篇task2作文，请确认');">取消预约</a>
                </td>
    </tr>
        <tr>
    	<td style="padding-left:5px;" height="25" align="center">
    		<input type="checkbox" class="checkbox" name="check_single" id="check_single" value="12122"/>
    	</td> 
		<td style="padding-left:5px;" height="25" align="center">2014-09-07</td>
        <td style="padding-left:5px;" height="25" align="center">nkwwy</td>
        <td style="padding-left:5px;" height="25" align="center">task2</td>
        <td style="padding-left:5px;" height="25" align="center">7</td>
        <td style="padding-left:5px;" height="25" align="center">18901035967@189.cn-task2-cycle7-Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">
                   <!--<a href="do/do_function.php?f_name=delete_appointment&editId=12122">取消预约</a>-->
           <a href="javascript:do_function('delete_appointment','12122','index.php?type=appointment_list','即将为您取消2014-09-07的一篇task2作文，请确认');">取消预约</a>
                </td>
    </tr>
        <tr>
    	<td style="padding-left:5px;" height="25" align="center">
    		<input type="checkbox" class="checkbox" name="check_single" id="check_single" value="12157"/>
    	</td> 
		<td style="padding-left:5px;" height="25" align="center">2014-09-07</td>
        <td style="padding-left:5px;" height="25" align="center">yesproof</td>
        <td style="padding-left:5px;" height="25" align="center">task2</td>
        <td style="padding-left:5px;" height="25" align="center">4</td>
        <td style="padding-left:5px;" height="25" align="center">358216024@qq.com-task2-cycle4-Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">
                   <!--<a href="do/do_function.php?f_name=delete_appointment&editId=12157">取消预约</a>-->
           <a href="javascript:do_function('delete_appointment','12157','index.php?type=appointment_list','即将为您取消2014-09-07的一篇task2作文，请确认');">取消预约</a>
                </td>
    </tr>
        <tr>
    	<td style="padding-left:5px;" height="25" align="center">
    		<input type="checkbox" class="checkbox" name="check_single" id="check_single" value="12120"/>
    	</td> 
		<td style="padding-left:5px;" height="25" align="center">2014-09-07</td>
        <td style="padding-left:5px;" height="25" align="center">高清和靓靓</td>
        <td style="padding-left:5px;" height="25" align="center">task1</td>
        <td style="padding-left:5px;" height="25" align="center">38</td>
        <td style="padding-left:5px;" height="25" align="center">16360010@qq.com-task1-cycle38-Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">
                   <!--<a href="do/do_function.php?f_name=delete_appointment&editId=12120">取消预约</a>-->
           <a href="javascript:do_function('delete_appointment','12120','index.php?type=appointment_list','即将为您取消2014-09-07的一篇task1作文，请确认');">取消预约</a>
                </td>
    </tr>
        <tr>
    	<td style="padding-left:5px;" height="25" align="center">
    		<input type="checkbox" class="checkbox" name="check_single" id="check_single" value="12118"/>
    	</td> 
		<td style="padding-left:5px;" height="25" align="center">2014-09-07</td>
        <td style="padding-left:5px;" height="25" align="center">高清和靓靓</td>
        <td style="padding-left:5px;" height="25" align="center">task1</td>
        <td style="padding-left:5px;" height="25" align="center">37</td>
        <td style="padding-left:5px;" height="25" align="center">16360010@qq.com-task1-cycle37-Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">
                   <!--<a href="do/do_function.php?f_name=delete_appointment&editId=12118">取消预约</a>-->
           <a href="javascript:do_function('delete_appointment','12118','index.php?type=appointment_list','即将为您取消2014-09-07的一篇task1作文，请确认');">取消预约</a>
                </td>
    </tr>
        <tr>
    	<td style="padding-left:5px;" height="25" align="center">
    		<input type="checkbox" class="checkbox" name="check_single" id="check_single" value="12162"/>
    	</td> 
		<td style="padding-left:5px;" height="25" align="center">2014-09-07</td>
        <td style="padding-left:5px;" height="25" align="center">533ma</td>
        <td style="padding-left:5px;" height="25" align="center">task2</td>
        <td style="padding-left:5px;" height="25" align="center">3</td>
        <td style="padding-left:5px;" height="25" align="center">533ma@sina.com-task2-cycle3-Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">
           已取消
                </td>
    </tr>
        <tr>
    	<td style="padding-left:5px;" height="25" align="center">
    		<input type="checkbox" class="checkbox" name="check_single" id="check_single" value="12125"/>
    	</td> 
		<td style="padding-left:5px;" height="25" align="center">2014-09-07</td>
        <td style="padding-left:5px;" height="25" align="center">legendyang96</td>
        <td style="padding-left:5px;" height="25" align="center">task2</td>
        <td style="padding-left:5px;" height="25" align="center">2</td>
        <td style="padding-left:5px;" height="25" align="center">1206657029@qq.com-task2-cycle2-Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">
           已取消
                </td>
    </tr>
        <tr>
    	<td style="padding-left:5px;" height="25" align="center">
    		<input type="checkbox" class="checkbox" name="check_single" id="check_single" value="12160"/>
    	</td> 
		<td style="padding-left:5px;" height="25" align="center">2014-09-07</td>
        <td style="padding-left:5px;" height="25" align="center">liumanman19681001</td>
        <td style="padding-left:5px;" height="25" align="center">task2</td>
        <td style="padding-left:5px;" height="25" align="center">11</td>
        <td style="padding-left:5px;" height="25" align="center">1378141556@qq.com-task2-cycle11-Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">
           已取消
                </td>
    </tr>
        <tr>
    	<td style="padding-left:5px;" height="25" align="center">
    		<input type="checkbox" class="checkbox" name="check_single" id="check_single" value="12123"/>
    	</td> 
		<td style="padding-left:5px;" height="25" align="center">2014-09-07</td>
        <td style="padding-left:5px;" height="25" align="center">skx1967</td>
        <td style="padding-left:5px;" height="25" align="center">task1</td>
        <td style="padding-left:5px;" height="25" align="center">1</td>
        <td style="padding-left:5px;" height="25" align="center">1220762779@qq.com-task1-cycle1-Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">
           已取消
                </td>
    </tr>
        <tr>
    	<td style="padding-left:5px;" height="25" align="center">
    		<input type="checkbox" class="checkbox" name="check_single" id="check_single" value="12126"/>
    	</td> 
		<td style="padding-left:5px;" height="25" align="center">2014-09-07</td>
        <td style="padding-left:5px;" height="25" align="center">skx1967</td>
        <td style="padding-left:5px;" height="25" align="center">task1</td>
        <td style="padding-left:5px;" height="25" align="center">3</td>
        <td style="padding-left:5px;" height="25" align="center">1220762779@qq.com-task1-cycle3-Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">
           已取消
                </td>
    </tr>
        <tr>
    	<td style="padding-left:5px;" height="25" align="center">
    		<input type="checkbox" class="checkbox" name="check_single" id="check_single" value="12124"/>
    	</td> 
		<td style="padding-left:5px;" height="25" align="center">2014-09-07</td>
        <td style="padding-left:5px;" height="25" align="center">skx1967</td>
        <td style="padding-left:5px;" height="25" align="center">task1</td>
        <td style="padding-left:5px;" height="25" align="center">2</td>
        <td style="padding-left:5px;" height="25" align="center">1220762779@qq.com-task1-cycle2-Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">
           已取消
                </td>
    </tr>
        <tr>
    	<td style="padding-left:5px;" height="25" align="center">
    		<input type="checkbox" class="checkbox" name="check_single" id="check_single" value="12148"/>
    	</td> 
		<td style="padding-left:5px;" height="25" align="center">2014-09-07</td>
        <td style="padding-left:5px;" height="25" align="center">viviliqian</td>
        <td style="padding-left:5px;" height="25" align="center">task2</td>
        <td style="padding-left:5px;" height="25" align="center">6</td>
        <td style="padding-left:5px;" height="25" align="center">NAU_tbec@163.com-task2-cycle6-Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">Aimee</td>
        <td style="padding-left:5px;" height="25" align="center">
           已取消
                </td>
    </tr>
        <tr>
    	<td style="padding-left:5px;" height="25" align="center">
    		<input type="checkbox" class="checkbox" name="check_single" id="check_single" value="12130"/>
    	</td> 
		<td style="padding-left:5px;" height="25" align="center">2014-09-07</td>
        <td style="padding-left:5px;" height="25" align="center">huliang_xy</td>
        <td style="padding-left:5px;" height="25" align="center">task2</td>
        <td style="padding-left:5px;" height="25" align="center">3</td>
        <td style="padding-left:5px;" height="25" align="center">530659606@qq.com-task2-cycle3-Lea</td>
        <td style="padding-left:5px;" height="25" align="center">Lea</td>
        <td style="padding-left:5px;" height="25" align="center">
                   <!--<a href="do/do_function.php?f_name=delete_appointment&editId=12130">取消预约</a>-->
           <a href="javascript:do_function('delete_appointment','12130','index.php?type=appointment_list','即将为您取消2014-09-07的一篇task2作文，请确认');">取消预约</a>
                </td>
    </tr>
        <tr>
    	<td style="padding-left:5px;" height="25" align="center">
    		<input type="checkbox" class="checkbox" name="check_single" id="check_single" value="12149"/>
    	</td> 
		<td style="padding-left:5px;" height="25" align="center">2014-09-07</td>
        <td style="padding-left:5px;" height="25" align="center">liruiyan1464</td>
        <td style="padding-left:5px;" height="25" align="center">task2</td>
        <td style="padding-left:5px;" height="25" align="center">1</td>
        <td style="padding-left:5px;" height="25" align="center">851405059@qq.com-task2-cycle1-Lea</td>
        <td style="padding-left:5px;" height="25" align="center">Lea</td>
        <td style="padding-left:5px;" height="25" align="center">
                   <!--<a href="do/do_function.php?f_name=delete_appointment&editId=12149">取消预约</a>-->
           <a href="javascript:do_function('delete_appointment','12149','index.php?type=appointment_list','即将为您取消2014-09-07的一篇task2作文，请确认');">取消预约</a>
                </td>
    </tr>
        <tr>
    	<td style="padding-left:5px;" height="25" align="center">
    		<input type="checkbox" class="checkbox" name="check_single" id="check_single" value="12117"/>
    	</td> 
		<td style="padding-left:5px;" height="25" align="center">2014-09-07</td>
        <td style="padding-left:5px;" height="25" align="center">辉辉的小窝7</td>
        <td style="padding-left:5px;" height="25" align="center">task2</td>
        <td style="padding-left:5px;" height="25" align="center">4</td>
        <td style="padding-left:5px;" height="25" align="center">517016718@qq.com-task2-cycle4-Lea</td>
        <td style="padding-left:5px;" height="25" align="center">Lea</td>
        <td style="padding-left:5px;" height="25" align="center">
                   <!--<a href="do/do_function.php?f_name=delete_appointment&editId=12117">取消预约</a>-->
           <a href="javascript:do_function('delete_appointment','12117','index.php?type=appointment_list','即将为您取消2014-09-07的一篇task2作文，请确认');">取消预约</a>
                </td>
    </tr>
        <tr>
    	<td style="padding-left:5px;" height="25" align="center">
    		<input type="checkbox" class="checkbox" name="check_single" id="check_single" value="12119"/>
    	</td> 
		<td style="padding-left:5px;" height="25" align="center">2014-09-07</td>
        <td style="padding-left:5px;" height="25" align="center">apple_forever92</td>
        <td style="padding-left:5px;" height="25" align="center">task2</td>
        <td style="padding-left:5px;" height="25" align="center">2</td>
        <td style="padding-left:5px;" height="25" align="center">fxyxshxxb@163.com-task2-cycle2-Lea</td>
        <td style="padding-left:5px;" height="25" align="center">Lea</td>
        <td style="padding-left:5px;" height="25" align="center">
           已取消
                </td>
    </tr>
        <tr>
    	<td style="padding-left:5px;" height="25" align="center">
    		<input type="checkbox" class="checkbox" name="check_single" id="check_single" value="12116"/>
    	</td> 
		<td style="padding-left:5px;" height="25" align="center">2014-09-07</td>
        <td style="padding-left:5px;" height="25" align="center">viviliqian</td>
        <td style="padding-left:5px;" height="25" align="center">task2</td>
        <td style="padding-left:5px;" height="25" align="center">5</td>
        <td style="padding-left:5px;" height="25" align="center">NAU_tbec@163.com-task2-cycle5-Lea</td>
        <td style="padding-left:5px;" height="25" align="center">Lea</td>
        <td style="padding-left:5px;" height="25" align="center">
           已取消
                </td>
    </tr>
         <!--<tr>
		<td style="padding-left:5px;" height="25" align="center">2012/7/11</td>
        <td style="padding-left:5px;" height="25" align="center">task1</td>
        <td style="padding-left:5px;" height="25" align="center">1</td>
        <td style="padding-left:5px;" height="25" align="center">teacherA</td>
        <td style="padding-left:5px;" height="25" align="center">已取消</td>
    </tr>
     <tr>
		<td style="padding-left:5px;" height="25" align="center">2012/7/11</td>
        <td style="padding-left:5px;" height="25" align="center">task1</td>
        <td style="padding-left:5px;" height="25" align="center">1</td>
        <td style="padding-left:5px;" height="25" align="center">teacherA</td>
        <td style="padding-left:5px;" height="25" align="center">已取消</td>
    </tr>
      <tr>
		<td style="padding-left:5px;" height="25" align="center">2012/7/11</td>
        <td style="padding-left:5px;" height="25" align="center">task1</td>
        <td style="padding-left:5px;" height="25" align="center">1</td>
        <td style="padding-left:5px;" height="25" align="center">teacherA</td>
        <td style="padding-left:5px;" height="25" align="center"><a href="index.php?type=appointment_edit&backType=appointment_list&editId=1">取消预约</a></td>
    </tr>
     <tr>
		<td style="padding-left:5px;" height="25" align="center">2012/7/11</td>
        <td style="padding-left:5px;" height="25" align="center">task1</td>
        <td style="padding-left:5px;" height="25" align="center">1</td>
        <td style="padding-left:5px;" height="25" align="center">teacherA</td>
        <td style="padding-left:5px;" height="25" align="center"><a href="index.php?type=appointment_edit&backType=appointment_list&editId=1">取消预约</a></td>
    </tr>
     <tr>
		<td style="padding-left:5px;" height="25" align="center">2012/7/11</td>
        <td style="padding-left:5px;" height="25" align="center">task1</td>
        <td style="padding-left:5px;" height="25" align="center">1</td>
        <td style="padding-left:5px;" height="25" align="center">teacherA</td>
        <td style="padding-left:5px;" height="25" align="center"><a href="index.php?type=appointment_edit&backType=appointment_list&editId=1">取消预约</a></td>
    </tr>-->
   
</table>
<table width="100%" border="0" cellspacing="0">
	<tr colspan="4" height="10"></tr>
    <tr>
      <td width="220" height="30" align='left'></td>
      <td width="" class="title" colspan='3'><a href='#'>首頁</a>&nbsp;&nbsp;&nbsp;&nbsp; <a href='#'>上一頁</a>&nbsp;&nbsp;&nbsp;&nbsp; <a href='#'>下一頁</a>&nbsp;&nbsp;&nbsp;&nbsp; <a href='#'>末頁</a>&nbsp;&nbsp;&nbsp;&nbsp; <select onchange="javascript:submitback('index.php?type=appointment_list&page='+ this.options[this.selectedIndex].value)">
	<option value=1 selected>1</option>
</select> &nbsp;&nbsp;總共有&nbsp;1&nbsp;頁 &nbsp;&nbsp;每頁100&nbsp;條記錄</td>
	</tr>
	<tr colspan="4" height="10"></tr>
</table>
</div>
					 </div>
				 </div>
		 	   </td>
			</tr>
  		</table>
 	</div>
</body>

</html>

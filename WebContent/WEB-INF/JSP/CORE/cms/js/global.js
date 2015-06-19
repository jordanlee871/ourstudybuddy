//cms functions
function trim(str){ //删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

//复制到剪切板
function copytoclip(obj){  
	//var objtest=obj.innerText;  
    window.clipboardData.setData('text', obj);  
      //alert("复制成功");  
} 

/**
 *	批量删除预约
 **/
function deleteAppoint(){
	var checkList = $("[name='check']:checked");
	var deleteStr = "";
	for(var i=0;i<checkList.length;i++){
		deleteStr = deleteStr + $(checkList[i]).val() + ",";
	}
	deleteStr = deleteStr.substr(0,deleteStr.length-1);
	$("#mask").show();
	$.ajax({
		url:'do/do_function.php',
		data:{
			f_name:'delete_appoint_s',
			deleteStr:deleteStr
		},
		success:function(rep){
			$("#mask").hide();
			alert(rep);
		}
	});
}

function submitform(actionurl,returl,checkjs){
	var options  = {
            url:actionurl,
            type:'post',
			beforeSubmit:function(){
				if($("input[name=table]").length > 0){
					if($("input[name=table]").val() == 't_appointment'){
						$("input[name=student_wangwang]").val( trim($("input[name=student_wangwang]").val() ) );
						if(confirm(
							"即将为"+$("input[name=student_wangwang]").val()
							+"预约"+$(".teacher_name").html()
							+"老师在"+$("input[name=date]").val()
							+"的"+$('input:radio[name="type"]:checked').val()
							+"，请确认") ){
								//$("input[name=publish]").hide();
								$("#mask").show();
								return true;
						}else{
							return false
						}
						//即将为[学生WW]预约[teacher] 老师在 YYYY-MM-DD 的[task1/task2/other]，请确认
					}
				}
				if($("input[name=qq]").length > 0){	//qq格式检查
					$("input[name=qq]").val( trim($("input[name=qq]").val() ) );
					var qq = $("input[name=qq]").val();
					if(qq.len > 0){
						var reg = /^[1-9][0-9]{4,10}$/;
						if(reg.test(qq) == false && qq!=''){
							alert("QQ格式为5至11位数，请重新填写");
							return false;
						}
					}
				}
				if($("input[name=email]").length > 0){	//邮箱格式检查
					$("input[name=email]").val( trim($("input[name=email]").val() ) );
					var email = $("input[name=email]").val();
					var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*.\w+([-.]\w+)*$/;
					if(reg.test(email) == false){
						alert("email格式出错，请重新填写");
						return false;
					}
				}
				if($("input[name=phone]").length > 0){	//电话格式检查
					$("input[name=phone]").val( trim($("input[name=phone]").val() ) );
					var phone = $("input[name=phone]").val();
					var reg = eval("("+"/^(" +
						"(\\d{11})" + //11位手机号码	
						/*"|^(" +	
						"(\\d{7,8})|" +			//7-8位号码
						"(\\d{3,4})-(\\d{7,8})|" + 	//区号+7-8位号码
						"(\\d{3,4})-(\\d{7,8})-(\\d{1,4})|" + 	//区号+7-8位号码+分机号码
						"(\\d{7,8})-(\\d{1,4})" + 	//7-8位号码+分机号码
						")$" +*/
						")$/"+")");
					//alert(eval("(/\d/)"));
					if(reg.test(phone) == false){
						alert("电话号码格式出错，请重新填写");
						return false;
					}
				}
				//alert("submit");
				//return false;
				$("#mask").show();
			},
            success:function(data){
				$("input[name=publish]").show();
				if($("input[name=table]").length > 0){
					if($("input[name=table]").val()=='com_member' && $("input[name=editId]").val()==''){
						var obj = eval("("+data+")");
						if(obj.success){
							alert("注册成功");
							window.location.href = obj.href;
							//returl = 'index.php?type=student_edit&backType=student_list&editId='+$("input[name=editId]").val();
						}else{
							alert(obj.msg);
						}
						$("#mask").hide();
						return;
					}else if($("input[name=table]").val() == 't_appointment'){
						if( !-[1,]){
							copytoclip(data);
						}
						var type = $(":radio:checked").val();
						
						//alert(data.indexOf("恭喜你"));
						if(data.indexOf("恭喜你") > -1){
							//$.ajax({
							//	url:'do/do_function.php?type='+type+'&f_name=get_link',
							//	success:function(link_to){
							//		$("#alert_box .text").html(data+"<br><br><a href='"
							//			+link_to+"' target='_blank'>"+link_to+"</a>");
							//		$("#alert_box").show();
							//	}
							//});
							$("#alert_box .text").html(data);
							$("#alert_box").show();
							$("#mask").hide();
							return;
						}
					}
				}
				$("#mask").hide();
				alert(data);
				window.location.href=returl;
            },
            error:function(xhr)
            {
                alert(xhr.responseText);
            }
        };
    $('#viewLinkForm').ajaxForm(options);  
}

function submitback(returl){
	window.location.href=returl;
}

function showFishbox(url,title){
	$("#fishboxTitle").html(title);
	$("#mask").show().css("opacity",0.7).css("height",$("#Main").get(0).offsetHeight+"px");
	$("#fishbox").show('slow',function(){});
	$("#fishboxContent").load(url);
}
function closeFishbox(){
	$("#fishbox").hide('slow',function(){$("#mask").hide();});		
}
	
function common_del(table,id,returnUrl){
	if(confirm("確定刪除？")){
		$.ajax({
			url:'do/do_ComonDel.php?delid='+id+'&tbl='+table,
			success:function(data){
				alert(data);
				location.href=returnUrl;
			}
		});
	}
}

/**
 * 执行异步操作
 * @param f_name 异步操作的方法名，详细查看/teach3/cms/sitemap/do/do_function.php中的方法
 * @param id 要传过去的id，接受的参数名将会是id
 * @param returnUrl 执行成功后，跳转页面，空字符串不跳转
 * @param alertMsg 执行前提示内容，无则不提示
 */
function do_function(f_name,id,returnUrl,alertMsg){
	if(alertMsg != '' && alertMsg!=undefined && alertMsg!='undefined'){
		if(confirm(alertMsg)){
			$.ajax({
				url:'do/do_function.php?do_id='+id+'&f_name='+f_name,
				success:function(data){
					alert(data);
					if(returnUrl != ""){
						location.href=returnUrl;
					}
				}
			});
		}
	}else{
		$.ajax({
			url:'do/do_function.php?do_id='+id+'&f_name='+f_name,
			success:function(data){
				alert(data);
				if(returnUrl != ""){
					location.href=returnUrl;
				}
			}
		});
	}
}

function editFunction(doUrl){
	location.href = doUrl;
}

function get_type_amount(student_id,el){
	var showValue = '';
	$.ajax({
		url:'do/get_data.php?student_id='+student_id,
		success:function(data){
			var obj = eval("("+data+")");
			if(el.value == 'task1'){
				showValue = obj.amount_task1;	
			}else if(el.value == 'task2'){
				showValue = obj.amount_task2;	
			}else if(el.value == 'other'){
				showValue = obj.amount_other;	
			}
			if(showValue=='' || showValue==null)
				showValue =0;
			$(".amount").html(showValue);
			//alert(showValue);
		}
	});
}


//project funtions
function checkEmail(parameter)
{
	//var temp =document.getElementById(parameter); 	
	var myreg = /^([a-zA-Z0-9]+[\-|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[\-|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if(parameter.value!=""){
		if(!myreg.test(parameter.value)){
		alert('電郵格式錯誤!');	
		parameter.value="";
		parameter.focus();
		return false;
		}else{
			return true;
		}
	}
}

function checkpwd(){
	if($("#newpwd").val()==''){
		alert('新密碼不能為空.');
		$("#newpwd").focus();
		return false;
	}
	if($("#conpwd").val()==''){
		alert('確認密碼不能為空.');
		$("#conpwd").focus();
		return false;
	}
	if($("#conpwd").val()!=$("#newpwd").val()){
		alert('兩次輸入密碼不相等.');
		$("#newpwd").val('');
		$("#conpwd").val('');
		$("#newpwd").focus();
		return false;
	}
	return true;
}


//-----------------teacher----------------------------


//-----------------teacher----------------------------

//-----------------assistant----------------------------
function appointTeacher(returnUrl){
	
	var options  = {
            url:'do/do_function.php',
            type:'post',
			beforeSubmit:function(){},
            success:function(data){
				alert(data);
				window.location.href=returnUrl;
            },
            error:function(xhr)
            {
                alert(xhr.responseText);
            }
      };
    $('#viewLinkForm').ajaxSubmit(options); 
}

$(document).ready(function(){
	$(".change_input").change(function(e){
		var el = $(e.target);
		var el_hidden = $("#hide_"+el.attr("id"));
		
		/*if(parseInt(el.val()) < parseInt(el_hidden.val()) ){//不能改少
			alert("修改限制时不能少于原来的数");
			el.val(el_hidden.val());	//还原
			return;
		}*/
		if(el.val() != el_hidden.val()){
			el.css("background-color","yellow");
		}else{
			el.css("background-color","#ffffff");
		}
		//alert(el.attr("id"));
	});
	
	$(".do_appointment_a").click(function(e){
		var el = $(e.target);
	});
	
	$(".excel_a").click(function(e){	
		var url_param = "";
		
		var start_date = $("[name=start_date]").val();
		if(start_date!='' && start_date!=undefined){
			url_param = url_param + "&start_date=" + start_date;
		}
		var end_date = $("[name=end_date]").val();
		if(end_date!='' && end_date!=undefined){
			url_param = url_param + "&end_date=" + end_date;
		}
		var app_start_date = $("[name=app_start_date]").val();
		if(app_start_date!='' && app_start_date!=undefined){
			url_param = url_param + "&app_start_date=" + app_start_date;
		}
		var app_end_date = $("[name=app_end_date]").val();
		if(app_end_date!='' && app_end_date!=undefined){
			url_param = url_param + "&app_end_date=" + app_end_date;
		}
		var type = $("[name=type]").val();
		if(type!='' && type!=undefined){
			url_param = url_param + "&type=" + type;
		}
		var cycle = $("[name=cycle]").val();
		if(cycle!='' && cycle!=undefined){
			url_param = url_param + "&cycle=" + cycle;
		}
		var teacher_name_en = $("[name=teacher_name_en]").val();
		if(teacher_name_en!='' && teacher_name_en!=undefined){
			url_param = url_param + "&teacher_name_en=" + teacher_name_en;
		}
		window.open("../export_excel.php?ew=1"+url_param,'newwindow');
	});
	
	$(".amount_change").change(function(e){
		var el = $(e.target);
		var pre = $(".amount_change_pre"+el.attr("alt")).val() ;
		$(".amount_change_next"+el.attr("alt")).val(
		Number($(".amount_change_pre"+el.attr("alt")).val()) + Number(el.val()) );
	});
	
	$("#alert_box .close_btn").click(function(){
		$("#alert_box .text").html("");
		$("#alert_box").hide();
	});
	
	$("#alert_box").hide();
	
	$(".choose_all").click(function(e){
		var el = $(e.target);
		for(var i=0;i<$("[name=check]").length;i++){
			$($("[name=check]")[i]).attr("checked",el.attr("checked") );
		}
	});

	//左侧菜单搜索框，搜索事件
	jQuery("#fuzzy_search_box").keyup(function (event) {
	    keycode = event.which;
	    if (keycode == 13) { //按下回车键，触发搜索
	        jQuery("#fuzzy_search_box").blur();
	        location.href="index.php?type=fuzzy_student_edit&keyword=" + jQuery("#fuzzy_search_box").val();
	    }
 	});
 	
 	jQuery("#sendSmsBtn").click(function(){
 		var check_value = "";
 		var check_arr = document.getElementsByName("check_single");
 		for(var i=0;i<check_arr.length;i++){
 			if(jQuery(check_arr[i]).attr("checked")){
 				check_value = check_value + "," + check_arr[i].value;
 			}
 		}
 		if(check_value==''){
 			alert("请选择要发回的学生");
 			return;
 		}
 		do_function("send_sms_return",check_value,"", "是否发送短信到被选中学生？");
 	});
 	
 	jQuery("#check_all").click(function(){
 		if(jQuery("#check_all").attr("checked")){
 			jQuery("[name=check_single]").attr("checked", true);
 		}else{
 			jQuery("[name=check_single]").attr("checked", false);
 		}
 	});
});
//-----------------assistant----------------------------

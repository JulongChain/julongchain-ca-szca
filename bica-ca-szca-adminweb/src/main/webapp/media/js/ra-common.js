/*
 *
 * Copyright © 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
 * Copyright © 2018  SZCA. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

var baseurl="";

function initRaJs(url){
	baseurl=url;
}

function resetform(){
$("#formId")[0].reset();
}

function changeProfile(par){
  	window.location.href=baseurl+"/ra/addendentity.html?profileid="+$(par).val();
}

function goListPage(){
	window.location.href=baseurl+"/ra/listEndentity.html"; 
	}
	 
function  requiredCheck(){
    var msg=""
   	 $(".bclass").each(function(index) { 
   		 var prevVal=$(this).prev().val();
   		  if(isEmpty(prevVal)){
   			  msg=msg+","+($(this).parent().prev().html());
   			  $(this).prev().closest('.control-group').removeClass('success').addClass('error');
     		    }else{
    		    	$(this).prev().closest('.control-group').removeClass('error')
    	 		  }
   	     });
   	 
          if(msg!=""){
   	      alertFn("必要信息未填写完成："+msg.substring(1,msg.length)+"字段未填写！");
   		   return false;
   	       }else{
   	    	 return true;
   	      }
}



function fillCAField(par,par2){	   
	   var certprofid = par; //值
	   var jsonStr=par2;
	   var relObj=JSON.parse(jsonStr);
	   var optionHtml = new Array();
	    $("#selectca").html("");//清空之前的记录
        for(var i = 0; i < relObj.length; i++){
       if(certprofid==relObj[i].certificateprofileId){
     	  var cadataObj =relObj[i].cadata;
           for(var j = 0; j < cadataObj.length; j++){
         	   optionHtml.push("<option value="+cadataObj[j].caid+">"+cadataObj[j].caname+"</option>");
            }
           $("#selectca").append(optionHtml.join(""));
           return ; 
           }
		}  	   
	}


function fillCAFieldAndSelected(par,par2,par3){	   
	   var certprofid = par; //值
	   var jsonStr=par2;
	   var relObj=JSON.parse(jsonStr);
	   var optionHtml = new Array();
	    $("#selectca").html("");//清空之前的记录
    for(var i = 0; i < relObj.length; i++){
       if(certprofid==relObj[i].certificateprofileId){
     	  var cadataObj =relObj[i].cadata;
           for(var j = 0; j < cadataObj.length; j++){
               if(par3==cadataObj[j].caid){
            	   optionHtml.push("<option value="+cadataObj[j].caid+" selected='selected' >"+cadataObj[j].caname+"</option>");
                    }else{
                	   optionHtml.push("<option value="+cadataObj[j].caid+">"+cadataObj[j].caname+"</option>");
               }
            }
           $("#selectca").append(optionHtml.join(""));
           return ; 
        }
		}  	   
	}

function  isEmpty(arg){
	return (arg==null||arg==undefined||arg=="")  ;
}



function saveData(){
	if(requiredCheck()){
    $("#endentityprofileid").val($("#selectendentityprofile").val());
    var textfieldusername=$("#textfieldusername").val();
     var selectusername=$("#selectusername").val();
    if(!isEmpty(textfieldusername)){
    	 $("#username").val(textfieldusername);
      }
  
    if(!isEmpty(selectusername)){
   	 $("#username").val(selectusername);
     }
   
    var textfieldpassword=$("#textfieldpassword").val();
    var selectpassword=$("#selectpassword").val();


    if(!isEmpty(textfieldpassword)){
   	 $("#password").val(textfieldpassword);
     }
   
   if(!isEmpty(selectpassword)){
  	 $("#password").val(selectpassword);
    }
   if($("#textfieldconfirmpassword").val()!=$("#password").val()){
	  	  alertFn("两次密码不一致") ;
	 }
  
   var radiomaxfailedlogins=$("#radiomaxfailedlogins").val();
   var maxFailedLogins="";
   if('unlimited'==radiomaxfailedlogins){
   	maxFailedLogins=-1;
     }else{
   	maxFailedLogins=$("#textfieldmaxfailedlogins").val();  
    }
    if(maxFailedLogins!=""){
   	 $("#maxLoginAttempts").val(maxFailedLogins);
   	 $("#remainingLoginAttempts").val(maxFailedLogins);
     }
  
   
	 var textfieldemail=$("#textfieldemail").val();
     var textfieldemaildomain=$("#textfieldemaildomain").val();
     if(!isEmpty(textfieldusername)&&!isEmpty(textfieldemaildomain)){
      $("#subjectEmail").val(textfieldemail+"@"+textfieldemaildomain);
       }
     var selectemaildomain=$("#selectemaildomain").val();
     
     if(!isEmpty(textfieldusername)&&!isEmpty(selectemaildomain)){
         $("#subjectEmail").val(textfieldemail+"@"+selectemaildomain);
       }

     // var selectemaildomain=$("#").val();\
     if($("#checkboxkeyrecoverable").is(':checked')){
    	 $("#keyrecoverable").val("true");
         }else{
        $("#keyrecoverable").val("false")
        }
    
     if($("#checkboxcleartextpassword").is(':checked')){
    	 $("#cleartextpwd").val("true");
         }else{
         $("#cleartextpwd").val("false")
        }
    
     $("#caid").val($("#selectca").val());
     $("#tokentype").val($("#selecttoken").val());

    $("#certificateprofileid").val($("#selectcertificateprofile").val());
    showMask();
  	$("#formId").ajaxSubmit({
	    type: 'post',
	    url: baseurl+"/ra/saveEndentity.html",
	    dataType: 'json',
	    success: function(data){
	      	if(data.success){
	      		confirmFn("操作成功！返回列表？",function(){goListPage()});
		    	  }else {
		    	alertFn(data.msg);
		     	}
			hideMask();
	    },
	    error: function(XmlHttpRequest, textStatus, errorThrown){
	    	alertFn("网络或者服务器繁忙，请稍后重试！");
			hideMask();
	    }
	});
	}
}






function changeUserData(){
	if(requiredCheck()){
	var textfieldusername=$("#textfieldusername").val();
    var selectusername=$("#selectusername").val();
    if(!isEmpty(textfieldusername)){
    	 $("#username").val(textfieldusername);
      }
    
    if(!isEmpty(selectusername)){
   	 $("#username").val(selectusername);
     }
     
    var textfieldpassword=$("#textfieldpassword").val();
    var selectpassword=$("#selectpassword").val();


    if(!isEmpty(textfieldpassword)){
   	 $("#password").val(textfieldpassword);
     }
   
   if(!isEmpty(selectpassword)){
  	 $("#password").val(selectpassword);
    }
   if($("#textfieldconfirmpassword").val()!=$("#password").val()){
	  	  alertFn("两次密码不一致") ;
	 }
   var radiomaxfailedlogins=$("#radiomaxfailedlogins").val();
    var maxFailedLogins="";
    if('unlimited'==radiomaxfailedlogins){
    	maxFailedLogins=-1;
      }else{
    	maxFailedLogins=$("#textfieldmaxfailedlogins").val();  
     }
     if(maxFailedLogins!=""){
    	 $("#maxLoginAttempts").val(maxFailedLogins);
        }
   
	 var textfieldemail=$("#textfieldemail").val();
     var textfieldemaildomain=$("#textfieldemaildomain").val();
     if(!isEmpty(textfieldusername)&&!isEmpty(textfieldemaildomain)){
      $("#subjectEmail").val(textfieldemail+"@"+textfieldemaildomain);
       }
     var selectemaildomain=$("#selectemaildomain").val();
     
     if(!isEmpty(textfieldusername)&&!isEmpty(selectemaildomain)){
         $("#subjectEmail").val(textfieldemail+"@"+selectemaildomain);
       }

     // var selectemaildomain=$("#").val();\
     if($("#checkboxkeyrecoverable").is(':checked')){
    	 $("#keyrecoverable").val("true");
         }else{
        $("#keyrecoverable").val("false")
        }
    
     if($("#checkboxcleartextpassword").is(':checked')){
    	 $("#cleartextpwd").val("true");
         }else{
         $("#cleartextpwd").val("false")
        }
    
     
     $("#caid").val($("#selectca").val());
     $("#tokentype").val($("#selecttoken").val());

     $("#certificateprofileid").val($("#selectcertificateprofile").val());
	     showMask();
   	 $("#formId").ajaxSubmit({
		    type: 'post',
		    url: baseurl+"/ra/changeUserData.html",
		    dataType: 'json',
		    success: function(data){
		      	if(data.success){
		      		confirmFn("操作成功！返回列表？",function(){goListPage()});
		    	  }else {
		    	 	 alertFn(data.msg);
		     	}
				hideMask();
		    },
		    error: function(XmlHttpRequest, textStatus, errorThrown){
				hideMask();
		    }
		});
}
	}

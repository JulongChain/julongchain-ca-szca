
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

// 加载层
function showMask(){
	var windowHeight = $(window).height();
	var windowWidth = $(window).width();
	$('body').append('<div id="mask" class="mask"></div>');
	$('body').append('<img class="imgLoader" style="position: fixed;" src="../media/image/loading-1.gif" />');
    $("#mask").css("height",$(document).height());     
    $("#mask").css("width",$(document).width());
    $(".imgLoader").css("top",windowHeight/2-37);
    $(".imgLoader").css("left",windowWidth/2-12);   
    $("#mask").show();     
}  
 //隐藏遮罩层  
function hideMask(){
	$('#mask').remove(); 
    $('.imgLoader').remove();         
}
// 弹出层
function alertFn(msg) {
    var windowHeight = $(window).height();
    var windowWidth = $(window).width();
    var str = '';
    str = '<a href="javascript:void(0);" class="layui-layer-btn0" onclick="checkFn()">确定</a>';
    $('body').append('<div id="mask" class="mask"></div>');
    $('body').append('<div class="layui-layer-dialog"><div class="layui-layer-title">信息</div><div class="layui-layer-content">' + msg+ '</div><div class="layui-layer-btn">' + str +'</div></div>');
    $("#mask").css("height",$(document).height());     
    $("#mask").css("width",$(document).width());
    $(".layui-layer-dialog").css("top",windowHeight/2-160);
    $(".layui-layer-dialog").css("left",windowWidth/2-240);  
    $("#mask").show(); 
} 
function confirmFn(msg,cb) {
    var windowHeight = $(window).height();
    var windowWidth = $(window).width();
    var str = '';
    str = '<a href="javascript:void(0);" class="layui-layer-btn0" onclick="checkFn(' + cb + ')">确定</a><a href="javascript:void(0);" onclick="cancelFn(' + cb + ')" class="layui-layer-btn1">取消</a>';
    $('body').append('<div id="mask" class="mask"></div>');
    $('body').append('<div class="layui-layer-dialog"><div class="layui-layer-title">信息</div><div class="layui-layer-content">' + msg+ '</div><div class="layui-layer-btn">' + str +'</div></div>');
    $("#mask").css("height",$(document).height());     
    $("#mask").css("width",$(document).width());
    $(".layui-layer-dialog").css("top",windowHeight/2-160);
    $(".layui-layer-dialog").css("left",windowWidth/2-240);  
    $("#mask").show(); 
}  
function checkFn(cb) {
    $('#mask').remove(); 
    $('.layui-layer-dialog').remove(); 
    if (typeof cb == 'function') {
        cb();
    }
}
function cancelFn(cb) {
    $('#mask').remove(); 
    $('.layui-layer-dialog').remove(); 
    // if (typeof cb == 'function') {
    //     cb();
    // }
}
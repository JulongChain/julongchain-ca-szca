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

/**
 * This jQuery plugin displays pagination links inside the selected elements.
 *
 * @author Gabriel Birke (birke *at* d-scribe *dot* de)
 * @version 1.2
 * @param {int} maxentries Number of entries to paginate
 * @param {Object} opts Several options (see README for documentation)
 * @return {Object} jQuery Object
 */
function onKeyPressGotoPage(event,pageSize)
{ 
    if(event.keyCode == "13")  
    {
    	gotoPageFunctions(pageSize);
    }
}

function gotoPageFunctions(pageSize){
		var url = $("#hiddenUrlForPager").val();
		var val = $("#gotoPageNumber").val();
		var reg1 =  /^\d+$/;
		if(!reg1.test(val)){
			val = 1;
		}
		var pageNo = val-1;
		url = url + "&pageSize="+pageSize+"&currPageNo="+pageNo;
		//$('#gotopageform').action = url;
		//$('#gotopageform').submit();
		window.location.href = url;
	}

	function changePageSize(o){	
		var url = $("#hiddenUrlForPager").val();	
		url = url + "&pageSize="+o.value+"&currPageNo=0";
		window.location.href = url;
	}

jQuery.fn.pagination = function(maxentries, opts){
	opts = jQuery.extend({
		pagerList:[10,20,30],
		items_per_page:10,
		num_display_entries:4,
		current_page:0,
		num_edge_entries:1,
		link_to:"#",
		prev_text:"&lt; 上一页",
		next_text:"下一页 &gt;",
		ellipse_text:"...",
		prev_show_always:true,
		next_show_always:true,
		callback:function(){return false;}
	},opts||{});
	
	return this.each(function() {
		/**
		 * Calculate the maximum number of pages
		 */
		function numPages() {
			return Math.ceil(maxentries/opts.items_per_page);
		}
		
		/**
		 * Calculate start and end point of pagination links depending on 
		 * current_page and num_display_entries.
		 * @return {Array}
		 */
		function getInterval()  {
			var ne_half = Math.ceil(opts.num_display_entries/2);
			var np = numPages();
			var upper_limit = np-opts.num_display_entries;
			var start = current_page>ne_half?Math.max(Math.min(current_page-ne_half, upper_limit), 0):0;
			var end = current_page>ne_half?Math.min(current_page+ne_half, np):Math.min(opts.num_display_entries, np);
			return [start,end];
		}
		
		/**
		 * This is the event handling function for the pagination links. 
		 * @param {int} page_id The new page number
		 */
		function pageSelected(page_id, evt){
			current_page = page_id;
			drawLinks();
			var continuePropagation = opts.callback(page_id, panel);
			if (!continuePropagation) {
				if (evt.stopPropagation) {
					evt.stopPropagation();
				}
				else {
					evt.cancelBubble = true;
				}
			}
			return continuePropagation;
		}			
    	
		/**
		 * This function inserts the pagination links into the container element
		 */
		function drawLinks() {       
			panel.empty();	
			var interval = getInterval();
			var np = numPages();
			// This helper function returns a handler function that calls pageSelected with the right page_id
			var getClickHandler = function(page_id) {
				return function(evt){ return pageSelected(page_id,evt); };
			};
			// Helper function for generating a single link (or a span tag if it's the current page)
			var appendItem = function(page_id, appendopts){
				page_id = page_id<0?0:(page_id<np?page_id:np-1); // Normalize page id to sane value
				appendopts = jQuery.extend({text:page_id+1, classes:""}, appendopts||{});
				var lnk;
				if(page_id == current_page){
					lnk = jQuery("<span class='current'>"+(appendopts.text)+"</span>");
				}
				else
				{
					lnk = jQuery("<a>"+(appendopts.text)+"</a>")
						.bind("click", getClickHandler(page_id))
						//.attr('href', opts.link_to.replace(/__id__/,page_id)
						.attr('href', opts.link_to+"&pageSize="+opts.items_per_page+"&currPageNo="+page_id);			
				}
				if(appendopts.classes){lnk.addClass(appendopts.classes);}
				panel.append(lnk);
			};
			// Generate "Previous"-Link
			if(opts.prev_text && (current_page > 0 || opts.prev_show_always)){
				appendItem(current_page-1,{text:opts.prev_text, classes:"prev"});
			}
			// Generate starting points
			if (interval[0] > 0 && opts.num_edge_entries > 0)
			{
				var end = Math.min(opts.num_edge_entries, interval[0]);
				for(var i=0; i<end; i++) {
					appendItem(i);
				}
				if(opts.num_edge_entries < interval[0] && opts.ellipse_text)
				{
					jQuery("<span>"+opts.ellipse_text+"</span>").appendTo(panel);
				}
			}
			// Generate interval links
			for(var i=interval[0]; i<interval[1]; i++) {
				appendItem(i);
			}
			// Generate ending points
			if (interval[1] < np && opts.num_edge_entries > 0)
			{
				if(np-opts.num_edge_entries > interval[1]&& opts.ellipse_text)
				{
					jQuery("<span>"+opts.ellipse_text+"</span>").appendTo(panel);
				}
				var begin = Math.max(np-opts.num_edge_entries, interval[1]);
				for(var i=begin; i<np; i++) {
					appendItem(i);
				}
				
			}
			// Generate "Next"-Link
			if(opts.next_text && (current_page < np-1 || opts.next_show_always)){
				appendItem(current_page+1,{text:opts.next_text, classes:"next"});
			}
			
			
			
			/*新增每页显示begin*/
			var selectOption ="";
			selectOption += "<form onsubmit=\"return false\">";
	
			selectOption += "<span class=\"pageBox mr10 vam\">";
			/*新增每页显示end*/
			
			/*新增跳至 begin*/
			var gotoUrl = "</span>跳转到第<input onkeypress=\"onKeyPressGotoPage(event,"+opts.items_per_page+")\" id=\"gotoPageNumber\" style='width:40px;' name=\"gotoPageNumber\" value=\""+(opts.current_page+1)+"\">页";
			gotoUrl+="<a class=\"vam ml5 pageBtn radius ml10\" onclick=\"gotoPageFunctions("+opts.items_per_page+")\" >确定</a>";
			var hiddenUrl = "<input type=\"hidden\" id=\"hiddenUrlForPager\" value=\""+opts.link_to+"\" ></form>";

			/*新增跳至 end*/
			
			var panelHtml = selectOption+panel.html()+gotoUrl+hiddenUrl;
			panel.empty();	
			panel.append(panelHtml);
		}
		
		// Extract current_page from options
		var current_page = opts.current_page;
		// Create a sane value for maxentries and items_per_page
		maxentries = (!maxentries || maxentries < 0)?1:maxentries;
		opts.items_per_page = (!opts.items_per_page || opts.items_per_page < 0)?1:opts.items_per_page;
		// Store DOM element for easy access from all inner functions
		var panel = jQuery(this);
		// Attach control functions to the DOM element 
		this.selectPage = function(page_id){ pageSelected(page_id);};
		this.prevPage = function(){ 
			if (current_page > 0) {
				pageSelected(current_page - 1);
				return true;
			}
			else {
				return false;
			}
		};
		this.nextPage = function(){ 
			if(current_page < numPages()-1) {
				pageSelected(current_page+1);
				return true;
			}
			else {
				return false;
			}
		};
		// When all initialisation is done, draw the links
		drawLinks();
        // call callback function
        opts.callback(current_page, this);
	});	
};





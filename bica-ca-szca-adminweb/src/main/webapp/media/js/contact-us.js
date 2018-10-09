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

var ContactUs = function () {

    return {
        //main function to initiate the module
        init: function () {
			var map;
			$(document).ready(function(){
			  map = new GMaps({
				div: '#map',
				lat: -13.004333,
				lng: -38.494333
			  });
			   var marker = map.addMarker({
		            lat: -13.004333,
					lng: -38.494333,
		            title: 'Loop, Inc.',
		            infoWindow: {
		                content: "<b>Loop, Inc.</b> 795 Park Ave, Suite 120<br>San Francisco, CA 94107"
		            }
		        });

			   marker.infoWindow.open(map, marker);
			});
        }
    };

}();
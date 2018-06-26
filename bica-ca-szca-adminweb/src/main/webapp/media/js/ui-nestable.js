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

var UINestable = function () {

    var updateOutput = function (e) {
        var list = e.length ? e : $(e.target),
            output = list.data('output');
        if (window.JSON) {
            output.val(window.JSON.stringify(list.nestable('serialize'))); //, null, 2));
        } else {
            output.val('JSON browser support required for this demo.');
        }
    };


    return {
        //main function to initiate the module
        init: function () {

            // activate Nestable for list 1
            $('#nestable_list_1').nestable({
                group: 1
            })
                .on('change', updateOutput);

            // activate Nestable for list 2
            $('#nestable_list_2').nestable({
                group: 1
            })
                .on('change', updateOutput);

            // output initial serialised data
            updateOutput($('#nestable_list_1').data('output', $('#nestable_list_1_output')));
            updateOutput($('#nestable_list_2').data('output', $('#nestable_list_2_output')));

            $('#nestable_list_menu').on('click', function (e) {
                var target = $(e.target),
                    action = target.data('action');
                if (action === 'expand-all') {
                    $('.dd').nestable('expandAll');
                }
                if (action === 'collapse-all') {
                    $('.dd').nestable('collapseAll');
                }
            });

            $('#nestable_list_3').nestable();

        }

    };

}();
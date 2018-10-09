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

var UITree = function () {

    return {
        //main function to initiate the module
        init: function () {
            // handle collapse/expand for tree_1
            $('#tree_1_collapse').click(function () {
                $('.tree-toggle', $('#tree_1 > li > ul')).addClass("closed");
                $('.branch', $('#tree_1 > li > ul')).removeClass("in");
            });

            $('#tree_1_expand').click(function () {
                $('.tree-toggle', $('#tree_1 > li > ul')).removeClass("closed");
                $('.branch', $('#tree_1 > li > ul')).addClass("in");
            });

            // handle collapse/expand for tree_2
            $('#tree_2_collapse').click(function () {
                $('.tree-toggle', $('#tree_2 > li > ul')).addClass("closed");
                $('.branch', $('#tree_2 > li > ul')).removeClass("in");
            });

            $('#tree_2_expand').click(function () {
                //$('.tree-toggle', $('#tree_2 > li > ul')).removeClass("closed");
                // iterate tree nodes and exppand all nodes
                $('.tree-toggle', $('#tree_2 > li > ul')).each(function () {
                    $(this).click(); //trigger tree node click
                });
                $('.branch', $('#tree_2 > li > ul')).addClass("in");
            });

            //This is a quick example of capturing the select event on tree leaves, not branches
            $("#tree_1").on("nodeselect.tree.data-api", "[data-role=leaf]", function (e) {
                var output = "";

                output += "Node nodeselect event fired:\n";
                output += "Node Type: leaf\n";
                output += "Value: " + ((e.node.value) ? e.node.value : e.node.el.text()) + "\n";
                output += "Parentage: " + e.node.parentage.join("/");

                alert(output);
            });

            //This is a quick example of capturing the select event on tree branches, not leaves
            $("#tree_1").on("nodeselect.tree.data-api", "[role=branch]", function (e) {
                var output = "Node nodeselect event fired:\n"; + "Node Type: branch\n" + "Value: " + ((e.node.value) ? e.node.value : e.node.el.text()) + "\n" + "Parentage: " + e.node.parentage.join("/") + "\n"

                alert(output);
            });

            //Listening for the 'openbranch' event. Look for e.node, which is the actual node the user opens

            $("#tree_1").on("openbranch.tree", "[data-toggle=branch]", function (e) {

                var output = "Node openbranch event fired:\n" + "Node Type: branch\n" + "Value: " + ((e.node.value) ? e.node.value : e.node.el.text()) + "\n" + "Parentage: " + e.node.parentage.join("/") + "\n"

                alert(output);
            });


            //Listening for the 'closebranch' event. Look for e.node, which is the actual node the user closed

            $("#tree_1").on("closebranch.tree", "[data-toggle=branch]", function (e) {

                var output = "Node closebranch event fired:\n" + "Node Type: branch\n" + "Value: " + ((e.node.value) ? e.node.value : e.node.el.text()) + "\n" + "Parentage: " + e.node.parentage.join("/") + "\n"

                alert(output);
            });
        }

    };

}();
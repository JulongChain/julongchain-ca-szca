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

/*jslint unparam: true */
/*global define, window, XDomainRequest */

(function (factory) {
    'use strict';
    if (typeof define === 'function' && define.amd) {
        // Register as an anonymous AMD module:
        define(['jquery'], factory);
    } else {
        // Browser globals:
        factory(window.jQuery);
    }
}(function ($) {
    'use strict';
    if (window.XDomainRequest && !$.support.cors) {
        $.ajaxTransport(function (s) {
            if (s.crossDomain && s.async) {
                if (s.timeout) {
                    s.xdrTimeout = s.timeout;
                    delete s.timeout;
                }
                var xdr;
                return {
                    send: function (headers, completeCallback) {
                        var addParamChar = /\?/.test(s.url) ? '&' : '?';
                        function callback(status, statusText, responses, responseHeaders) {
                            xdr.onload = xdr.onerror = xdr.ontimeout = $.noop;
                            xdr = null;
                            completeCallback(status, statusText, responses, responseHeaders);
                        }
                        xdr = new XDomainRequest();
                        // XDomainRequest only supports GET and POST:
                        if (s.type === 'DELETE') {
                            s.url = s.url + addParamChar + '_method=DELETE';
                            s.type = 'POST';
                        } else if (s.type === 'PUT') {
                            s.url = s.url + addParamChar + '_method=PUT';
                            s.type = 'POST';
                        } else if (s.type === 'PATCH') {
                            s.url = s.url + addParamChar + '_method=PATCH';
                            s.type = 'POST';
                        }
                        xdr.open(s.type, s.url);
                        xdr.onload = function () {
                            callback(
                                200,
                                'OK',
                                {text: xdr.responseText},
                                'Content-Type: ' + xdr.contentType
                            );
                        };
                        xdr.onerror = function () {
                            callback(404, 'Not Found');
                        };
                        if (s.xdrTimeout) {
                            xdr.ontimeout = function () {
                                callback(0, 'timeout');
                            };
                            xdr.timeout = s.xdrTimeout;
                        }
                        xdr.send((s.hasContent && s.data) || null);
                    },
                    abort: function () {
                        if (xdr) {
                            xdr.onerror = $.noop();
                            xdr.abort();
                        }
                    }
                };
            }
        });
    }
}));

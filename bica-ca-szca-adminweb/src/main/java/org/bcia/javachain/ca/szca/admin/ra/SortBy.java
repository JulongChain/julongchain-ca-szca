

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

package org.bcia.javachain.ca.szca.admin.ra;

public class SortBy implements java.io.Serializable {
    private static final long serialVersionUID = -2924038902779549663L;
    // Public constants
      // Constants used by userdata.
    public static final int USERNAME         = 0;
    public static final int PASSWORD         = 1;
    public static final int COMMONNAME       = 2;
    public static final int DNSERIALNUMBER   = 3;  
    public static final int TITLE            = 4;        
    public static final int ORGANIZATIONALUNIT = 5;
    public static final int ORGANIZATION     = 6;
    public static final int LOCALITY         = 7;
    public static final int STATEORPROVINCE  = 8;
    public static final int DOMAINCOMPONENT  = 9;      
    public static final int COUNTRY          = 10;
    public static final int EMAIL            = 11;
    public static final int STATUS           = 12; 
    public static final int TIMECREATED      = 13;     
    public static final int TIMEMODIFIED     = 14;     
    public static final int CA               = 15;    
      // Constants used by logentrydata. 

    public static final int ADMINTYPE        = 1;
    public static final int ADMINDATA        = 2;
    public static final int MODULE           = 4;
    public static final int TIME             = 5;
    public static final int CERTIFICATE      = 6;    
    public static final int EVENT            = 7;     
    public static final int COMMENT          = 8;     
    
    public static final int ACCENDING        = 0;
    public static final int DECENDING        = 1;

    
    /** Creates a new instance of SortBy */
    public SortBy() {
      this.sortby=USERNAME;
      this.sortorder=ACCENDING;
    }
    
    public SortBy(int sortby, int sortorder){
      this.sortby=sortby;   
      this.sortorder=sortorder;
    }
    
    public int getSortBy() {
      return sortby;
    }
    
    public int getSortOrder() {
      return sortorder;
    }
    
    public void setSortBy(int sortby) {
       this.sortby=sortby;      
    }

    public void setSortOrder(int sortorder){
      this.sortorder=sortorder;        
    }
    // Private fields.
    private int sortby;
    private int sortorder;
}

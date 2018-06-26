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
package org.bcia.javachain.ca.szca.admin.crl;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Jacky Luo
 *
 */
@Entity
@Table(name="SYS_CRL_STRATEGY")
public class CrlStrategyData {
	private int caid;
	private String cronExpr;
	private String taskresult;
	private boolean running;
	private long crttime=0;
	private long modtime=0;
	private String crtuser;
	private String moduser;
	
	@Id
	public int getCaid() {
		return caid;
	}
	public void setCaid(int caid) {
		this.caid = caid;
	}
	public String getCronExpr() {
		return cronExpr;
	}
	public void setCronExpr(String cronExpr) {
		this.cronExpr = cronExpr;
	}
	 
	public boolean isRunning() {
		return running;
	}
	public void setRunning(boolean running) {
		this.running = running;
	}
	public long getCrttime() {
		return crttime;
	}
	public void setCrttime(long crttime) {
		this.crttime = crttime;
	}
	public long getModtime() {
		return modtime;
	}
	public void setModtime(long modtime) {
		this.modtime = modtime;
	}
	public String getCrtuser() {
		return crtuser;
	}
	public void setCrtuser(String crtuser) {
		this.crtuser = crtuser;
	}
	public String getTaskresult() {
		return taskresult;
	}
	public void setTaskresult(String taskresult) {
		this.taskresult = taskresult;
	}
	public String getModuser() {
		return moduser;
	}
	public void setModuser(String moduser) {
		this.moduser = moduser;
	}
	
	
}

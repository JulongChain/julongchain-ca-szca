/*
 * Copyright ? 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
 * Copyright ? 2018  SZCA. All Rights Reserved.
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
 */

package org.bcia.javachain.ca.szca.publicweb.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;

@Entity  
@Table(name="endentityapprovaldata")  
public class EndEntityApprovalData {
	public static final int ENDENTITY_STATUS_APPROVAL_FAIL = -1;
	public static final int ENDENTITY_STATUS_WAITING_FOR_APPROVAL = 0;
	public static final int ENDENTITY_STATUS_APPROVAL = 1;
	
	@Id  
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String userName;
	private String cn;
	private String o;
	private String caName;
	private int caId;
	private Long processId;
	private Date createTime;
	private Date approvalTime;
	private String authName;
	private int status;
	private String memo;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAuthName() {
		return authName;
	}
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	
	public Long getProcessId() {
		return processId;
	}
	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Date getApprovalTime() {
		return approvalTime;
	}
	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getO() {
		return o;
	}
	public void setO(String o) {
		this.o = o;
	}
	public String getCaName() {
		return caName;
	}
	public void setCaName(String caName) {
		this.caName = caName;
	}
	public int getCaId() {
		return caId;
	}
	public void setCaId(int caId) {
		this.caId = caId;
	}
	
	public static EndEntityApprovalData getEndEntityApprovalDataByUserName(EntityManager entityManager ,String userName) {
		final Query query = entityManager.createQuery("SELECT a FROM EndEntityApprovalData a WHERE a.userName=:userName Order by createTime desc");
		query.setParameter("userName", userName);
		List<EndEntityApprovalData> list = query.getResultList();
		if(list != null && list.size() > 0) 
			return list.get(0);
		else return null;
	}
	
	public static List<EndEntityApprovalData> getEndEntityApprovalDataListByUserName(EntityManager entityManager ,String userName) {
		final Query query = entityManager.createQuery("SELECT a FROM EndEntityApprovalData a WHERE a.userName=:userName Order by createTime desc");
		query.setParameter("userName", userName);
		List<EndEntityApprovalData> list = query.getResultList();
		if(list != null && list.size() > 0) 
			return list;
		else return null;
	}
}

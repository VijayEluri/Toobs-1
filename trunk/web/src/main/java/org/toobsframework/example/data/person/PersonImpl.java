/*
 * This file is licensed to the Toobs Framework Group under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The Toobs Framework Group licenses this file to You under the Apache 
 * License, Version 2.0 (the "License"); you may not use this file 
 * except in compliance with the License.  You may obtain a copy of the 
 * License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.toobsframework.example.data.person;

import java.util.Date;

public class PersonImpl {

	private String guid;
	private int versionInt;
	private String username;
	private String password;
	private Date createTs;

	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public int getVersionInt() {
		return versionInt;
	}
	public void setVersionInt(int versionInt) {
		this.versionInt = versionInt;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateTs() {
		return createTs;
	}
	public void setCreateTs(Date createTs) {
		this.createTs = createTs;
	}
}

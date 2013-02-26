/*
 * Copyright 2012 Retechcorp.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.cloudlet.web.core.server;

import org.cloudlet.web.core.shared.CorePackage;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = CorePackage.Reference)
@TypeDef(name = CorePackage.CONTENT, typeClass = ResourceType.class)
public class Reference {

  @Id
  private String id;

  @Type(type = CorePackage.CONTENT)
  @Columns(columns = { @Column(name = "sourceType"), @Column(name = "sourceId") })
  private Entry source;

  @Type(type = CorePackage.CONTENT)
  @Columns(columns = { @Column(name = "targetType"), @Column(name = "targetId") })
  private Content target;

  private String path;

  public String getId() {
    return id;
  }

  public String getPath() {
    return path;
  }

  public Entry getSource() {
    return source;
  }

  public Content getTarget() {
    return target;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setSource(Entry source) {
    this.source = source;
  }

  public void setTarget(Content target) {
    this.target = target;
  }

}

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
@Table(name = CorePackage.PREFIX + "Relationship")
@TypeDef(name = "content", typeClass = ResourceType.class)
public class Relationship {

  @Id
  private String id;

  @Type(type = "content")
  @Columns(columns = { @Column(name = "sourceType"), @Column(name = "sourceId") })
  private Resource source;

  @Type(type = "content")
  @Columns(columns = { @Column(name = "targetType"), @Column(name = "targetId") })
  private Resource target;

  private String path;

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the path
   */
  public String getPath() {
    return path;
  }

  /**
   * @return the source
   */
  public Resource getSource() {
    return source;
  }

  /**
   * @return the target
   */
  public Resource getTarget() {
    return target;
  }

  /**
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @param path the path to set
   */
  public void setPath(String path) {
    this.path = path;
  }

  /**
   * @param source the source to set
   */
  public void setSource(Resource source) {
    this.source = source;
  }

  /**
   * @param target the target to set
   */
  public void setTarget(Resource target) {
    this.target = target;
  }

}

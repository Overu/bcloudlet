/*
 * Copyright (c) 1996, 1998, Oracle and/or its affiliates. All rights reserved. ORACLE
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package java.security;

/**
 * This interface represents the abstract notion of a principal, which can be used to represent any
 * entity, such as an individual, a corporation, and a login id.
 * 
 * @see java.security.cert.X509Certificate
 * 
 * @author Li Gong
 */
public interface Principal {

  /**
   * Compares this principal to the specified object. Returns true if the object passed in matches
   * the principal represented by the implementation of this interface.
   * 
   * @param another principal to compare with.
   * 
   * @return true if the principal passed in is the same as that encapsulated by this principal, and
   *         false otherwise.
   */
  @Override
  public boolean equals(Object another);

  /**
   * Returns the name of this principal.
   * 
   * @return the name of this principal.
   */
  public String getName();

  /**
   * Returns a hashcode for this principal.
   * 
   * @return a hashcode for this principal.
   */
  @Override
  public int hashCode();

  /**
   * Returns a string representation of this principal.
   * 
   * @return a string representation of this principal.
   */
  @Override
  public String toString();
}

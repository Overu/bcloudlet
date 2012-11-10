/*
 * The contents of this file are subject to the terms 
 * of the Common Development and Distribution License 
 * (the License).  You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the license at 
 * https://glassfish.dev.java.net/public/CDDLv1.0.html or
 * glassfish/bootstrap/legal/CDDLv1.0.txt.
 * See the License for the specific language governing 
 * permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL 
 * Header Notice in each file and include the License file 
 * at glassfish/bootstrap/legal/CDDLv1.0.txt.  
 * If applicable, add the following below the CDDL Header, 
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information: 
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 */

package javax.persistence;

import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;


/**
 * Expresses a dependency on an {@link EntityManagerFactory}.
 *
 * @since Java Persistence 1.0
 */
@Target({TYPE, METHOD, FIELD})
@Retention(RUNTIME)
public @interface PersistenceUnit {

    /**
     * The name by which the entity manager factory is to be accessed 
     * in the environment referencing context, and is not needed when 
     * dependency injection is used.
     */
    String name() default "";

    /**
     * The name of the persistence unit as defined in the persistence.xml 
     * file. If specified, the persistence unit for the entity manager factory 
     * that is accessible in JNDI must have the same name.
     */
    String unitName() default "";

}

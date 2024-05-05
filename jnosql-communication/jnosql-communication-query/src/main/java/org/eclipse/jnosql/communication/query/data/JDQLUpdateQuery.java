/*
 *  Copyright (c) 2024 Contributors to the Eclipse Foundation
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *  The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 *  and the Apache License v2.0 is available at http://www.opensource.org/licenses/apache2.0.php.
 *  You may elect to redistribute this code under either of these licenses.
 *  Contributors:
 *  Otavio Santana
 */
package org.eclipse.jnosql.communication.query.data;

import org.eclipse.jnosql.communication.query.UpdateItem;
import org.eclipse.jnosql.communication.query.UpdateQuery;
import org.eclipse.jnosql.communication.query.Where;

import java.util.List;
import java.util.Optional;

record JDQLUpdateQuery(String entity, List<UpdateItem> set, Where condition) implements UpdateQuery {

    @Override
    public Optional<Where> where() {
        return Optional.ofNullable(condition);
    }
}

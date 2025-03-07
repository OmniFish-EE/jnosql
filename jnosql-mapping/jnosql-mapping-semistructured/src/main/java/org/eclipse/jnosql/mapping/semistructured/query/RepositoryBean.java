/*
 *  Copyright (c) 2025 Contributors to the Eclipse Foundation
 *   All rights reserved. This program and the accompanying materials
 *   are made available under the terms of the Eclipse Public License v1.0
 *   and Apache License v2.0 which accompanies this distribution.
 *   The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 *   and the Apache License v2.0 is available at http://www.opensource.org/licenses/apache2.0.php.
 *
 *   You may elect to redistribute this code under either of these licenses.
 *
 *   Contributors:
 *
 *   Otavio Santana
 */
package org.eclipse.jnosql.mapping.semistructured.query;

import jakarta.data.repository.DataRepository;
import org.eclipse.jnosql.mapping.DatabaseType;
import org.eclipse.jnosql.mapping.core.Converters;
import org.eclipse.jnosql.mapping.metadata.EntitiesMetadata;
import org.eclipse.jnosql.mapping.semistructured.SemiStructuredTemplate;

import java.lang.reflect.InvocationHandler;

public class RepositoryBean<T extends DataRepository<T, ?>> extends BaseRepositoryBean<T> {

    public RepositoryBean(Class<?> type, String provider, DatabaseType databaseType) {
        super(type, provider, databaseType);
    }

    @Override
    protected Class<? extends SemiStructuredTemplate> getTemplateClass() {
        return SemiStructuredTemplate.class;
    }

    @Override
    protected InvocationHandler createHandler(EntitiesMetadata entities, SemiStructuredTemplate template, Converters converters) {
        return new SemiStructuredRepositoryProxy<>(template, entities, getBeanClass(), converters);
    }
}


/*
 *  Copyright (c) 2023 Contributors to the Eclipse Foundation
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
package org.eclipse.jnosql.mapping.core.repository;


import jakarta.data.Limit;
import jakarta.data.Order;
import jakarta.data.page.PageRequest;
import jakarta.data.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The repository features has support for specific types like PageRequest and Sort,
 * to apply pagination and sorting to your queries dynamically.
 */
public final class SpecialParameters {
    static final SpecialParameters EMPTY = new SpecialParameters(null, null, Collections.emptyList());

    private final PageRequest<?> pageRequest;

    private final List<Sort<?>> sorts;
    private final Limit limit;

    private SpecialParameters(PageRequest<?> pageRequest, Limit limit, List<Sort<?>> sorts) {
        this.pageRequest = pageRequest;
        this.sorts = sorts;
        this.limit = limit;
    }

    /**
     * Returns the PageRequest as optional.
     *
     * @return a {@link PageRequest} or {@link Optional#empty()} when there is not PageRequest instance
     */
    public Optional<PageRequest<?>> PageRequest() {
        return Optional.ofNullable(pageRequest);
    }

    /**
     * Returns the sorts including {@link PageRequest#sorts()} appended
     *
     * @return the sorts as list
     */
    public List<Sort<?>> sorts() {
        return sorts;
    }

    /**
     * Returns true when {@link SpecialParameters#PageRequest()} is empty and
     * {@link SpecialParameters#isSortEmpty()} is true
     *
     * @return when there is no sort and PageRequest
     */
    public boolean isEmpty() {
        return this.sorts.isEmpty() && pageRequest == null && limit == null;
    }

    /**
     * Return true when there is no sorts
     *
     * @return the sort
     */
    public boolean isSortEmpty() {
        return this.sorts.isEmpty();
    }

    /**
     * Returns true if it only has sort and {@link PageRequest} empty
     *
     * @return true if only have {@link PageRequest}
     */
    public boolean hasOnlySort() {
        return pageRequest == null && !sorts.isEmpty();
    }

    /**
     * Returns the Limit instance or {@link Optional#empty()}
     *
     * @return the Limit instance or {@link Optional#empty()}
     */
    public Optional<Limit> limit() {
        return Optional.ofNullable(limit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SpecialParameters that = (SpecialParameters) o;
        return Objects.equals(pageRequest, that.pageRequest) && Objects.equals(sorts, that.sorts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageRequest, sorts);
    }

    @Override
    public String toString() {
        return "SpecialParameters{" +
                "PageRequest=" + pageRequest +
                ", sorts=" + sorts +
                '}';
    }

    static SpecialParameters of(Object[] parameters) {
        List<Sort<?>> sorts = new ArrayList<>();
        PageRequest<?> pageRequest = null;
        Limit limit = null;
        for (Object parameter : parameters) {
            if (parameter instanceof PageRequest<?> pageRequestInstance) {
                pageRequest = pageRequestInstance;
                sorts.addAll(pageRequestInstance.sorts());
            } else if (parameter instanceof Sort<?> sort) {
                sorts.add(sort);
            } else if (parameter instanceof Limit limitInstance) {
                limit = limitInstance;
            } else if(parameter instanceof Iterable<?> iterable) {
                for (Object value : iterable) {
                    if (value instanceof Sort<?> sortValue) {
                        sorts.add(sortValue);
                    }
                }
            } else if(parameter instanceof Order<?> order) {
                order.iterator().forEachRemaining(sorts::add);
            }
        }
        return new SpecialParameters(pageRequest, limit, sorts);
    }
}

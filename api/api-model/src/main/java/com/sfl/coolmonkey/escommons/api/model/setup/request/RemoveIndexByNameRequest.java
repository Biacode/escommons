package com.sfl.coolmonkey.escommons.api.model.setup.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.coolmonkey.escommons.api.model.common.EsCommonsRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arthur Asatryan.
 * Date: 7/18/17
 * Time: 2:03 PM
 */
public class RemoveIndexByNameRequest implements EsCommonsRequest {
    private static final long serialVersionUID = -6332150135725495512L;

    //region Properties
    @JsonProperty("indexName")
    private String indexName;
    //endregion

    //region Constructors
    public RemoveIndexByNameRequest() {
    }

    public RemoveIndexByNameRequest(final String indexName) {
        this.indexName = indexName;
    }
    //endregion

    //region Equals, HashCode and ToString
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RemoveIndexByNameRequest)) {
            return false;
        }
        final RemoveIndexByNameRequest that = (RemoveIndexByNameRequest) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(indexName, that.indexName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(indexName)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("indexName", indexName)
                .toString();
    }
    //endregion

    //region Properties getters and setters
    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(final String indexName) {
        this.indexName = indexName;
    }
    //endregion
}

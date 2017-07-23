package com.biacode.escommons.api.model.setup.response;

import com.biacode.escommons.api.model.common.EsCommonsResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arthur Asatryan.
 * Date: 7/18/17
 * Time: 2:30 PM
 */
public class PrepareIndexResponse implements EsCommonsResponse {
    private static final long serialVersionUID = 4636795338222045504L;

    //region Properties
    @JsonProperty("indexName")
    private String indexName;
    //endregion

    //region Constructors
    public PrepareIndexResponse() {
    }

    public PrepareIndexResponse(final String indexName) {
        this.indexName = indexName;
    }
    //endregion

    //region Equals, HashCode and ToString
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrepareIndexResponse)) {
            return false;
        }
        final PrepareIndexResponse that = (PrepareIndexResponse) o;
        return new EqualsBuilder()
                .append(indexName, that.indexName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(indexName)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
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

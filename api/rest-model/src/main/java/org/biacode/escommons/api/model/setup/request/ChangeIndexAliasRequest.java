package org.biacode.escommons.api.model.setup.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.biacode.escommons.api.model.common.EsCommonsRequest;

/**
 * Created by Arthur Asatryan.
 * Date: 7/18/17
 * Time: 2:03 PM
 */
public class ChangeIndexAliasRequest implements EsCommonsRequest {
    private static final long serialVersionUID = 8441054922724283503L;

    //region Properties
    @JsonProperty("indexName")
    private String indexName;

    @JsonProperty("alias")
    private String alias;
    //endregion

    //region Constructors
    public ChangeIndexAliasRequest() {
    }

    public ChangeIndexAliasRequest(final String indexName, final String alias) {
        this.indexName = indexName;
        this.alias = alias;
    }
    //endregion

    //region Equals, HashCode and ToString
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChangeIndexAliasRequest)) {
            return false;
        }
        final ChangeIndexAliasRequest that = (ChangeIndexAliasRequest) o;
        return new EqualsBuilder()
                .append(indexName, that.indexName)
                .append(alias, that.alias)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(indexName)
                .append(alias)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("indexName", indexName)
                .append("alias", alias)
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(final String alias) {
        this.alias = alias;
    }
    //endregion
}

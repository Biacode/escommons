package com.sfl.coolmonkey.escommons.api.model.setup.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.coolmonkey.escommons.api.model.common.EsCommonsRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 7/18/17
 * Time: 2:27 PM
 */
public class PrepareIndexRequest implements EsCommonsRequest {
    private static final long serialVersionUID = 8785182381548729784L;

    //region Properties
    @JsonProperty("alias")
    private String alias;

    @JsonProperty("types")
    private List<String> types;
    //endregion

    //region Constructors
    public PrepareIndexRequest() {
    }

    public PrepareIndexRequest(final String alias, final List<String> types) {
        this.alias = alias;
        this.types = types;
    }
    //endregion

    //region Equals, HashCode and ToString
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrepareIndexRequest)) {
            return false;
        }
        final PrepareIndexRequest that = (PrepareIndexRequest) o;
        return new EqualsBuilder()
                .append(alias, that.alias)
                .append(types, that.types)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(alias)
                .append(types)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("alias", alias)
                .append("types", types)
                .toString();
    }
    //endregion

    //region Properties getters and setters
    public String getAlias() {
        return alias;
    }

    public void setAlias(final String alias) {
        this.alias = alias;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(final List<String> types) {
        this.types = types;
    }
    //endregion
}

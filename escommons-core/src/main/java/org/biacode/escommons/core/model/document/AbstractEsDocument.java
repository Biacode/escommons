package org.biacode.escommons.core.model.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
public abstract class AbstractEsDocument {

    //region Properties
    @JsonProperty("id")
    private String id;
    //endregion

    //region Constructors
    public AbstractEsDocument() {
        // Default constructor
    }

    public AbstractEsDocument(final String id) {
        this.id = id;
    }
    //endregion

    //region Equals, HashCode and ToString
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractEsDocument)) {
            return false;
        }
        final AbstractEsDocument that = (AbstractEsDocument) o;
        return new EqualsBuilder()
                .append(id, that.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("id", id)
                .toString();
    }
    //endregion

    //region Properties getters and setters
    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }
    //endregion
}

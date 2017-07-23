package com.biacode.escommons.core.model.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
public abstract class AbstractEsDocument implements Serializable {
    private static final long serialVersionUID = 3278502006044953323L;

    //region Properties
    @JsonProperty("uuid")
    private String uuid;
    //endregion

    //region Constructors
    public AbstractEsDocument() {
        // Default constructor
    }

    public AbstractEsDocument(final String uuid) {
        this.uuid = uuid;
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
                .append(uuid, that.uuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("uuid", uuid)
                .toString();
    }
    //endregion

    //region Properties getters and setters
    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }
    //endregion
}

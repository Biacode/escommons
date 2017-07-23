package com.biacode.escommons.core.model.response;

import com.biacode.escommons.core.model.document.AbstractEsDocument;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
public class DocumentsAndTotalCount<T extends AbstractEsDocument> implements Serializable {
    private static final long serialVersionUID = 3178283910546145486L;

    //region Properties
    private transient List<T> documents;

    private long totalCount;
    //endregion

    //region Constructors
    public DocumentsAndTotalCount() {
        // Default constructor
    }

    public DocumentsAndTotalCount(final List<T> documents, final long totalCount) {
        this.documents = documents;
        this.totalCount = totalCount;
    }
    //endregion

    //region Equals, HashCode and ToString
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentsAndTotalCount)) {
            return false;
        }
        final DocumentsAndTotalCount<?> that = (DocumentsAndTotalCount<?>) o;
        return new EqualsBuilder()
                .append(totalCount, that.totalCount)
                .append(documents, that.documents)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(documents)
                .append(totalCount)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("documents", documents)
                .append("totalCount", totalCount)
                .toString();
    }
    //endregion

    //region Properties getters and setters
    public List<T> getDocuments() {
        return documents;
    }

    public void setDocuments(final List<T> documents) {
        this.documents = documents;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(final long totalCount) {
        this.totalCount = totalCount;
    }
    //endregion
}

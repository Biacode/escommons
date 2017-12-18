package org.biacode.escommons.api.model.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arthur Asatryan.
 * Date: 7/18/17
 * Time: 2:03 PM
 */
public class EsCommonsResultResponse<T extends EsCommonsResponse> {

    //region Properties
    @JsonProperty("response")
    private T response;

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("errors")
    private Map<String, Object> errors;
    //endregion

    //region Constructors
    public EsCommonsResultResponse() {
        this.errors = new HashMap<>();
        this.success = true;
    }

    public EsCommonsResultResponse(final T response) {
        this();
        this.response = response;
    }

    public EsCommonsResultResponse(final Map<String, Object> errors) {
        this.success = false;
        this.errors = errors;
    }
    //endregion

    //region Public methods
    public boolean hasErrors() {
        return !errors.isEmpty();
    }
    //endregion

    //region Equals, HashCode and ToString
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EsCommonsResultResponse)) {
            return false;
        }
        final EsCommonsResultResponse<?> that = (EsCommonsResultResponse<?>) o;
        return new EqualsBuilder()
                .append(success, that.success)
                .append(response, that.response)
                .append(errors, that.errors)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(response)
                .append(success)
                .append(errors)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("response", response)
                .append("success", success)
                .append("errors", errors)
                .toString();
    }
    //endregion

    //region Properties getters and setters
    public T getResponse() {
        return response;
    }

    public void setResponse(final T response) {
        this.response = response;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(final boolean success) {
        this.success = success;
    }

    public Map<String, Object> getErrors() {
        return errors;
    }

    public void setErrors(final Map<String, Object> errors) {
        this.success = false;
        this.errors = errors;
    }
    //endregion
}

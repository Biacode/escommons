package org.biacode.escommons.core.component.impl;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.biacode.escommons.core.model.document.AbstractEsDocument;

/**
 * Created by Arthur Asatryan.
 * Date: 7/14/17
 * Time: 2:57 PM
 */
public class Person extends AbstractEsDocument {
    private static final long serialVersionUID = -4857552235251849532L;

    private String firstName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        final Person person = (Person) o;
        return new EqualsBuilder()
                .append(firstName, person.firstName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(firstName)
                .toHashCode();
    }
}

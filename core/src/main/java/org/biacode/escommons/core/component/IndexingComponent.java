package org.biacode.escommons.core.component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * Created by Arthur Asatryan.
 * Date: 7/10/17
 * Time: 6:11 PM
 */
public interface IndexingComponent {
    /**
     * Generates a new time-based index name for given original index's name,
     * creates an index with that new name,
     * sets up mappings for that new index (puts mappings)
     * and returns the new index's name
     *
     * @param originalIndex the original index
     * @param types         the types
     * @param settings      settings for index
     * @return new generated index name
     */
    @Nonnull
    String createIndexAndSetupMappings(@Nonnull final String originalIndex, @Nonnull final List<String> types, @Nullable final Map<String, Object> settings);

    /**
     * Creates alias with originalIndex's name which points to newIndex
     * <p>
     * If an alias was created successfully, then all other indexes starting with original index's name
     * are deleted (except for original index (the alias) and newIndex)
     *
     * @param originalIndex the original index
     * @param newIndex      the new index
     */
    void addAlias(@Nonnull final String originalIndex, @Nonnull final String newIndex);

    /**
     * remove unfinished index if re-indexation failed
     *
     * @param indexName the index name
     */
    void removeIndexByName(@Nonnull final String indexName);
}

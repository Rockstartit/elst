package edu.kit.elst.course_conceptualization;

import edu.kit.elst.core.Guards;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Getter
@Accessors(fluent = true)
@Table(name = "page_building_block_property_values")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class PageBuildingBlockPropertyValue {
    @EmbeddedId
    private final PageBuildingBlockPropertyId propertyId;

    @Column(name = "property_value")
    private String value;

    public PageBuildingBlockPropertyValue(PageBuildingBlockPropertyId propertyId) {
        Guards.notNull(propertyId, "propertyId");

        this.propertyId = propertyId;
        this.value = null;
    }

    public void set(String value) {
        this.value = value;
    }

    public String key() {
        return propertyId.key();
    }
}

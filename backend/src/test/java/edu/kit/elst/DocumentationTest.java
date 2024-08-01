package edu.kit.elst;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

import java.util.Set;

public class DocumentationTest {
    private final Set<String> coreModules = Set.of("Authorization", "Core", "Rest_api");

    @Test
    void createModuleDocumentation() {
        new Documenter(ApplicationModules.of(BackendApplication.class).verify()).writeDocumentation(
                Documenter.DiagramOptions.defaults()
                        .withComponentFilter(component -> !coreModules.contains(component.getName())),
                Documenter.CanvasOptions.defaults()
        );
    }
}

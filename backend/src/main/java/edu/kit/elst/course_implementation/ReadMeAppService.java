package edu.kit.elst.course_implementation;

import edu.kit.elst.building_blocks.BuildingBlockVersion;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class ReadMeAppService {
    private static final String defaultReadMeContent = """
    # ReadMe
        
    ## Getting Started
        
    Starte mit der Entwicklung des Bausteins, indem du eine Beschreibung hinzufügst und Entwickler einlädst.
    Je mehr Informationen zur Verfügung stehen, desto einfacher ist es die Komponente zu entwickeln.
        
    ## Issues
        
    Melde Probleme oder Anregungen für diesen Baustein.
        
    ## Anforderungen
        
    Spezifiziere das Verhalten der Komponente.
        
    ## Mockups
        
    Bekomme einen ersten Eindruck von einer möglichen Benutzeroberfläche.
        
    ## Diagramme
        
    Modelliere den Baustein.
        
    ## Diskussionen
        
    Tausche dich mit Mitwirkenden aus.
    """;

    private final ReadMeRepository readMeRepository;

    public void editReadMe(BuildingBlockVersion buildingBlockVersion, String content) {
        ReadMe readMe = readMeRepository.findById(buildingBlockVersion)
                .orElse(new ReadMe(buildingBlockVersion));

        readMe.data(content);

        readMeRepository.save(readMe);
    }

    public String getReadMe(BuildingBlockVersion buildingBlockVersion) {
        return readMeRepository.findById(buildingBlockVersion)
                .map(ReadMe::content)
                .orElse(defaultReadMeContent);
    }
}

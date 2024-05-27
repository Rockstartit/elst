package edu.kit.elst.building_blocks;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    public void editReadMe(BuildingBlockId buildingBlockId, String content) {
        ReadMe readMe = readMeRepository.findById(buildingBlockId)
                .orElse(new ReadMe(buildingBlockId));

        readMe.data(content);

        readMeRepository.save(readMe);
    }

    public String getReadMe(BuildingBlockId buildingBlockId) {
        return readMeRepository.findById(buildingBlockId)
                .map(ReadMe::content)
                .orElse(defaultReadMeContent);
    }
}

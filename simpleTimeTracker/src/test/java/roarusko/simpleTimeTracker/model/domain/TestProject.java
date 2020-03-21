package roarusko.simpleTimeTracker.model.domain;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import roarusko.simpleTimeTracker.model.domain.Project;

/**
 * Testaillaan Project-luokkaa muutamilla testeillä. Luokka sisältää vain
 * gettereitä ja settereitä, joiden testaaminen on jokseenkin hyödytöntä.
 * @author aleks
 * @version 21 Mar 2020
 *
 */
public class TestProject {
    
    /**
     * Testaillaan Project-luokkaa
     */
    @Test
    public void TestProject1() {
        Project project = new Project(123, "Project1");
        
        assertEquals(-1, project.getId());
        assertEquals(123, project.getOwnerId());
        assertEquals("Project1", project.getName());
        
        project = new Project(123, 234, "Project2");
        
        assertEquals(123, project.getId());
        assertEquals(234, project.getOwnerId());
        assertEquals("Project2", project.getName());
        
        project = new Project(123, "Project1");
        
        project.setId(444);
        project.setName("asdasd");
        
        assertEquals(444, project.getId());
        assertEquals(123, project.getOwnerId());
        assertEquals("asdasd", project.getName());
        
        // ei pitäisi onnistua
        project.setId(123);
        assertEquals(444, project.getId());
    }
}

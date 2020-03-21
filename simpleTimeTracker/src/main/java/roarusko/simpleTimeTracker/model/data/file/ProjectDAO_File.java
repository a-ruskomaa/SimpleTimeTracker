package roarusko.simpleTimeTracker.model.data.file;

import java.util.List;

import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.domain.User;

/**
 * Projekteja tiedostoon tallentava ja lukeva luokka. Tiedostojen käsittely toteutetaan abstraktin yläluokan
 * tarjoamilla metodeilla. Luokka sisältää tarvittavan logiikan, jolla merkkijonoista saadaan luotua Project-tyyppisiä
 * olioita ja Project-olioista merkkijonoja.
 * @author aleks
 * @version 2 Mar 2020
 *
 */
public class ProjectDAO_File extends AbstractChildDAO_File<Project, User> {
    private static final String defaultPath = "data/projects.dat";

    /**
     * Luo uuden ProjectDAO:n oletusarvoisella tallennustiedostolla
     */
    public ProjectDAO_File() {
        super(defaultPath);
    }
    
    
    /**
     * Luo uuden ProjectDAO annetulla tallennustiedostolla
     * @param pathToFile Merkkijonomuotoinen polku haluttuun tallennustiedostoon
     * 
     */
    public ProjectDAO_File(String pathToFile) {
        super(pathToFile);
    }

    
    @Override
    public List<Project> list(User user) {
        return super.list(user);
    }

    @Override
    protected Project parseObject(String row) {
        String[] parts = row.split(",");
        try {
            return new Project(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2]);
        } catch (NullPointerException e) {
            System.out.println("Not enough fields" + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Wrong input type" + e.getMessage());
        }
        return null;
    }


    @Override
    protected String objectToStringForm(Project project) {
        return String.format("%d,%d,%s", project.getId(), project.getOwnerId(), project.getName());
    }


}

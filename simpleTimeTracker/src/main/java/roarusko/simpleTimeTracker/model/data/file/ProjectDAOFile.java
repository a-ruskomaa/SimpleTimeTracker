package roarusko.simpleTimeTracker.model.data.file;

import java.util.List;

import roarusko.simpleTimeTracker.model.data.ProjectDAO;
import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.domain.User;

public class ProjectDAOFile extends AbstractDAOFile<Project> implements ProjectDAO {
    

    public ProjectDAOFile() {
        super("projects.dat");
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

package fxTyoaika.model.dataAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fxTyoaika.SampleData;
import fxTyoaika.model.Entry;
import fxTyoaika.model.IdGenerator;
import fxTyoaika.model.Project;
import fxTyoaika.model.User;

public class ProjectDAO extends AbstractDAO<Project> {
    
    @Override
    public Project create(Project object) {
        ArrayList<Project> projects = (ArrayList<Project>) SampleData.getProjects();
        
        Project project = new Project(IdGenerator.getNewProjectId(), object.getName(), object.getOwner(), object.getEntries());
        
        projects.add(project);
        
        return project;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Project> getData() {
        return (ArrayList<Project>) SampleData.getData(Project.class);
    }
    
    @Override
    public List<Project> list() {
        return list(Project.class);
    }
    
    public List<Project> list(User user) {
        return list(Project.class, user);
    }
}

package fxTyoaika.model.dataAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import fxTyoaika.SampleData;
import fxTyoaika.model.IdGenerator;
import fxTyoaika.model.Project;
import fxTyoaika.model.User;

public class UserDAO extends AbstractDAO<User> {

    @Override
    
    public User create(User object) {
        ArrayList<User> users = (ArrayList<User>) SampleData.getUsers();
        
        User user = new User(IdGenerator.getNewUserId(), object.getName(), object.getProjects());
        
        users.add(user);
        
        return user;
    }

    @Override
    public List<User> getData() {
        // TODO Auto-generated method stub
        return (ArrayList<User>) SampleData.getData(User.class);
    }
    
    public List<User> list() {
        return list(User.class);
    }

}
